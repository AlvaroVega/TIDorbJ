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
package es.tid.TIDorbj.core;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.Bounds;
import org.omg.CORBA.CTX_RESTRICT_SCOPE;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.Context;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.TCKind;

import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

/**
 * TIDorb ContextImpl pseudobject implementation.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class ContextImpl extends org.omg.CORBA.Context
{

    public ContextImpl(TIDORB orb, String name)
    {
        this(orb, name, null);
    }

    public ContextImpl(TIDORB orb, String name, Context parent)
    {
        m_orb = orb;
        m_name = name;
        m_parent = parent;
        m_values = null;
        m_childs = null;
    }

    public String context_name()
    {
        return m_name;
    }

    public org.omg.CORBA.Context parent()
    {
        return m_parent;
    }

    public org.omg.CORBA.Context create_child(String child_context_name)
    {
        if (child_context_name == null) {

            throw new BAD_PARAM("Null string reference", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        ContextImpl child = new ContextImpl(m_orb, child_context_name, this);

        if (m_childs == null) {
            m_childs = new java.util.Vector();
        }
        m_childs.addElement(child);
        return child;
    }

    public void set_one_value(String prop_name, org.omg.CORBA.Any value)
    {
        if ((prop_name == null) || (value == null)) {
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        if (m_values == null) {
            m_values = new NVListImpl(m_orb);
        }

        if (value.type().kind().value() != TCKind._tk_string) {
            throw new BAD_PARAM("Value must have a string TypeCode.");
        }
        // new value
        m_values.add_value(prop_name, value, 0);
    }

    public void set_values(org.omg.CORBA.NVList values)
    {
        if (values == null) {
            throw new BAD_PARAM("Null NVList reference", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        m_values = new NVListImpl(m_orb);

        int list_size = values.count();
        try {
            for (int i = 0; i < list_size; i++) {
                set_one_value(values.item(i).name(), values.item(i).value());
            }
        }
        catch (Bounds bds) {}
    }

    public void delete_values(String prop_name)
    {
        if (prop_name == null)
            throw new BAD_PARAM("Null string reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_values != null) {
            int list_size = m_values.count();
            try {
                for (int i = 0; i < list_size; i++) {
                    if (prop_name.equals(m_values.item(i).name())) {
                        m_values.remove(i);
                    }
                }
            }
            catch (Bounds bds) {}
        }
    }

    public org.omg.CORBA.NVList get_values(String start_scope, int op_flags,
                                           String pattern)
    {
        if ((start_scope == null) || (pattern == null)) {
            throw new BAD_PARAM("Null string reference", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        NVListImpl list = new NVListImpl(m_orb);

        if (start_scope.equals(m_name)) {
            int list_size = m_values.count();

            try {
                for (int i = 0; i < list_size; i++) {

                    if (matchPattern(m_values.item(i).name(), pattern)) {

                        list.add_value(m_values.item(i).name(), m_values
                            .item(i).value(), 0);
                    }
                }
            }
            catch (Bounds bds) {};
        }

        if ((m_childs != null) && (op_flags != CTX_RESTRICT_SCOPE.value)) {
            int child_size = m_childs.size();
            try {
                NVList aux_list;
                int list_size;

                for (int i = 0; i < child_size; i++) {
                    aux_list = ((Context) m_childs.elementAt(i))
                        .get_values(start_scope, op_flags, pattern);
                    list_size = aux_list.count();

                    for (int j = 0; j < list_size; j++) {
                        list.add_value(aux_list.item(i).name(), aux_list
                            .item(i).value(), 0);
                    }
                }
            }
            catch (Bounds bds) {}

        }

        return list;
    }

    public String item(int i)
        throws org.omg.CORBA.Bounds
    {
        return (m_values.item(i)).value().extract_string();
    }

    protected boolean matchPattern(String name, String pattern)
    {
        if (pattern.equals("*")) {
            return true;
        }

        int position = pattern.indexOf("*", 0);
        if (position == -1) {
            return name.equals(pattern);
        }
        if (position > name.length()) {
            return false;
        }

        String sub1 = pattern.substring(0, position);
        String sub2 = name.substring(0, position);
        return sub1.equals(sub2);

    }

    public static ContextImpl read(CDRInputStream input)
    {
        int pair_count = input.read_ulong();

        if (pair_count == 0) {
            return null;
        }

        if ((pair_count < 1) || (pair_count % 2 != 0)) {
            throw new MARSHAL("Malformed context name-value pairs", 0,
                              CompletionStatus.COMPLETED_NO);
        }

        int num_values = pair_count / 2;

        ContextImpl context = new ContextImpl((TIDORB) input.orb(), "");

        String name;
        Any value;

        for (int i = 0; i < num_values; i++) {
            name = input.read_string();
            value = input.orb().create_any();

            value.insert_string(input.read_string());

            context.set_one_value(name, value);
        }

        return context;
    }

    public static void write(CDROutputStream output, org.omg.CORBA.Context ctx,
                             org.omg.CORBA.ContextList contexts)
    {
        int contexts_count = contexts.count();

        if (contexts_count == 0) {
            //no context
            output.write_ulong(0);
            return;
        }

        java.util.Vector nv_context = new java.util.Vector();

        try {
            NVList aux_list = null;
            NamedValue nam_val = null;
            int aux_list_size = 0;

            for (int i = 0; i < contexts_count; i++) {
                aux_list = ctx.get_values("", 0, contexts.item(i));
                aux_list_size = aux_list.count();
                for (int j = 0; j < aux_list_size; j++) {
                    nam_val = aux_list.item(j);
                    nv_context.addElement(nam_val.name());
                    nv_context.addElement(nam_val.value().extract_string());
                }

            }
        }
        catch (Bounds bds) {}

        int total_ctx_size = nv_context.size();

        output.write_ulong(total_ctx_size);

        for (int i = 0; i < total_ctx_size; i++) {
            output.write_string((String) nv_context.elementAt(i));
        }
    }

    // members

    protected TIDORB m_orb;

    protected String m_name;

    protected Context m_parent;

    protected NVList m_values;

    protected java.util.Vector m_childs;

}