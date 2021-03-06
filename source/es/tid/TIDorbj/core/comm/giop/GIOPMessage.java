/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 478 $
* Date: $Date: 2011-04-29 16:42:47 +0200 (Fri, 29 Apr 2011) $
* Last modified by: $Author: avega $
*
* (C) Copyright 2004 Telef�nica Investigaci�n y Desarrollo
*     S.A.Unipersonal (Telef�nica I+D)
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
package es.tid.TIDorbj.core.comm.giop;

import org.omg.CORBA.INTERNAL;
import org.omg.Compression.CompressorIdLevel;

import es.tid.TIDorbj.core.cdr.BufferCDR;
import es.tid.TIDorbj.core.cdr.CDROutputStream;
import es.tid.TIDorbj.core.cdr.ChunkCDR;
import es.tid.TIDorbj.core.comm.Connection;

/**
 * Represents the 1.0, 1.1 and 1.2 GIOP version messages used in a IIOP
 * connection.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class GIOPMessage
{

    /**
     * Message Header
     */
    GIOPHeader m_header;

    /**
     * Message Buffer, including the message headers.
     */
    BufferCDR m_message_buffer;

    /**
     * Indicates if the message is ready to be send.
     */
    boolean m_message_completed;

    boolean m_headers_marshaled;

    CompressorIdLevel m_compressor;

    protected GIOPMessage(GIOPHeader header)
    {
        m_header = header;
        m_message_buffer = null;
        m_headers_marshaled = false;

        switch (header.getMsgType().m_value)
        {
            case MsgType._MessageError:
            case MsgType._CloseConnection:
                m_message_completed = true;
            break;
            default:
                m_message_completed = false;
        }
        m_compressor = new CompressorIdLevel((short)0, (short)0);
    }

    public String toString()
    {
        return m_header.toString();
    }

    public GIOPHeader getHeader()
    {
        return m_header;
    }

    /**
     * @return the message buffer including the message headers
     */

    public BufferCDR getMessageBuffer()
    {
        if (!m_message_completed)
            return null;

        if (!m_headers_marshaled)
            writeHeaders();

        return m_message_buffer;
    }

    /**
     * Sets the message_buffer where the message will be marshalled.
     */
    public void setMessageBuffer(BufferCDR cdr)
    {
        m_message_buffer = cdr;
    }

    public void setMessageCompleted(boolean value)
    {
        m_message_completed = value;
    }

    public boolean hasBody()
    {
        switch (m_header.getMsgType().m_value)
        {
            case MsgType._MessageError:
                return false;
            case MsgType._CloseConnection:
                return false;
            default:
                return true;
        }
    }

    //TODO: giop should not know anything about IIOPConnections!!
    public static ChunkCDR receiveChunk(Connection conn, GIOPHeader header,
                                        byte[] header_bytes)
    {
        // the buffer has also the header

        int body_size = header.getSize();

        byte[] buffer = new byte[body_size + GIOPHeader.HEADER_SIZE];

        // copy the header

        System.arraycopy(header_bytes, 0, buffer, 0, GIOPHeader.HEADER_SIZE);

        conn.read(buffer, GIOPHeader.HEADER_SIZE, body_size);

        return new ChunkCDR(buffer);

    }

    //TODO: giop should not know anything about IIOPConnections!!
    public void receiveBody(Connection conn, byte[] header_bytes)
    {
        m_message_buffer =
            new BufferCDR(receiveChunk(conn, m_header, header_bytes));

        m_message_completed = true;
        m_headers_marshaled = true;
    }


    public void setBody(BufferCDR buf)
    {
        m_message_buffer = buf;
        m_message_completed = true;
        m_headers_marshaled = true;
    }

    /**
     * Writes the header into the Message Chunks
     */
    protected void writeHeaders()
    {
        if (hasBody()) {
            if (!m_message_completed)
                throw new INTERNAL("Uncompleted Message: no body");

            if (!m_headers_marshaled) {
                CDROutputStream out = 
                    new CDROutputStream(null, m_message_buffer);

                out.setVersion(m_header.getVersion());

                // write message size = buffer size - 12 octets from header

                m_header.setSize(m_message_buffer.getAvailable()
                                 - GIOPHeader.HEADER_SIZE);

                m_header.write(out);

                out = null;

                m_headers_marshaled = true;
            }

        } else {
            if (!m_headers_marshaled) //header message
            {
                m_message_buffer = 
                    new BufferCDR(new byte[GIOPHeader.HEADER_SIZE]);
                CDROutputStream out = 
                    new CDROutputStream(null, m_message_buffer);
                m_header.write(out);
                out = null;
                m_headers_marshaled = true;

            }
        }
    }

    /**
     * Sends the message
     */
    public void send(Connection conn)
    {

        GIOPVersion header_version = m_header.getVersion();

        if (header_version == GIOPVersion.VERSION_1_2)
            conn.writeVersion1_2(getMessageBuffer());
        else if (header_version == GIOPVersion.VERSION_1_1)
            conn.writeVersion1_1(getMessageBuffer());
        else
            conn.writeVersion1_0(getMessageBuffer());
    }

    public CompressorIdLevel get_compressor() 
    {
        return m_compressor;
    } 

    public void set_compressor(CompressorIdLevel compressor)
    {
        m_compressor = compressor;
    }

}
