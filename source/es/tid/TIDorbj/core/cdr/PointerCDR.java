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
 * PointerCDR class points up a buffer position for future marshaling and
 * unmarshaling.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class PointerCDR
{
    /**
     * Buffer pointered.
     */
    private BufferCDR m_buffer;

    /**
     * Chunk number of the buffer.
     */
    private int m_num_chunk;

    /**
     * Position in the chunk
     */
    private int m_position;

    /**
     * Absolute position in the buffer.
     */
    private AbsolutePosition m_absolute_position;

    /**
     * Constructor: creates a pointer to position of a chunk in a buffer.
     */

    public PointerCDR(BufferCDR buffer, int num_chunk, int position)
    {
        this.m_buffer = buffer;
        this.m_num_chunk = num_chunk;
        this.m_position = position;
        m_absolute_position = null;
    }

    public BufferCDR getBuffer()
    {
        return m_buffer;
    }

    public int getNumChunk()
    {
        return m_num_chunk;
    }

    public int getPosition()
    {
        return m_position;
    }

    /**
     * Calculates the absolute position of a pointer from the begining of the
     * buffer.
     * 
     * @return the absulte position from the begining of the buffer.
     */

    public AbsolutePosition getAbsolutePosition()
    {

        if (m_absolute_position == null) {

            int total_position = 0;
            // add all the precedent chunks sizes
            for (int i = 0; i < m_num_chunk; i++)
                total_position += m_buffer.getChunk(i).getLength();
            // add the actual chunk position
            total_position += m_position;
            m_absolute_position = 
                AbsolutePosition.createAbsolutePosition(total_position);
        }
        return m_absolute_position;
    }

    /**
     * calculates the hash code of the pointer for a Hashtable. The hash code is
     * the absolute position in the buffer that is a unique value in the buffer.
     * 
     * @return the hash code.
     */

    public int hashCode()
    {
        return getAbsolutePosition().getValue();
    }
}