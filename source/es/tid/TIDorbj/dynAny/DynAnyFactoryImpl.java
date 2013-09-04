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
import org.omg.CORBA.BAD_TYPECODE;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.DynamicAny.DynAnyFactoryLocalBase;
import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;

import es.tid.TIDorbj.core.TIDORB;

/**
 * DynAnyFactory implementation.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class DynAnyFactoryImpl extends DynAnyFactoryLocalBase
{

    private boolean m_destroyed;

    private TIDORB m_orb;

    public DynAnyFactoryImpl()
    {
        m_destroyed = false;
    }

    public void set_orb(org.omg.CORBA.ORB orb)
    {
        m_orb = (TIDORB) orb;
    }

    public synchronized void destroy()
    {
        m_orb = null;
        m_destroyed = true;
    }

    public synchronized org.omg.DynamicAny.DynAny
    	create_dyn_any(org.omg.CORBA.Any value)
        throws org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST();

        if (value == null)
            throw new BAD_PARAM("Null any reference");

        switch (value.type().kind().value())
        {
            // basic typecodes
            case TCKind._tk_null:
            case TCKind._tk_objref:
            case TCKind._tk_TypeCode:
            case TCKind._tk_void:
            case TCKind._tk_short:
            case TCKind._tk_long:
            case TCKind._tk_ushort:
            case TCKind._tk_ulong:
            case TCKind._tk_float:
            case TCKind._tk_double:
            case TCKind._tk_boolean:
            case TCKind._tk_char:
            case TCKind._tk_octet:
            case TCKind._tk_longlong:
            case TCKind._tk_ulonglong:
            case TCKind._tk_wchar:
            case TCKind._tk_string:
            case TCKind._tk_wstring:
                return new DynAnyImpl(this, m_orb, value, value.type());
            case TCKind._tk_any:
                return new DynAnyWithAny(this, m_orb, value, value.type());
            case TCKind._tk_alias:
                return createAliasDynAny(value);

            // complex typecodes
            case TCKind._tk_sequence:
                return new DynSequenceImpl(this, m_orb, value, value.type());
            case TCKind._tk_struct:
                return new DynStructImpl(this, m_orb, value, value.type());
            case TCKind._tk_except:
                return new DynExceptImpl(this, m_orb, value, value.type());
            case TCKind._tk_enum:
                return new DynEnumImpl(this, m_orb, value, value.type());
            case TCKind._tk_array:
                return new DynArrayImpl(this, m_orb, value, value.type());
            case TCKind._tk_union:
                return new DynUnionImpl(this, m_orb, value, value.type());
            case TCKind._tk_fixed:
                return new DynFixedImpl(this, m_orb, value, value.type());

            case TCKind._tk_value:
                return new DynValueImpl(this, m_orb, value, value.type());

            case TCKind._tk_longdouble:
                throw new org.omg.CORBA.NO_IMPLEMENT();

            default:
                throw new InconsistentTypeCode("Bad TypeCode");
        }
    }

    protected org.omg.DynamicAny.DynAny
    	createAliasDynAny(org.omg.CORBA.Any value)
        throws org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode
    {
        if (value == null)
            throw new BAD_PARAM("Null any reference");

        TypeCode base_type = null;

        try {
            base_type = value.type().content_type();

            while (base_type.kind().value() == TCKind._tk_alias) {
                base_type = base_type.content_type();
            }
        }
        catch (BadKind bk) {
            throw new 
            	BAD_TYPECODE("Alias typecode fault in content_type() method:"
            	             + bk.toString());
        }	

        switch (base_type.kind().value())
        {
            // basic typecodes
            case TCKind._tk_null:
            case TCKind._tk_objref:
            case TCKind._tk_TypeCode:
            case TCKind._tk_void:
            case TCKind._tk_short:
            case TCKind._tk_long:
            case TCKind._tk_ushort:
            case TCKind._tk_ulong:
            case TCKind._tk_float:
            case TCKind._tk_double:
            case TCKind._tk_boolean:
            case TCKind._tk_char:
            case TCKind._tk_octet:
            case TCKind._tk_longlong:
            case TCKind._tk_ulonglong:
            case TCKind._tk_wchar:
            case TCKind._tk_string:
            case TCKind._tk_wstring:
                return new DynAnyImpl(this, m_orb, value, base_type);
            case TCKind._tk_any:
                return new DynAnyWithAny(this, m_orb, value, base_type);
            // complex typecodes

            case TCKind._tk_struct:
                return new DynStructImpl(this, m_orb, value, base_type);
            case TCKind._tk_sequence:
                return new DynSequenceImpl(this, m_orb, value, base_type);
            case TCKind._tk_except:
                return new DynExceptImpl(this, m_orb, value, base_type);
            case TCKind._tk_enum:
                return new DynEnumImpl(this, m_orb, value, base_type);
            case TCKind._tk_array:
                return new DynArrayImpl(this, m_orb, value, base_type);
            case TCKind._tk_union:
                return new DynUnionImpl(this, m_orb, value, base_type);
            case TCKind._tk_fixed:
                return new DynFixedImpl(this, m_orb, value, base_type);
            case TCKind._tk_value:
                return new DynValueImpl(this, m_orb, value, base_type);
            case TCKind._tk_longdouble:
                throw new org.omg.CORBA.NO_IMPLEMENT();
            default:
                throw new InconsistentTypeCode("Bad TypeCode");
        }
    }

    public synchronized org.omg.DynamicAny.DynAny 
    	create_dyn_any_from_type_code(org.omg.CORBA.TypeCode type)
        throws org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST();

        if (type == null)
            throw new BAD_PARAM("Null TypeCode reference");

        switch (type.kind().value())
        {
            // basic typecodes
            case TCKind._tk_null:
            case TCKind._tk_objref:
            case TCKind._tk_TypeCode:
            case TCKind._tk_void:
            case TCKind._tk_short:
            case TCKind._tk_long:
            case TCKind._tk_ushort:
            case TCKind._tk_ulong:
            case TCKind._tk_float:
            case TCKind._tk_double:
            case TCKind._tk_boolean:
            case TCKind._tk_char:
            case TCKind._tk_octet:
            case TCKind._tk_longlong:
            case TCKind._tk_ulonglong:
            case TCKind._tk_wchar:
            case TCKind._tk_string:
            case TCKind._tk_wstring:
                return new DynAnyImpl(this, m_orb, type, type);
            case TCKind._tk_any:
                return new DynAnyWithAny(this, m_orb, type, type);
            case TCKind._tk_alias:
                return createAliasDynAnyFromTypeCode(type);
            // complex typecodes

            case TCKind._tk_struct:
                return new DynStructImpl(this, m_orb, type, type);
            case TCKind._tk_sequence:
                return new DynSequenceImpl(this, m_orb, type, type);
            case TCKind._tk_except:
                return new DynExceptImpl(this, m_orb, type, type);
            case TCKind._tk_enum:
                return new DynEnumImpl(this, m_orb, type, type);
            case TCKind._tk_array:
                return new DynArrayImpl(this, m_orb, type, type);
            case TCKind._tk_union:
                return new DynUnionImpl(this, m_orb, type, type);
            case TCKind._tk_fixed:
                return new DynFixedImpl(this, m_orb, type, type);
            case TCKind._tk_value:
                return new DynValueImpl(this, m_orb, type, type);
            case TCKind._tk_longdouble:
                throw new org.omg.CORBA.NO_IMPLEMENT();
            default:
                throw new InconsistentTypeCode("Bad TypeCode");
        }
    }

    public org.omg.DynamicAny.DynAny 
    	createAliasDynAnyFromTypeCode(org.omg.CORBA.TypeCode type)
        throws org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode
    {
        if (type == null)
            throw new BAD_TYPECODE("Null TypeCode reference");

        TypeCode base_type = null;

        try {
            base_type = type.content_type();

            while (base_type.kind().value() == TCKind._tk_alias) {
                base_type = base_type.content_type();
            }
        }
        catch (BadKind bk) {
            throw new 
            	BAD_TYPECODE("Alias typecode fault in content_type() method:"
            	             + bk.toString());
        }

        switch (base_type.kind().value())
        {
            // basic typecodes
            case TCKind._tk_null:
            case TCKind._tk_objref:
            case TCKind._tk_TypeCode:
            case TCKind._tk_void:
            case TCKind._tk_short:
            case TCKind._tk_long:
            case TCKind._tk_ushort:
            case TCKind._tk_ulong:
            case TCKind._tk_float:
            case TCKind._tk_double:
            case TCKind._tk_boolean:
            case TCKind._tk_char:
            case TCKind._tk_octet:
            case TCKind._tk_longlong:
            case TCKind._tk_ulonglong:
            case TCKind._tk_wchar:
            case TCKind._tk_string:
            case TCKind._tk_wstring:
                return new DynAnyImpl(this, m_orb, type, base_type);
            case TCKind._tk_any:
                return new DynAnyWithAny(this, m_orb, type, base_type);
            // complex typecodes
            case TCKind._tk_struct:
                return new DynStructImpl(this, m_orb, type, base_type);
            case TCKind._tk_sequence:
                return new DynSequenceImpl(this, m_orb, type, base_type);
            case TCKind._tk_enum:
                return new DynEnumImpl(this, m_orb, type, base_type);
            case TCKind._tk_except:
                return new DynExceptImpl(this, m_orb, type, base_type);
            case TCKind._tk_array:
                return new DynArrayImpl(this, m_orb, type, base_type);
            case TCKind._tk_union:
                return new DynUnionImpl(this, m_orb, type, base_type);
            case TCKind._tk_fixed:
                return new DynFixedImpl(this, m_orb, type, base_type);
            case TCKind._tk_value:
                return new DynValueImpl(this, m_orb, type, base_type);
            case TCKind._tk_longdouble:
                throw new org.omg.CORBA.NO_IMPLEMENT();
            default:
                throw new InconsistentTypeCode("Bad TypeCode");
        }
    }

    public static boolean isBasic(org.omg.CORBA.TypeCode type)
    {
        if (type == null)
            throw new BAD_PARAM("Null type reference");

        switch (type.kind().value())
        {
            // basic typecodes
            case TCKind._tk_null:
            case TCKind._tk_any:
            case TCKind._tk_objref:
            case TCKind._tk_TypeCode:
            case TCKind._tk_void:
            case TCKind._tk_short:
            case TCKind._tk_long:
            case TCKind._tk_ushort:
            case TCKind._tk_ulong:
            case TCKind._tk_float:
            case TCKind._tk_double:
            case TCKind._tk_boolean:
            case TCKind._tk_char:
            case TCKind._tk_octet:
            case TCKind._tk_longlong:
            case TCKind._tk_ulonglong:
            case TCKind._tk_wchar:
            case TCKind._tk_string:
            case TCKind._tk_wstring:
                return true;
            case TCKind._tk_alias:
                return aliasWithBasicTC(type);
            default:
                return false;
        }
    }

    protected static boolean aliasWithBasicTC(org.omg.CORBA.TypeCode type)
    {
        if (type == null)
            throw new BAD_PARAM("Null any reference");

        TypeCode base_type = null;

        try {
            base_type = type.content_type();

            while (base_type.kind().value() == TCKind._tk_alias) {
                base_type = base_type.content_type();
            }
        }
        catch (BadKind bk) {
            throw new 
            	BAD_TYPECODE("Alias typecode fault in content_type() method:"
            	             + bk.toString());
        }

        return isBasic(base_type);
    }

}