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

import java.math.BigDecimal;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.BAD_TYPECODE;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.portable.InputStream;
import org.omg.DynamicAny.DynFixed;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

import es.tid.TIDorbj.core.AnyImpl;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.util.FixedHolder;

/**
 * DynFixed implementation.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class DynFixedImpl extends DynSingleton
    implements org.omg.DynamicAny.DynFixed, org.omg.CORBA.portable.Streamable
{

    BigDecimal fixed_value;

    private DynFixedImpl(DynAnyFactoryImpl factory, TIDORB orb)
    {
        super(factory, orb);
        fixed_value = new BigDecimal(0.0D);
    }

    /**
     * Constructor. Gets an any object for reading its value.
     * 
     * @param any
     *            the any value.
     */

    protected DynFixedImpl(DynAnyFactoryImpl factory, TIDORB orb, Any any,
                           TypeCode real_type)
    {
        super(factory, orb, any.type(), real_type);

        InputStream in = any.create_input_stream();
        FixedHolder holder = new FixedHolder(m_base_type);
        holder._read(in);

        fixed_value = holder.value;
    }

    protected DynFixedImpl(DynAnyFactoryImpl factory, TIDORB orb,
                           TypeCode type, TypeCode real_type)
    {
        super(factory, orb, type, real_type);

        try {

            fixed_value = BigDecimal.valueOf(0, m_base_type.fixed_scale());

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE(bk.toString());
        }

    }

    // DynFixed operations

    public String get_value()
    {

        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        return fixed_value.toString();
    }

    public boolean set_value(java.lang.String val)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {

        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");
        if (val == null)
            throw new BAD_PARAM("null string value");

        BigDecimal value = new BigDecimal(val);
        try {
            if (value.scale() != m_base_type.fixed_scale())
                throw new InvalidValue("Bad fixed scale");
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE(bk.toString());
        }

        fixed_value = value;

        return true;
    }

    // Streamable operations

    public void _read(org.omg.CORBA.portable.InputStream in)
    {
        FixedHolder holder = new FixedHolder(m_base_type);
        holder._read(in);

        fixed_value = holder.value;
    }

    public void _write(org.omg.CORBA.portable.OutputStream out)
    {
        out.write_fixed(fixed_value);
    }

    public org.omg.CORBA.TypeCode type()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        return m_dyn_type;
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
            throw new org.omg.DynamicAny.DynAnyPackage.TypeMismatch();

        try {
            set_value(((DynFixed) dyn_any).get_value());
        }
        catch (InvalidValue iv) {
            throw new BAD_PARAM("Invalid value");
        }
        catch (ClassCastException cce) {
            throw new BAD_PARAM("Not a DynFixed value");
        }

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
        //new_value.insert_Streamable(this);

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

        if (dyn_any instanceof DynFixed) {
            return fixed_value.equals(
                new BigDecimal(new String(((DynFixed) dyn_any).get_value())));
        } else { // invalid error
            throw new BAD_PARAM("dyn_any value does not instantiate DynFixed!"
                                + " but its type is fixed;");
        }
    }

    protected void destroyNow()
    {
        super.destroyNow();
        fixed_value = null;
    }

    public org.omg.DynamicAny.DynAny copy()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        DynFixedImpl new_dyn = new DynFixedImpl(m_factory, m_orb);
        new_dyn.fixed_value = fixed_value;
        new_dyn.m_dyn_type = m_dyn_type;
        new_dyn.m_base_type = m_base_type;

        return new_dyn;
    }

    // OBJECT methods

    public boolean _is_a(java.lang.String repositoryIdentifier)
    {
        if (repositoryIdentifier == null)
            throw new BAD_PARAM("Null string reference");

        if (repositoryIdentifier.equals("IDL:omg.org/DynamicAny/DynFixed:1.0"))
            return true;

        return super._is_a(repositoryIdentifier);
    }
}