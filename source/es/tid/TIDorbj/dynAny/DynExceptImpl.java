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
import org.omg.CORBA.TypeCode;

import es.tid.TIDorbj.core.TIDORB;

/**
 * DynStruct implementation for exceptions.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class DynExceptImpl extends DynStructImpl
{

    /**
     * Empty Constructor for generate copies.
     */

    protected DynExceptImpl(DynAnyFactoryImpl factory, TIDORB orb)
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

    protected DynExceptImpl(DynAnyFactoryImpl factory, TIDORB orb, Any any,
                            TypeCode real_type)
    {
        super(factory, orb, any, real_type);
        if (!validateName()) {
            throw new org.omg.CORBA.MARSHAL("Unexpected exception id in any");
        }
    }

    protected boolean validateName()
    {
        try {
            return m_next_value.read_string().equals(m_base_type.id());
        }
        catch (org.omg.CORBA.TypeCodePackage.BadKind bk) {
            throw new org.omg.CORBA.BAD_TYPECODE();
        }
    }

    /**
     * Constructor. Gets a simple TypeCode to create a new value. Warning: It
     * assumes that the TypeCode is tk_struct or tk_value (for DynValueImpl that
     * extends DynStructImpl)
     * 
     * @param type
     *            the TypeCode value.
     */

    protected DynExceptImpl(DynAnyFactoryImpl factory, TIDORB orb,
                            TypeCode type, TypeCode real_type)
    {
        super(factory, orb, type, real_type);
    }

    public void from_any(org.omg.CORBA.Any value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        super.from_any(value);
        if (!validateName()) {
            throw new org.omg.CORBA.MARSHAL("Unexpected exception id in any");
        }
    }

    public void _read(org.omg.CORBA.portable.InputStream is)
    {
        super._read(is);
        if (!validateName()) {
            throw new org.omg.CORBA.MARSHAL("Unexpected exception id in any");
        }
    }

    public void _write(org.omg.CORBA.portable.OutputStream os)
    {
        try {
            os.write_string(m_base_type.id());
        }
        catch (org.omg.CORBA.TypeCodePackage.BadKind bk) {
            throw new org.omg.CORBA.BAD_TYPECODE();
        }

        super._write(os);
    }

    public org.omg.DynamicAny.DynAny copy()
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        DynExceptImpl new_dyn = new DynExceptImpl(m_factory, m_orb, m_dyn_type,
                                                  m_base_type);

        copyTo(new_dyn);

        if (new_dyn.m_next_value != null)
            new_dyn.m_next_value.skipString();

        return new_dyn;
    }

}