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

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_TYPECODE;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.TypeCodePackage.Bounds;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import es.tid.TIDorbj.core.AnyImpl;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

/**
 * The <code>UnionTypeCode</code> class represents a <code>TypeCode</code>
 * object which is associated with an IDL union.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class UnionTypeCode extends ComplexTypeCode
{

    protected TypeCode m_discriminator_type;

    protected int m_default_used;

    protected UnionMember[] m_members;

    public UnionTypeCode()
    {
        super(TCKind.tk_union);
        m_discriminator_type = null;
        m_members = null;
    }

    public UnionTypeCode(String repositoryId, String name,
                         TypeCode discriminator_type,
                         org.omg.CORBA.UnionMember[] members)
    {
        super(TCKind.tk_union, repositoryId, name);
        m_discriminator_type = discriminator_type;

        boolean thereis_default = false;
        int i = 0;

        m_default_used = -1; // default_used mecanism not explicited in CORBA
        // 2.3

        TypeCode tc_default = TypeCodeFactory.tc_octet;

        for (i = 0; i < members.length; i++) {
            if (tc_default.equal(members[i].label.type())) {
                m_default_used = i;
                break;
            }
        }

        m_members = members;

    }

    public boolean equal(org.omg.CORBA.TypeCode tc)
    {
        if (!super.equal(tc))
            return false;

        if (!m_exhaustive_equal)
            return true;
        try {

            if (!m_discriminator_type.equal(tc.discriminator_type()))
                return false;
            if (m_members.length != tc.member_count())
                return false;
            if (m_default_used != tc.default_index())
                return false;
            for (int i = 0; i < m_members.length; i++) {
                if (!(m_members[i].name).equals(tc.member_name(i)))
                    return false;
                if (m_members[i].type.equal(tc.member_type(i)))
                    return false;
            }

            // all rigth
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

            if (!m_discriminator_type.equivalent(tc.discriminator_type()))
                return false;
            if (m_members.length != tc.member_count())
                return false;
            if (m_default_used != tc.default_index())
                return false;
            for (int i = 0; i < m_members.length; i++) {
                if (!(m_members[i].name).equals(tc.member_name(i)))
                    return false;
                if (m_members[i].type.equivalent(tc.member_type(i)))
                    return false;
            }

            // all rigth
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

    public org.omg.CORBA.TypeCode discriminator_type()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        return m_discriminator_type;
    }

    public int default_index()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        return m_default_used;
    }

    public org.omg.CORBA.Any member_label(int index)
        throws org.omg.CORBA.TypeCodePackage.BadKind,
        org.omg.CORBA.TypeCodePackage.Bounds
    {
        if (index < m_members.length)
            return m_members[index].label;
        else
            throw new org.omg.CORBA.TypeCodePackage.Bounds();

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

    //TIDORB operations

    public void readParams(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        super.readParams(input);

        m_discriminator_type = input.read_TypeCode();
        m_default_used = input.read_long();
        int length = input.read_ulong();

        m_members = new UnionMember[length];

        for (int i = 0; i < length; i++) {
            m_members[i] = new org.omg.CORBA.UnionMember();
            // Change, only the the value of label is marshaled
            //_members[i].label = input.read_any();
            m_members[i].label = input.orb().create_any();
            m_members[i].label.read_value(input, m_discriminator_type);
            if (!m_discriminator_type.equal(m_members[i].label.type())) {
                if (m_members[i].label.type().kind().value() 
                    == TCKind._tk_octet) {
                    if (m_default_used != i)
                        throw new MARSHAL(
                                          "Bad label: Union does not have any "
                                          + "defalt member.",
                                          0, CompletionStatus.COMPLETED_NO);
                } else {
                    throw new MARSHAL("Invalid Union discriminator TypeCode",
                                      0, CompletionStatus.COMPLETED_NO);
                }
            }
            m_members[i].name = input.read_string();
            m_members[i].type = input.read_TypeCode();
        }
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

        try {
            Any discriminator = input.orb().create_any();

            discriminator.read_value(input, type.discriminator_type());

            int member_index = searchMemberIndex(type, discriminator);

            if (member_index <= -1)
                throw new MARSHAL("TypeCode with no default case.");

            return TypeCodeMarshaler.skipValue(type.member_type(member_index),
                                               input);

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in union operations:"
                                   + bk.toString());

        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in union operations:"
                                   + bds.toString());
        }
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
     * @pre the the <code>TypeCode</code> must be an union type
     */

    public static void writeParams(TypeCode type, CDROutputStream output)
    {
        try {
            ComplexTypeCode.writeParams(type, output);

            output.write_TypeCode(type.discriminator_type());
            output.write_long(type.default_index());

            int length = type.member_count();

            output.write_ulong(length);

            for (int i = 0; i < length; i++) {
                // Change, only the value of discriminator is marshalled
                // output.write_any(type.member_label(i));
                type.member_label(i).write_value(output);
                output.write_string(type.member_name(i));
                output.write_TypeCode(type.member_type(i));
            }

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in union operations:"
                                   + bk.toString());

        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in union operations:"
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

    public static void remarshalValue(TypeCode type, InputStream input,
                                      OutputStream output)
    {
        try {
            Any discriminator = input.orb().create_any();

            discriminator.read_value(input, type.discriminator_type());

            int member_index = searchMemberIndex(type, discriminator);

            if (member_index <= -1)
                throw new MARSHAL("TypeCode with no default case.");

            discriminator.write_value(output);

            TypeCodeMarshaler.remarshalValue(type.member_type(member_index),
                                             input, output);

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in union operations:"
                                   + bk.toString());

        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in union operations:"
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
     * @pre <code>type</code> must be an union type.
     */

    public static boolean valuesEqual(org.omg.CORBA.TypeCode type,
                                      InputStream input_a, InputStream input_b)
    {
        try {
            Any a_discriminator = input_a.orb().create_any();

            a_discriminator.read_value(input_a, type.discriminator_type());

            Any b_discriminator = input_b.orb().create_any();

            b_discriminator.read_value(input_b, type.discriminator_type());

            if (!a_discriminator.equal(b_discriminator))
                return false;

            int member_index = searchMemberIndex(type, a_discriminator);

            if (member_index <= -1)
                throw new MARSHAL("TypeCode with no default case.");

            return 
            	TypeCodeMarshaler.valuesEqual(type.member_type(member_index),
            	                              input_a,
            	                              input_b);
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in union operations:"
                                   + bk.toString());

        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in union operations:"
                                   + bds.toString());
        }
    }

    /**
     * Dumps the description of a given TypeCode.
     * 
     * @param type
     *            the <code>TypeCode</code>
     * @param output
     *            the output stream where the TypeCode will be dumped
     * @pre <code>type</code> must be an union type.
     */

    public static void dump(TypeCode type, java.io.PrintWriter output)
        throws java.io.IOException
    {
        try {
            output.print("[TYPECODE]{union (");

            int length = type.member_count();

            output.print(length);
            output.print(" members) ");
            ComplexTypeCode.dumpParams(type, output);
            output.print(" {");

            for (int i = 0; i < length; i++) {
                output.print(" case ");
                AnyImpl.dump(type.member_label(i), output);

                output.print(": ");
                output.print(type.member_name(i));
                output.print(" -> ");
                TypeCodeDumper.dump(type.member_type(i), output);
                output.print(" | ");
            }

            output.print('}');

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in union operations:"
                                   + bk.toString());

        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in union operations:"
                                   + bds.toString());
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
     * @pre the typecode must be a struct type
     * @return <code>true</code> if if has been possible dump the value.
     */

    public static boolean dumpValue(TypeCode type, InputStream input,
                                    java.io.PrintWriter output)
        throws java.io.IOException
    {
        try {
            output.print("[VALUE]{union ");
            output.print(type.name());
            output.print(" discriminator: ");
            Any disc_any = input.orb().create_any();
            disc_any.read_value(input, type.discriminator_type());
            AnyImpl.dump(disc_any, output);

            int member_index = searchMemberIndex(type, disc_any);

            if (member_index < 0)
                throw new MARSHAL("TypeCode with no default case.");

            if (member_index == type.default_index())
                output.print(" /default/ ");
            output.print(type.member_name(member_index));
            output.print(" -> ");

            if (!TypeCodeDumper.dumpValue(type.member_type(member_index),
                                          input, output))
                return false;

            output.print('}');

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in union operations:" 
                                   + bk.toString());

        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in union operations:"
                                   + bds.toString());
        }
        return true;
    }

    public static int searchMemberIndex(TypeCode type,
                                        org.omg.CORBA.Any discriminator)
    {
        try {
            int length = type.member_count();
            for (int i = 0; i < length; i++)
                if (discriminator.equal(type.member_label(i)))
                    return i;

            return type.default_index();

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in union operations:" 
                                   + bk.toString());

        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in union operations:"
                                   + bds.toString());
        }
    }

}