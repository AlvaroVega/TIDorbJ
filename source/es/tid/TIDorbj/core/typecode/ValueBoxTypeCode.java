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
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.portable.InputStream;

import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

/**
 * The <code>ValueBoxTypeCode</code> class represents a <code>TypeCode</code>
 * object which is associated with an IDL valuebox.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 0.1
 */

public class ValueBoxTypeCode extends ComplexTypeCode
{

    TypeCode m_type;

    public ValueBoxTypeCode()
    {
        super(TCKind.tk_value_box);
        m_type = null;
    }

    public ValueBoxTypeCode(String repositoryId, String name, TypeCode type)
    {
        super(TCKind.tk_value_box, repositoryId, name);
        m_type = type;
    }

    public boolean equal(org.omg.CORBA.TypeCode tc)
    {
        if (!super.equal(tc))
            return false;

        if (!m_exhaustive_equal)
            return true;
        try {
            return m_type.equal(tc.content_type());
        }
        catch (org.omg.CORBA.TypeCodePackage.BadKind bk) {
            /* unreachable */
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
            return m_type.equivalent(tc.content_type());
        }
        catch (org.omg.CORBA.TypeCodePackage.BadKind bk) {
            /* unreachable */
            return false;
        }

    }
    public org.omg.CORBA.TypeCode content_type()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        return m_type;
    }

    //TIDORB operations

    public void readParams(CDRInputStream input)
    {
        super.readParams(input);
        m_type = input.read_TypeCode();
    }

    public static boolean skipValue(TypeCode type, CDRInputStream input)
    {
        try {
            return TypeCodeMarshaler.skipValue(type.content_type(), input);
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE(" Fault ivoking content_type().");
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
     * @pre the the <code>TypeCode</code> must be a valuebox type
     */

    public static void writeParams(TypeCode type, CDROutputStream output)
    {
        try {
            ComplexTypeCode.writeParams(type, output);

            output.write_TypeCode(type.content_type());

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in valuebox operations:"
                                   + bk.toString());
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
            TypeCodeMarshaler.remarshalValue(type.content_type(),
                                             input, 
                                             output);
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE(" Fault ivoking content_type().");
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
     * @pre <code>type</code> must be a valuebox type.
     */

    public static boolean valuesEqual(org.omg.CORBA.TypeCode type,
                                      InputStream input_a, InputStream input_b)
    {
        try {
            return TypeCodeMarshaler.valuesEqual(type.content_type(), input_a,
                                                 input_b);
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Fault in valuebox operations:"
                                   + bk.toString());
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
     * @pre the typecode must be an alias type
     * @return <code>true</code> if if has been possible dump the value.
     */

    public static boolean dumpValue(TypeCode type, InputStream input,
                                    java.io.PrintWriter output)
        throws java.io.IOException
    {
        try {
            output.print("[VALUE]{valuebox ");
            output.print(type.name());
            output.print(": ");
            TypeCodeDumper.dumpValue(type.content_type(), input, output);
            output.print('}');
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE(" Fault ivoking content_type().");
        }
        return true;
    }
}