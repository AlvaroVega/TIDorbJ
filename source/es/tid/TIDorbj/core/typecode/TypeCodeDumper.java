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
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;

/**
 * Dumps the description of a TypeCode and the marshaled value asociated to it.
 * <p>
 * For example, a 5 long elements array typecode will be dumed as:
 * <p>
 * <code>[TYPECODE]{[TYPECODE]{long}[5]}
 *
 * @author Juan A. C&aacute;ceres
 * @version 1.0
 */
public class TypeCodeDumper
{

    /**
     * Dumps the description of a given TypeCode.
     * 
     * @param type
     *            the <code>TypeCode</code>
     * @param output
     *            the output stream where the TypeCode will be dumped
     */
    public static void dump(TypeCode type, java.io.PrintWriter output)
        throws java.io.IOException
    {
        switch (type.kind().value())
        {
            case TCKind._tk_struct:
                StructTypeCode.dump(type, output);
            break;
            case TCKind._tk_objref:
                ObjectRefTypeCode.dump(type, output);
            break;
            case TCKind._tk_union:
                UnionTypeCode.dump(type, output);
            break;
            case TCKind._tk_enum:
                EnumTypeCode.dump(type, output);
            break;
            case TCKind._tk_string:
                StringTypeCode.dump(type, output);
            break;
            case TCKind._tk_sequence:
                SequenceTypeCode.dump(type, output);
            break;
            case TCKind._tk_array:
                ArrayTypeCode.dump(type, output);
            break;
            case TCKind._tk_alias:
                AliasTypeCode.dump(type, output);
            break;
            case TCKind._tk_except:
                ExceptionTypeCode.dump(type, output);
            break;
            case TCKind._tk_wstring:
                WStringTypeCode.dump(type, output);
            break;
            case TCKind._tk_fixed:
                FixedTypeCode.dump(type, output);
            break;
            case TCKind._tk_value:
                ValueTypeCode.dump(type, output);
            break;
            case TCKind._tk_value_box:
                ValueBoxTypeCode.dump(type, output);
            break;
            case TCKind._tk_native:
                NativeTypeCode.dump(type, output);
            break;
            case TCKind._tk_abstract_interface:
                AbstractInterfaceTypeCode.dump(type, output);
            break;
            default:
                TypeCodeImpl.dump(type, output);
        }
    }

    /**
     * Dumps the description of a the marshaled value of a given TypeCode.
     * 
     * @param type
     *            the <code>TypeCode</code>
     * @param input
     *            the input stream where the value is marshaled
     * @param output
     *            the output stream where the value will be dumped
     * @return <code>true</code> if if has been possible dump the value.
     */

    public static boolean dumpValue(TypeCode type, InputStream input,
                                    java.io.PrintWriter output)
        throws java.io.IOException
    {
        switch (type.kind().value())
        {
            case TCKind._tk_struct:
                return StructTypeCode.dumpValue(type, input, output);
            case TCKind._tk_objref:
                return ObjectRefTypeCode.dumpValue(type, input, output);
            case TCKind._tk_union:
                return UnionTypeCode.dumpValue(type, input, output);
            case TCKind._tk_enum:
                return EnumTypeCode.dumpValue(type, input, output);
            case TCKind._tk_string:
                return StringTypeCode.dumpValue(type, input, output);
            case TCKind._tk_sequence:
                return SequenceTypeCode.dumpValue(type, input, output);
            case TCKind._tk_array:
                return ArrayTypeCode.dump_value(type, input, output);
            case TCKind._tk_alias:
                return AliasTypeCode.dump_value(type, input, output);
            case TCKind._tk_except:
                return ExceptionTypeCode.dumpValue(type, input, output);
            case TCKind._tk_wstring:
                return WStringTypeCode.dumpValue(type, input, output);
            case TCKind._tk_fixed:
                return FixedTypeCode.dumpValue(type, input, output);
            case TCKind._tk_value:
                return ValueTypeCode.dump_value(type, input, output);
            case TCKind._tk_value_box:
                return ValueBoxTypeCode.dumpValue(type, input, output);
            case TCKind._tk_native:
                return NativeTypeCode.dump_value(type, input, output);
            case TCKind._tk_abstract_interface:
                return 
                    AbstractInterfaceTypeCode.dump_value(type, input, output);
            default:
                return TypeCodeImpl.dump_value(type, input, output);
        }
    }
}