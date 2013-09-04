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

import es.tid.TIDorbj.core.cdr.CDRInputStream;

/**
 * The <code>AbstractInterfaceTypeCode</code> class represents a
 * <code>TypeCode</code> object which is associated with an IDL abstract
 * interface.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class AbstractInterfaceTypeCode extends ComplexTypeCode
{

    public AbstractInterfaceTypeCode()
    {
        super(TCKind.tk_abstract_interface);
    }

    public AbstractInterfaceTypeCode(String repositoryId, String name)
    {
        super(TCKind.tk_abstract_interface, repositoryId, name);
    }

    /**
     * Dumps the description of a given ComplexTypeCode.
     * 
     * @param type
     *            the <code>TypeCode</code>
     * @param output
     *            the output stream where the TypeCode will be dumped
     * @pre <code>type</code> must be a ComplexTypeCode.
     */
    public static void dump(TypeCode type, java.io.PrintWriter output)
        throws java.io.IOException
    {

        output.print("[TYPECODE]{abstract interface: ");
        ComplexTypeCode.dumpParams(type, output);
        output.print('}');
    }

    public static boolean skip_value(TypeCode type, CDRInputStream input)
    {
        input.read_abstract_interface();
        return true;
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

    public static boolean 
    	dump_value(TypeCode type,
    	           org.omg.CORBA_2_3.portable.InputStream input,
    	           java.io.PrintWriter output)
        throws java.io.IOException
    {
        java.lang.Object obj = input.read_abstract_interface();

        dump(type, output);
        output.print("[VALUE] {--}");
        return true;
    }
}