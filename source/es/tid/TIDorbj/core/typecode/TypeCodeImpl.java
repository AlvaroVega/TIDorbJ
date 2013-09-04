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

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import es.tid.TIDorbj.core.AnyImpl;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

/**
 * The <code>TypeCode</code> class represents a <code>TypeCode</code> object
 * which is associated with an IDL of a basic type. It is the base class to the
 * rest of simple and complex typecodes.
 * 
 * @autor Juan A. Ca&acute;ceres
 * @version 1.0
 */

public class TypeCodeImpl extends org.omg.CORBA.TypeCode
{

    TCKind m_kind;

    boolean m_exhaustive_equal = false;

    protected TypeCodeImpl()
    {
        m_kind = TCKind.tk_null;
    }

    public TypeCodeImpl(TCKind kind)
    {
        m_kind = kind;
    }

    public void setExhaustiveEqual(boolean value)
    {
        m_exhaustive_equal = value;
    }

    public boolean equal(org.omg.CORBA.TypeCode tc)
    {
        return (m_kind.value() == tc.kind().value());
    }

   
    public boolean equivalent(org.omg.CORBA.TypeCode tc)
    {
        if (tc.kind().value() == TCKind._tk_alias) {
            try { // the exception must never be throwed, but, it is in the
                // definition.
                return equivalent(tc.content_type());
            }
            catch (org.omg.CORBA.TypeCodePackage.BadKind ex) {
                return false;
            }
        } else {
            return equals(tc);
        }
    }

    public org.omg.CORBA.TypeCode get_compact_typecode()
    {
        return this;
    }

    public org.omg.CORBA.TCKind kind()
    {
        return m_kind;
    }

