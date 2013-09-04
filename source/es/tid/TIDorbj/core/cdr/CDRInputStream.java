/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 2 $
* Date: $Date: 2005-12-19 08:58:21 +0100 (Mon, 19 Dec 2005) $
* Last modified by: $Author: caceres $
*
* (C) Copyright 2004 Telefónica Investigación y Desarrollo
*     S.A.Unipersonal (Telefónica I+D)
*
* Info about members and contributors of the MORFEO project
* is available at:
*
*   http://www.morfeo-project.org/TIDorbJ/CREDITS
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
*
* If you want to use this software an plan to distribute a
* proprietary application in any way, and you are not licensing and
* distributing your source code under GPL, you probably need to
* purchase a commercial license of the product.  More info about
* licensing options is available at:
*
*   http://www.morfeo-project.org/TIDorbJ/Licensing
*/    
package es.tid.TIDorbj.core.cdr;

import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.ValueFactory;

import es.tid.TIDorbj.core.AnyImpl;
import es.tid.TIDorbj.core.ContextImpl;
import es.tid.TIDorbj.core.ObjectImpl;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.comm.giop.GIOPFragmentMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPHeader;
import es.tid.TIDorbj.core.comm.giop.GIOPVersion;
import es.tid.TIDorbj.core.comm.iiop.IIOPCommunicationLayer;
import es.tid.TIDorbj.core.comm.iiop.IIOPCommunicationLayerPropertiesInfo;
import es.tid.TIDorbj.core.iop.DefaultIOR;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.typecode.TypeCodeMarshaler;
import es.tid.TIDorbj.core.util.FixedHolder;

