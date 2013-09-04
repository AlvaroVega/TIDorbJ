/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 18 $
* Date: $Date: 2006-05-26 10:56:46 +0200 (Fri, 26 May 2006) $
* Last modified by: $Author: scheca $
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
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

import es.tid.TIDorbj.core.AnyImpl;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.cdr.CDR;
import es.tid.TIDorbj.core.util.StringHolder;
import es.tid.TIDorbj.core.util.WStringHolder;

/**
 * DynAny implementation.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class DynAnyImpl extends DynAnyBase
{
    /**
     * Primitive type value.
     */

    AnyImpl m_dyn_value;

    protected DynAnyImpl(DynAnyFactoryImpl factory, TIDORB orb)
    {
        super(factory, orb);
        m_dyn_value = null;
    }

    /**
     * Constructor. Gets an any object for reading its value.
     * 
     * @param any
     *            the any value, assumes that the TypeCode is Basic.
     */

    protected DynAnyImpl(DynAnyFactoryImpl factory, TIDORB orb, Any any,
                         TypeCode real_type)
    {
        super(factory, orb, any.type(), real_type);

        initValue(any);
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

    protected DynAnyImpl(DynAnyFactoryImpl factory, TIDORB orb, TypeCode type,
                         TypeCode real_type)
    {
        super(factory, orb, type, real_type);

        initValue();
    }

    // Streamable operations

    public void _read(org.omg.CORBA.portable.InputStream in)
    {
        m_dyn_value.read_value(in, m_base_type);
    }

    public void _write(org.omg.CORBA.portable.OutputStream out)
    {
        m_dyn_value.write_value(out);
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
            switch (m_base_type.kind().value())
            {
                case TCKind._tk_null:
                case TCKind._tk_void:
                    m_dyn_value.type(m_base_type);
                    return;
                case TCKind._tk_objref:
                    m_dyn_value.insert_Object(dyn_any.get_reference());
                    return;
                case TCKind._tk_TypeCode:
                    m_dyn_value.insert_TypeCode(dyn_any.get_typecode());
                    return;
                case TCKind._tk_short:
                    m_dyn_value.insert_short(dyn_any.get_short());
                    return;
                case TCKind._tk_long:
                    m_dyn_value.insert_long(dyn_any.get_long());
                    return;
                case TCKind._tk_ushort:
                    m_dyn_value.insert_ushort(dyn_any.get_ushort());
                    return;
                case TCKind._tk_ulong:
                    m_dyn_value.insert_ulong(dyn_any.get_ulong());
                    return;
                case TCKind._tk_float:
                    m_dyn_value.insert_float(dyn_any.get_float());
                    return;
                case TCKind._tk_double:
                    m_dyn_value.insert_double(dyn_any.get_double());
                    return;
                case TCKind._tk_boolean:
                    m_dyn_value.insert_boolean(dyn_any.get_boolean());
                    return;
                case TCKind._tk_char:
                    m_dyn_value.insert_char(dyn_any.get_char());
                    return;
                case TCKind._tk_octet:
                    m_dyn_value.insert_octet(dyn_any.get_octet());
                    return;
                case TCKind._tk_longlong:
                    m_dyn_value.insert_longlong(dyn_any.get_longlong());
                    return;
                case TCKind._tk_ulonglong:
                    m_dyn_value.insert_ulonglong(dyn_any.get_ulonglong());
                    return;
                case TCKind._tk_wchar:
                    m_dyn_value.insert_wchar(dyn_any.get_wchar());
                    return;
                case TCKind._tk_string:
                    m_dyn_value.insert_string(dyn_any.get_string());
                    return;
                case TCKind._tk_wstring:
                    m_dyn_value.insert_string(dyn_any.get_string());
                    return;

            }
        }
        catch (InvalidValue iv) {
            throw new BAD_PARAM(iv.toString());
        }

        // set the any type to the "dyn_type"
        m_dyn_value.setEquivalentType(m_base_type);
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

        initValue(value);
    }

    public org.omg.CORBA.Any to_any()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        AnyImpl new_value = (AnyImpl) m_orb.create_any();

        AnyImpl.assign(m_dyn_value, new_value);

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
            switch (m_base_type.kind().value())
            {
                case TCKind._tk_null:
                case TCKind._tk_void:
                    return true;
                case TCKind._tk_objref:
                    return m_dyn_value.extract_Object()
                                      ._is_equivalent(dyn_any.get_reference());
                case TCKind._tk_TypeCode:
                    return m_dyn_value.extract_TypeCode()
                                      .equal(dyn_any.get_typecode());
                case TCKind._tk_short:
                    return m_dyn_value.extract_short() == dyn_any.get_short();
                case TCKind._tk_long:
                    return m_dyn_value.extract_long() == dyn_any.get_long();
                case TCKind._tk_ushort:
                    return m_dyn_value.extract_ushort() == dyn_any.get_ushort();
                case TCKind._tk_ulong:
                    return m_dyn_value.extract_ulong() == dyn_any.get_ulong();
                case TCKind._tk_float:
                    return m_dyn_value.extract_float() == dyn_any.get_float();
                case TCKind._tk_double:
                    return m_dyn_value.extract_double() == dyn_any.get_double();
                case TCKind._tk_boolean:
                    return m_dyn_value.extract_boolean()
                           == dyn_any.get_boolean();
                case TCKind._tk_char:
                    return m_dyn_value.extract_char() == dyn_any.get_char();
                case TCKind._tk_octet:
                    return m_dyn_value.extract_octet() == dyn_any.get_octet();
                case TCKind._tk_longlong:
                    return m_dyn_value.extract_longlong() 
                           == dyn_any.get_longlong();
                case TCKind._tk_ulonglong:
                    return m_dyn_value.extract_ulonglong()
                           == dyn_any.get_ulonglong();
                case TCKind._tk_wchar:
                    return m_dyn_value.extract_wchar() == dyn_any.get_wchar();
                case TCKind._tk_string:
                    return m_dyn_value.extract_string() == dyn_any.get_string();
                case TCKind._tk_wstring:
                    return m_dyn_value.extract_string() == dyn_any.get_string();
            }
        }
        catch (InvalidValue iv) {
            throw new BAD_PARAM(iv.toString());
        }
        catch (TypeMismatch tm) {
            throw new INTERNAL(tm.toString());
        }
        throw new INTERNAL("DynAnyImpl with no basic TypeCode");

    }

    protected void destroyNow()
    {
        super.destroyNow();
        m_dyn_value = null;
    }

    public org.omg.DynamicAny.DynAny copy()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        DynAnyImpl new_dyn = new DynAnyImpl(m_factory, m_orb, m_dyn_type,
                                            m_base_type);

        try {
            new_dyn.assign(this);
        }
        catch (TypeMismatch tm) {}

        return new_dyn;
    }

    public void insert_boolean(boolean value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_dyn_value.type().kind().value() != TCKind._tk_boolean)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_boolean(value);
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public void insert_octet(byte value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_octet)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_octet(value);
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public void insert_char(char value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_char)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_char(value);
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }
    }

    public void insert_short(short value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_short)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_short(value);
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public void insert_ushort(short value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_ushort)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_ushort(value);
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public void insert_long(int value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_long)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_long(value);
        }
        catch (org.omg.CORBA.BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public void insert_ulong(int value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_ulong)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_ulong(value);
        }
        catch (org.omg.CORBA.BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public void insert_float(float value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_float)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_float(value);
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public void insert_double(double value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_double)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_double(value);
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public void insert_string(String value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value == null)
            throw new BAD_PARAM("Null string reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_base_type.kind().value() != TCKind._tk_string)
            throw new TypeMismatch("Unexpected type");

        try {
            if (m_base_type.length() == 0) { // unboundled string
                m_dyn_value.insert_string(value);
                return;
            }
            if (value.length() > m_base_type.length())
                throw new BAD_PARAM("String out of bounds.",
                                    0,
                                    CompletionStatus.COMPLETED_NO);
            m_dyn_value.insert_Streamable(new StringHolder(m_base_type, value));

        }
        catch (org.omg.CORBA.TypeCodePackage.BadKind bk) {}
        catch (org.omg.CORBA.BAD_OPERATION bo) {
            throw new InvalidValue();
        }
    }

    public void insert_wstring(String value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value == null)
            throw new BAD_PARAM("Null string reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_base_type.kind().value() != TCKind._tk_wstring)
            throw new TypeMismatch("Unexpected type");

        try {
            if (m_base_type.length() == 0) { // unboundled string
                m_dyn_value.insert_wstring(value);
                return;
            }
            if (value.length() > (m_base_type.length() / CDR.WCHAR_SIZE))
                throw new BAD_PARAM("WString out of bounds.", 0,
                                    CompletionStatus.COMPLETED_NO);
            m_dyn_value.insert_Streamable(
                new WStringHolder(m_base_type, value));

        }
        catch (org.omg.CORBA.TypeCodePackage.BadKind bk) {}
        catch (BAD_OPERATION bo) {
            throw new InvalidValue();
        }
    }

    public void insert_reference(org.omg.CORBA.Object value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_objref)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_Object(value);
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public void insert_typecode(org.omg.CORBA.TypeCode value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value == null)
            throw new BAD_PARAM("Null TypeCode reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_TypeCode)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_TypeCode(value);
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public void insert_longlong(long value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_longlong)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_longlong(value);
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public void insert_ulonglong(long value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_ulonglong)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_ulonglong(value);
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public void insert_wchar(char value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_wchar)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_wchar(value);
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

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

        throw new TypeMismatch("Unexpected type");
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

        throw new TypeMismatch("Unexpected type");
    }

    public void insert_val(java.io.Serializable value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_value)
            throw new TypeMismatch("Unexpected type");

        try {
            m_dyn_value.insert_Value(value);
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public boolean get_boolean()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_boolean)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_boolean();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public byte get_octet()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_octet)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_octet();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public char get_char()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_char)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_char();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public short get_short()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_short)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_short();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public short get_ushort()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_ushort)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_ushort();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public int get_long()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_long)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_long();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public int get_ulong()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_ulong)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_ulong();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public float get_float()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_float)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_float();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public double get_double()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_double)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_double();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public String get_string()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_base_type.kind().value() != TCKind._tk_string)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_string();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue();
        }

    }

    public org.omg.CORBA.Object get_reference()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_objref)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_Object();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public org.omg.CORBA.TypeCode get_typecode()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_TypeCode)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_TypeCode();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public long get_longlong()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_longlong)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_longlong();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public long get_ulonglong()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_ulonglong)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_ulonglong();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public char get_wchar()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_wchar)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_wchar();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }

    }

    public String get_wstring()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_base_type.kind().value() != TCKind._tk_wstring)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_wstring();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue();
        }

    }

    public org.omg.CORBA.Any get_any()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        throw new TypeMismatch("Unexpected type");
    }

    public org.omg.DynamicAny.DynAny get_dyn_any()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        throw new TypeMismatch("Unexpected type");
    }

    public java.io.Serializable get_val()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_base_type.kind().value() != TCKind._tk_value)
            throw new TypeMismatch("Unexpected type");

        try {
            return m_dyn_value.extract_Value();
        }
        catch (BAD_OPERATION bo) {
            throw new InvalidValue(bo.toString());
        }
    }

    /**
     * Initialize the content value with the any value.
     * 
     * @param any
     *            the value
     */

    protected void initValue(Any any)
    {
        if (m_dyn_value == null)
            m_dyn_value = (AnyImpl) m_orb.create_any();

        if (m_dyn_type.kind().value() == TCKind._tk_alias) {

            m_dyn_value.read_value(any.create_input_stream(), m_base_type);

        } else {
            AnyImpl.assign(any, m_dyn_value);
            m_dyn_value.setEquivalentType(m_base_type);
        }
    }

    /**
     * Initialize the content value with the default value of the type.
     */

    protected void initValue()
    {
        if (m_dyn_value == null)
            m_dyn_value = (AnyImpl) m_orb.create_any();

        switch (m_base_type.kind().value())
        {
            // basic typecodes
            case TCKind._tk_short:
                m_dyn_value.insert_short((short) 0);
                return;
            case TCKind._tk_long:
                m_dyn_value.insert_long(0);
                return;
            case TCKind._tk_ushort:
                m_dyn_value.insert_ushort((short) 0);
                return;
            case TCKind._tk_ulong:
                m_dyn_value.insert_ulong(0);
                return;
            case TCKind._tk_float:
                m_dyn_value.insert_float(0.0F);
                return;
            case TCKind._tk_double:
                m_dyn_value.insert_double(0.0D);
                return;
            case TCKind._tk_boolean:
                m_dyn_value.insert_boolean(false);
                return;
            case TCKind._tk_char:
                m_dyn_value.insert_char((char) 0);
                return;
            case TCKind._tk_octet:
                m_dyn_value.insert_octet((byte) 0);
                return;
            case TCKind._tk_longlong:
                m_dyn_value.insert_longlong(0L);
                return;
            case TCKind._tk_ulonglong:
                m_dyn_value.insert_ulonglong(0L);
                return;
            case TCKind._tk_wchar:
                m_dyn_value.insert_wchar((char) 0);
                return;
            case TCKind._tk_string:
                m_dyn_value.insert_string("");
                return;
            case TCKind._tk_wstring:
                m_dyn_value.insert_wstring("");
                return;
            case TCKind._tk_objref:
                m_dyn_value.insert_Object(null);
                return;
            case TCKind._tk_TypeCode:
                m_dyn_value.insert_TypeCode(
                    m_orb.get_primitive_tc(TCKind.tk_null));
                return;
            default:
                throw new INTERNAL("TypeCode not expected");
        }
    }

}