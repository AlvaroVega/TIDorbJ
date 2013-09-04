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

import es.tid.TIDorbj.core.cdr.CDR;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;
import es.tid.TIDorbj.core.cdr.Encapsulation;
import es.tid.TIDorbj.core.cdr.PointerCDR;

/**
 * The <code>ComplexTypeCode</code> class is base class the base for
 * constructing complex <code>TypeCode</code> classes as
 * <code>StructTypeCode</code>,<code>AliasTypeCode</code>, etc.
 * 
 * @autor Juan A. Ca&acute;ceres
 * @version 1.0
 */

public class ComplexTypeCode extends TypeCodeImpl
{

    protected String m_repository_id;

    protected String m_name;

    protected Encapsulation m_encapsuled_params;

    public ComplexTypeCode(TCKind type)
    {
        super(type);
        m_repository_id = null;
        m_name = null;
        m_encapsuled_params = null;
    }

    public ComplexTypeCode(TCKind type, String repositoryId, String name)
    {
        super(type);
        m_repository_id = repositoryId;
        m_name = name;
        m_encapsuled_params = null;
    }

    public boolean equal(org.omg.CORBA.TypeCode tc)
    {
        if (!super.equal(tc))
            return false;
        try {
            return (m_repository_id.equals(tc.id())
                    && m_name.equals(tc.name()));
        }
        catch (org.omg.CORBA.TypeCodePackage.BadKind bk) {
            return false;
        }
    }
    
    
    

    public java.lang.String id()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        return m_repository_id;
    }

    public java.lang.String name()
        throws org.omg.CORBA.TypeCodePackage.BadKind
    {
        return m_name;
    }

    public int hashCode()
    {
        return m_repository_id.hashCode();
    }

    //TIDORB operations

    public boolean isSimple()
    {
        return false;
    }

    public static void writeParams(TypeCode type, CDROutputStream output)
    {
        try {
            output.write_string(type.id());
            output.write_string(type.name());
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("TypeCode has not params");
        }
    }

    public void readParams(CDRInputStream input)
    {
        m_repository_id = input.read_string();
        m_name = input.read_string();
    }

    public static void skipParams(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        int length = input.read_ulong();
        input.skip(length);
    }

    public void partialUnmarshal(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        CDRInputStream encap_input = input.copy();

        input.enterEncapsulation();

        readParams(input);

        if (!input.getContextCDR().hasExternalIndirections()) {
            m_encapsuled_params = encap_input.readEncapsulation();
        }

        encap_input = null;

        input.exitEncapsulation();

    }

    /**
     * Marshal the given typecode in a
     * <code>es.tid.TIDorbj.core.CDRInputStream</code>. This method will
     * alwais be invoked by this stream via the <code>TypeCodeMarshaler</code>.
     * 
     * @param type
     *            the <code>TypeCode</code>
     * @param output
     *            the <code>es.tid.TIDorbj.core.CDRInputStream</code>
     * @pre the <code>TypeCode</code> must be a complex type
     */

    public static void marshal(TypeCode type, CDROutputStream output)
    {
        // views if the typecode has been marshaled before

        PointerCDR previous_position = null;

        // "Indirections are not "freestanding", but only exist inside some
        // other encoded TypeCode

        if (!output.getContextCDR().isRootContext()) {

            try {
                previous_position = output.getContextCDR()
                                          .lookupPosition(type.id());
            }
            catch (BadKind bk) {
                throw new BAD_TYPECODE("type.id() fails.");
            }
        }

        if (previous_position != null) { // marshal indirection

            output.writeIndirection(previous_position);
            return;
        }

        // aligns to get the typeCode position

        output.alignment(CDR.LONG_SIZE);

        // calculate the typecode position

        PointerCDR typecode_position;

        typecode_position = output.getPointer();

        try {
            // saves the typecode position
            output.getContextCDR().putObject(type.id(), typecode_position);
        }
        catch (BadKind bk) {}

        // write the tc_kind value and un

        output.write_long(type.kind().value());

        // if the typecode has a copy of the params marshaled write its
        // encapsulation
        if (type instanceof ComplexTypeCode) {

            synchronized (type) {
                // esto se debe cambiar a una tabla de typecodes para tratar
                // typecodes de
                // otros ORBs
                Encapsulation encap = 
                    ((ComplexTypeCode) type).m_encapsuled_params;

                if (encap != null) {
                    output.writeEncapsulation(encap);
                    return;
                }

                // create input stream for save the encapsualation

                boolean read_encapsulation = false;

                CDRInputStream encapsulation_input;

                encapsulation_input = output.inputStreamAtThisPosition();

                // marshalling of params

                output.enterEncapsulation();

                TypeCodeMarshaler.writeParams(type, output);

                read_encapsulation = !output.getContextCDR()
                                            .hasExternalIndirections();

                output.exitEncapsulation();

                // if the typecode can be saved, because it does not have any
                // indirection out
                // of the scope of the encapsulation, save it

                if (read_encapsulation)
                    ((ComplexTypeCode) type).m_encapsuled_params = 
                        encapsulation_input.readEncapsulation();

                try {
                    encapsulation_input.close();
                }
                catch (Exception e) {}
            }

            return;

        }

        output.enterEncapsulation();

        TypeCodeMarshaler.writeParams(type, output);

        output.exitEncapsulation();

    }

    /**
     * Dumps the description of a given ComplexTypeCode. Fails if it is not
     * overriden by a child class.
     * 
     * @param type
     *            the <code>TypeCode</code>
     * @param output
     *            the output stream where the TypeCode will be dumped
     */

    public static void dump(TypeCode type, java.io.PrintWriter output)
        throws java.io.IOException
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Dumps the parameters description of a a given complex TypeCode.
     * 
     * @param type
     *            the <code>TypeCode</code>
     * @param output
     *            the output stream where the value will be dumped
     */

    protected static void dumpParams(TypeCode type, java.io.PrintWriter output)
        throws java.io.IOException
    {
        try {
            output.print("repositoryId= ");
            output.print(type.id());
            output.print(", name=");
            output.print(type.name());
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE("Complex type expected.");
        }
    }
}