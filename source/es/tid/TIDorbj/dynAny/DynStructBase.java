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
package es.tid.TIDorbj.dynAny;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_TYPECODE;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.TypeCodePackage.Bounds;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.NameDynAnyPair;
import org.omg.DynamicAny.NameValuePair;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

import es.tid.TIDorbj.core.TIDORB;

/**
 * DynStructBase implementation. Base for Struct, Exception and ValueType.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public abstract class DynStructBase extends DynComposite
{

    /**
     * Empty Constructor for generate copies.
     */

    protected DynStructBase(DynAnyFactoryImpl factory, TIDORB orb)
    {
        super(factory, orb);
    }

    /**
     * Constructor. Gets an any object for reading its value. It assumes that
     * the any contains an struct TypeCode.
     * 
     * @param any
     *            the any value.
     */

    protected DynStructBase(DynAnyFactoryImpl factory, TIDORB orb, Any any,
                            TypeCode real_type)
    {
        super(factory, orb, any, real_type);

        try {
            m_component_count = real_type.member_count();
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE();
        }

        if (m_component_count == 0) {
            m_current_index = -1;
        } else {
            m_current_index = 0;
        }

    }

    /**
     * Constructor. Gets a simple TypeCode to create a new value. Warning: It
     * assumes that the TypeCode is tk_struct or tk_value (for DynValueImpl that
     * extends DynStructBase)
     * 
     * @param type
     *            the TypeCode value.
     */

    protected DynStructBase(DynAnyFactoryImpl factory, TIDORB orb,
                            TypeCode type, TypeCode real_type)
    {
        super(factory, orb, type, real_type);
        try {
            m_component_count = real_type.member_count();
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE();
        }

        if (m_component_count == 0) {
            m_current_index = -1;
        } else {
            m_current_index = 0;
        }

    }

    public String current_member_name()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("No member (current = -1)");

        // at this point alwais curremt_member is valid
        try {
            return m_base_type.member_name(m_current_index);
        }
        catch (BadKind bk) {
            /* unreachable */
            throw new BAD_TYPECODE();
        }
        catch (Bounds bd) {
            /* unreachable */
            throw new INTERNAL(bd.toString());
        }
    }

    public org.omg.CORBA.TCKind current_member_kind()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("No member (current = -1)");

        try {
            return m_base_type.member_type(m_current_index).kind();
        }
        catch (BadKind bk) {
            /* unreachable */
            throw new TypeMismatch(bk.toString());
        }
        catch (Bounds bd) {
            /* unreachable */
            throw new TypeMismatch(bd.toString());
        }

    }

    public org.omg.DynamicAny.NameValuePair[] get_members()
    {

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        try {

            Any[] any_members = super.get_elements();

            NameValuePair[] members = new NameValuePair[m_component_count];

            for (int i = 0; i < m_component_count; i++) {

                members[i] = new NameValuePair(m_base_type.member_name(i),
                                               any_members[i]);
            }

            return members;

        }
        catch (BadKind bk) {
            /* unreachable */
            throw new BAD_TYPECODE(bk.toString());

        }
        catch (Bounds bd) {
            /* unreachable */
            throw new INTERNAL(bd.toString());
        }

    }

    public void set_members(org.omg.DynamicAny.NameValuePair[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        try {
            if (m_destroyed)
                throw new OBJECT_NOT_EXIST("DynAny destroyed.");

            reset();

            if (value.length != m_component_count)
                throw new TypeMismatch("Invalid number of members: "
                                       + m_component_count + " expected, not "
                                       + value.length);

            Any[] members_values = new Any[m_component_count];

            for (int i = 0; i < m_component_count; i++) {
                members_values[i] = value[i].value;
                if (!m_base_type.member_name(i).equals(value[i].id))
                    throw new InvalidValue("Invalid member name, "
                                           + m_base_type.member_name(i)
                                           + "expected, not " + value[i].id);
            }

            set_elements(members_values);

        }
        catch (BadKind bk) {
            /* unreachable */
            throw new BAD_TYPECODE(bk.toString());

        }
        catch (Bounds bd) {
            /* unreachable */
            throw new INTERNAL(bd.toString());
        }

    }

    public org.omg.DynamicAny.NameDynAnyPair[] get_members_as_dyn_any()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        try {

            DynAny[] dyns = super.get_elements_as_dyn_any();

            NameDynAnyPair[] members = new NameDynAnyPair[m_component_count];

            for (int i = 0; i < m_component_count; i++)
                members[i] = new NameDynAnyPair(m_base_type.member_name(i),
                                                dyns[i]);

            return members;

        }
        catch (BadKind bk) {
            /* unreachable */
            throw new BAD_TYPECODE(bk.toString());
        }
        catch (Bounds bd) {
            /* unreachable */
            throw new INTERNAL(bd.toString());
        }
    }

    public void 
    	set_members_as_dyn_any(org.omg.DynamicAny.NameDynAnyPair[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value.length != m_component_count)
            throw new TypeMismatch("Invalid number of members: "
                                   + m_component_count + " expected, not "
                                   + value.length);

        DynAny[] members_values = new DynAny[m_component_count];

        try {
            for (int i = 0; i < m_component_count; i++) {
                members_values[i] = value[i].value;
                if (!m_base_type.member_name(i).equals(value[i].id))
                    throw new InvalidValue("Invalid member name, "
                                           + m_base_type.member_name(i)
                                           + "expected, not " + value[i].id);
            }
        }
        catch (BadKind bk) {
            /* unreachable */
            throw new BAD_TYPECODE(bk.toString());
        }
        catch (Bounds bd) {
            /* unreachable */
            throw new INTERNAL(bd.toString());
        }

        set_elements_as_dyn_any(members_values);
    }

    protected TypeCode getComponentType(int position)
    {
        try {
            return m_base_type.member_type(position);
        }
        catch (BadKind bk) {
            /* unreachable */
            throw new BAD_TYPECODE(bk.toString());
        }
        catch (Bounds bd) {
            /* unreachable */
            throw new INTERNAL(bd.toString());
        }

    }
}