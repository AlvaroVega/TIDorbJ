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
package es.tid.TIDorbj.core.util;

public class LinkedList
{

    protected static int NULL = -1;

    public LinkedList(int maximumSize)
    {
        m_maximum_size = maximumSize;
        m_next = new int[maximumSize];
        m_previous = new int[maximumSize];
        m_free_indexes = new int[maximumSize];
        m_objects = new Object[maximumSize];
        m_last_free = maximumSize - 1;
        for (int i = 0; i < maximumSize; i++) {
            m_free_indexes[i] = i;
        }
        m_first = NULL;
        m_last = NULL;
    }

    public void append(Object object)
        throws Exception
    {
        if (m_last_free < 0) {
            throw new Exception("Not enough space in linked list of size "
                                + m_maximum_size + "!!");
        }
        int index = m_free_indexes[m_last_free];
        m_last_free -= 1;
        if (m_first >= 0) {
            m_next[m_last] = index;
            m_objects[index] = object;
            m_next[index] = NULL;
            m_previous[index] = m_last;
            m_last = index;
        } else {
            m_first = index;
            m_last = index;
            m_objects[index] = object;
            m_next[index] = NULL;
            m_previous[index] = NULL;
        }
    }

    public Object removeFirst()
    {
        return internalRemove(m_first);
    }

    public Object removeLast()
    {
        return internalRemove(m_last);
    }

    public java.util.Enumeration elements()
    {
        return new LinkedListEnumeration(this);
    }

    public int getSize()
    {
        return m_maximum_size - m_last_free - 1;
    }

    public int getMaximumSize()
    {
        return m_maximum_size;
    }

    public void dump(java.io.PrintStream os)
    {
        int i = m_first;
        os.print("[");
        while (i > NULL) {
            os.print(m_objects[i]);
            i = m_next[i];
            if (i > NULL)
                os.print(", ");
        }
        os.println("]");
    }

    /**
     * @param pos
     *            internal index of object to remove (it must be a legal
     *            position)
     */
    protected Object internalRemove(int pos)
    {
        Object removed = m_objects[pos];
        if (pos != m_first)
            m_next[m_previous[pos]] = m_next[pos];
        else
            m_first = m_next[pos];
        if (pos != m_last)
            m_previous[m_next[pos]] = m_previous[pos];
        else
            m_last = m_previous[pos];
        m_last_free += 1;
        m_free_indexes[m_last_free] = pos;
        return removed;
    }

    protected int getLast()
    {
        return m_last;
    }

    protected int getFirst()
    {
        return m_first;
    }

    protected int getNext(int index)
    {
        return m_next[index];
    }

    protected int getPrevious(int index)
    {
        return m_previous[index];
    }

    protected Object getObjectAt(int index)
    {
        return m_objects[index];
    }

    // Maximum size
    private int m_maximum_size;

    // Arrays for objects and indexes
    private int[] m_next = null;

    private int[] m_previous = null;

    private int[] m_free_indexes = null;

    private Object[] m_objects = null;

    // Indexes
    private int m_last_free;

    private int m_first;

    private int m_last;

    /**
     * Enumeration support.
     */
    protected class LinkedListEnumeration
        implements java.util.Enumeration
    {

        public LinkedListEnumeration(LinkedList list)
        {
            _list = list;
            _index = _list.m_first;
        }

        public boolean hasMoreElements()
        {
            return (_index != NULL);
        }

        public Object nextElement()
        {
            int i = _index;
            _index = _list.m_next[_index];
            return _list.m_objects[i];
        }

        private int _index;

        private LinkedList _list;
    }

    // Main - test method
    /*
     * public static void main(String[] args) { LinkedList l = new
     * LinkedList(10); l.append(new Integer(1)); l.append(new Integer(2));
     * l.append(new Integer(3)); l.append(new Integer(4)); l.append(new
     * Integer(5)); l.append(new Integer(6)); l.append(new Integer(7));
     * l.append(new Integer(8)); l.append(new Integer(9)); l.append(new
     * Integer(10)); System.out.println("-------"); l.dump(System.out);
     * l.removeLast(); System.out.println("-------"); l.dump(System.out);
     * l.removeFirst(); System.out.println("-------"); l.dump(System.out);
     * l.append(new Integer(11)); System.out.println("-------");
     * l.dump(System.out); l.append(new Integer(12));
     * System.out.println("-------"); l.dump(System.out); l.removeFirst();
     * System.out.println("-------"); l.dump(System.out); l.removeLast();
     * System.out.println("-------"); l.dump(System.out);
     * System.out.println("Tam: " + l.getSize()); }
     */
}