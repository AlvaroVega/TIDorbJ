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
 * Represents the absolute position in the CDR buffer of any data.
 * <p>
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */
public class AbsolutePosition
{

    /**
     * The absulute position in the buffer;
     */

    private int m_value;

    private AbsolutePosition(int position)
    {
        m_value = position;
    }

    public static AbsolutePosition createAbsolutePosition(int position)
    {
        if (position < 0)
            return null;
        return new AbsolutePosition(position);
    }

    public int getValue()
    {
        return m_value;
    }

    public int hashCode()
    {
        return m_value;
    }

    public boolean equals(java.lang.Object obj)
    {
        if (obj instanceof AbsolutePosition)
            return m_value == ((AbsolutePosition) obj).m_value;
        else
            return false;
    }

    /**
     * Calculates the offset from another Absolute Position.
     * 
     * @return the offset ( <0 if the other position is higher than the actual
     *         position or >0 otherwise)
     */

    public int offset(AbsolutePosition from)
    {
        return m_value - from.m_value;
    }

    public AbsolutePosition addOffset(int offset)
    {
        if (m_value + offset < 0)
            return null;
        else
            return new AbsolutePosition(m_value + offset);
    }

    /**
     * Alings the position for the given type size. The aligment is refered to
     * the absolute position.
     * 
     * @param type_size
     *            the data type size.
     */

    public void align(int type_size)
    {
        int gap, rest;
        rest = m_value % type_size;
        gap = (rest == 0) ? 0 : type_size - rest;
        m_value += gap;
    }
}