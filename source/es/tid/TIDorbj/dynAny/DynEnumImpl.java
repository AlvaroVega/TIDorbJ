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
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.TypeCodePackage.Bounds;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

import es.tid.TIDorbj.core.AnyImpl;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.cdr.CDR;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

/**
 * DynEnum implementation.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class DynEnumImpl extends DynSingleton
    implements org.omg.DynamicAny.DynEnum
{

    int m_enum_value;

    private DynEnumImpl(DynAnyFactoryImpl factory, TIDORB orb)
    {
        super(factory, orb);
        m_enum_value = 0;
    }

    /**
     * Constructor. Gets an any object for reading its value.
     * 
     * @param any
     *            the any value.
     * @throws org.omg.DynamicAny.DynAnyPackage.InvalidValue
     */

    protected DynEnumImpl(DynAnyFactoryImpl factory, TIDORB orb, Any any,
                          TypeCode real_type)
    {
        super(factory, orb);

        m_dyn_type = any.type();

        m_base_type = real_type;

        CDROutputStream out = new CDROutputStream(orb, CDR.ULONG_SIZE);
        any.write_value(out);
        CDRInputStream in = (CDRInputStream) out.create_input_stream();
        m_enum_value = in.read_ulong();
    }

    /**
     * Constructor. Gets a simple TypeCode to create a new value; It assumes
     * that the TypeCode contains is simple (octect, short ...).
     * 
     * @param type
     *            the new TypeCode value.
     * @exception org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode
     *                if the TypeCode is not basic.
     */

    protected DynEnumImpl(DynAnyFactoryImpl factory, TIDORB orb, TypeCode type,
                          TypeCode real_type)
    {
        super(factory, orb);
        m_dyn_type = type;
        m_base_type = real_type;
        m_enum_value = 0;
    }

    public String get_as_string()
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        try {
            return m_base_type.member_name(m_enum_value);
        }
        catch (Bounds bd) {
            return null;
        }
        catch (BadKind bk) {
            return null;
        }
    }

    public void set_as_string(String value)
        throws org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        try {
            int num_values = m_base_type.member_count();

            for (int i = 0; i < num_values; i++) {
                if (value.equals(type().member_name(i))) {
                    m_enum_value = i;
                    return;
                }
            }
            throw new InvalidValue("No member named " + value + ".");

        }
        catch (Bounds bd) { /* unreachable */
        }
        catch (BadKind bk) { /* unreachable */
        }
    }

    public int get_as_ulong()
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        return m_enum_value;
    }

    public void set_as_ulong(int value)
        throws org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        try {
            int count = m_base_type.member_count();

            if ((value < 0) || (value >= count))
                throw new InvalidValue(type().id() + " : value " + value
                                       + " out of range.");
        }
        catch (BadKind bk) { /* unreachable */
        }

        m_enum_value = value;
    }

    // Streamable operations

    public void _read(org.omg.CORBA.portable.InputStream in)
    {
        try {
            set_as_ulong(in.read_ulong());
        }
        catch (InvalidValue iv) {
            throw new MARSHAL(iv.toString());
        }
    }

    public void _write(org.omg.CORBA.portable.OutputStream out)
    {
        out.write_ulong(m_enum_value);
    }

    // DynAny Operations

    public void assign(org.omg.DynamicAny.DynAny dyn_any)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch
    {
        if (dyn_any == null)
            throw new BAD_PARAM("Null DynAny reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (!m_base_type.equivalent(dyn_any.type()))
            throw new TypeMismatch();

        if (dyn_any instanceof org.omg.DynamicAny.DynEnum)
            m_enum_value = 
                ((org.omg.DynamicAny.DynEnum) dyn_any).get_as_ulong();
        else
            // invalid error
            throw new TypeMismatch();
    }

    public void from_any(org.omg.CORBA.Any value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value == null)
            throw new BAD_PARAM("Null Any reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (!m_base_type.equivalent(value.type()))
            throw new TypeMismatch();

        try {
            _read(value.create_input_stream());
        }
        catch (MARSHAL m) {
            throw new InvalidValue();
        }
    }

    public org.omg.CORBA.Any to_any()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        AnyImpl new_value = (AnyImpl) m_orb.create_any();
        new_value.type(type());
        org.omg.CORBA.portable.OutputStream output;
        output = new_value.create_output_stream();
        _write(output);

        //	new_value.insert_Streamable(this);

        return new_value;
    }

    public boolean equal(org.omg.DynamicAny.DynAny dyn_any)
    {
        if (dyn_any == null)
            throw new BAD_PARAM("Null DynAny reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (!m_base_type.equivalent(dyn_any.type()))
            return false;

        if (dyn_any instanceof org.omg.DynamicAny.DynEnum)
            return (m_enum_value 
                    == ((org.omg.DynamicAny.DynEnum) dyn_any).get_as_ulong());
        else
            // invalid error
            throw new  BAD_PARAM("DynAny does not implements DynEnum when"
                                 + " its type is enum");

    }

    public org.omg.DynamicAny.DynAny copy()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        DynEnumImpl new_dyn = new DynEnumImpl(m_factory, m_orb);
        new_dyn.m_enum_value = m_enum_value;
        new_dyn.m_dyn_type = m_dyn_type;
        new_dyn.m_base_type = m_base_type;

        return new_dyn;
    }

    // OBJECT methods

    public boolean _is_a(java.lang.String repositoryIdentifier)
    {
        if (repositoryIdentifier == null)
            throw new BAD_PARAM("Null string reference");

        if (repositoryIdentifier.equals("IDL:omg.org/DynamicAny/DynEnum:1.0"))
            return true;

        return super._is_a(repositoryIdentifier);
    }
}