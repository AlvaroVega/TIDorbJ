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
 * Represents an correction aligment offset from a position in the buffer of an
 * encapsulation. This aligment offset is the number of positions that the
 * beginning of the encapsulation needs to be aligned to the maximun data size
 * (longlong) representing the 0 position.
 * <p>
 * The offset is calculated by the factory operation
 * <code> calculateOffset</code>:
 * <P>
 * The offset value is defined in a range of <code>[0..MAX_ALIGNMENT-1]</code>.
 * offset = CDR.MAX_ALIGNMENT- (actual_positon % CDR.MAX_ALIGNMENT) The Aligment
 * offet requires the GIOP message size to be multiple of
 * <code>CDR.MAX_ALIGNMENT</code>, that is, 8, for allowing fragmentation.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

class AlignmentOffset
{

    /**
     * The offset value. It must be <code>[0..MAX_ALIGNMENT-1]</code>.
     */
    public int m_value;

    /**
     * Private constructor. Use calculateOffsetFrom.
     */
    private AlignmentOffset(int offset)
    {
        m_value = offset;
    }

    /**
     * Object Factory, that calculates the correct offset for a position.
     */

    public static AlignmentOffset calculateOffsetFrom(int position)
    {
        int offset = (CDR.MAX_ALIGNMENT - (position % CDR.MAX_ALIGNMENT));
        // makes the offset 0 if its the MAX_ALIGNMENT
        if (offset == CDR.MAX_ALIGNMENT)
            offset = 0;

        return new AlignmentOffset(offset);
    }

}