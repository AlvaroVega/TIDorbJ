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
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.TypeCodePackage.Bounds;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

/**
 * The <code>EnumTypeCode</code> class represents a <code>TypeCode</code>
 * object which is associated with an IDL enum.
 * 
 * @autor Juan A. Ca&acute;ceres
 * @version 1.0
 */

public class EnumTypeCode extends ComplexTypeCode
{

    protected String[] m_members;

    public EnumTypeCode()
    {
        super(TCKind.tk_enum);
        m_members = null;

    }

    public EnumTypeCode(String id, String name, String[] members)
    {
        super(TCKind.tk_enum, id, name);
        m_members = members;

    }

    public boolean equal(org.omg.CORBA.TypeCode tc)
    {
        if (!super.equal(tc))
            return false;
        try {

            if (m_members.length != tc.member_count())
                return false;

            for (int i = 0; i < m_members.length; i++) {
                if (!m_members[i].equals(tc.member_name(i)))
                    return false;
            }
            // all rigth
            return true;

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in enum operations:" 
                                   + bk.toString());

        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in enum operations:" 
                                   + bds.toString());
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
        
        try {

            if (m_members.length != tc.member_count())
                return false;

            for (int i = 0; i < m_members.length; i++) {
                if (!m_members[i].equals(tc.member_name(i)))
                    return false;
            }
            // all rigth
            return true;

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in enum operations:" 
                                   + bk.toString());

        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in enum operations:" 
                                   + bds.toString());
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
            return m_members[index];
        else
            throw new org.omg.CORBA.TypeCodePackage.Bounds();
    }

    //TIDORB operations

    public void readParams(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        super.readParams(input);
        int length = input.read_ulong();

        m_members = new String[length];
        for (int i = 0; i < length; i++)
            m_members[i] = input.read_string();

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
        input.skipUlong();
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
     * @pre the the <code>TypeCode</code> must be an enum type
     */

    public static void writeParams(TypeCode type, CDROutputStream output)
    {
        ComplexTypeCode.writeParams(type, output);

        try {

            int length = type.member_count();

            output.write_ulong(length);
            for (int i = 0; i < length; i++)
                output.write_string(type.member_name(i));

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in enum operations:" 
                                   + bk.toString());
        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in enum operations:" 
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
     * @pre the <code>TypeCode</code> must be an enum type
     */

    public static void remarshalValue(TypeCode type, InputStream input,
                                      OutputStream output)
    {
        int value = input.read_ulong();

        try {

            if (value >= type.member_count())
                throw new MARSHAL("Enum discriminator value out of range.");

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in enum operations:" + bk.toString());
        }

        output.write_ulong(value);
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
     * @pre <code>type</code> must be an alias type.
     */

    public static boolean valuesEqual(org.omg.CORBA.TypeCode type,
                                      InputStream input_a, InputStream input_b)
    {
        try {
            int value = input_a.read_ulong();
            if (value >= type.member_count())
                throw new MARSHAL("Enum discriminator value out of range.");

            return value == input_b.read_ulong();

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in enum operations:" + bk.toString());
        }
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

            output.print("[TYPECODE]{enum ");
            ComplexTypeCode.dumpParams(type, output);
            output.print(" {");
            int length = type.member_count();

            output.print(type.member_name(0));
            for (int i = 1; i < length; i++) {
                output.print(" ,");
                output.print(type.member_name(i));
            }
            output.print("} }");
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in enum operations:"
                                   + bk.toString());
        }
        catch (Bounds bds) {
            throw new BAD_TYPECODE("Fault in enum operations:"
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
     * @pre the typecode must be an enum type
     * @return <code>true</code> if if has been possible dump the value.
     */

    public static boolean dumpValue(TypeCode type, InputStream input,
                                    java.io.PrintWriter output)
        throws java.io.IOException
    {
        try {
            output.print("[VALUE]{enum ");
            output.print(type.id());
            output.print(": ");
            output.print(input.read_ulong());
            output.print('}');
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in enum operations:" + bk.toString());
        }
        return true;
    }

}