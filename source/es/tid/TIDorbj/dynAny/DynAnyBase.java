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

import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.TypeCode;

import es.tid.TIDorbj.core.TIDORB;

/**
 * Base clase for all DynAny implementation classes.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */
abstract class DynAnyBase extends es.tid.TIDorbj.core.PseudoObject
    implements org.omg.DynamicAny.DynAny, org.omg.CORBA.portable.Streamable
{

    /**
     * The active ORB.
     */

    TIDORB m_orb;

    /**
     * The active DynAny factory.
     */
    DynAnyFactoryImpl m_factory;

    /**
     * The DynAny typecode. It maybe different from the <code>base_type</code>
     * typecode, if it is a alias typecode.
     */

    TypeCode m_dyn_type;

    /**
     * The "real" DynAny typecode base type.
     */

    TypeCode m_base_type;

    /**
     * If <code>true</code> the DynAny can be destroyed by the user.
     * Otherwise, it only will be destroyed by the container DynAny that created
     * it.
     * <p>
     * Default value: <code>true</code> (user can destroy it).
     */

    boolean m_user_destroy;

    protected DynAnyBase(DynAnyFactoryImpl factory, TIDORB orb)
    {
        m_factory = factory;
        m_orb = orb;
        m_destroyed = false;
        m_user_destroy = true;
        m_dyn_type = null;
        m_base_type = null;
    }

    protected DynAnyBase(DynAnyFactoryImpl factory, TIDORB orb, TypeCode type,
                         TypeCode real_type)
    {
        this.m_factory = factory;
        this.m_orb = orb;
        m_destroyed = false;
        m_user_destroy = true;
        m_dyn_type = type;
        m_base_type = real_type;
    }

    public void destroy()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_user_destroy) {
            destroyNow();
        }
    }

    protected void destroyNow()
    {
        m_destroyed = true;
        m_dyn_type = null;
        m_base_type = null;
        m_factory = null;
        m_orb = null;
    }

    protected void setUserDestroy(boolean value)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST();

        m_user_destroy = value;
    }

    // DynAny operations

    public org.omg.CORBA.TypeCode type()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        return m_dyn_type;
    }

    // DynAny without components methods

    public int component_count()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        return 0;
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

        // no components

        return false;
    }

    public org.omg.DynamicAny.DynAny current_component()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch
    {
        typeMismatch();
        return null;
    }

    public void insert_abstract(java.lang.Object value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public java.lang.Object get_abstract()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public void insert_boolean_seq(boolean[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_octet_seq(byte[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_char_seq(char[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_short_seq(short[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_ushort_seq(short[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_long_seq(int[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_ulong_seq(int[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_float_seq(float[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_double_seq(double[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_longlong_seq(long[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_ulonglong_seq(long[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public void insert_wchar_seq(char[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
    }

    public boolean[] get_boolean_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public byte[] get_octet_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public char[] get_char_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public short[] get_short_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public short[] get_ushort_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public int[] get_long_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public int[] get_ulong_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public float[] get_float_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public double[] get_double_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public long[] get_longlong_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public long[] get_ulonglong_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    public char[] get_wchar_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        typeMismatch();
        return null;
    }

    // Streamable operations

    public org.omg.CORBA.TypeCode _type()
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        return type();
    }

    // OBJECT methods

    public boolean _is_a(java.lang.String repositoryIdentifier)
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        if (repositoryIdentifier == null)
            throw new BAD_PARAM("Null string reference");

        if (repositoryIdentifier.equals("IDL:omg.org/DynamicAny/DynAny:1.0"))
            return true;

        return super._is_a(repositoryIdentifier);

    }

    // Implementation

    protected void typeMismatch()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        throw new org.omg.DynamicAny.DynAnyPackage.TypeMismatch();
    }

}