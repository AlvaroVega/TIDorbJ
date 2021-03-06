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
package es.tid.TIDorbj.core.typecode;

import org.omg.CORBA.TCKind;

/**
 * Factory of basic and complex typecodes.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class TypeCodeFactory
{

    public static final es.tid.TIDorbj.core.typecode.TypeCodeImpl 
    getBasicTypeCode(TCKind tcKind)
    {
        switch (tcKind.value())
        {
            case TCKind._tk_null:
                return tc_null;
            case TCKind._tk_objref:
                return tc_objref;
            case TCKind._tk_any:
                return tc_any;
            case TCKind._tk_TypeCode:
                return tc_TypeCode;
            case TCKind._tk_void:
                return tc_void;
            case TCKind._tk_short:
                return tc_short;
            case TCKind._tk_long:
                return tc_long;
            case TCKind._tk_ushort:
                return tc_ushort;
            case TCKind._tk_ulong:
                return tc_ulong;
            case TCKind._tk_float:
                return tc_float;
            case TCKind._tk_double:
                return tc_double;
            case TCKind._tk_boolean:
                return tc_boolean;
            case TCKind._tk_char:
                return tc_char;
            case TCKind._tk_octet:
                return tc_octet;
            case TCKind._tk_longlong:
                return tc_longlong;
            case TCKind._tk_ulonglong:
                return tc_ulonglong;
            case TCKind._tk_longdouble:
                return tc_longdouble;
            case TCKind._tk_wchar:
                return tc_wchar;
            case TCKind._tk_string:
                return tc_string;
            case TCKind._tk_wstring:
                return tc_wstring;
            default:
                return null;
        }
    }

    public static final es.tid.TIDorbj.core.typecode.TypeCodeImpl
    	getComplexTypeCode(TCKind tcKind)
    {
        switch (tcKind.value())
        {
            case TCKind._tk_struct:
                return new StructTypeCode();
            case TCKind._tk_objref:
                return new ObjectRefTypeCode();
            case TCKind._tk_union:
                return new UnionTypeCode();
            case TCKind._tk_enum:
                return new EnumTypeCode();
            case TCKind._tk_alias:
                // return new TransparentAliasTypeCode();
                return new AliasTypeCode();
            // this is a transparent alias it will be the content type for user
            case TCKind._tk_except:
                return new ExceptionTypeCode();
            case TCKind._tk_value:
                return new ValueTypeCode();
            case TCKind._tk_value_box:
                return new ValueBoxTypeCode();
            case TCKind._tk_native:
                return new NativeTypeCode();
            case TCKind._tk_abstract_interface:
                return new AbstractInterfaceTypeCode();
            default:
                return null;
        }
    }

    public static final es.tid.TIDorbj.core.typecode.TypeCodeImpl 
    	getSemiComplexTypeCode(TCKind tcKind)
    {
        switch (tcKind.value())
        {
            case TCKind._tk_string:
                return new StringTypeCode();
            case TCKind._tk_sequence:
                return new SequenceTypeCode();
            case TCKind._tk_array:
                return new ArrayTypeCode();
            case TCKind._tk_wstring:
                return new WStringTypeCode();
            case TCKind._tk_fixed:
                return new FixedTypeCode();
            default:
                return null;
        }
    }

    public static final boolean isComplex(TCKind kind)
    {
        switch (kind.value())
        {
            case TCKind._tk_struct:
            case TCKind._tk_objref:
            case TCKind._tk_union:
            case TCKind._tk_enum:
            case TCKind._tk_alias:
            case TCKind._tk_except:
            case TCKind._tk_value:
            case TCKind._tk_value_box:
            case TCKind._tk_native:
            case TCKind._tk_abstract_interface:
                return true;
            default:
                return false;
        }
    }

    public static final boolean isSemiComplex(TCKind kind)
    {

        switch (kind.value())
        {
            case TCKind._tk_sequence:
            case TCKind._tk_string:
            case TCKind._tk_array:
            case TCKind._tk_wstring:
            case TCKind._tk_fixed:
                return true;
            default:
                return false;
        }
    }

    public static final TypeCodeImpl tc_null = new TypeCodeImpl(TCKind.tk_null);

    public static final TypeCodeImpl tc_objref = 
        new ObjectRefTypeCode("IDL:omg/org/CORBA/Object:1.0",
        					  "Object");

    public static final TypeCodeImpl tc_any = new TypeCodeImpl(TCKind.tk_any);
    public static final TypeCodeImpl tc_TypeCode =
        new TypeCodeImpl(TCKind.tk_TypeCode);
    public static final TypeCodeImpl tc_void = new TypeCodeImpl(TCKind.tk_void);
    public static final TypeCodeImpl tc_short =  new TypeCodeImpl(TCKind.tk_short);
    public static final TypeCodeImpl tc_long = new TypeCodeImpl(TCKind.tk_long);
    public static final TypeCodeImpl tc_ushort = 
        new TypeCodeImpl(TCKind.tk_ushort);
    public static final TypeCodeImpl tc_ulong = 
        new TypeCodeImpl(TCKind.tk_ulong);
    public static final TypeCodeImpl tc_float = 
        new TypeCodeImpl(TCKind.tk_float);
    public static final TypeCodeImpl tc_double = 
        new TypeCodeImpl(TCKind.tk_double);
    public static final TypeCodeImpl tc_boolean = 
        new TypeCodeImpl(TCKind.tk_boolean);
    public static final TypeCodeImpl tc_char = new TypeCodeImpl(TCKind.tk_char);
    public static final TypeCodeImpl tc_octet = 
        new TypeCodeImpl(TCKind.tk_octet);
    public static final TypeCodeImpl tc_longlong = 
        new TypeCodeImpl(TCKind.tk_longlong);
    public static final TypeCodeImpl tc_ulonglong =
        new TypeCodeImpl(TCKind.tk_ulonglong);
    public static final TypeCodeImpl tc_longdouble = 
        new TypeCodeImpl(TCKind.tk_longdouble);
    public static final TypeCodeImpl tc_wchar =
        new TypeCodeImpl(TCKind.tk_wchar);
    public static final StringTypeCode tc_string =
        (StringTypeCode) getSemiComplexTypeCode(TCKind.tk_string);
    public static final WStringTypeCode tc_wstring =
        (WStringTypeCode) getSemiComplexTypeCode(TCKind.tk_wstring);

}