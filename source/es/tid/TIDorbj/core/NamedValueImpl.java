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
package es.tid.TIDorbj.core;

import org.omg.CORBA.ARG_IN;
import org.omg.CORBA.ARG_INOUT;
import org.omg.CORBA.ARG_OUT;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;

/**
 * TIDorb NameValueImpl pseudobject implementation.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class NamedValueImpl extends org.omg.CORBA.NamedValue
{

    protected java.lang.String m_name;

    protected org.omg.CORBA.Any m_value;

    protected int m_flags;

    protected NamedValueImpl()
    {}

    public static NamedValueImpl from_int(int flags, java.lang.String name,
                                          org.omg.CORBA.Any value)
        throws org.omg.CORBA.BAD_PARAM
    {
        if ((name == null) || (value == null))
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        verifyFlags(flags);
        NamedValueImpl named = new NamedValueImpl();
        named.m_name = name;
        named.m_value = value;
        named.m_flags = flags;
        return named;

    }

    private static void verifyFlags(int flags)
    {
        if ((flags != ARG_IN.value) && (flags != ARG_INOUT.value)
            && (flags != ARG_OUT.value))
            throw new BAD_PARAM("Invalid flag argument", 0,
                                CompletionStatus.COMPLETED_NO);
    }

    public String name()
    {
        return m_name;
    }

    public org.omg.CORBA.Any value()
    {
        return m_value;
    }

    public int flags()
    {
        return m_flags;
    }
}