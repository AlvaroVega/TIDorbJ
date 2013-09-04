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

import org.omg.CORBA.BAD_TYPECODE;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.TypeCodePackage.Bounds;
import org.omg.CORBA.portable.InputStream;

import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

/**
 * The <code>ValueTypeCode</code> class represents a <code>TypeCode</code>
 * object which is associated with an IDL value.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class ValueTypeCode extends ComplexTypeCode
{

    protected short m_value_modifier;

    protected TypeCode m_concrete_base;

    protected ValueMember[] m_members;

    public ValueTypeCode()
    {
        super(TCKind.tk_value);
        m_concrete_base = null;
        m_members = null;

    }

    public ValueTypeCode(String repositoryId, String name,
                         short value_modifier, TypeCode concrete_base,
                         ValueMember[] members)
    {
        super(TCKind.tk_value, repositoryId, name);
        m_value_modifier = value_modifier;
        m_concrete_base = concrete_base;
        m_members = members;

    }

    public boolean equal(org.omg.CORBA.TypeCode tc)
    {
        if (!super.equal(tc))
            return false;

        if (!m_exhaustive_equal)
            return true;

        try {

            if (m_value_modifier != tc.type_modifier())
                return false;
            if (!m_concrete_base.equal(tc.concrete_base_type()))
                return false;
            if (m_members.length != tc.member_count())
                return false;

            for (int i = 0; i < m_members.length; i++) {

                if (!(m_members[i].name).equals(tc.member_name(i)))
                    return false;
                if (m_members[i].type.equal(tc.member_type(i)))
                    return false;
                if (m_members[i].access != tc.member_visibility(i))
                    return false;
                if (m_members[i].type.equal(tc.member_type(i)))
                    return false;

            }

            // allrigth
            return true;
        }
        catch (org.omg.CORBA.TypeCodePackage.BadKind bk) {
            return false;
        }
        catch (org.omg.CORBA.TypeCodePackage.Bounds bk) {
            return false;
        }

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
        } 
    	
        if (m_kind.value() != tc.kind().value()) {
            return false;
        }

        if (!m_exhaustive_equal)
            return true;

        try {

            if (m_value_modifier != tc.type_modifier())
                return false;
            if (!m_concrete_base.equivalent(tc.concrete_base_type()))
                return false;
            if (m_members.length != tc.member_count())
                return false;

            for (int i = 0; i < m_members.length; i++) {

                if (!(m_members[i].name).equals(tc.member_name(i)))
                    return false;
                if (m_members[i].type.equivalent(tc.member_type(i)))
                    return false;
                if (m_members[i].access != tc.member_visibility(i))
                    return false;

            }

            // allrigth
            return true;
        }
        catch (org.omg.CORBA.TypeCodePackage.BadKind bk) {
            return false;
        }
        catch (org.omg.CORBA.TypeCodePackage.Bounds bk) {
            return false;
        }

    }

    public int member_count()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        return m_members.length;
    }

    public java.lang.String member_name(int index)
        throws org.omg.CORBA.TypeCodePackage.BadKind,
        org.omg.CORBA.TypeCodePackage.Bounds
    {
        if (index < m_members.length)
            return m_members[index].name;
        else
            throw new org.omg.CORBA.TypeCodePackage.Bounds();
    }

    public org.omg.CORBA.TypeCode member_type(int index)
        throws org.omg.CORBA.TypeCodePackage.BadKind,
        org.omg.CORBA.TypeCodePackage.Bounds
    {
        if (index < m_members.length)
            return m_members[index].type;
        else
            throw new org.omg.CORBA.TypeCodePackage.Bounds();
    }

    public short member_visibility(int index)
        throws org.omg.CORBA.TypeCodePackage.BadKind,
        org.omg.CORBA.TypeCodePackage.Bounds
    {
        if (index < m_members.length)
            return m_members[index].access;
        else
            throw new org.omg.CORBA.TypeCodePackage.Bounds();
    }

    public short type_modifier()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        return m_value_modifier;
    }

    public org.omg.CORBA.TypeCode concrete_base_type()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        return m_concrete_base;

    }

    //TIDORB operations

    public void readParams(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        super.readParams(input);
        m_value_modifier = input.read_short();
        m_concrete_base = input.read_TypeCode();

        int length = input.read_ulong();

        m_members = new ValueMember[length];

        for (int i = 0; i < length; i++) {
            m_members[i].name = input.read_string();
            m_members[i].type = input.read_TypeCode();
            m_members[i].access = input.read_short();
        }
    }

    public static boolean skipValue(TypeCode type, CDRInputStream input)
    {
        try {
            int member_length = type.member_count();
            for (int i = 0; i < member_length; i++) {
                if (!TypeCodeMarshaler.skipValue(type.member_type(i), input))
                    return false;
            }
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in value operations:"
                                   + bk.toString());
        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in value operations:"
                                   + bds.toString());
        }
        return true;
    }

    /**
     * Marshal the given typecode params in a
     * <code>es.tid.TIDorbj.core.CDRInputStream</code>. This method will
     * alwais be invoked by this stream.
     * 
     * @param type
     *            the <code>TypeCode</code>
     * @param output
     *            the <code>es.tid.TIDorbj.core.CDRInputStream</code>
     * @pre the the <code>TypeCode</code> must be a value type
     */

    public static void writeParams(TypeCode type, CDROutputStream output)
    {
        try {
            ComplexTypeCode.writeParams(type, output);

            output.write_short(type.type_modifier());
            output.write_TypeCode(type.concrete_base_type());

            int length = type.member_count();
            output.write_ulong(length);

            for (int i = 0; i < length; i++) {
                output.write_string(type.member_name(i));
                output.write_TypeCode(type.member_type(i));
                output.write_short(type.member_visibility(i));
            }

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in value type operations:"
                                   + bk.toString());

        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in value type operations:"
                                   + bds.toString());
        }

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
     * @pre the <code>TypeCode</code> must be an alias type
     */

    public static void remarshalValue(org.omg.CORBA.TypeCode type,
                                      InputStream input, CDROutputStream output)
    {
        try {
            int member_length = type.member_count();

            for (int i = 0; i < member_length; i++)
                TypeCodeMarshaler.remarshalValue(type.member_type(i), input,
                                                 output);

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in value operations:"
                                   + bk.toString());

        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in value operations:"
                                   + bds.toString());
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
     * @pre <code>type</code> must be a value type.
     */

    public static boolean valuesEqual(org.omg.CORBA.TypeCode type,
                                      InputStream input_a, InputStream input_b)
    {
        try {
            int member_length = type.member_count();

            for (int i = 0; i < member_length; i++)
                if (!TypeCodeMarshaler.valuesEqual(type.member_type(i),
                                                   input_a, input_b))
                    return false;
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in struct operations:"
                                   + bk.toString());

        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in struct operations:"
                                   + bds.toString());
        }

        return true;
    }

    /**
     * Dumps the description of a given TypeCode.
     * 
     * @param type
     *            the <code>TypeCode</code>
     * @param output
     *            the output stream where the TypeCode will be dumped
     * @pre <code>type</code> must be an enum type.
     */
    public static void dump(TypeCode type, java.io.PrintWriter output)
        throws java.io.IOException
    {
        try {
            output.print("[TYPECODE]{value ");
            ComplexTypeCode.dumpParams(type, output);
            output.print(", visibility= ");
            output.print(type.type_modifier());
            output.print(", concrete base= ");
            TypeCodeDumper.dump(type.concrete_base_type(), output);

            output.print(" {");

            int length = type.member_count();

            for (int i = 0; i < length; i++) {
                output.print(type.member_visibility(i));
                output.print(' ');
                output.print(type.member_name(i));
                output.print(": ");
                TypeCodeDumper.dump(type.member_type(i), output);
            }
            output.print('}');

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in value operations:" 
                                   + bk.toString());

        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in value operations:"
                                   + bds.toString());
        }

    }

}