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
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

import es.tid.TIDorbj.core.AnyImpl;
import es.tid.TIDorbj.core.TIDORB;

/**
 * DynAny implementation that contains an any with an inner any.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class DynAnyWithAny extends DynSingleton
{

    // contained any as a DynAny

    DynAnyBase m_any_value;

    protected DynAnyWithAny(DynAnyFactoryImpl factory, TIDORB orb)
    {
        super(factory, orb);
        initValue();
    }

    /**
     * Constructor. Gets an any object for reading its value.
     * 
     * @param any
     *            the any value, assumes that the TypeCode is Basic.
     */

    protected DynAnyWithAny(DynAnyFactoryImpl factory, TIDORB orb, Any any,
                            TypeCode real_type)
    {
        super(factory, orb, any.type(), real_type);
        initValue(any.extract_any());
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

    protected DynAnyWithAny(DynAnyFactoryImpl factory, TIDORB orb,
                            TypeCode type, TypeCode real_type)
    {
        super(factory, orb, type, real_type);
        initValue();
    }

    // Streamable operations

    public void _read(org.omg.CORBA.portable.InputStream in)
    {
        initValue(in.read_any());
    }

    public void _write(org.omg.CORBA.portable.OutputStream out)
    {
        out.write_TypeCode(m_any_value.type());
        m_any_value._write(out);
    }

    // DynAny operations

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

        try {
            initValue(dyn_any.get_any());
        }
        catch (InvalidValue invalidValue) {}
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

        if (!m_dyn_type.equivalent(value.type()))
            throw new TypeMismatch();

        initValue(value.extract_any());
    }

    public org.omg.CORBA.Any to_any()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        AnyImpl new_value = (AnyImpl) m_orb.create_any();

        new_value.insert_any(m_any_value.to_any());

        new_value.setEquivalentType(m_dyn_type);

        return new_value;
    }

    public boolean equal(org.omg.DynamicAny.DynAny dyn_any)
    {
        if (dyn_any == null)
            throw new BAD_PARAM("Null DynAny reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (!m_base_type.equivalent(dyn_any.type()))
            return false;

        try {
            return m_any_value.equal(dyn_any.get_dyn_any());
        }
        catch (TypeMismatch typeMismatch) {}
        catch (InvalidValue invalidValue) {}

        return false;
    }

    protected void destroyNow()
    {
        super.destroyNow();
        if (m_any_value != null) {
            m_any_value.destroyNow();
            m_any_value = null;
        }
    }

    public org.omg.DynamicAny.DynAny copy()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        DynAnyWithAny new_dyn = new DynAnyWithAny(m_factory, m_orb, m_dyn_type,
                                                  m_base_type);

        try {
            new_dyn.assign(this);
        }
        catch (TypeMismatch tm) {}

        return new_dyn;
    }

    public void insert_any(org.omg.CORBA.Any value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value == null)
            throw new BAD_PARAM("Null Any reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        initValue(value);
    }

    public void insert_dyn_any(org.omg.DynamicAny.DynAny value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value == null)
            throw new BAD_PARAM("Null DynAny reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        initValue(value.to_any());
    }

    public org.omg.CORBA.Any get_any()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        return m_any_value.to_any();
    }

    public synchronized org.omg.DynamicAny.DynAny get_dyn_any()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_any)
            throw new TypeMismatch("Unexpected type");

        return m_any_value;
    }

    /**
     * Initialize the content value with the default value of the type.
     */

    protected void initValue()
    {
        AnyImpl val = (AnyImpl) m_orb.create_any();
        initValue(val);
    }

    protected void initValue(Any any)
    {
        if (m_any_value != null) {
            m_any_value.destroyNow();
        }

        try {

            m_any_value = (DynAnyBase) m_factory.create_dyn_any(any);
            m_any_value.setUserDestroy(false);

        }
        catch (InconsistentTypeCode inconsistentTypeCode) {}
    }

}