/**
 * Portable InputStream for demarshalling.
 * <p>
 * A <code>CDRInputStream</code> has an buffer iterator
 * <code>CDRIterator</code>. The iterator maintains the data position and
 * correct aligment, and <code>TypeCode</code> marshaling information.
 * 
 * @see es.tid.TIDorbj.core.cdr.CDROutputStream
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class CDRInputStream extends org.omg.CORBA_2_3.portable.InputStream
    implements org.omg.CORBA.DataInputStream
{

    /**
     * ORB instance that created the input stream.
     */

    TIDORB m_orb;

    /**
     * GIOPVersion used.
     */

    GIOPVersion m_version;

    /**
     * The buffer is a GIOP message. A GIOP Message implies that the GIOP Header
     * space will be reserved in the BufferCDR, that is, each ChunkCDR will have
     * the header size bytes allocated.
     */

    boolean m_is_message;

    /**
     * Buffer iterator.
     */

    protected IteratorCDR m_iterator;

    /**
     * Current chunk buffer.
     */

    protected byte[] m_chunk_buffer;

    /**
     * Data buffer for Broken data splitted in to chunks in an encapsulations
     */

    protected byte[] m_data_buffer = null;

    protected static javax.rmi.CORBA.ValueHandler st_value_handler = null;
    

    public CDRInputStream(TIDORB orb, byte[] buffer)
    {
        this(orb, new IteratorCDR(new BufferCDR(buffer)));
    }

    public CDRInputStream(TIDORB orb, BufferCDR buffer)
    {
        this(orb, new IteratorCDR(buffer));
    }

    protected CDRInputStream(TIDORB orb, IteratorCDR iterator) {
        this.m_orb = orb;

        if ( orb != null ){
            this.m_version = GIOPVersion.fromString (
                m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
                .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.GIOP_VERSION )
                .getValue()
            ); 
        } else {
            this.m_version = GIOPVersion.fromString(
                IIOPCommunicationLayerPropertiesInfo.DEFAULT_GIOP_VERSION
            );
        }

        this.m_is_message = false;
        this.m_iterator = iterator;

        m_chunk_buffer = iterator.m_chunk.m_buffer;
        m_data_buffer = new byte[CDR.LONGLONG_SIZE];
    }

    // DataInputStream
    static String[] st_data_input_ids = 
    	{ "IDL:omg.org/CORBA/DataInputStream:1.0" };

    public String[] _truncatable_ids()
    {
        return st_data_input_ids;
    }

    /**
     * @return the GIOP version used to marshal.
     */
    public GIOPVersion getVersion()
    {
        return m_version;
    }

    /**
     * Sets the GIOP version used to marshal.
     */
    public void setVersion(GIOPVersion ver)
    {
        m_version = ver;
    }

    /**
     * @return <code>true</code> if the stream is a GIOP message.
     */
    public boolean isMessage()
    {
        return m_is_message;
    }

    /**
     * Sets if the stream is a GIOP message.
     */
    public void setMessage(boolean is)
    {
        m_is_message = is;

        if ((is) && (m_iterator.m_current_chunk_num == 0)
            && (m_iterator.m_position == 0))
            skip(es.tid.TIDorbj.core.comm.giop.GIOPHeader.HEADER_SIZE);
    }

    public org.omg.CORBA.ORB orb()
    {
        if (m_orb != null)
            return m_orb;
        else
            return es.tid.TIDorbj.core.TIDORB.init();
    }

    /**
     * Sets the stream byte order.
     * 
     * @param byte_order
     *            if <code>true</code> little-endian, else big-endian
     */
    public void setByteOrder(boolean byte_order)
    {
        m_iterator.setByteOrder(byte_order);
    }

    /**
     * @return the stream byte order, if <code>true</code> little-endian, else
     *         big-endian
     */
    public boolean getByteOrder()
    {
        return m_iterator.getByteOrder();
    }

    /**
     * @return the stream buffer
     */
    public BufferCDR getBuffer()
    {
        return m_iterator.getBuffer();
    }

    /**
     * @return the Encapsulation Context
     */
    public ContextCDR getContextCDR()
    {
        return m_iterator.m_context;
    }

    /**
     * Creates a new Encapsulation Context with it own aligment an indirections.
     */
    public void enterEncapsulation()
    {
        skipUlong(); // skip encapsulation size
        // sets the real encapsulation start point
        alignment(CDR.OCTET_SIZE);
        m_iterator.enterEncapsulation();
        //sets the byte order
        m_iterator.setByteOrder(read_boolean());
    }

    /**
     * Close the Encapsulation Context, and restore the father Context.
     * 
     * @see es.tid.TIDorbj.core.cdr.ContextCDR
     */
    public void exitEncapsulation()
    {
        m_iterator.exitEncapsulation();
    }

    /**
     * Extract from the buffer an encapsulation, creating a new bufferCDR
     * containing the octect sequence.
     * 
     * @return a new CDRInputStream for reading the encapsulation.
     * @see es.tid.TIDorbj.core.cdr.ContextCDR
     */
    public Encapsulation readEncapsulation()
    {
        return Encapsulation.read(this, m_version);
    }

    public void skipEncapsulation()
    {
        int encapsulation_size = read_ulong();
        skipOctetArray(encapsulation_size);
    }

    /**
     * Sets the aligment offset in the current Context.
     * 
     * @param pos
     *            the new offset
     * @see es.tid.TIDorbj.core.cdr.AligmentOffset
     */
    public void set_alignment_offset(int pos)
    {
        m_iterator.setAlignmentOffset(pos);
    }

    /**
     * @return the absolute value in the buffer order of the current position.
     */
    public AbsolutePosition getAbsolutePosition()
    {
        return m_iterator.getPointer().getAbsolutePosition();
    }

    /**
     * @return the current position in the buffer.
     */

    public PointerCDR getPointer()
    {
        return m_iterator.getPointer();
    }

    /**
     * Jumps to the absolute position.
     * 
     * @pram position the new position
     */
    public void setPosition(AbsolutePosition position)
    {
        m_iterator.setPosition(position);
    }

    /**
     * @return a new inputstream pointing the same position.
     */
    public CDRInputStream copy()
    {
        CDRInputStream stream = new CDRInputStream(m_orb, m_iterator.copy());
        stream.setMessage(m_is_message);
        stream.setVersion(m_version);
        return stream;
    }

    public boolean read_boolean()
    {
        alignment(CDR.BOOLEAN_SIZE);
        return ((m_chunk_buffer[m_iterator.m_position++] == 0) ? false : true);
    }

    public void skipBoolean()
    {
        alignment(CDR.BOOLEAN_SIZE);
        m_iterator.m_position++;
    }

    public char read_char()
    {
        // WARNING: Java char is 2 bytes long, there is a cast
        //          from byte to char
        alignment(CDR.CHAR_SIZE);
        return (char) (0xff & m_chunk_buffer[m_iterator.m_position++]);
    }

    public void skipChar()
    {
        // WARNING: Java char is 2 bytes long, there is a cast
        //          from byte to char
        alignment(CDR.CHAR_SIZE);
        m_iterator.m_position++;
    }

    public char read_wchar()
    {

        // WARNING: Java char is 2 bytes long, there is a cast
        //          from byte to char
        if (alignment(CDR.WCHAR_SIZE)) {
            if (m_iterator.m_byte_order)
                return (char) ((m_chunk_buffer[m_iterator.m_position++] & 0xff)
                    | ((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 8));
            else
                return (char) 
                	(((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 8) 
                	    | (m_chunk_buffer[m_iterator.m_position++] & 0xff));
        } else {
            readOctetArrayAux(m_data_buffer, 0, CDR.WCHAR_SIZE);
            if (m_iterator.m_byte_order) {
                return (char) ((m_data_buffer[0] & 0xff) 
                    | ((m_data_buffer[1] & 0xff) << 8));
            } else {
                return (char) (((m_data_buffer[0] & 0xff) << 8) 
                    | (m_data_buffer[1] & 0xff));
            }
        }
    }

    public void skipWchar()
    {
        // WARNING: Java char is 2 bytes long, there is a cast
        //          from byte to char
        if (alignment(CDR.WCHAR_SIZE)) {
            m_iterator.m_position += CDR.WCHAR_SIZE;
        } else {
            skip(CDR.WCHAR_SIZE);
        }
    }

    public byte read_octet()
    {
        alignment(CDR.OCTET_SIZE);
        return m_chunk_buffer[m_iterator.m_position++];
    }

    public void skipOctet()
    {
        alignment(CDR.OCTET_SIZE);
        m_iterator.m_position++;
    }

    public short read_short()
    {
        if (alignment(CDR.SHORT_SIZE)) {
            if (m_iterator.m_byte_order)
                return (short) 
                	((m_chunk_buffer[m_iterator.m_position++] & 0xff)
                	    | ((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 8));
            else
                return (short) 
                	(((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 8) 
                	    | (m_chunk_buffer[m_iterator.m_position++] & 0xff));
        } else {
            readOctetArrayAux(m_data_buffer, 0, CDR.SHORT_SIZE);
            if (m_iterator.m_byte_order) {
                return (short) ((m_data_buffer[0] & 0xff)
                    | ((m_data_buffer[1] & 0xff) << 8));
            } else {
                return (short) (((m_data_buffer[0] & 0xff) << 8)
                    | (m_data_buffer[1] & 0xff));
            }
        }
    }

    public void skipShort()
    {
        if (alignment(CDR.SHORT_SIZE)) {
            m_iterator.m_position += CDR.SHORT_SIZE;
        } else {
            skip(CDR.SHORT_SIZE);
        }
    }

    /**
     * JAVA MAPPING: "negative" short = ushort values will be handled by the
     * user
     */
    public short read_ushort()
    {
        if (alignment(CDR.SHORT_SIZE)) {
            if (m_iterator.m_byte_order)
                return (short) ((m_chunk_buffer[m_iterator.m_position++] & 0xff)
                    | ((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 8));
            else
                return (short) 
                	(((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 8) 
                	    | (m_chunk_buffer[m_iterator.m_position++] & 0xff));
        } else {
            readOctetArrayAux(m_data_buffer, 0, CDR.USHORT_SIZE);
            if (m_iterator.m_byte_order) {
                return (short) ((m_data_buffer[0] & 0xff) 
                    | ((m_data_buffer[1] & 0xff) << 8));
            } else {
                return (short) (((m_data_buffer[0] & 0xff) << 8)
                    | (m_data_buffer[1] & 0xff));
            }
        }
    }

    public void skipUshort()
    {
        if (alignment(CDR.USHORT_SIZE)) {
            m_iterator.m_position += CDR.USHORT_SIZE;
        } else {
            skip(CDR.USHORT_SIZE);
        }
    }

    public int read_long()
    {
        if (alignment(CDR.LONG_SIZE)) {
            if (m_iterator.m_byte_order) {
                return 
                	(m_chunk_buffer[m_iterator.m_position++] & 0xff)
                	| ((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 8)
                	| ((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 16)
                	| ((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 24);
            } else {
                return 
                	((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 24)
                	| ((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 16)
                	| ((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 8)
                	| (m_chunk_buffer[m_iterator.m_position++] & 0xff);
            }
        } else {
            readOctetArrayAux(m_data_buffer, 0, CDR.LONG_SIZE);
            if (m_iterator.m_byte_order) {
                return (m_data_buffer[0] & 0xff)
                       | ((m_data_buffer[1] & 0xff) << 8)
                       | ((m_data_buffer[2] & 0xff) << 16)
                       | ((m_data_buffer[3] & 0xff) << 24);
            } else {
                return ((m_data_buffer[0] & 0xff) << 24)
                       | ((m_data_buffer[1] & 0xff) << 16)
                       | ((m_data_buffer[2] & 0xff) << 8)
                       | (m_data_buffer[3] & 0xff);
            }
        }
    }

    public void skipLong()
    {
        if (alignment(CDR.LONG_SIZE)) {
            m_iterator.m_position += CDR.LONG_SIZE;
        } else {
            skip(CDR.LONG_SIZE);
        }
    }

    /**
     * JAVA MAPPING: "negative" int = positive ulong values will be handled by
     * the user
     */
    public int read_ulong()
    {
        if (alignment(CDR.ULONG_SIZE)) {
            if (m_iterator.m_byte_order) {
                return 
                	(m_chunk_buffer[m_iterator.m_position++] & 0xff)
                	| ((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 8)
                	| ((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 16)
                	| ((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 24);
            } else {
                return 
                	((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 24)
                	| ((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 16)
                	| ((m_chunk_buffer[m_iterator.m_position++] & 0xff) << 8)
                	| (m_chunk_buffer[m_iterator.m_position++] & 0xff);
            }
        } else {
            readOctetArrayAux(m_data_buffer, 0, CDR.ULONG_SIZE);
            if (m_iterator.m_byte_order) {
                return (m_data_buffer[0] & 0xff)
                       | ((m_data_buffer[1] & 0xff) << 8)
                       | ((m_data_buffer[2] & 0xff) << 16)
                       | ((m_data_buffer[3] & 0xff) << 24);
            } else {
                return ((m_data_buffer[0] & 0xff) << 24)
                       | ((m_data_buffer[1] & 0xff) << 16)
                       | ((m_data_buffer[2] & 0xff) << 8)
                       | (m_data_buffer[3] & 0xff);
            }
        }
    }

    public void skipUlong()
    {
        if (alignment(CDR.ULONG_SIZE)) {
            m_iterator.m_position += CDR.ULONG_SIZE;
        } else {
            skip(CDR.ULONG_SIZE);
        }
    }

    public long read_longlong()
    {
        if (alignment(CDR.LONGLONG_SIZE)) {
            if (m_iterator.m_byte_order) {
              return
              ((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)<< 8)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)<< 16)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)<< 24)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)<< 32)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)<< 40)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)<< 48)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)<< 56);

            } else {
              return 
              (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL) << 56)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL) << 48)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL) << 40)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL) << 32)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL) << 24)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL) << 16)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL) << 8)
              | ((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL);
            }	
        } else {
            readOctetArrayAux(m_data_buffer, 0, CDR.LONGLONG_SIZE);
            if (m_iterator.m_byte_order) {
                return ((long) m_data_buffer[0] & 0xffL)
                       | (((long) m_data_buffer[1] & 0xffL) << 8)
                       | (((long) m_data_buffer[2] & 0xffL) << 16)
                       | (((long) m_data_buffer[3] & 0xffL) << 24)
                       | (((long) m_data_buffer[4] & 0xffL) << 32)
                       | (((long) m_data_buffer[5] & 0xffL) << 40)
                       | (((long) m_data_buffer[6] & 0xffL) << 48)
                       | (((long) m_data_buffer[7] & 0xffL) << 56);
            } else {
                return (((long) m_data_buffer[0] & 0xffL) << 56)
                       | (((long) m_data_buffer[1] & 0xffL) << 48)
                       | (((long) m_data_buffer[2] & 0xffL) << 40)
                       | (((long) m_data_buffer[3] & 0xffL) << 32)
                       | (((long) m_data_buffer[4] & 0xffL) << 24)
                       | (((long) m_data_buffer[5] & 0xffL) << 16)
                       | (((long) m_data_buffer[6] & 0xffL) << 8)
                       | ((long) m_data_buffer[7] & 0xffL);
            }
        }
    }

    public void skip_longlong()
    {
        if (alignment(CDR.LONGLONG_SIZE)) {
            m_iterator.m_position += CDR.LONGLONG_SIZE;
        } else {
            skip(CDR.LONGLONG_SIZE);
        }
    }

    /**
     * JAVA MAPPING: "negative" longlong = positive ulonglong values will be
     * handled by the user
     */
    public long read_ulonglong()
    {
        if (alignment(CDR.ULONGLONG_SIZE)) {
            if (m_iterator.m_byte_order) {
              return 
              ((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)<< 8)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)<< 16)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)<< 24)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)<< 32)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)<< 40)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)<< 48)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL)<< 56);

            } else {
              return 
              (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL) << 56)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL) << 48)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL) << 40)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL) << 32)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL) << 24)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL) << 16)
              | (((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL) << 8)
              | ((long) m_chunk_buffer[m_iterator.m_position++] & 0xffL);
            }	
        } else {
            readOctetArrayAux(m_data_buffer, 0, CDR.ULONGLONG_SIZE);
            if (m_iterator.m_byte_order) {
                return ((long) m_data_buffer[0] & 0xffL)
                       | (((long) m_data_buffer[1] & 0xffL) << 8)
                       | (((long) m_data_buffer[2] & 0xffL) << 16)
                       | (((long) m_data_buffer[3] & 0xffL) << 24)
                       | (((long) m_data_buffer[4] & 0xffL) << 32)
                       | (((long) m_data_buffer[5] & 0xffL) << 40)
                       | (((long) m_data_buffer[6] & 0xffL) << 48)
                       | (((long) m_data_buffer[7] & 0xffL) << 56);
            } else {
                return (((long) m_data_buffer[0] & 0xffL) << 56)
                       | (((long) m_data_buffer[1] & 0xffL) << 48)
                       | (((long) m_data_buffer[2] & 0xffL) << 40)
                       | (((long) m_data_buffer[3] & 0xffL) << 32)
                       | (((long) m_data_buffer[4] & 0xffL) << 24)
                       | (((long) m_data_buffer[5] & 0xffL) << 16)
                       | (((long) m_data_buffer[6] & 0xffL) << 8)
                       | ((long) m_data_buffer[7] & 0xffL);
            }
        }
    }

    public void skipUlonglong()
    {
        if (alignment(CDR.ULONGLONG_SIZE)) {
            m_iterator.m_position += CDR.ULONGLONG_SIZE;
        } else {
            skip(CDR.ULONGLONG_SIZE);
        }
    }

    public float read_float()
    {
        return java.lang.Float.intBitsToFloat(read_long());
    }

    public void skipFloat()
    {
        skipLong();
    }

    public double read_double()
    {
        return java.lang.Double.longBitsToDouble(read_longlong());
    }

    public void skipDouble()
    {
        skip_longlong();
    }

    public String read_string()
    {
        // read the string length, including the end of string '\0' character
        int length = read_ulong();

        if (length <= 0)
            throw new MARSHAL("read_string: invalid string  length: " + length,
                              0, CompletionStatus.COMPLETED_NO);

        if (length == 1) {
            skipChar(); //skip the null character
            return "";
        } else {
            char[] array = new char[length];
            readCharArrayAux(array, 0, length);
            //skip the null character
            return new String(array, 0, length - 1);
        }
    }

    public void skipString()
    {
        // read the string length, including the end of string '\0' character
        int length = read_ulong();

        if (length <= 0)
            throw new MARSHAL("skip_string: invalid string  length: " + length,
                              0, CompletionStatus.COMPLETED_NO);

        skip(length);
    }

    public String read_wstring()
    {
        // read the string length, in wstring there is a null '\0' character at
        // the end

        int length = read_ulong();

        // the length is the number of bytes in the wide-string
        int real_length = 0;

        if (m_version == GIOPVersion.VERSION_1_2) {
            real_length = (length / CDR.WCHAR_SIZE) - 1;

        } else if(m_version == GIOPVersion.VERSION_1_1) {
            real_length = length - 1;
        } else {
            throw new MARSHAL("GIOP 1.0 does not supports wstring", 
                              6, 
                              CompletionStatus.COMPLETED_NO);
        }

        if (real_length < 0)
            throw new MARSHAL(
                              "read_wstring: invalid string  length: " + length,
                              0, CompletionStatus.COMPLETED_NO);

        if (real_length == 0) {
            skipWchar();
            return "";
        } else {
            char[] array = new char[real_length];
            read_wchar_array(array, 0, real_length);
            skipWchar(); //  skiping the '\0'
            return new String(array, 0, real_length);
        }
    }

    public void skipWstring()
    {
        // read the string length, in bytes, not including the end of string
        // '\0' character
        int length = read_ulong();
        skipOctetArray(length);
    }

    public void read_boolean_array(boolean[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null boolean array reference.", 0,
                                CompletionStatus.COMPLETED_NO);

        int booleans_can_read = 0;

        // número de octetos que quedan por leer en el chunk actual

        int remain_booleans = length;

        int position = 0;
        int stop_at = 0;

        while (remain_booleans > 0) {

            position = m_iterator.m_position;

            booleans_can_read = java.lang.Math.min(remain_booleans,
                                                   m_iterator.available());

            stop_at = position + booleans_can_read;

            while (position < stop_at) {
                value[offset++] = (m_chunk_buffer[position++] == 0) ? false
                    : true;
            }

            m_iterator.m_position = stop_at;
            remain_booleans -= booleans_can_read;

            if (remain_booleans > 0) {
                getNextChunk();
            }
        }
    }

    public void skipBooleanArray(int length)
    {
        skip(length);
    }

    public void read_char_array(char[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null byte array reference.", 0,
                                CompletionStatus.COMPLETED_NO);

        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        readCharArrayAux(value, offset, length);
    }

    public void readCharArrayAux(char[] value, int offset, int length)
    {
        int chars_can_read = 0;
        int remain_chars = length;
        int position = 0;
        int stop_at = 0;

        while (remain_chars > 0) {
            // calculates how many bytes the actual chunk has got.
            chars_can_read = java.lang.Math.min(remain_chars,
                                                m_iterator.available());

            position = m_iterator.m_position;

            stop_at = position + chars_can_read;

            while (position < stop_at)
                value[offset++] = (char) (m_chunk_buffer[position++] & 0xff);

            m_iterator.m_position = position;
            remain_chars -= chars_can_read;

            if (remain_chars > 0)
                getNextChunk();
        }
    }

    public void skipCharArray(int length)
    {
        skip(length);
    }

    public void read_wchar_array(char[] value, int offset, int length)
    {

        if (value == null)
            throw new BAD_PARAM("Null wchar array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        // número de datos wchar que se pueden leer enteros
        int wchars_can_read = 0;

        int position = 0;

        // numero de wchars que quedan
        int remain_wchars = length;

        while (remain_wchars > 0) {

            if (!alignment(CDR.WCHAR_SIZE)) { // encapsulación, el dato no cabe
                                              // completo
                readOctetArrayAux(m_data_buffer, 0, CDR.WCHAR_SIZE);

                if (m_iterator.m_byte_order) {
                    value[offset++] = (char) ((m_data_buffer[0] & 0xff) 
                        | ((m_data_buffer[1] & 0xff) << 8));
                } else {
                    value[offset++] = (char) (((m_data_buffer[0] & 0xff) << 8)
                        | (m_data_buffer[1] & 0xff));
                }

                remain_wchars--;

            } else {
                position = m_iterator.m_position;

                wchars_can_read = java.lang.Math
                                                .min(
                                                     remain_wchars,
                                                     m_iterator.available()
                                                                                                                                                                                                                    / CDR.WCHAR_SIZE);

                for (int i = wchars_can_read; i > 0; i--) {
                    if (m_iterator.m_byte_order)
                        value[offset++] = 
                            (char) ((m_chunk_buffer[position++] & 0xff)
                                | ((m_chunk_buffer[position++] & 0xff) << 8));
                    else
                        value[offset++] = 
                            (char) (((m_chunk_buffer[position++] & 0xff) << 8)
                                | (m_chunk_buffer[position++] & 0xff));
                }

                remain_wchars -= wchars_can_read;
                m_iterator.m_position = position;

                if (remain_wchars > 0) {
                    getNextChunk();
                }
            }
        }
    }

    public void skipWcharArray(int length)
    {
        if (length > 0) {
            alignment(CDR.WCHAR_SIZE);
            skip(length * CDR.WCHAR_SIZE);
        }
    }

    public void read_octet_array(byte[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null byte array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        readOctetArrayAux(value, offset, length);
    }

    public void readOctetArrayAux(byte[] value, int offset, int length)
    {
        int to_read, how_many = length;

        while (how_many > 0) {

            // calculates how many bytes the actual chunk has got.
            to_read = java.lang.Math.min(how_many, m_iterator.available());

            System.arraycopy(m_chunk_buffer, m_iterator.m_position, value,
                             offset, to_read);

            offset += to_read;
            m_iterator.m_position += to_read;
            how_many -= to_read;

            if (how_many > 0)
                getNextChunk();

        }
    }

    public void skipOctetArray(int length)
    {
        skip(length);
    }

    public void read_short_array(short[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null short array reference.", 0,
                                CompletionStatus.COMPLETED_NO);

        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        // número de datos short que se pueden leer enteros
        int shorts_can_read = 0;

        int position = 0;

        // numero de shorts que quedan
        int remain_shorts = length;

        while (remain_shorts > 0) {

            if (!alignment(CDR.SHORT_SIZE)) { // encapsulación, el dato no cabe
                                              // completo
                readOctetArrayAux(m_data_buffer, 0, CDR.SHORT_SIZE);

                if (m_iterator.m_byte_order) {
                    value[offset++] = (short) ((m_data_buffer[0] & 0xff)
                        | ((m_data_buffer[1] & 0xff) << 8));
                } else {
                    value[offset++] = (short) (((m_data_buffer[0] & 0xff) << 8)
                        | (m_data_buffer[1] & 0xff));
                }

                remain_shorts--;

            } else {
                position = m_iterator.m_position;

                shorts_can_read = 
                    java.lang.Math.min(remain_shorts,
                                       m_iterator.available()
                                                                                                                                                                                                                    / CDR.SHORT_SIZE);

                for (int i = shorts_can_read; i > 0; i--) {
                    if (m_iterator.m_byte_order)
                        value[offset++] = 
                            (short) ((m_chunk_buffer[position++] & 0xff) 
                                | ((m_chunk_buffer[position++] & 0xff) << 8));
                    else
                        value[offset++] = 
                            (short) (((m_chunk_buffer[position++] & 0xff) << 8)
                                | (m_chunk_buffer[position++] & 0xff));
                }

                remain_shorts -= shorts_can_read;
                m_iterator.m_position = position;

                if (remain_shorts > 0) {
                    getNextChunk();
                }
            }
        }
    }

    public void skipShortArray(int length)
    {
        skipArray(length, CDR.SHORT_SIZE);
    }

    public void read_ushort_array(short[] value, int offset, int length)
    {
        read_short_array(value, offset, length);
    }

    public void skipUshortArray(int length)
    {
        skipArray(length, CDR.USHORT_SIZE);
    }

    public void read_long_array(int[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null int array reference.", 0,
                                CompletionStatus.COMPLETED_NO);

        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        // número de datos int que se pueden leer enteros
        int ints_can_read = 0;

        int position = 0;

        // numero de ints que quedan
        int remain_ints = length;

        while (remain_ints > 0) {

            if (!alignment(CDR.LONG_SIZE)) {// encapsulación, el dato no cabe
                                            // completo

                readOctetArrayAux(m_data_buffer, 0, CDR.LONG_SIZE);

                if (m_iterator.m_byte_order) {
                    value[offset++] = (m_data_buffer[0] & 0xff)
                                      | ((m_data_buffer[1] & 0xff) << 8)
                                      | ((m_data_buffer[2] & 0xff) << 16)
                                      | ((m_data_buffer[3] & 0xff) << 24);
                } else {
                    value[offset++] = ((m_data_buffer[0] & 0xff) << 24)
                                      | ((m_data_buffer[1] & 0xff) << 16)
                                      | ((m_data_buffer[2] & 0xff) << 8)
                                      | (m_data_buffer[3] & 0xff);
                }

                remain_ints--;

            } else {
                position = m_iterator.m_position;

                ints_can_read = java.lang.Math
                                              .min(
                                                   remain_ints,
                                                   m_iterator.available()
                                                                                                                                                                                                            / CDR.LONG_SIZE);

                for (int i = ints_can_read; i > 0; i--) {
                    if (m_iterator.m_byte_order)
                        value[offset++] = 
                            (m_chunk_buffer[position++] & 0xff)
                            | ((m_chunk_buffer[position++] & 0xff) << 8)
                            | ((m_chunk_buffer[position++] & 0xff) << 16)
                            | ((m_chunk_buffer[position++] & 0xff) << 24);
                    else
                        value[offset++] = 
                            ((m_chunk_buffer[position++] & 0xff) << 24)
                            | ((m_chunk_buffer[position++] & 0xff) << 16)
                            | ((m_chunk_buffer[position++] & 0xff) << 8)
                            | (m_chunk_buffer[position++] & 0xff);
                }

                remain_ints -= ints_can_read;
                m_iterator.m_position = position;

                if (remain_ints > 0) {
                    getNextChunk();
                }
            }
        }
    }

    public void skipLongArray(int length)
    {
        skipArray(length, CDR.LONG_SIZE);
    }

    public void read_ulong_array(int[] value, int offset, int length)
    {
        read_long_array(value, offset, length);
    }

    public void skipUlongArray(int length)
    {
        skipArray(length, CDR.ULONG_SIZE);
    }

    public void read_longlong_array(long[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null long array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        // número de datos long que se pueden leer enteros
        int longs_can_read = 0;

        int position = 0;

        // numero de longs que quedan
        int remain_longs = length;

        while (remain_longs > 0) {

            if (!alignment(CDR.LONGLONG_SIZE)) { // encapsulación, el dato no
                                                 // cabe completo
                readOctetArrayAux(m_data_buffer, 0, CDR.LONGLONG_SIZE);

                if (m_iterator.m_byte_order) {
                    value[offset++] =
                        ((long) m_data_buffer[0] & 0xffL)
                        | (((long) m_data_buffer[1] & 0xffL) << 8)
                        | (((long) m_data_buffer[2] & 0xffL) << 16)
                        | (((long) m_data_buffer[3] & 0xffL) << 24)
                        | (((long) m_data_buffer[4] & 0xffL) << 32)
                        | (((long) m_data_buffer[5] & 0xffL) << 40)
                        | (((long) m_data_buffer[6] & 0xffL) << 48)
                        | (((long) m_data_buffer[7] & 0xffL) << 56);
                } else {
                    value[offset++] =
                        (((long) m_data_buffer[0] & 0xffL) << 56)
                        | (((long) m_data_buffer[1] & 0xffL) << 48)
                        | (((long) m_data_buffer[2] & 0xffL) << 40)
                        | (((long) m_data_buffer[3] & 0xffL) << 32)
                        | (((long) m_data_buffer[4] & 0xffL) << 24)
                        | (((long) m_data_buffer[5] & 0xffL) << 16)
                        | (((long) m_data_buffer[6] & 0xffL) << 8)
                        | ((long) m_data_buffer[7] & 0xffL);
                }

                remain_longs--;

            } else {
                position = m_iterator.m_position;

                longs_can_read = java.lang.Math
                                               .min(
                                                    remain_longs,
                                                    m_iterator.available()
                                                                                                                                                                                                                / CDR.LONGLONG_SIZE);

                for (int i = longs_can_read; i > 0; i--) {
                    if (m_iterator.m_byte_order)
                        value[offset++] = 
                          ((long) m_chunk_buffer[position++] & 0xffL)
                          | (((long) m_chunk_buffer[position++] & 0xffL) << 8)
                          | (((long) m_chunk_buffer[position++] & 0xffL) << 16)
                          | (((long) m_chunk_buffer[position++] & 0xffL) << 24)
                          | (((long) m_chunk_buffer[position++] & 0xffL) << 32)
                          | (((long) m_chunk_buffer[position++] & 0xffL) << 40)
                          | (((long) m_chunk_buffer[position++] & 0xffL) << 48)
                          | (((long) m_chunk_buffer[position++] & 0xffL) << 56);
                    else	
                        value[offset++] =
                          (((long) m_chunk_buffer[position++] & 0xffL) << 56)
                          | (((long) m_chunk_buffer[position++] & 0xffL) << 48)
                          | (((long) m_chunk_buffer[position++] & 0xffL) << 40)
                          | (((long) m_chunk_buffer[position++] & 0xffL) << 32)
                          | (((long) m_chunk_buffer[position++] & 0xffL) << 24)
                          | (((long) m_chunk_buffer[position++] & 0xffL) << 16)
                          | (((long) m_chunk_buffer[position++] & 0xffL) << 8)
                          | ((long) m_chunk_buffer[position++] & 0xffL);
                }	

                remain_longs -= longs_can_read;
                m_iterator.m_position = position;

                if (remain_longs > 0) {
                    getNextChunk();
                }
            }
        }
    }

    public void skipLonglongArray(int length)
    {
        skipArray(length, CDR.LONGLONG_SIZE);
    }

    public void read_ulonglong_array(long[] value, int offset, int length)
    {
        read_longlong_array(value, offset, length);
    }

    // DataInputStream
    public void read_ulonglong_array(org.omg.CORBA.ULongLongSeqHolder seq,
                                     int offset, int length)
    {
        read_ulonglong_array(seq.value, offset, length);
    }

    public void skipUlonglongArray(int length)
    {
        skipArray(length, CDR.ULONGLONG_SIZE);
    }

    public void read_float_array(float[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null int array reference.", 0,
                                CompletionStatus.COMPLETED_NO);

        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        // número de datos int que se pueden leer enteros
        int ints_can_read = 0;

        int position = 0;

        // numero de ints que quedan
        int remain_ints = length;

        while (remain_ints > 0) {

            if (!alignment(CDR.FLOAT_SIZE)) { // encapsulación, el dato no cabe
                                              // completo
                readOctetArrayAux(m_data_buffer, 0, CDR.FLOAT_SIZE);

                if (m_iterator.m_byte_order) {
                    value[offset++] =
                      Float.intBitsToFloat((m_data_buffer[0] & 0xff)
                                           | ((m_data_buffer[1] & 0xff) << 8)
                                           | ((m_data_buffer[2] & 0xff) << 16)
                                           | ((m_data_buffer[3] & 0xff) << 24));
                } else {
                    value[offset++] = 
                      Float.intBitsToFloat(((m_data_buffer[0] & 0xff) << 24)
                                           | ((m_data_buffer[1] & 0xff) << 16)
                                           | ((m_data_buffer[2] & 0xff) << 8)
                                           | (m_data_buffer[3] & 0xff));
                }

                remain_ints--;

            } else {
                position = m_iterator.m_position;

                ints_can_read = java.lang.Math
                                              .min(
                                                   remain_ints,
                                                   m_iterator.available()
                                                                                                                                                                                                            / CDR.FLOAT_SIZE);

                for (int i = ints_can_read; i > 0; i--) {
                    if (m_iterator.m_byte_order)
                        value[offset++] =
                          Float.intBitsToFloat(
                              (m_chunk_buffer[position++] & 0xff)
                              | ((m_chunk_buffer[position++] & 0xff) << 8)
                              | ((m_chunk_buffer[position++] & 0xff) << 16)
                              | ((m_chunk_buffer[position++] & 0xff) << 24));
                    else
                        value[offset++] =
                          Float.intBitsToFloat(
                              ((m_chunk_buffer[position++] & 0xff) << 24)
                              | ((m_chunk_buffer[position++] & 0xff) << 16)
                              | ((m_chunk_buffer[position++] & 0xff) << 8)
                              | (m_chunk_buffer[position++] & 0xff));
                }

                remain_ints -= ints_can_read;
                m_iterator.m_position = position;

                if (remain_ints > 0) {
                    getNextChunk();
                }
            }
        }
    }

    public void skipFloatArray(int length)
    {
        skipArray(length, CDR.FLOAT_SIZE);
    }

    public void read_double_array(double[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null long array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        // número de datos long que se pueden leer enteros
        int longs_can_read = 0;

        int position = 0;

        // numero de longs que quedan
        int remain_longs = length;

        while (remain_longs > 0) {

            if (!alignment(CDR.DOUBLE_SIZE)) { // encapsulación, el dato no cabe
                                               // completo

                readOctetArrayAux(m_data_buffer, 0, CDR.DOUBLE_SIZE);

                if (m_iterator.m_byte_order) {
                    value[offset++] = 
                        Double.longBitsToDouble(
                            ((long) m_data_buffer[0] & 0xffL)
                            | (((long) m_data_buffer[1] & 0xffL) << 8)
                            | (((long) m_data_buffer[2] & 0xffL) << 16)
                            | (((long) m_data_buffer[3] & 0xffL) << 24)
                            | (((long) m_data_buffer[4] & 0xffL) << 32)
                            | (((long) m_data_buffer[5] & 0xffL) << 40)
                            | (((long) m_data_buffer[6] & 0xffL) << 48)
                            | (((long) m_data_buffer[7] & 0xffL) << 56));
                } else {	
                    value[offset++] =
                        Double.longBitsToDouble(
                            (((long) m_data_buffer[0] & 0xffL) << 56)
                            | (((long) m_data_buffer[1] & 0xffL) << 48)
                            | (((long) m_data_buffer[2] & 0xffL) << 40)
                            | (((long) m_data_buffer[3] & 0xffL) << 32)
                            | (((long) m_data_buffer[4] & 0xffL) << 24)
                            | (((long) m_data_buffer[5] & 0xffL) << 16)
                            | (((long) m_data_buffer[6] & 0xffL) << 8)
                            | ((long) m_data_buffer[7] & 0xffL));
                }

                remain_longs--;

            } else {
                position = m_iterator.m_position;

                longs_can_read = 
                    java.lang.Math.min(remain_longs,
                                       m_iterator.available()
                                                                                                                                                                                                                / CDR.LONGLONG_SIZE);

                for (int i = longs_can_read; i > 0; i--) {
                    if (m_iterator.m_byte_order)
                        value[offset++] =
                            Double.longBitsToDouble(
                                ((long) m_chunk_buffer[position++] & 0xffL)
                                | (((long) m_chunk_buffer[position++] & 0xffL) << 8)
                                | (((long) m_chunk_buffer[position++] & 0xffL) << 16)
                                | (((long) m_chunk_buffer[position++] & 0xffL) << 24)
                                | (((long) m_chunk_buffer[position++] & 0xffL) << 32)
                                | (((long) m_chunk_buffer[position++] & 0xffL) << 40)
                                | (((long) m_chunk_buffer[position++] & 0xffL) << 48)
                                                                  | (((long) m_chunk_buffer[position++] & 0xffL) << 56));
                    else
                        value[offset++] = 
                          Double.longBitsToDouble(
                            (((long) m_chunk_buffer[position++] & 0xffL) << 56)
                            | (((long) m_chunk_buffer[position++] & 0xffL)<< 48)
                            | (((long) m_chunk_buffer[position++] & 0xffL)<< 40)
                            | (((long) m_chunk_buffer[position++] & 0xffL)<< 32)
                            | (((long) m_chunk_buffer[position++] & 0xffL)<< 24)
                            | (((long) m_chunk_buffer[position++] & 0xffL)<< 16)
                            | (((long) m_chunk_buffer[position++] & 0xffL)<< 8)
                            | ((long) m_chunk_buffer[position++] & 0xffL));
                }

                remain_longs -= longs_can_read;
                m_iterator.m_position = position;

                if (remain_longs > 0) {
                    getNextChunk();
                }
            }
        }
    }

    public void skipDoubleArray(int length)
    {
        skipArray(length, CDR.DOUBLE_SIZE);
    }

    public org.omg.CORBA.Object read_Object()
    {
        IOR ior = new DefaultIOR();
        ior.read( this );

        if (ior.memberCount() == 0) // null reference
            return null;
        else
            return ObjectImpl.fromIOR(m_orb, ior);
    }

    public org.omg.CORBA.Object read_Object(java.lang.Class clz)
    {
        java.lang.Object stub = null;

        try {
            stub = clz.newInstance();
        }
        catch (InstantiationException ie) {
            throw new org.omg.CORBA.BAD_PARAM("Can not instantiate class"
                                              + clz.getName());
        }
        catch (IllegalAccessException iae) {
            throw new org.omg.CORBA.BAD_PARAM("Can not instantiate class: "
                                              + iae.toString());
        }

        if ((stub != null)
            && (stub instanceof org.omg.CORBA.portable.ObjectImpl)) {
            org.omg.CORBA.portable.ObjectImpl obj_impl = 
                (org.omg.CORBA.portable.ObjectImpl) read_Object();
            org.omg.CORBA.portable.ObjectImpl stub_ref =
                (org.omg.CORBA.portable.ObjectImpl) stub;
            stub_ref._set_delegate(obj_impl._get_delegate());
            return stub_ref;

        }
        throw new BAD_PARAM("Invalid Reference Class:"
                                                                                                                                                                        + " it does not implements org.omg.CORBA.portable.ObjectImpl");
    }

    public void skipObject()
    {
        IOR.skip(this);
    }

    public org.omg.CORBA.TypeCode read_TypeCode()
    {
        return TypeCodeReader.read(this);
    }

    public void skipTypeCode()
    {
        read_TypeCode();
        //TypeCodeReader.skip(this);
    }

    public org.omg.CORBA.Any read_any()
    {
        TypeCode type = read_TypeCode();

        AnyImpl any = new AnyImpl(m_orb);
        any.type(type);
        any.read_value(this, type);
        return any;
    }

    public void skipAny()
    {
        TypeCode type = read_TypeCode();
        TypeCodeMarshaler.skipValue(type, this);
    }

    public void read_any_array(org.omg.CORBA.Any[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null long array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        int last = offset + length;

        for (int i = offset; i < last; i++)
            value[i] = read_any();
    }

    public org.omg.CORBA.Context read_Context()
    {
        return ContextImpl.read(this);
    }

    public void skipContext()
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * @deprecated Deprecated by CORBA 2.2
     */
    public org.omg.CORBA.Principal read_Principal()
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();

    }

    public void skipPrincipal()
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public java.math.BigDecimal read_fixed()
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public void skipFixed()
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public void skip_fixed(short digits, short scale)
    {
        TypeCode fixed_type = m_orb.create_fixed_tc(digits, scale);
        TypeCodeMarshaler.skipValue(fixed_type, this);
    }

    public org.omg.CORBA.Any read_fixed(short digits, short scale)
        throws org.omg.CORBA.BadFixedValue
    {
        TypeCode fixed_type = m_orb.create_fixed_tc(digits, scale);
        FixedHolder holder = new FixedHolder(fixed_type);
        holder._read(this);
        AnyImpl any = (AnyImpl) m_orb.create_any();
        any.insert_Streamable(holder);
        return any;
    }

    public void skip_fixed_array(int length, short digits, short scale)
    {
        TypeCode fixed_type = m_orb.create_fixed_tc(digits, scale);

        for (int i = 0; i < length; i++)
            TypeCodeMarshaler.skipValue(fixed_type, this);

    }

    public void read_fixed_array(org.omg.CORBA.AnySeqHolder seq, int offset,
                                 int length, short digits, short scale)
        throws org.omg.CORBA.BadFixedValue
    {
        if ((seq == null) || (seq.value == null))
            throw new BAD_PARAM("Null long array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (seq.value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        int last = offset + length;

        for (int i = offset; i < last; i++)
            seq.value[i] = read_fixed(digits, scale);
    }

    /////////////////////
    // DataInputStream //
    /////////////////////

    public void read_boolean_array(org.omg.CORBA.BooleanSeqHolder seq,
                                   int offset, int length)
    {
        read_boolean_array(seq.value, offset, length);
    }

    public void read_char_array(org.omg.CORBA.CharSeqHolder seq, int offset,
                                int length)
    {
        read_char_array(seq.value, offset, length);
    }

    public void read_wchar_array(org.omg.CORBA.WCharSeqHolder seq, int offset,
                                 int length)
    {
        read_wchar_array(seq.value, offset, length);
    }

    public void read_octet_array(org.omg.CORBA.OctetSeqHolder seq, int offset,
                                 int length)
    {
        read_octet_array(seq.value, offset, length);
    }

    public void read_short_array(org.omg.CORBA.ShortSeqHolder seq, int offset,
                                 int length)
    {
        read_short_array(seq.value, offset, length);
    }

    public void read_ushort_array(org.omg.CORBA.UShortSeqHolder seq,
                                  int offset, int length)
    {
        read_ushort_array(seq.value, offset, length);
    }

    public void read_long_array(org.omg.CORBA.LongSeqHolder seq, int offset,
                                int length)
    {
        read_long_array(seq.value, offset, length);
    }

    public void read_ulong_array(org.omg.CORBA.ULongSeqHolder seq, int offset,
                                 int length)
    {
        read_ulong_array(seq.value, offset, length);
    }

    public void read_longlong_array(org.omg.CORBA.LongLongSeqHolder seq,
                                    int offset, int length)
    {
        read_longlong_array(seq.value, offset, length);
    }

    public void read_float_array(org.omg.CORBA.FloatSeqHolder seq, int offset,
                                 int length)
    {
        read_float_array(seq.value, offset, length);
    }

    public void read_double_array(org.omg.CORBA.DoubleSeqHolder seq,
                                  int offset, int length)
    {
        read_double_array(seq.value, offset, length);
    }

    public void read_any_array(org.omg.CORBA.AnySeqHolder seq, int offset,
                               int length)
    {
        read_any_array(seq.value, offset, length);
    }

    /////////////////
    // InputStream //
    /////////////////
    public int available()
    {
        return m_iterator.bufferAvailable();
    }

    public int read()
    {
        return (int) read_octet();
    }

    public int read(byte[] value)
    {
        read_octet_array(value, 0, value.length);
        return value.length;
    }

    public int read(byte[] value, int offset, int length)
    {
        read_octet_array(value, offset, length);
        return length;
    }

    public boolean markSupported()
    {
        return true;
    }

    public void mark(int readlimit)
    {
        m_iterator.mark();
    }

    public void reset()
        throws java.io.IOException
    {
        m_iterator.reset();
    }

    //corba 2.3

    protected ValueTypeInfo getIndirectedValue()
    {
        AbsolutePosition indirected_position = readIndirection();

        java.lang.Object obj = 
            getContextCDR().lookupObject(indirected_position);

        if (obj instanceof ValueTypeInfo)
            return (ValueTypeInfo) obj;
        else
            throw new MARSHAL("No Value Indirected");
    }

    public java.io.Serializable read_value()
    {
        return readValueWithId(null);
    }

    public java.io.Serializable read_value(java.lang.String rep_id)
    {
        if (rep_id == null)
            throw new BAD_PARAM("Null rep_id");

        return readValueWithId(rep_id);
    }

    // DataInputStream
    public java.io.Serializable read_Value()
    {
        return read_value();
    }

    protected java.io.Serializable readValueWithId(java.lang.String rep_id)
    {
        alignment(CDR.LONG_SIZE);

        MarkCDR value_mark = m_iterator.getMark();

        AbsolutePosition value_position = getAbsolutePosition();

        ValueTypeInfo value_info = ValueTypeInfo.read(this);

        if (value_info.isNull())
            return null;

        if (value_info.isIndirection()) {
            value_info = getIndirectedValue();

            if ((rep_id != null) && (!value_info.is_truncable(rep_id)))
                throw new BAD_PARAM("Cannot truncate value to: " + rep_id);

            return value_info.get_value();
        }

        String id = value_info.get_id();

        if (id == null) {
            if (rep_id == null) {
                throw new MARSHAL("Unknown Repository Id");
            }

            id = rep_id;
        } else if ((rep_id != null) && (!value_info.is_truncable(rep_id))) {
            throw new BAD_PARAM("Cannot truncate value to: " + rep_id);
        }

        java.io.Serializable value = null;

        if (id.equals("IDL:omg.org/CORBA/WStringValue:1.0")) {
            // special handling of strings, according to spec
            value = read_wstring();
        } else if (id.startsWith("IDL:")) {
            ValueFactory factory = m_orb.lookup_value_factory(id);

            if (factory == null)
                throw new MARSHAL("Cannot get Value Factory");

            if (value_info.isFragmented()) {
                throw new 
                	NO_IMPLEMENT("Fragmented value reading not implemented");
            }

            m_iterator.goBack(value_mark);

            value = factory.read_value(this);
        } else
        // RMI
        {
            // ValueHandler wants class, repository_id, and sending context.
            // I wonder why it wants all of these.
            // If we settle down on this implementation, compute these
            // values more efficiently elsewhere.

            String className = 
                es.tid.TIDorbj.core.util.RepositoryId.className(id);

            Class c = null;

            try {
                c = Thread.currentThread().getContextClassLoader()
                          .loadClass(className);
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException("class not found: " + className);
            }

            m_iterator.goBack(value_mark);

            initValueHandler();

            value = 
              st_value_handler.readValue(this, //InputStream
                                         0, //offset
                                         c, //value class
                                         id, // repository Id
                                         st_value_handler.getRunTimeCodeBase());
        }

        value_info.set_value(value);

        getContextCDR().putPosition(value_position, value_info);

        return value;
    }

    public java.io.Serializable read_value(java.lang.Class clz)
    {
        if ((clz == null))
            throw new BAD_PARAM();

        java.lang.Object obj = null;

        try {
            java.lang.reflect.Constructor constructor = 
                clz.getConstructor(new Class[0]);

            obj = constructor.newInstance(new Object[0]);

        }
        catch (Throwable th) {
            throw new org.omg.CORBA.UNKNOWN(th.toString());
        }

        if (obj instanceof java.io.Serializable)
            return read_value((java.io.Serializable) obj);
        else
            throw new 
            	BAD_PARAM("Class does not implements java.io.Serializable");
    }

    public java.io.Serializable 
    	read_value(org.omg.CORBA.portable.BoxedValueHelper factory)
    {
        alignment(CDR.LONG_SIZE);

        AbsolutePosition value_position = getAbsolutePosition();

        ValueTypeInfo value_info = ValueTypeInfo.read(this);

        if (value_info.isNull())
            return null;

        if (value_info.isIndirection()) {
            value_info = getIndirectedValue();

            if (!value_info.get_id().equals(factory.get_id()))
                throw new BAD_PARAM("Cannot truncate value");

            return value_info.get_value();
        }

        es.tid.TIDorbj.core.util.RepositoryId[] ids = 
            value_info.get_repository_ids();

        if ((ids == null) || (ids.length == 0)) {
            String[] rep_ids = new String[1];
            rep_ids[0] = factory.get_id();
            value_info.set_repository_ids(rep_ids);
        } else if (!ids[0].m_value.equals(factory.get_id())) {
            throw new 
            	BAD_PARAM("Invalid RepositoryId: value id does not " +
            			  "match valuebox id");
        }

        if (value_info.isFragmented()) {
            throw new NO_IMPLEMENT("Fragmented value reading not implemented");
        }

        java.io.Serializable value = factory.read_value(this);

        value_info.set_value(value);

        getContextCDR().putPosition(value_position, value_info);

        return value;
    }

    public java.io.Serializable read_value(java.io.Serializable value)
    {
        alignment(CDR.LONG_SIZE);

        AbsolutePosition value_position = getAbsolutePosition();

        ValueTypeInfo value_info = ValueTypeInfo.read(this);

        if (value_info.isNull())
            return null;

        if (value_info.isIndirection()) {

            value_info = getIndirectedValue();

            java.io.Serializable value_readed = value_info.get_value();

            if (!value_readed.getClass().isInstance(value))
                throw new BAD_PARAM("Cannot truncate value");

            return value_readed;
        }

        if (value instanceof org.omg.CORBA.portable.StreamableValue) {
            org.omg.CORBA.portable.StreamableValue streamable = 
                (org.omg.CORBA.portable.StreamableValue) value;

            String value_id = value_info.get_id();

            try {
                if ((value_id != null)
                    && (!value_id.equals(streamable._type().id())))
                    throw new BAD_PARAM("Streamable id is not the same");
            }
            catch (org.omg.CORBA.TypeCodePackage.BadKind bk) {}

            streamable._read(this);

        } else if (value instanceof org.omg.CORBA.portable.CustomValue) {

            org.omg.CORBA.portable.CustomValue custom = 
                (org.omg.CORBA.portable.CustomValue) value;

            String value_id = value_info.get_id();

            if (value_id != null) {
                String[] custom_ids = custom._truncatable_ids();

                boolean truncable = false;

                for (int i = 0; i < custom_ids.length; i++) {
                    if (value_info.is_truncable(custom_ids[i])) {
                        truncable = true;
                        break;
                    }
                }

                if (!truncable)
                    throw new BAD_PARAM("Streamable id is not the same");
            }

            if (value_info.isFragmented()) {
                throw new 
                	NO_IMPLEMENT("Fragmented value reading not implemented");
            }

            custom.unmarshal(this);
        } else if (value instanceof java.lang.String) {
            value = read_wstring();
        } else if (value instanceof java.io.Serializable) {
            initValueHandler();

            value = 
              st_value_handler.readValue(this, //InputStream
                                         0, //offset
                                         value.getClass(), //value class
                                         value_info.get_id(), // repository Id
                                         st_value_handler.getRunTimeCodeBase());
        } else {
            throw new BAD_PARAM("No Serializable class");
        }

        value_info.set_value(value);

        getContextCDR().putPosition(value_position, value_info);

        return value;
    }

    public void initValueHandler()
    {
        synchronized (this.getClass()) {
            if (st_value_handler == null)
                st_value_handler = javax.rmi.CORBA.Util.createValueHandler();
        }
    }

    public java.lang.Object read_abstract_interface()
    {
        boolean is_reference = read_boolean();

        if (is_reference)
            return read_Object();
        else
            return read_value();
    }

    //DataInputStream

    public java.lang.Object read_Abstract()
    {
        return read_abstract_interface();
    }

    public java.lang.Object read_abstract_interface(java.lang.Class clz)
    {
        boolean is_reference = read_boolean();

        if (is_reference)
            return read_Object();
        else
            return read_value(clz);
    }

    // tidorb operations

    public void skipIndirection()
    {
        skipLong();
    }

    public AbsolutePosition readIndirection()
    {
        // Prevent fragment jumps

        alignment(CDR.LONG_SIZE);

        // calculate the indirection

        AbsolutePosition typecode_indirection = getAbsolutePosition();

        // read indirection offset

        int offset = read_long();

        // SELF-INDIRECTINIG not allowed

        if (offset >= -CDR.LONG_SIZE)
            throw new MARSHAL("Invalid offset");

        int headers_length = 0;

        // if message add to offset the message headers

        if ((m_is_message) && (m_iterator.m_current_chunk_num > 0)) {
            int fragment_header_size = 0;

            if (getVersion() == GIOPVersion.VERSION_1_2)
                fragment_header_size = 
                    GIOPFragmentMessage.FRAGMENT_HEADER_SIZE_1_2;
            else
                fragment_header_size = 
                    GIOPFragmentMessage.FRAGMENT_HEADER_SIZE_1_1;

            BufferCDR buffer = getBuffer();

            // minimun valid offset from the fragment where is the indirection

            int current_fragment = m_iterator.m_current_chunk_num;

            ChunkCDR chunk = buffer.getChunk(current_fragment);

            int offset_from_fragment = 0;

            if (chunk.hasHeader())
                offset_from_fragment = CDR.LONG_SIZE + fragment_header_size
                                       - m_iterator.m_position;
            else
                offset_from_fragment = CDR.LONG_SIZE - m_iterator.m_position;

            // loop: add to offset fragment header size while the offset was
            //       less than valid offset from indirection fragment
            while ((current_fragment > 0) && (offset < offset_from_fragment)) {
                chunk = buffer.getChunk(--current_fragment);

                offset_from_fragment -= chunk.getAvailable();

                if (chunk.hasHeader())
                    offset -= fragment_header_size;
            }
        }

        AbsolutePosition referenced_position;

        referenced_position = typecode_indirection.addOffset(offset);

        if (referenced_position == null)
            throw new MARSHAL("Invalid indirection: offset out of bounds.", 0,
                              CompletionStatus.COMPLETED_NO);

        // align the position to read the typecode in a aligned position of long
        // this position alwais is aligned to long independently of
        // encapsulation.

        //  referenced_position.align(CDR.LONG_SIZE);

        return referenced_position;
    }

    public void fixStarting()
    {
        m_iterator.fixStarting();
    }

    /**
     * Sets the stream to the begining of the buffer.
     */
    public void rewind()
    {
        m_iterator.rewind();

        if ((m_is_message) && (m_iterator.m_current_chunk_num == 0)
            && (m_iterator.m_position == 0))
            skip(es.tid.TIDorbj.core.comm.giop.GIOPHeader.HEADER_SIZE);

        m_chunk_buffer = m_iterator.m_chunk.m_buffer;
    }

    /**
     * Get the next chunk of the buffer
     * 
     * @throws org.omg.CORBA.MARSHAL
     *             if the end of the buffer is reached.
     * @see es.tid.TIDorbj.core.cdr.ChunkCDR
     * @see es.tid.TIDorbj.core.cdr.BufferCDR
     */
    public void getNextChunk()
    {
        if ((m_is_message) && (m_version != GIOPVersion.VERSION_1_0)) {
            /*
             * if(version == GIOPVersion.VERSION_1_0) throw new MARSHAL("End of
             * Buffer: GIOP 1.0 does not allows fragmentation");
             */
            m_iterator.nextChunk();

            if (m_iterator.m_chunk.hasHeader()) {
                skip(GIOPHeader.HEADER_SIZE);

                if (m_version == GIOPVersion.VERSION_1_2) {
                    GIOPFragmentMessage.skipFragmentHeader1_2(this);
                }
            }

        } else {
            m_iterator.nextChunk();
        }

        m_chunk_buffer = m_iterator.m_chunk.m_buffer;
    }

    /**
     * Set the stream pointer to the next aligned position of given data type
     * 
     * @param type_size
     *            the type size in octets
     */
    public void goNextAlignedPosition(int type_size)
    {
        // set the aligned position

        int next_position = m_iterator.alignPosition(type_size);

        if (next_position > m_iterator.m_chunk.m_available) {
            if (m_iterator.m_encapsulation) {
                // continue the encapsulation aligment in next chunk
                int gap = next_position - m_iterator.m_position;
                skip(gap);
                alignment(CDR.OCTET_SIZE); // prevents next_position == length
            } else {
                getNextChunk();
            }
        } else if (next_position == m_iterator.m_chunk.m_available) {
            getNextChunk();
        } else {
            m_iterator.m_position = next_position;
        }
    }

    /**
     * Sets the reading pointer in the correct aligmnet for the data type. If
     * necessary, gets the next chunk.
     * <p>
     * CORBA 2.3 specification says: A primitive data type of 8 bytes or
     * smaller, should never be broken across two fragments.Therefore, if the
     * remaining bytes are not sufficient for reading the data next chunk will
     * be set for reading.
     * <p>
     * If it is reading a encapsulation and the data is broken into two chunks,
     * reads the data compoentes byte per byte.
     * 
     * @param <code>type_size</code> the type size for alignment.
     * @return <code>true</code> if can read directly the data or
     *         <code>false</code> if byte swap is needed because the data is
     *         splitted in two framgments and is contended in a encapsulation.
     * @throws org.omg.CORBA.MARSHAL
     *             if the end of the buffer is reached.
     */

    public boolean alignment(int type_size)
        throws org.omg.CORBA.MARSHAL
    {

        goNextAlignedPosition(type_size);

        if (m_iterator.enoughSpace(type_size))
            return true;

        // there is no space for whole data bytes

        if (m_iterator.m_encapsulation)
            return false; // marshal byte per byte

        // marshaling in the main buffer, get next chunk
        getNextChunk();

        return true;
    }

    private void skipArray(int length, int type_length)
    {
        if (length < 0)
            throw new BAD_PARAM("Invalid array length", 0,
                                CompletionStatus.COMPLETED_NO);

        // número de octetos pertenecientes a datos que se pueden leer enteros
        int octets_can_skip = 0;

        // numero de datos que quedan por saltar
        int remain_data = length;

        while (remain_data > 0) {

            if (!alignment(type_length)) { // encapsulación, el dato no cabe
                                           // completo

                skip(type_length);

                remain_data--;

            } else {
                octets_can_skip = java.lang.Math.min(remain_data * type_length,
                                                     m_iterator.available());

                remain_data -= octets_can_skip / type_length;
                m_iterator.m_position += octets_can_skip;

                if (remain_data > 0) {
                    getNextChunk();
                }
            }
        }
    }

    public long skip(long n)
    {
        if (n > 0L) {
            int chunk_left = m_iterator.available();
            if (n <= chunk_left)
                m_iterator.skip((int) n);
            else {
                getNextChunk();
                skip(n - chunk_left);
            }
            return n;
        }
        return 0;
    }
}