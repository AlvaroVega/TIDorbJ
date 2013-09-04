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
import org.omg.CORBA.DATA_CONVERSION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.portable.ValueBase;

import es.tid.TIDorbj.core.ContextImpl;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.comm.CommunicationDelegate;
import es.tid.TIDorbj.core.comm.giop.GIOPVersion;
import es.tid.TIDorbj.core.comm.iiop.IIOPCommunicationLayer;
import es.tid.TIDorbj.core.comm.iiop.IIOPCommunicationLayerPropertiesInfo;
import es.tid.TIDorbj.core.iop.DefaultIOR;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.typecode.TypeCodeMarshaler;

/**
 * Portable OutputStream for demarshalling.
 * <p>
 * A <code>CDROutputStream</code> has an buffer iterator
 * <code>CDRIterator</code>. The iterator maintains the data position and
 * correct aligment, and <code>TypeCode</code> marshaling information.
 * 
 * @see es.tid.TIDorbj.core.cdr.CDRInputStream
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class CDROutputStream extends org.omg.CORBA_2_3.portable.OutputStream
    implements org.omg.CORBA.DataOutputStream
{

    /**
     * ORB instance that created the output stream.
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
     * Buffer Iterator.
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

    // DataOutputStream
    static String[] st_data_output_ids =
    	{ "IDL:omg.org/CORBA/DataOutputStream:1.0" };

    public String[] _truncatable_ids()
    {
        return st_data_output_ids;
    }

    public CDROutputStream(TIDORB orb, byte[] buffer)
    {
        this(orb, new IteratorCDR(new BufferCDR(buffer)));
    }

    public CDROutputStream(TIDORB orb, BufferCDR buffer)
    {
        this(orb, new IteratorCDR(buffer));
    }

    public CDROutputStream(TIDORB orb, int chunk_size)
    {
        this(orb, new IteratorCDR(new BufferCDR(chunk_size)));
    }

    protected CDROutputStream(TIDORB orb, IteratorCDR iterator)
    {
        m_orb = orb;

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
        iterator.m_byte_order = CDR.BIG_ENDIAN;

        m_chunk_buffer = iterator.m_chunk.m_buffer;
        m_data_buffer = new byte[CDR.LONGLONG_SIZE];
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

        if ((is) && (m_iterator.m_position == 0)) {
            skip(es.tid.TIDorbj.core.comm.giop.GIOPHeader.HEADER_SIZE);
        }
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
     * Sets the aligment offset in the current Context.
     * 
     * @param pos
     *            the new offset
     * @see es.tid.TIDorbj.core.cdr.AligmentOffset
     */
    public void setAlignmentOffset(int pos)
    {
        m_iterator.setAlignmentOffset(pos);
    }

    /**
     * @return pointer to the actual position in the buffer.
     */
    public PointerCDR actualPointer()
    {
        return m_iterator.getPointer();
    }

    /**
     * Creates a new Encapsulation Context with it own aligment an indirections.
     */
    public void enterEncapsulation()
    {
        // skip the length

        CDROutputStream length_out = copy();

        write_ulong(1);

        // get the real start position of encapsulation

        alignment(CDR.OCTET_SIZE);

        m_iterator.enterEncapsulation();

        //write byte order
        write_boolean(m_iterator.getByteOrder());

        // save the length output stream in the new context
        m_iterator.m_context.setLengthOut(length_out);
    }

    /**
     * Close the Encapsulation Context, write en encapsulation length, and
     * restore the father Context.
     * 
     * @see es.tid.TIDorbj.core.cdr.ContextCDR
     */

    public void exitEncapsulation()
    {

        CDROutputStream length_out = m_iterator.m_context.m_length_out;

        if (length_out == null)
            throw new org.omg.CORBA.INTERNAL("Null Length out");

        int headers_length = 0;

        // if message Calculate the headers size to subtract the value to the
        // Encapsulation size
        /*
         * if (message) { int fragments = iterator.current_chunk_num -
         * length_out.iterator.current_chunk_num;
         * 
         * 
         * if(version == GIOPVersion.VERSION_1_2) headers_length = fragments *
         * GIOPFragmentMessage.FRAGMENT_HEADER_SIZE_1_2; else if (version ==
         * GIOPVersion.VERSION_1_1) headers_length = fragments *
         * GIOPFragmentMessage.FRAGMENT_HEADER_SIZE_1_1; else headers_length =
         * 0; }
         */
        int encapsulation_length = 
            getAbsolutePosition().getValue()
            - m_iterator.m_context.m_starts_at.getAbsolutePosition().getValue();
        //    - headers_length;

        length_out.write_ulong(encapsulation_length);

        m_iterator.m_context.deleteLengthOut();

        m_iterator.exitEncapsulation();

    }

    /**
     * @return an input stream with its beginning at this marshaling position
     */
    public CDRInputStream inputStreamAtThisPosition()
    {
        CDRInputStream input = new CDRInputStream(m_orb, m_iterator.copy());
        input.fixStarting();
        input.setMessage(m_is_message);
        input.setVersion(m_version);
        return input;
    }

    public org.omg.CORBA.ORB orb()
    {
        if (m_orb != null)
            return m_orb;
        else
            return es.tid.TIDorbj.core.TIDORB.init();
    }

    public org.omg.CORBA.portable.InputStream create_input_stream()
    {
        CDRInputStream input;

        input = new CDRInputStream(m_orb, m_iterator.copy());

        input.setMessage(m_is_message);
        input.setVersion(m_version);

        input.rewind();

        return input;
    }

    /**
     * @return a new inputstream pointing the same position.
     */
    public CDROutputStream copy()
    {
        CDROutputStream stream = new CDROutputStream(m_orb, m_iterator.copy());
        stream.setMessage(m_is_message);
        stream.setVersion(m_version);
        return stream;
    }

    public ContextCDR getContextCDR()
    {
        return m_iterator.m_context;
    }

    /**
     * Writes as an encapsulation the buffer.
     * <p>
     * The first octet in the encapsulation must be the byte-order.
     * 
     * @encapsulation a buffer with the encapsulation.
     */
    public void writeEncapsulation(Encapsulation encapsulation)
    {
        encapsulation.write(this);
    }

    /**
     * @return the absolute value in the buffer order of the current position.
     */
    public AbsolutePosition getAbsolutePosition()
    {
        return m_iterator.getPointer().getAbsolutePosition();
    }

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

    public void write_boolean(boolean value)
    {
        alignment(CDR.BOOLEAN_SIZE);
        m_chunk_buffer[m_iterator.m_position++] = (value) ? (byte) 1 : (byte) 0;

        //recalculates the available bytes
        if (m_iterator.m_position > m_iterator.m_chunk.m_available)
            m_iterator.m_chunk.m_available = m_iterator.m_position;
    }

    public void write_char(char value)
    {
        // WARNING: Java char is 2 bytes long, there is a cast
        //          from char to byte
        if (value > 255) {
            throw new DATA_CONVERSION("Char out of range.",
                                      0,
                                      CompletionStatus.COMPLETED_NO);
        }
        alignment(CDR.CHAR_SIZE);
        m_chunk_buffer[m_iterator.m_position++] = (byte) value;

        //recalculates the available bytes
        if (m_iterator.m_position > m_iterator.m_chunk.m_available)
            m_iterator.m_chunk.m_available = m_iterator.m_position;
    }

    public void write_wchar(char value)
    {
        // Java char is 2 bytes long, there is a cast

        // WARNING: Java char is 2 bytes long, there is a cast
        //          from byte to char
        if (alignment(CDR.WCHAR_SIZE)) {
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 8);
            m_chunk_buffer[m_iterator.m_position++] = (byte) value;

            //recalculates the available bytes
            if (m_iterator.m_position > m_iterator.m_chunk.m_available)
                m_iterator.m_chunk.m_available = m_iterator.m_position;

        } else {
            m_data_buffer[0] = (byte) (value >>> 8);
            m_data_buffer[1] = (byte) value;
            write_octet_array(m_data_buffer, 0, CDR.WCHAR_SIZE);
        }
    }

    public void write_octet(byte value)
    {
        alignment(CDR.OCTET_SIZE);
        m_chunk_buffer[m_iterator.m_position++] = value;

        //recalculates the available bytes
        if (m_iterator.m_position > m_iterator.m_chunk.m_available)
            m_iterator.m_chunk.m_available = m_iterator.m_position;
    }

    public void write_short(short value)
    {
        if (alignment(CDR.SHORT_SIZE)) {
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 8);
            m_chunk_buffer[m_iterator.m_position++] = (byte) value;

            //recalculates the available bytes
            if (m_iterator.m_position > m_iterator.m_chunk.m_available)
                m_iterator.m_chunk.m_available = m_iterator.m_position;

        } else {
            m_data_buffer[0] = (byte) (value >>> 8);
            m_data_buffer[1] = (byte) value;
            write_octet_array(m_data_buffer, 0, CDR.SHORT_SIZE);
        }
    }

    public void write_ushort(short value)
    {
        // JAVA MAPPING: "negative" int = positive ulong values
        //               will be handled by the user
        if (alignment(CDR.USHORT_SIZE)) {
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 8);
            m_chunk_buffer[m_iterator.m_position++] = (byte) value;

            //recalculates the available bytes
            if (m_iterator.m_position > m_iterator.m_chunk.m_available)
                m_iterator.m_chunk.m_available = m_iterator.m_position;

        } else {
            m_data_buffer[0] = (byte) (value >>> 8);
            m_data_buffer[1] = (byte) value;
            write_octet_array(m_data_buffer, 0, CDR.USHORT_SIZE);
        }
    }

    public void write_long(int value)
    {
        if (alignment(CDR.LONG_SIZE)) {
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 24);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 16);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 8);
            m_chunk_buffer[m_iterator.m_position++] = (byte) value;

            //recalculates the available bytes
            if (m_iterator.m_position > m_iterator.m_chunk.m_available)
                m_iterator.m_chunk.m_available = m_iterator.m_position;
        } else {
            m_data_buffer[0] = (byte) (value >>> 24);
            m_data_buffer[1] = (byte) (value >>> 16);
            m_data_buffer[2] = (byte) (value >>> 8);
            m_data_buffer[3] = (byte) value;
            write_octet_array(m_data_buffer, 0, CDR.LONG_SIZE);
        }
    }

    public void write_ulong(int value)
    {
        // JAVA MAPPING: "negative" int = positive ulong values
        //               will be handled by the user

        if (alignment(CDR.ULONG_SIZE)) {
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 24);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 16);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 8);
            m_chunk_buffer[m_iterator.m_position++] = (byte) value;

            //recalculates the available bytes
            if (m_iterator.m_position > m_iterator.m_chunk.m_available)
                m_iterator.m_chunk.m_available = m_iterator.m_position;
        } else {
            m_data_buffer[0] = (byte) (value >>> 24);
            m_data_buffer[1] = (byte) (value >>> 16);
            m_data_buffer[2] = (byte) (value >>> 8);
            m_data_buffer[3] = (byte) value;
            write_octet_array(m_data_buffer, 0, CDR.ULONG_SIZE);
        }
    }

    public void write_longlong(long value)
    {
        if (alignment(CDR.LONGLONG_SIZE)) {
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 56);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 48);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 40);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 32);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 24);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 16);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 8);
            m_chunk_buffer[m_iterator.m_position++] = (byte) value;
            //recalculates the available bytes
            if (m_iterator.m_position > m_iterator.m_chunk.m_available)
                m_iterator.m_chunk.m_available = m_iterator.m_position;
        } else {
            m_data_buffer[0] = (byte) (value >>> 56);
            m_data_buffer[1] = (byte) (value >>> 48);
            m_data_buffer[2] = (byte) (value >>> 40);
            m_data_buffer[3] = (byte) (value >>> 32);
            m_data_buffer[4] = (byte) (value >>> 24);
            m_data_buffer[5] = (byte) (value >>> 16);
            m_data_buffer[6] = (byte) (value >>> 8);
            m_data_buffer[7] = (byte) value;
            write_octet_array(m_data_buffer, 0, CDR.LONGLONG_SIZE);
        }
    }

    public void write_ulonglong(long value)
    {
        // JAVA MAPPING: "negative" long = positive ulonglong values
        //               will be handled by the user

        if (alignment(CDR.ULONGLONG_SIZE)) {
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 56);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 48);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 40);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 32);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 24);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 16);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 8);
            m_chunk_buffer[m_iterator.m_position++] = (byte) (value >>> 0);
            //recalculates the available bytes
            if (m_iterator.m_position > m_iterator.m_chunk.m_available)
                m_iterator.m_chunk.m_available = m_iterator.m_position;
        } else {
            m_data_buffer[0] = (byte) (value >>> 56);
            m_data_buffer[1] = (byte) (value >>> 48);
            m_data_buffer[2] = (byte) (value >>> 40);
            m_data_buffer[3] = (byte) (value >>> 32);
            m_data_buffer[4] = (byte) (value >>> 24);
            m_data_buffer[5] = (byte) (value >>> 16);
            m_data_buffer[6] = (byte) (value >>> 8);
            m_data_buffer[7] = (byte) value;
            write_octet_array(m_data_buffer, 0, CDR.ULONGLONG_SIZE);
        }
    }

    public void write_float(float value)
    {
        write_long(java.lang.Float.floatToIntBits(value));
    }

    public void write_double(double value)
    {
        write_longlong(java.lang.Double.doubleToLongBits(value));

    }

    public void write_string(String value)
    {
        if (value == null)
            throw new BAD_PARAM("Null string reference.", 0,
                                CompletionStatus.COMPLETED_NO);

        // write the string and the final of string char '\0'

        int string_length = value.length();

        int cdr_string_length = string_length + 1;

        char[] char_array = new char[cdr_string_length];

        value.getChars(0, string_length, char_array, 0);

        char_array[string_length] = '\0';

        // write length

        write_ulong(cdr_string_length);

        // write value data

        writeCharArrayAux(char_array, 0, cdr_string_length);
    }

    public void write_wstring(String value)
    {
        if (value == null)
            throw new BAD_PARAM("Null string reference.", 0,
                                CompletionStatus.COMPLETED_NO);

        // write the string and the final of string char '\0'

        char[] char_array = value.toCharArray();

        // write length

        if (m_version == GIOPVersion.VERSION_1_2) {
            write_ulong((char_array.length + 1) * CDR.WCHAR_SIZE);
        
        } else  if (m_version == GIOPVersion.VERSION_1_1){
            write_ulong(char_array.length + 1);
        } else {
            throw new MARSHAL("wstring is not present in GIOP 1.0", 
                              6,
                              CompletionStatus.COMPLETED_NO);
        }

        // write value data
        write_wchar_array(char_array, 0, char_array.length);

        // write string end char
        write_wchar('\0');
    }

    public void write_boolean_array(boolean[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null boolean array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        int can_write = 0;

        // número de octetos que quedan por escribir

        int remain_booleans = length;

        int position = 0;
        int stop_at = 0;

        while (remain_booleans > 0) {

            position = m_iterator.m_position;

            can_write = java.lang.Math.min(remain_booleans,
                                           m_chunk_buffer.length - position);

            stop_at = position + can_write;

            while (position < stop_at) {
                m_chunk_buffer[position++] = (byte) ((value[offset++]) ? 1 : 0);
            }

            m_iterator.m_position = stop_at;
            remain_booleans -= can_write;

            if (remain_booleans > 0) {
                getNextChunk();
            } else {
                m_iterator.m_chunk.m_available = m_iterator.m_position;
            }
        }
    }

    public void write_char_array(char[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null char array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        writeCharArrayAux(value, offset, length);
    }

    public void writeCharArrayAux(char[] value, int offset, int length)
    {
        int chars_can_write = 0;

        // número de octetos que quedan por escribir

        int remain_chars = length;

        int position = 0;
        int stop_at = 0;

        int current_value = 0;

        while (remain_chars > 0) {

            position = m_iterator.m_position;

            chars_can_write = 
                java.lang.Math.min( remain_chars,
                                    m_chunk_buffer.length  - position);

            stop_at = position + chars_can_write;

            for (; position < stop_at;) {

                current_value = value[offset++];

                // CORBA chars are 1 byte length, value must be < 256
                if (current_value > 255) {
                    throw new DATA_CONVERSION("Char out of range.",
                                              0,
                                              CompletionStatus.COMPLETED_NO);
                }

                m_chunk_buffer[position++] = (byte) current_value;
            }

            m_iterator.m_position = stop_at;
            remain_chars -= chars_can_write;

            if (remain_chars > 0) {
                getNextChunk();
            } else {
                if (m_iterator.m_chunk.m_available < m_iterator.m_position)
                    m_iterator.m_chunk.m_available = m_iterator.m_position;
            }
        }
    }

    public void write_wchar_array(char[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null wchar array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        // número de octetos que quedan por escribir en el chunk actual
        int free_octets = 0;

        // número de datos wchar que se pueden escribir enteros
        int wchars_can_write = 0;

        int position = 0;
        int wchar_value = 0;

        // numero de wchars que quedan
        int remain_wchars = length;

        while (remain_wchars > 0) {

            if (!alignment(CDR.WCHAR_SIZE)) // encapsulación, el dato no cabe
            // completo
            {
                wchar_value = value[offset++];
                m_data_buffer[0] = (byte) (wchar_value >>> 8);
                m_data_buffer[1] = (byte) wchar_value;

                write_octet_array(m_data_buffer, 0, CDR.WCHAR_SIZE);

                remain_wchars--;

            } else {
                position = m_iterator.m_position;

                free_octets = m_chunk_buffer.length - position;

                wchars_can_write = 
                    java.lang.Math.min(remain_wchars,
                                       free_octets / CDR.WCHAR_SIZE);

                for (int i = wchars_can_write; i > 0; i--) {
                    wchar_value = value[offset++];
                    m_chunk_buffer[position++] = (byte) (wchar_value >>> 8);
                    m_chunk_buffer[position++] = (byte) wchar_value;
                }

                remain_wchars -= wchars_can_write;
                m_iterator.m_position = position;

                if (remain_wchars > 0) {
                    getNextChunk();
                } else if (m_iterator.m_chunk.m_available < position)
                    m_iterator.m_chunk.m_available = position;
            }
        }
    }

    public void write_octet_array(byte[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null byte array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        // número de octetos que quedan por escribir
        int remain_bytes = length;

        // bytes que se pueden escribir por vuelta
        int bytes_can_write = 0;
        int position = 0;

        while (remain_bytes > 0) {

            position = m_iterator.m_position;

            bytes_can_write = java.lang.Math
                                            .min(
                                                 remain_bytes,
                                                 m_chunk_buffer.length
                                                                                                                                                                                                    - position);

            System.arraycopy(value, offset, m_chunk_buffer, position,
                             bytes_can_write);

            m_iterator.m_position += bytes_can_write;
            remain_bytes -= bytes_can_write;
            offset += bytes_can_write;

            if (remain_bytes > 0)
                getNextChunk();
            else if (m_iterator.m_chunk.m_available < m_iterator.m_position)
                m_iterator.m_chunk.m_available = m_iterator.m_position;
        }
    }

    public void write_short_array(short[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null short array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        // número de octetos que quedan por escribir en el chunk actual
        int free_octets = 0;

        // número de datos short que se pueden escribir enteros
        int shorts_can_write = 0;

        int position = 0;
        int short_value = 0;

        // numero de shorts que quedan
        int remain_shorts = length;

        while (remain_shorts > 0) {

            if (!alignment(CDR.SHORT_SIZE)) // encapsulación, el dato no cabe
            // completo
            {
                short_value = value[offset++];
                m_data_buffer[0] = (byte) (short_value >>> 8);
                m_data_buffer[1] = (byte) short_value;

                write_octet_array(m_data_buffer, 0, CDR.SHORT_SIZE);

                remain_shorts--;

            } else {
                position = m_iterator.m_position;

                free_octets = m_chunk_buffer.length - position;

                shorts_can_write = 
                    java.lang.Math.min(remain_shorts,
                                       free_octets / CDR.SHORT_SIZE);

                for (int i = shorts_can_write; i > 0; i--) {
                    short_value = value[offset++];
                    m_chunk_buffer[position++] = (byte) (short_value >>> 8);
                    m_chunk_buffer[position++] = (byte) short_value;
                }

                remain_shorts -= shorts_can_write;
                m_iterator.m_position = position;

                if (remain_shorts > 0) {
                    getNextChunk();
                } else if (m_iterator.m_chunk.m_available < position)
                    m_iterator.m_chunk.m_available = position;
            }
        }
    }

    public void write_ushort_array(short[] value, int offset, int length)
    {
        write_short_array(value, offset, length);
    }

    public void write_long_array(int[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null int array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        // número de octetos que quedan por escribir en el chunk actual
        int free_octets = 0;

        // número de datos int que se pueden escribir enteros
        int ints_can_write = 0;

        int position = 0;
        int int_value = 0;

        // numero de ints que quedan por escribir
        int remain_ints = length;

        while (remain_ints > 0) {

            if (!alignment(CDR.LONG_SIZE)) // encapsulación, el dato no cabe
            // completo
            {
                int_value = value[offset++];

                m_data_buffer[0] = (byte) (int_value >>> 24);
                m_data_buffer[1] = (byte) (int_value >>> 16);
                m_data_buffer[2] = (byte) (int_value >>> 8);
                m_data_buffer[3] = (byte) int_value;
                write_octet_array(m_data_buffer, 0, CDR.LONG_SIZE);

                remain_ints--;

            } else {
                position = m_iterator.m_position;

                free_octets = m_chunk_buffer.length - position;

                ints_can_write = 
                    java.lang.Math.min(remain_ints,
                                       free_octets / CDR.LONG_SIZE);

                for (int i = ints_can_write; i > 0; i--) {
                    int_value = value[offset++];
                    m_chunk_buffer[position++] = (byte) (int_value >>> 24);
                    m_chunk_buffer[position++] = (byte) (int_value >>> 16);
                    m_chunk_buffer[position++] = (byte) (int_value >>> 8);
                    m_chunk_buffer[position++] = (byte) int_value;
                }

                remain_ints -= ints_can_write;
                m_iterator.m_position = position;

                if (remain_ints > 0) {
                    getNextChunk();
                } else if (m_iterator.m_chunk.m_available < position)
                    m_iterator.m_chunk.m_available = position;
            }
        }
    }

    public void write_ulong_array(int[] value, int offset, int length)
    {
        write_long_array(value, offset, length);
    }

    public void write_longlong_array(long[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null long array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        // número de octetos que quedan por escribir en el chunk actual
        int free_octets = 0;

        // número de datos long que se pueden escribir enteros
        int longs_can_write = 0;

        int position = 0;
        long long_value = 0;

        // numero de longs que quedan
        int remain_longs = length;

        while (remain_longs > 0) {

            if (!alignment(CDR.LONGLONG_SIZE))
            {
                // encapsulation, the data does not fit complete
                long_value = value[offset++];

                m_data_buffer[0] = (byte) (long_value >>> 56);
                m_data_buffer[1] = (byte) (long_value >>> 48);
                m_data_buffer[2] = (byte) (long_value >>> 40);
                m_data_buffer[3] = (byte) (long_value >>> 32);
                m_data_buffer[4] = (byte) (long_value >>> 24);
                m_data_buffer[5] = (byte) (long_value >>> 16);
                m_data_buffer[6] = (byte) (long_value >>> 8);
                m_data_buffer[7] = (byte) long_value;

                write_octet_array(m_data_buffer, 0, CDR.LONGLONG_SIZE);

                remain_longs--;

            } else {
                position = m_iterator.m_position;

                free_octets = m_chunk_buffer.length - position;

                longs_can_write = 
                    java.lang.Math.min(remain_longs,
                                       free_octets / CDR.LONGLONG_SIZE);

                for (int i = longs_can_write; i > 0; i--) {
                    long_value = value[offset++];
                    m_chunk_buffer[position++] = (byte) (long_value >>> 56);
                    m_chunk_buffer[position++] = (byte) (long_value >>> 48);
                    m_chunk_buffer[position++] = (byte) (long_value >>> 40);
                    m_chunk_buffer[position++] = (byte) (long_value >>> 32);
                    m_chunk_buffer[position++] = (byte) (long_value >>> 24);
                    m_chunk_buffer[position++] = (byte) (long_value >>> 16);
                    m_chunk_buffer[position++] = (byte) (long_value >>> 8);
                    m_chunk_buffer[position++] = (byte) long_value;
                }

                remain_longs -= longs_can_write;
                m_iterator.m_position = position;

                if (remain_longs > 0) {
                    getNextChunk();
                } else if (m_iterator.m_chunk.m_available < position)
                    m_iterator.m_chunk.m_available = position;
            }
        }
    }

    public void write_ulonglong_array(long[] value, int offset, int length)
    {
        write_longlong_array(value, offset, length);
    }

    public void write_float_array(float[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null float array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        // número de octetos que quedan por escribir en el chunk actual
        int free_octets = 0;

        // número de datos float que se pueden escribir enteros
        int ints_can_write = 0;

        int position = 0;
        int int_value = 0;

        // numero de floats que quedan que quedan por escribir
        int remain_floats = length;

        while (remain_floats > 0) {

            if (!alignment(CDR.FLOAT_SIZE)) // encapsulación, el dato no cabe
            // completo
            {
                int_value = Float.floatToIntBits(value[offset++]);

                m_data_buffer[0] = (byte) (int_value >>> 24);
                m_data_buffer[1] = (byte) (int_value >>> 16);
                m_data_buffer[2] = (byte) (int_value >>> 8);
                m_data_buffer[3] = (byte) int_value;
                write_octet_array(m_data_buffer, 0, CDR.FLOAT_SIZE);

                remain_floats--;

            } else {
                position = m_iterator.m_position;

                free_octets = m_chunk_buffer.length - position;

                ints_can_write = 
                    java.lang.Math.min(remain_floats,
                                       free_octets / CDR.FLOAT_SIZE);

                for (int i = ints_can_write; i > 0; i--) {
                    int_value = Float.floatToIntBits(value[offset++]);

                    m_chunk_buffer[position++] = (byte) (int_value >>> 24);
                    m_chunk_buffer[position++] = (byte) (int_value >>> 16);
                    m_chunk_buffer[position++] = (byte) (int_value >>> 8);
                    m_chunk_buffer[position++] = (byte) int_value;
                }

                remain_floats -= ints_can_write;
                m_iterator.m_position = position;

                if (remain_floats > 0) {
                    getNextChunk();
                } else if (m_iterator.m_chunk.m_available < position)
                    m_iterator.m_chunk.m_available = position;
            }
        }
    }

    public void write_double_array(double[] value, int offset, int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null double array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new BAD_PARAM(
                                "value.length - offset must be less than length");

        // número de octetos que quedan por escribir en el chunk actual
        int free_octets = 0;

        // número de datos double que se pueden escribir enteros
        int doubles_can_write = 0;

        int position = 0;
        long long_value = 0;

        // numero de doubles que quedan
        int remain_doubles = length;

        while (remain_doubles > 0) {

            if (!alignment(CDR.DOUBLE_SIZE)) {
                long_value = Double.doubleToLongBits(value[offset++]);

                m_data_buffer[0] = (byte) (long_value >>> 56);
                m_data_buffer[1] = (byte) (long_value >>> 48);
                m_data_buffer[2] = (byte) (long_value >>> 40);
                m_data_buffer[3] = (byte) (long_value >>> 32);
                m_data_buffer[4] = (byte) (long_value >>> 24);
                m_data_buffer[5] = (byte) (long_value >>> 16);
                m_data_buffer[6] = (byte) (long_value >>> 8);
                m_data_buffer[7] = (byte) long_value;

                write_octet_array(m_data_buffer, 0, CDR.DOUBLE_SIZE);

                remain_doubles--;

            } else {
                position = m_iterator.m_position;

                free_octets = m_chunk_buffer.length - position;

                doubles_can_write = 
                    java.lang.Math.min(remain_doubles,
                                       free_octets / CDR.DOUBLE_SIZE);

                for (int i = doubles_can_write; i > 0; i--) {
                    long_value = Double.doubleToLongBits(value[offset++]);
                    m_chunk_buffer[position++] = (byte) (long_value >>> 56);
                    m_chunk_buffer[position++] = (byte) (long_value >>> 48);
                    m_chunk_buffer[position++] = (byte) (long_value >>> 40);
                    m_chunk_buffer[position++] = (byte) (long_value >>> 32);
                    m_chunk_buffer[position++] = (byte) (long_value >>> 24);
                    m_chunk_buffer[position++] = (byte) (long_value >>> 16);
                    m_chunk_buffer[position++] = (byte) (long_value >>> 8);
                    m_chunk_buffer[position++] = (byte) long_value;
                }

                remain_doubles -= doubles_can_write;
                m_iterator.m_position = position;

                if (remain_doubles > 0) {
                    getNextChunk();
                } else if (m_iterator.m_chunk.m_available < position)
                    m_iterator.m_chunk.m_available = position;
            }
        }
    }

    public void write_Object(org.omg.CORBA.Object value)
    {
        if (value == null) {
            es.tid.TIDorbj.core.iop.IOR.nullIOR().write(this);
            return;
        }

        if (value instanceof ValueBase)
            throw new org.omg.CORBA.NO_IMPLEMENT();

        if (value instanceof org.omg.CORBA.LocalObject)
            throw new MARSHAL("Impossible to marshall a local object.",
                              4, CompletionStatus.COMPLETED_NO);

        org.omg.CORBA.portable.Delegate delegate = 
            ((org.omg.CORBA.portable.ObjectImpl) value)
                                                                                              ._get_delegate();

        if (delegate instanceof CommunicationDelegate) {
            ((CommunicationDelegate) delegate).getReference().write(this);
        } else { // write ior from anothers's ORB Object
            IOR ior = 
                DefaultIOR.fromString(m_orb,
                               delegate.orb(value).object_to_string(value));
            ior.write(this);
        }
    }

    public void write_TypeCode(org.omg.CORBA.TypeCode value)
    {
        if (value == null)
            throw new BAD_PARAM("Null TypeCode reference.", 0,
                                CompletionStatus.COMPLETED_NO);

        TypeCodeMarshaler.marshal(value, this);
    }

    public void write_any(org.omg.CORBA.Any value)
    {
        if (value == null)
            throw new BAD_PARAM("Null any reference.", 0,
                                CompletionStatus.COMPLETED_NO);

        write_TypeCode(value.type());
        value.write_value(this);
    }

    //DataOutputStream
    public void write_any_array(org.omg.CORBA.Any[] value, int offset,
                                int length)
    {
        if (value == null)
            throw new BAD_PARAM("Null double array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        int last = offset + length;

        for (int i = offset; i < last; i++)
            write_any(value[i]);
    }

    public void write_Context(org.omg.CORBA.Context ctx,
                              org.omg.CORBA.ContextList contexts)
    {
        if (ctx == null)
            throw new BAD_PARAM("Null context reference.", 0,
                                CompletionStatus.COMPLETED_NO);

        ContextImpl.write(this, ctx, contexts);
    }

    /**
     * @deprecated Deprecated by CORBA 2.2
     */

    public void write_Principal(org.omg.CORBA.Principal value)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT("Deprecated by CORBA 2.2");
    }

    public void write_fixed(java.math.BigDecimal value)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public void write_fixed(org.omg.CORBA.Any value)
        throws org.omg.CORBA.BadFixedValue
    {
        if ((value == null)
            || (value.type().kind().value() != org.omg.CORBA.TCKind._tk_fixed))
            throw new org.omg.CORBA.BadFixedValue();

        value.write_value(this);
    }

    public void write_fixed_array(org.omg.CORBA.Any[] value, int offset,
                                  int length)
        throws org.omg.CORBA.BadFixedValue
    {
        if (value == null)
            throw new BAD_PARAM("Null double array reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        if (length < 0)
            throw new BAD_PARAM("length must be greater than zero");

        if (value.length - offset < length)
            throw new 
            	BAD_PARAM("value.length - offset must be less than length");

        int last = offset + length;

        for (int i = offset; i < last; i++)
            write_fixed(value[i]);
    }

    // CORBA 2.3

    public void write_value(java.io.Serializable value)
    {
        writeValueWithId(value, null);
    }

    public void write_value(java.io.Serializable value, java.lang.String rep_id)
    {
        writeValueWithId(value, rep_id);
    }

    protected void writeValueWithId(java.io.Serializable value,
                                    java.lang.String rep_id)
    {
        if (writeValueTags(value))
            return;

        if (value instanceof java.lang.String) {
            writeWStringValue((String) value);
        } else if (value instanceof org.omg.CORBA.portable.StreamableValue) {
            writeStreamableValue(
                (org.omg.CORBA.portable.StreamableValue) value);
        } else if (value instanceof org.omg.CORBA.portable.CustomValue) {
            writeCustomValue((org.omg.CORBA.portable.CustomValue) value);
        } else {
            initValueHandler();

            st_value_handler.writeValue(this, value);
        }
    }

    public void write_value(java.io.Serializable value, Class clz)
    {
        if (writeValueTags(value))
            return;

        if (value instanceof java.lang.String) {
            writeWStringValue((String) value);
        } else if (value instanceof org.omg.CORBA.portable.StreamableValue) {
            writeStreamableValue(
                (org.omg.CORBA.portable.StreamableValue) value);
        } else if (value instanceof org.omg.CORBA.portable.CustomValue) {
            writeCustomValue((org.omg.CORBA.portable.CustomValue) value);
        } else { // boxed value??

            java.lang.Object obj = null;

            try {
                java.lang.reflect.Constructor constructor = 
                    clz.getConstructor(new Class[0]);

                obj = constructor.newInstance(new Object[0]);

            }
            catch (Throwable th) {
                throw new org.omg.CORBA.UNKNOWN(th.toString());
            }

            if (obj instanceof org.omg.CORBA.portable.BoxedValueHelper) {
              writeBoxedValue(value,
                              (org.omg.CORBA.portable.BoxedValueHelper) value);
            } else {
                initValueHandler();
                ValueTypeInfo value_info = new ValueTypeInfo();
                String[] ids = { st_value_handler.getRMIRepositoryID(clz) };
                value_info.set_repository_ids(ids);
                value_info.write(this);
                st_value_handler.writeValue(this, value);
            }
        }
    }

    public void write_value(java.io.Serializable value,
                            org.omg.CORBA.portable.BoxedValueHelper factory)
    {
        if (writeValueTags(value))
            return;

        writeBoxedValue(value, factory);

    }

    //DataOutputStream
    public void write_Value(java.io.Serializable value)
    {
        write_value(value);
    }

    /**
     * Try to write null or an indirection tags
     * 
     * @return true if written or false otherwise
     */

    protected boolean writeValueTags(java.io.Serializable value)
    {
        // write null

        if (value == null) {
            ValueTypeInfo.write_null(this);
            return true;
        }

        PointerCDR previous_position = getContextCDR().lookupPosition(value);

        if (previous_position != null) {
            writeIndirection(previous_position);
            return true;
        }

        return false;
    }

    protected void writeWStringValue(String value)
    {
        ValueTypeInfo value_info = new ValueTypeInfo();

        // aligns to get the value position

        alignment(CDR.LONG_SIZE);

        // calculate the value position

        PointerCDR value_position = getPointer();

        value_info.write(this);

        write_wstring(value);

        getContextCDR().putObject(value, value_position);
    }

    protected void 
    	writeStreamableValue(org.omg.CORBA.portable.StreamableValue value)
    {
        ValueTypeInfo value_info = new ValueTypeInfo();

        value_info.set_repository_ids(value._truncatable_ids());

        // aligns to get the value position

        alignment(CDR.LONG_SIZE);

        // calculate the value position

        PointerCDR value_position = getPointer();

        value_info.write(this);

        value._write(this);

        getContextCDR().putObject(value, value_position);
    }

    protected void writeCustomValue(org.omg.CORBA.portable.CustomValue value)
    {
        ValueTypeInfo value_info = new ValueTypeInfo();

        value_info.set_repository_ids(value._truncatable_ids());

        // aligns to get the value position

        alignment(CDR.LONG_SIZE);

        // calculate the value position

        PointerCDR value_position = getPointer();

        value_info.write(this);

        value.marshal(this);

        getContextCDR().putObject(value, value_position);
    }

    protected void 
    	writeBoxedValue(java.io.Serializable value,
    	                org.omg.CORBA.portable.BoxedValueHelper factory)
    {
        ValueTypeInfo value_info = new ValueTypeInfo();

        String[] ids = { factory.get_id() };

        value_info.set_repository_ids(ids);

        // aligns to get the value position

        alignment(CDR.LONG_SIZE);

        // calculate the value position

        PointerCDR value_position = getPointer();

        value_info.write(this);

        factory.write_value(this, value);

        getContextCDR().putObject(value, value_position);
    }

    public void initValueHandler()
    {
        synchronized (this.getClass()) {
            if (st_value_handler == null)
                st_value_handler = javax.rmi.CORBA.Util.createValueHandler();
        }
    }

    public void write_abstract_interface(java.lang.Object object)
    {
        if (object == null)
            throw new org.omg.CORBA.BAD_PARAM("Null value reference");

        if (object instanceof org.omg.CORBA.Object) {
            org.omg.CORBA.Object ref = (org.omg.CORBA.Object) object;

            // is an object ref
            write_boolean(true);
            write_Object(ref);

        } else if (object instanceof java.io.Serializable) {
            write_boolean(false);
            write_value((java.io.Serializable) object);
        } else
            throw new 
            MARSHAL("Cannot marshal value: it does not implements " +
                    "java.io.Serializable or is not a org.omg.CORBA.Object");
    }

    //DataOutputStream
    public void write_Abstract(java.lang.Object value)
    {
        write_abstract_interface(value);
    }

    // TIDORB

    // write_referenceable_string for RepositoryId and URL

    protected void writeReferenceableString(String id)
    {
        PointerCDR previous_position = getContextCDR().lookupPosition(id);

        if (previous_position != null) {
            writeIndirection(previous_position);
            return;
        }

        // aligns to get the id position

        alignment(CDR.LONG_SIZE);

        // calculate the typecode position

        PointerCDR id_position = getPointer();

        write_string(id);

        getContextCDR().putObject(id, id_position);
    }

    public void writeIndirection(PointerCDR previous_position)
    {
        write_long(0xFFFFFFFF);

        // prevents that indirection value will be marshaled in the next chunk

        alignment(CDR.LONG_SIZE);

        // calculate the typecode position

        //    int headers_length = 0;

        // if message Calculate the headers size to subtract the value to the
        // Encapsulation size
        /*
         * if (get_message()) { int fragments = getCurrentChunkNum() -
         * previous_position.getNumChunk();
         * 
         * if(get_version() == GIOPVersion.VERSION_1_2) headers_length = fragments *
         * GIOPFragmentMessage.FRAGMENT_HEADER_SIZE_1_2; else if (get_version() ==
         * GIOPVersion.VERSION_1_1) headers_length = fragments *
         * GIOPFragmentMessage.FRAGMENT_HEADER_SIZE_1_1; else headers_length =
         * 0; }
         */
        AbsolutePosition indirection_position;

        indirection_position = getAbsolutePosition();

        int offset = previous_position.getAbsolutePosition().getValue()
                     - indirection_position.getValue();
        //    + headers_length;

        write_long(offset);

        return;
    }

    public int getCurrentChunkNum()
    {
        return m_iterator.m_current_chunk_num;
    }

    /*
     * public void getNextFragmentHeader() { if(!message) throw new
     * org.omg.CORBA.INTERNAL("Not it a message");
     * 
     * if(version == GIOPVersion.VERSION_1_0) throw new MARSHAL("End of Buffer: GIOP
     * 1.0 does not allows fragmentation");
     * 
     * iterator.nextChunk();
     * 
     * chunk_buffer = iterator.chunk.buffer; }
     */
    public void getNextChunk()
    {
        m_iterator.m_chunk.m_available = m_chunk_buffer.length;
        /*
         * if (message) { if(version == GIOPVersion.VERSION_1_0) { // throw new
         * MARSHAL("End of Buffer: GIOP 1.0 does not allows fragmentation");
         * iterator.bufferCDR.grow_1_0(); } else { if(iterator.current_chunk_num ==
         * (iterator.bufferCDR.getNumChunks() -1)) iterator.bufferCDR.grow();
         * 
         * iterator.nextChunk();
         * 
         * skip(GIOPHeader.HEADER_SIZE);
         * 
         * if(version == GIOPVersion.VERSION_1_2) {
         * GIOPFragmentMessage.skip_fragment_header_1_2(this); } } } else {
         */
        if (m_iterator.m_current_chunk_num 
            ==  (m_iterator.m_buffer_cdr.getNumChunks() - 1))
            m_iterator.m_buffer_cdr.grow();

        m_iterator.nextChunk();
        //    }

        m_chunk_buffer = m_iterator.m_chunk.m_buffer;
    }

    /**
     * Set the stream pointer to the next aligned position of given data type
     * 
     * @param type_size
     *            the type size in octets
     */
    protected void goNextAlignedPosition(int type_size)
    {
        int next_position = m_iterator.alignPosition(type_size);

        if (next_position < m_chunk_buffer.length) {
            m_iterator.m_position = next_position;
            return;
        }

        /*
         * if (message && (version == GIOPVersion.VERSION_1_0)) { getNextChunk();
         * iterator.position = next_position; return; }
         */

        if ((next_position > m_chunk_buffer.length)
            && (m_iterator.m_encapsulation)) {
            // continue the encapsulation aligment in next chunk
            int gap = next_position - m_iterator.m_position;
            skip(gap);
            alignment(CDR.OCTET_SIZE); // prevents next_position == length
            return;
        }

        getNextChunk();
    }

    /**
     * Set the stream pointer to the next aligned position of given data type
     * and sets it as the last available position
     * 
     * @param type_size
     *            the type size in octets
     */
    public void fixNextAlignedPosition(int type_size)
    {
        goNextAlignedPosition(type_size);

        if (m_iterator.m_position >= m_iterator.m_chunk.m_available)
            m_iterator.m_chunk.m_available = m_iterator.m_position + 1;
    }

    /**
     * Sets the writing pointer in the correct aligmnet for the data type. If
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
     */

    public boolean alignment(int type_size)
    {
        // set the aligned position

        goNextAlignedPosition(type_size);

        if (m_iterator.enoughSpace(type_size))
            return true;

        // there is no space for whole data bytes

        m_iterator.m_chunk.m_available = m_chunk_buffer.length;

        if (m_iterator.m_encapsulation)
            return false; // marshal byte per byte

        // marshaling in the main buffer, get next chunk
        getNextChunk();

        return true;
    }

    public void skip(long n)
    {
        if (n > 0L) {
            int chunk_left = m_chunk_buffer.length - m_iterator.m_position;

            if (chunk_left <= 0) {
                getNextChunk();
                skip(n);
            } else if (n <= chunk_left) {
                m_iterator.skip((int) n);
                if (m_iterator.m_position >= m_iterator.m_chunk.m_available) {
                    m_iterator.m_chunk.m_available = m_iterator.m_position + 1;
                }
            } else {
                getNextChunk();
                skip(n - chunk_left);
            }
        }
    }

    /**
     * Recycle the buffer pointered by the stream.
     * 
     * @see es.tid.TIDorbj.core.cdr.BufferCDR
     */
    public void recycle()
    {
        m_iterator.rewind();
        m_iterator.m_buffer_cdr.recycle();
        m_chunk_buffer = m_iterator.m_chunk.m_buffer;
        m_is_message = false;
    }
}