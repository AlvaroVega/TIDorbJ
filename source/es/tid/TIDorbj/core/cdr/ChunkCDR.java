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

/**
 * Represents a segment of a CDR buffer. It controls a marshaling byte buffer
 * that can be send or received in a IIOP message.
 * <p>
 * Copyright 2000 Telef&oacute;nica I+D. Printed in Spain (Europe). All Rights
 * Reserved.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class ChunkCDR
{

    /**
     * framgments of the chunk.
     */
    protected byte[] m_buffer;

    /**
     * Represents the amount of data available. Available is equal or lower than
     * chunk length.
     */
    protected int m_available;

    /**
     * The starting of the chunk has an message header
     */
    protected boolean m_with_message_header;

    /**
     * Constructor, gets the control of the byte array. It will have only a
     * fragment and it will not be increaseable.
     */
    public ChunkCDR(byte[] byte_array)
    {
        m_buffer = byte_array;
        m_available = m_buffer.length;
        m_with_message_header = false;
    }

    /**
     * Constructor, gets the control of the byte array. It will have only a
     * fragment and it will not be increaseable.
     */
    public ChunkCDR(int fixed_size)
    {
        m_buffer = new byte[fixed_size];
        m_available = 0;
        m_with_message_header = false;
    }

    public boolean hasHeader()
    {
        return m_with_message_header;
    }

    public void setHeader(boolean value)
    {
        m_with_message_header = value;
    }

    /**
     * @return the byte array associated to the buffer.
     */
    public byte[] getBuffer()
    {
        return m_buffer;
    }

    /**
     * Sets the available bytes to 0. It is used to reuse the chunk buffer for
     * new data.
     */
    public void recycle()
    {
        m_available = 0;
    }

    /**
     * Sets the available bytes to the <code>value</code> position. An error
     * can appear if the value is higher than the buffer length.
     * 
     * @return <code>true</code> if OK or <code>false</code> on error.
     */
    public boolean setAvailable(int value)
    {
        if (value <= m_buffer.length) {
            m_available = value;
            return true;
        }
        return false;
    }

    /**
     * @return The available bytes.
     */

    public int getAvailable()
    {
        return m_available;
    }

    /**
     * @return The lentgh ofthe byte buffer.
     */

    public int getLength()
    {
        return m_buffer.length;
    }

    public void setBuffer(byte[] new_buffer)
    {
        m_buffer = null;
        m_buffer = new_buffer;
    }
}