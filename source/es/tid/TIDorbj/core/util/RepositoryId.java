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
package es.tid.TIDorbj.core.util;

public class RepositoryId
{

    public String m_value;

    public RepositoryId(String value)
    {
        m_value = value;
    }

    public static String className(String repId)
    {
        if (repId.equals("IDL:omg.org/CORBA/WStringValue:1.0")) {
            return "java.lang.String";
        } else if (repId.startsWith("IDL:")) {
            // cut "IDL:" and version
            // and swap "org.omg" if necessary

            String id_base = repId.substring(4, repId.lastIndexOf(':'));
            if (id_base.startsWith("omg.org"))
                return ir2scopes("org.omg", id_base.substring(7));
            else
                return ir2scopes("", id_base);
        } else if (repId.startsWith("RMI:")) {
            return repId.substring(4, repId.indexOf(':', 4));
        } else {
            throw new RuntimeException("unrecognized RepositoryID: " + repId);
        }
    }

    /**
     * @return java.lang.String
     */

    private static String ir2scopes(String prefix, String s)
    {
        if (s.indexOf("/") < 0)
            return s;
        java.util.StringTokenizer strtok = 
            new java.util.StringTokenizer(s, "/");

        int count = strtok.countTokens();
        StringBuffer sb = new StringBuffer();
        sb.append(prefix);

        for (int i = 0; strtok.hasMoreTokens(); i++) {
            String sc = strtok.nextToken();
            Class c = null;
            if (sb.toString().length() > 0)
                c = loadClass(sb.toString() + "." + sc);
            else
                c = loadClass(sc);
            if (c == null)
                if (sb.toString().length() > 0)
                    sb.append("." + sc);
                else
                    sb.append(sc);
            else if (i < count - 1)
                sb.append("." + sc + "Package");
            else
                sb.append("." + sc);
        }

        return sb.toString();
    }

    public static String repId(Class c)
    {
        if (org.omg.CORBA.portable.IDLEntity.class.isAssignableFrom(c)) {
            String className = c.getName();
            String head = "";
            String body = "";

            // add "IDL:" and ":1.0"
            // and swap "org.omg" if necessary

            if (className.startsWith("org.omg")
                || className.startsWith("org/omg")) {
                if (className.length() > 7)
                    body = className.substring(7);
                return "IDL:omg.org/" + scopesToIR(body) + ":1.0";
            } else
                return "IDL:" + scopesToIR(className) + ":1.0";
        } else
            return javax.rmi.CORBA.Util.createValueHandler()
                                       .getRMIRepositoryID(c);
    }

    private static String scopesToIR(String s)
    {
        if (s.indexOf(".") < 0)
            return s;
        java.util.StringTokenizer strtok = 
            new java.util.StringTokenizer(s, ".");

        String scopes[] = new String[strtok.countTokens()];

        for (int i = 0; strtok.hasMoreTokens(); i++) {
            String sc = strtok.nextToken();
            if (sc.endsWith("Package"))
                scopes[i] = sc.substring(0, sc.indexOf("Package"));
            else
                scopes[i] = sc;
        }

        StringBuffer sb = new StringBuffer();
        if (scopes.length > 1) {
            for (int i = 0; i < scopes.length - 1; i++)
                sb.append(scopes[i] + "/");
        }

        sb.append(scopes[scopes.length - 1]);
        return sb.toString();
    }

    /**
     * convert a class name to a Repository ID <BR>
     * classname - the class name to convert resolveClass - indicates whether
     * the method should try to resolve and load the class. If true and the
     * class could not be loaded, an IllegalArgumentException will be thrown
     */

    public static String toRepositoryID(String className, boolean resolveClass)
    {
        if (className.equals("") || className.startsWith("IDL:")
            || className.startsWith("RMI:"))
            return className;
        else {
            if (resolveClass) {

                Class c = loadClass(className);
                if (c == null)
                    throw new IllegalArgumentException("cannot find class: "
                                                       + className);
                else
                    return repId(c);
            }
            return "IDL:" + className + ":1.0";
        }
    }

    public static String toRepositoryID(String className)
    {
        return toRepositoryID(className, true);
    }

    /**
     * Loads class `name' using an appropriate class loader. Returns the
     * corresponding class object, or null if the class loader cannot find a
     * class by that name.
     */
    private static Class loadClass(String name)
    {
        try {
            return Thread.currentThread().getContextClassLoader()
                         .loadClass(name);
        }
        catch (ClassNotFoundException e) {
            return null;
        }
    }
}