    public java.lang.String id()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    public java.lang.String name()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    public int member_count()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    public java.lang.String member_name(int index)
        throws org.omg.CORBA.TypeCodePackage.BadKind,
        org.omg.CORBA.TypeCodePackage.Bounds
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    public org.omg.CORBA.TypeCode member_type(int index)
        throws org.omg.CORBA.TypeCodePackage.BadKind,
        org.omg.CORBA.TypeCodePackage.Bounds
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    public org.omg.CORBA.Any member_label(int index)
        throws org.omg.CORBA.TypeCodePackage.BadKind,
        org.omg.CORBA.TypeCodePackage.Bounds
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    public org.omg.CORBA.TypeCode discriminator_type()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    public int default_index()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    public int length()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    public org.omg.CORBA.TypeCode content_type()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    public short fixed_digits()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    public short fixed_scale()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    public short member_visibility(int index)
        throws org.omg.CORBA.TypeCodePackage.BadKind,
        org.omg.CORBA.TypeCodePackage.Bounds
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    public short type_modifier()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    public org.omg.CORBA.TypeCode concrete_base_type()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        throw new org.omg.CORBA.TypeCodePackage.BadKind();
    }

    //TIDORB operations

    public boolean isSimple()
    {
        return true;
    }

    /**
     * TypeCode Marshaling.
     */

    public static void marshal(org.omg.CORBA.TypeCode type,
                               CDROutputStream output)
    {
        output.write_ulong(type.kind().value());
    }

    /**
     * TypeCode parameters demarshaling. In this case, theres nothing to do.
     * This operation will throw the <code>INTERNAL</code> exception.
     */
    public void partialUnmarshal(CDRInputStream input)
    {
        throw new org.omg.CORBA.INTERNAL("Nothing to unmarshal", 0,
                                         CompletionStatus.COMPLETED_NO);
    }

    /**
     * Skips the TypeCode parameters.
     */

    public static void skipParams(CDRInputStream input)
    {
    //nothing to do
    }

    /**
     * Skips the value asociated to the TypeCode. This operation is used by the
     * TIDorb's Any implementation an the subclass <code>skip_value()</code>
     * operations.
     * 
     * @param input
     *            must be alwais a reference to a CDRInputStream object.
     */

    public static boolean skipValue(TypeCode type, CDRInputStream input)
    {
        switch (type.kind().value())
        {
            case TCKind._tk_short:
                input.skipShort();
            break;
            case TCKind._tk_long:
                input.skipLong();
            break;
            case TCKind._tk_longlong:
                input.skip_longlong();
            break;
            case TCKind._tk_ushort:
                input.skipUshort();
            break;
            case TCKind._tk_ulong:
                input.skipUlong();
            break;
            case TCKind._tk_ulonglong:
                input.skipUlonglong();
            break;
            case TCKind._tk_float:
                input.skipFloat();
            break;
            case TCKind._tk_double:
                input.skipDouble();
            break;
            case TCKind._tk_boolean:
                input.skipBoolean();
            break;
            case TCKind._tk_char:
                input.skipChar();
            break;
            case TCKind._tk_wchar:
                input.skipWchar();
            break;
            case TCKind._tk_octet:
                input.skipOctet();
            break;
            case TCKind._tk_any:
                input.skipAny();
            break;
            case TCKind._tk_TypeCode:
                input.skipTypeCode();
            break;
            case TCKind._tk_Principal:
                input.skipPrincipal();
            break;
            case TCKind._tk_null:
            case TCKind._tk_void:
            break;
            default:
                throw new INTERNAL("TypeCode is not basic.", 0,
                                   CompletionStatus.COMPLETED_NO);
        }
        return true;
    }

    /**
     * Copies and remarshals the given typecode value marshaled in an
     * InputStream to a <code>es.tid.TIDorbj.core.CDRInputStream</code>. This
     * method will alwais be invoked by this stream.
     * 
     * @param type
     *            the value <code>TypeCode</code>
     * @param input
     *            the <code>InputStream</code> where the value is marshaled
     * @param output
     *            the <code>es.tid.TIDorbj.core.CDRInputStream</code>
     * @pre the <code>TypeCode</code> must be a basic type
     */

    public static void remarshalValue(TypeCode type, InputStream input,
                                      OutputStream output)
    {
        switch (type.kind().value())
        {
            case TCKind._tk_short:
                output.write_short(input.read_short());
            break;
            case TCKind._tk_long:
                output.write_long(input.read_long());
            break;
            case TCKind._tk_longlong:
                output.write_longlong(input.read_longlong());
            break;
            case TCKind._tk_ushort:
                output.write_ushort(input.read_ushort());
            break;
            case TCKind._tk_ulong:
                output.write_ulong(input.read_ulong());
            break;
            case TCKind._tk_ulonglong:
                output.write_ulonglong(input.read_ulonglong());
            break;
            case TCKind._tk_float:
                output.write_float(input.read_float());
            break;
            case TCKind._tk_double:
                output.write_double(input.read_double());
            break;
            case TCKind._tk_boolean:
                output.write_boolean(input.read_boolean());
            break;
            case TCKind._tk_char:
                output.write_char(input.read_char());
            break;
            case TCKind._tk_wchar:
                output.write_wchar(input.read_wchar());
            break;
            case TCKind._tk_octet:
                output.write_octet(input.read_octet());
            break;
            case TCKind._tk_any:
                output.write_any(input.read_any());
            break;
            case TCKind._tk_objref:
                output.write_Object(input.read_Object());
            break;
            case TCKind._tk_string:
                output.write_string(input.read_string());
            break;
            case TCKind._tk_wstring:
                output.write_wstring(input.read_wstring());
            break;
            case TCKind._tk_TypeCode:
                output.write_TypeCode(input.read_TypeCode());
            break;
            case TCKind._tk_Principal:
                output.write_Principal(input.read_Principal());
            break;
            case TCKind._tk_null:
            case TCKind._tk_void:
            break;
            default:
                throw new org.omg.CORBA.INTERNAL("TypeCode is not basic", 0,
                                                 CompletionStatus.COMPLETED_NO);
        }

    }

    /**
     * Compares two InputStream marshaled values of a given TypeCode to a
     * <code>es.tid.TIDorbj.core.CDRInputStream</code>. This method will
     * alwais be invoked by this stream.
     * 
     * @param type
     *            the value <code>TypeCode</code>
     * @param input_a
     *            the <code>InputStream</code> where one value is marshaled
     * @param input_b
     *            the <code>InputStream</code> where the value value is
     *            marshaled
     * @pre <code>type</code> must be a basic type.
     */

    public static boolean valuesEqual(org.omg.CORBA.TypeCode type,
                                      InputStream a_input, InputStream b_input)
    {
        switch (type.kind().value())
        {
            case TCKind._tk_short:
                return a_input.read_short() == b_input.read_short();
            case TCKind._tk_long:
                return a_input.read_long() == b_input.read_long();
            case TCKind._tk_longlong:
                return a_input.read_longlong() == b_input.read_longlong();
            case TCKind._tk_ushort:
                return a_input.read_ushort() == b_input.read_ushort();
            case TCKind._tk_ulong:
                return a_input.read_ulong() == b_input.read_ulong();
            case TCKind._tk_ulonglong:
                return a_input.read_ulonglong() == b_input.read_ulonglong();
            case TCKind._tk_float:
                return a_input.read_float() == b_input.read_float();
            case TCKind._tk_double:
                return a_input.read_double() == b_input.read_double();
            case TCKind._tk_boolean:
                return a_input.read_boolean() == b_input.read_boolean();
            case TCKind._tk_char:
                return a_input.read_char() == b_input.read_char();
            case TCKind._tk_wchar:
                return a_input.read_wchar() == b_input.read_wchar();
            case TCKind._tk_octet:
                return a_input.read_octet() == b_input.read_octet();
            case TCKind._tk_any:
                TypeCode tc_a,
                tc_b;
                tc_a = a_input.read_TypeCode();
                tc_b = b_input.read_TypeCode();
                if (tc_a.equal(tc_b))
                    return TypeCodeMarshaler
                                            .valuesEqual(tc_a, a_input, b_input);
                else
                    return false;
            case TCKind._tk_TypeCode:
                return (a_input.read_TypeCode()).equal(b_input.read_TypeCode());
            case TCKind._tk_Principal:
                return 
                    (a_input.read_Principal()).equals(b_input.read_Principal());
            case TCKind._tk_null:
            case TCKind._tk_void:
                return true;
            default:
                throw new org.omg.CORBA.MARSHAL("TypeCode is not basic", 0,
                                                CompletionStatus.COMPLETED_NO);

        }

    }

    /**
     * Dumps the description of a given string TypeCode.
     * 
     * @param type
     *            the <code>TypeCode</code>
     * @param output
     *            the output stream where the TypeCode will be dumped
     * @pre <code>type</code> must be a string type.
     */

    public static void dump(TypeCode type, java.io.PrintWriter output)
        throws java.io.IOException
    {
        output.print("[TYPECODE]");
        switch (type.kind().value())
        {
            case TCKind._tk_short:
                output.print("{short}");
            break;
            case TCKind._tk_long:
                output.print("{long}");
            break;
            case TCKind._tk_longlong:
                output.print("{longlong}");
            break;
            case TCKind._tk_ushort:
                output.print("{ushort}");
            break;
            case TCKind._tk_ulong:
                output.print("{ulong}");
            break;
            case TCKind._tk_ulonglong:
                output.print("{ulonglong}");
            break;
            case TCKind._tk_float:
                output.print("{float}");
            break;
            case TCKind._tk_double:
                output.print("{double}");
            break;
            case TCKind._tk_boolean:
                output.print("{boolean}");
            break;
            case TCKind._tk_char:
                output.print("{char}");
            break;
            case TCKind._tk_wchar:
                output.print("{wchar}");
            break;
            case TCKind._tk_octet:
                output.print("{octet}");
            break;
            case TCKind._tk_any:
                output.print("{any}");
            break;
            case TCKind._tk_string:
                output.print("{string}");
            break;
            case TCKind._tk_wstring:
                output.print("{wstring}");
            break;
            case TCKind._tk_TypeCode:
                output.print("{TypeCode}");
            break;
            case TCKind._tk_Principal:
                output.print("{Principal}");
            case TCKind._tk_null:
                output.print("{null}");
            break;
            case TCKind._tk_void:
                output.print("{void}");
            break;
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
     * @pre the typecode must be a simple type
     */

    public static boolean dump_value(TypeCode type, InputStream input,
                                     java.io.PrintWriter output)
        throws java.io.IOException

    {
        switch (type.kind().value())
        {
            case TCKind._tk_null:
                output.print("{null value}");
                return true;
            case TCKind._tk_short:
                output.print("{short: ");
                output.print(input.read_short());
                output.print("}");
                return true;
            case TCKind._tk_long:
                output.print("{long: ");
                output.print(input.read_long());
                output.print("}");
                return true;
            case TCKind._tk_longlong:
                output.print("{longlong: ");
                output.print(input.read_longlong());
                output.print("}");
                return true;
            case TCKind._tk_ushort:
                output.print("{ushort: ");
                output.print(input.read_ushort());
                output.print("}");
                return true;
            case TCKind._tk_ulong:
                output.print("{ulong: ");
                output.print(input.read_ulong());
                output.print("}");
                return true;
            case TCKind._tk_ulonglong:
                output.print("{ulonglong: ");
                output.print(input.read_ulonglong());
                output.print("}");
                return true;
            case TCKind._tk_float:
                output.print("{float: ");
                output.print(input.read_float());
                output.print("}");
                return true;
            case TCKind._tk_double:
                output.print("{double: ");
                output.print(input.read_double());
                output.print("}");
                return true;
            case TCKind._tk_boolean:
                output.print("{boolean: ");
                output.print(input.read_boolean());
                output.print("}");
                return true;
            case TCKind._tk_char:
                output.print("{char: ");
                output.print(input.read_char());
                output.print("}");
                return true;
            case TCKind._tk_wchar:
                output.print("{wchar: ");
                output.print(input.read_wchar());
                output.print("}");
                return true;
            case TCKind._tk_octet:
                output.print("{octet: ");
                output.print(input.read_octet());
                output.print("}");
                return true;
            case TCKind._tk_any:
                output.print("{any: ");
                AnyImpl.dump(input.read_any(), output);
                output.print("}");
                return true;
            case TCKind._tk_string:
                output.print("{string: ");
                output.print(input.read_short());
                output.print("}");
                return true;
            case TCKind._tk_wstring:
                output.print("{wstring: ");
                output.print(input.read_short());
                output.print("}");
                return true;
            case TCKind._tk_TypeCode:
                output.print("{TypeCode: ");
                TypeCodeDumper.dump(input.read_TypeCode(), output);
                output.print("}");
                return true;
            case TCKind._tk_Principal:
                output.print("{Principal: ");
                output.print(input.read_Principal());
                output.print("}");
                return true;
        }

        return false;
    }
}