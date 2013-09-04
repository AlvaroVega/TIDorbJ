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

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.TypeCode;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

import es.tid.TIDorbj.core.TIDORB;

/**
 * Abstract clase that only throws TypeMismatch in insert/get operations. It is
 * the base clase for <code>DynEnum</code> or <code>DynFixed</code>
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

abstract class DynSingleton extends DynAnyBase
{

    protected DynSingleton(DynAnyFactoryImpl factory, TIDORB orb)
    {
        super(factory, orb);
    }

    protected DynSingleton(DynAnyFactoryImpl factory, TIDORB orb,
                           TypeCode type, TypeCode real_type)
    {
        super(factory, orb, type, real_type);
    }

    public void insert_boolean(boolean value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_octet(byte value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_char(char value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_short(short value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_ushort(short value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_long(int value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_ulong(int value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_float(float value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_double(double value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_string(String value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_reference(org.omg.CORBA.Object value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_typecode(org.omg.CORBA.TypeCode value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_longlong(long value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_ulonglong(long value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_wchar(char value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_wstring(String value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_any(org.omg.CORBA.Any value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_dyn_any(org.omg.DynamicAny.DynAny value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_val(java.io.Serializable value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_abstract(java.lang.Object value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public boolean get_boolean()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return false;
    }

    public byte get_octet()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return (byte) 0;
    }

    public char get_char()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return (char) 0;
    }

    public short get_short()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return (short) 0;
    }

    public short get_ushort()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return (short) 0;
    }

    public int get_long()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return 0;
    }

    public int get_ulong()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return 0;
    }

    public float get_float()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return 0.0F;
    }

    public double get_double()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return 0.0D;
    }

    public String get_string()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return "";
    }

    public org.omg.CORBA.Object get_reference()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public org.omg.CORBA.TypeCode get_typecode()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public long get_longlong()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return 0L;
    }

    public long get_ulonglong()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return 0L;
    }

    public char get_wchar()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return '0';
    }

    public String get_wstring()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return "";
    }

    public org.omg.CORBA.Any get_any()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public org.omg.DynamicAny.DynAny get_dyn_any()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public java.io.Serializable get_val()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public java.lang.Object get_abstract()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public int component_count()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        return -1;
    }

    public boolean seek(int index)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        return false;
    }

    public void rewind()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);
    }

    public boolean next()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        return true;
    }

    public org.omg.DynamicAny.DynAny current_component()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        throw new TypeMismatch("DynAny without components");

    }
}