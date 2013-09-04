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
 * The <code>Indirection</code> class represents a node in the indirections to
 * the typecodes marshaled in the actual Encapsulation. If the node is in an
 * inner Encapsulation then it will references to the father encapsulation node.
 * 
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class IndirectionNode
{

    private java.util.Hashtable m_table;

    /**
     * Encapsulation Context.
     */

    private AbsolutePosition m_root_position;

    private AbsolutePosition m_lower_indirection;

    public IndirectionNode(AbsolutePosition start_position)
    {
        m_table = new Hashtable();
        m_root_position = start_position;
        m_lower_indirection = m_root_position;
    }

    public IndirectionNode(AbsolutePosition start_position,
                           java.util.Hashtable table)
    {
        m_table = table;// new Hashtable();
        m_root_position = start_position;
        m_lower_indirection = m_root_position;
    }

    /**
     * Search an indirected object in the buffer
     */
    public synchronized Object lookup(AbsolutePosition position)
    {

        if (position.getValue() < m_lower_indirection.getValue()) {
            m_lower_indirection = position;
            return null;
        }

        return m_table.get(position);
    }

    public void put(AbsolutePosition position, java.lang.Object obj)
    {
        m_table.put(position, obj);
    }

    public boolean hasExternalIndirections()
    {
        return m_lower_indirection.getValue() < m_root_position.getValue();
    }

    public java.util.Hashtable getTable()
    {
        return m_table;
    }
}