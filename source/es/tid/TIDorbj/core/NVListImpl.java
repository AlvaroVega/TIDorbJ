/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 14 $
* Date: $Date: 2006-02-22 13:30:10 +0100 (Wed, 22 Feb 2006) $
* Last modified by: $Author: iredondo $
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
package es.tid.TIDorbj.core;

import org.omg.CORBA.ARG_IN;
import org.omg.CORBA.ARG_OUT;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.Bounds;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

public class NVListImpl extends org.omg.CORBA.NVList
{
    TIDORB m_orb;

    protected java.util.Vector m_components;

    public NVListImpl(TIDORB orb)
    {
        m_orb = orb;
        m_components = new java.util.Vector();
    }

    public NVListImpl(TIDORB orb, int count)
    {
        m_components = new java.util.Vector(count);
    }

    public int count()
    {
        return m_components.size();
    }

    public org.omg.CORBA.NamedValue add(int flags)
    {
        NamedValueImpl n_value = NamedValueImpl.from_int(flags, "",
                                                         m_orb.create_any());
        m_components.addElement(n_value);
        return n_value;
    }

    public org.omg.CORBA.NamedValue add_item(String item_name, int flags)
    {
        NamedValueImpl n_value = NamedValueImpl.from_int(flags, item_name,
                                                         m_orb.create_any());
        m_components.addElement(n_value);
        return n_value;
    }

    public org.omg.CORBA.NamedValue add_value(String name,
                                              org.omg.CORBA.Any value,
                                              int flags)
    {
        NamedValueImpl n_value = NamedValueImpl.from_int(flags, name, value);
        m_components.addElement(n_value);
        return n_value;
    }

    public org.omg.CORBA.NamedValue item(int index)
        throws org.omg.CORBA.Bounds
    {
        if (index >= m_components.size())
            throw new Bounds();

        return (org.omg.CORBA.NamedValue) m_components.elementAt(index);
    }

    public void remove(int index)
        throws org.omg.CORBA.Bounds
    {
        if (index >= m_components.size())
            throw new Bounds();
        m_components.removeElementAt(index);
    }

    // TIDorb Operations

    public static void destroy(NVList list)
    {
        try {
            int size = list.count();
            for (int i = 0; i < size; i++)
                list.remove(i);
        }
        catch (org.omg.CORBA.Bounds b) {}
    }

    public static void assignOutArguments(NVList from_list, NVList to_list)
    {
        assignOutArguments(from_list, to_list, false);
    }

    public static void assignOutArguments(NVList from_list, NVList to_list,
                                          boolean wrap_anys)
    {
        int length = to_list.count();

        if (length < from_list.count())
            throw new MARSHAL("Invalid number of out arguments.", 0,
                              CompletionStatus.COMPLETED_NO);

        NamedValue to_nam_val = null;

        try {
            for (int i = 0; i < length; i++) {
                to_nam_val = to_list.item(i);
                if (to_nam_val.flags() != ARG_IN.value)
                    AnyImpl.assignValue(from_list.item(i).value(),
                                        to_nam_val.value(), wrap_anys);
            }

        }
        catch (Bounds bds) {
            throw new BAD_PARAM("Bad NVList");
        }
    }

    public static void assignInArguments(NVList from_list, NVList to_list)
    {
        assignInArguments(from_list, to_list, false);
    }

    public static void assignInArguments(NVList from_list, NVList to_list,
                                         boolean wrap_anys)
    {
        int length = to_list.count();

        if (length < from_list.count())
            throw new MARSHAL("Invalid number of out arguments.", 0,
                              CompletionStatus.COMPLETED_NO);

        NamedValue to_nam_val = null;

        try {
            for (int i = 0; i < length; i++) {
                to_nam_val = to_list.item(i);
                if (to_nam_val.flags() != ARG_OUT.value)
                    AnyImpl.assignValue(from_list.item(i).value(),
                                        to_nam_val.value(), wrap_anys);
            }

        }
        catch (Bounds bds) {
            throw new BAD_PARAM("Bad NVList");
        }

    }

    public static void readOutParams(NVList list, InputStream input)
    {
        if (list == null)
            return;

        int length = list.count();

        NamedValue nam_val = null;

        try {
            for (int i = 0; i < length; i++) {
                nam_val = list.item(i);
                if (nam_val.flags() != ARG_IN.value)
                    nam_val.value().read_value(input, nam_val.value().type());
            }
        }
        catch (Bounds bds) {
            throw new BAD_PARAM("Bad NVList");
        }

    }

    public static void writeOutParams(NVList list, OutputStream output)
    {
        if (list == null)
            return;

        int length = list.count();

        NamedValue nam_val = null;
        try {
            for (int i = 0; i < length; i++) {
                nam_val = list.item(i);
                if (nam_val.flags() != ARG_IN.value)
                    nam_val.value().write_value(output);
            }
        }
        catch (Bounds bds) {
            throw new BAD_PARAM("Bad NVList");
        }

    }

    public static void readInParams(NVList list, InputStream input)
    {
        if (list == null)
            return;

        int length = list.count();

        NamedValue nam_val = null;
        try {
            for (int i = 0; i < length; i++) {
                nam_val = list.item(i);
                if (nam_val.flags() != ARG_OUT.value)
                    nam_val.value().read_value(input, nam_val.value().type());
            }
        }
        catch (Bounds bds) {
            throw new BAD_PARAM("Bad NVList");
        }
    }

    public static void writeInParams(NVList list, OutputStream output)
    {
        if (list == null)
            return;

        int length = list.count();

        NamedValue nam_val = null;
        try {
            for (int i = 0; i < length; i++) {
                nam_val = list.item(i);
                if (nam_val.flags() != ARG_OUT.value)
                    nam_val.value().write_value(output);
            }
        }
        catch (Bounds bds) {
            throw new BAD_PARAM("Bad NVList");
        }
    }
    
    // AMI operations
    
    public static void setOutParamsAsIn(NVList list, RequestImpl replyHandlerRequest)
    {
        if (list == null)
            return;

        int length = list.count();

        NamedValue nam_val = null;

        try {
            for (int i = 0; i < length; i++) {
                nam_val = list.item(i);
                if (nam_val.flags() != ARG_IN.value) {
                    org.omg.CORBA.Any $arg = replyHandlerRequest.add_named_in_arg(nam_val.name());
                    $arg.type(nam_val.value().type());
                }
            }
        }
        catch (Bounds bds) {
            throw new BAD_PARAM("Bad NVList");
        }
    }

    
    public NVList removeInParams() {
    	NVList listWithoutIn = new NVListImpl(m_orb);
        int length = m_components.size();

        NamedValue nam_val = null;
        for (int i = 0; i < length; i++) {
        	nam_val = (NamedValue) m_components.elementAt(i);
            if (nam_val.flags() != ARG_IN.value)
            	listWithoutIn.add_value(nam_val.name(), nam_val.value(), nam_val.flags());
        }
        
        return listWithoutIn;
	}

    public NVList removeOutParams() {
    	NVList listWithoutOut = new NVListImpl(m_orb);
        int length = m_components.size();

        NamedValue nam_val = null;
        for (int i = 0; i < length; i++) {
        	nam_val = (NamedValue) m_components.elementAt(i);
            if (nam_val.flags() != ARG_OUT.value)
            	listWithoutOut.add_value(nam_val.name(), nam_val.value(), nam_val.flags());
        }
        
        return listWithoutOut;
	}
}