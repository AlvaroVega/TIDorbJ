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

import java.util.Hashtable;

/**
 * The <code>PositionNode</code> class represents a table with the identifier
 * and the position of the objects that has been marshaled in a buffer.
 * <p>
 * The table entries are the Object identifier and the AbsolutePosition of the
 * Object.
 * <p>
 * There is a <code>PositionNode</code> per encapsulation, an they are linked
 * to the father encausulation node throght the Encapsulation contexts.
 * 
 * <p>
 * Copyright 2001 Telef&oacute;nica I+D. Printed in Spain (Europe). All Rights
 * Reserved.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class PositionsNode
{

    /**
     * Hashtable that contains Absolute positions of the TypeCodes. The keys are
     * the repository ids.
     */
    private Hashtable m_table;

    /**
     * Position at the beginning of the encapsulation.
     */
    private AbsolutePosition m_root_position;

    /**
     * Lower Inidirected in the encapsulation sequence that has been looking up.
     */
    private AbsolutePosition m_lower_indirection;

    public PositionsNode(AbsolutePosition type_code_position)
    {
        m_table = null;
        m_root_position = type_code_position;
        m_lower_indirection = m_root_position;
    }

    /**
     * Looks up if an <code>Object</code> has been yet marshaled in a stream.
     * If the object has been marshaled in a father encapsulation, the it will
     * remark it at the <code>lower_indirection</code>
     * 
     * @param id
     *            the Object Id of the marshaled Object.
     */
    public synchronized PointerCDR lookup(java.lang.Object obj)
    {
        // search in own table
        if (m_table != null)
            return (PointerCDR) m_table.get(obj);

        return null;
    }

    /**
     * notify the node that a indirection has been set thru this node
     * 
     * @return true if the indirection pass through
     */
    public synchronized void indirection(AbsolutePosition position)
    {
        if ((position != null)
            && (position.getValue() < m_lower_indirection.getValue())) {
            m_lower_indirection = position;
        }
    }

    /**
     * Saves a <code>TypeCode</code> position in the table.
     * 
     * @param id
     *            the Object Id of the marshaled object
     * @param position
     *            the object position in the buffer
     */

    public synchronized void put(java.lang.Object id, PointerCDR position)
    {
        if (m_table == null)
            m_table = new Hashtable();

        m_table.put(id, position);
    }

    /**
     * Used to calculate if an marshaled TypeCode has any indirection out of the
     * encapsulation. This means that a TypeCode encapsulation may or not be
     * reused.
     * 
     * @return whether or not has any indirection out of the encapsulation.
     */
    public boolean hasExternalIndirections()
    {
        return m_lower_indirection.getValue() < m_root_position.getValue();
    }
}