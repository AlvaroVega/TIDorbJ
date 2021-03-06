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

import java.util.StringTokenizer;

import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextPackage.InvalidName;

import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.util.TranslateURLEscapes;

public class Corbaname
{

    public static org.omg.CORBA.Object toObject(TIDORB tidorb,
                                                String corbaname_URL)
        throws org.omg.CosNaming.NamingContextPackage.InvalidName,
        org.omg.CosNaming.NamingContextPackage.NotFound,
        org.omg.CosNaming.NamingContextPackage.CannotProceed,
        org.omg.CORBA.ORBPackage.InvalidName
    {
        String corbaname;
        int nameposition;
        org.omg.CosNaming.NameComponent[] name_path;
        org.omg.CosNaming.NamingContext ncontext;
        if (corbaname_URL.startsWith("corbaname:")) {
            corbaname = corbaname_URL.substring(10);
            nameposition = corbaname.indexOf('#');
            ncontext = 
                parseProtocol(tidorb, corbaname.substring(0,
                                                          nameposition));
            name_path = toName(corbaname.substring(nameposition + 1));
            return ncontext.resolve(name_path);
        } else
            throw new InvalidName(); /* Invalid corbaname" */

    }

    private static org.omg.CosNaming.NamingContext 
    	parseProtocol(TIDORB tidorb,
    	              String protocol)
        throws org.omg.CosNaming.NamingContextPackage.InvalidName,
        org.omg.CORBA.ORBPackage.InvalidName
    {
        if (protocol.equals("rir:") || protocol.equals("rir:/NameService")) {
            org.omg.CORBA.Object naming_ref = 
                tidorb.resolve_initial_references("NameService");
            return org.omg.CosNaming.NamingContextHelper.narrow(naming_ref);
        } else
            throw new InvalidName();
    }

    public static org.omg.CosNaming.NameComponent[] toName(String sn)
        throws org.omg.CosNaming.NamingContextPackage.InvalidName
    {
        int ntokensn, ntokenscmp;
        String name = "";
        String component = "";
        String id;
        String kind;
        name = TranslateURLEscapes.putEscapes(sn);
        if (name.indexOf("//") != -1)
            throw new InvalidName();
        StringTokenizer stname = new StringTokenizer(name, "/", false);
        ntokensn = stname.countTokens();
        org.omg.CosNaming.NameComponent[] n =
            new org.omg.CosNaming.NameComponent[ntokensn];
        for (int i = 0; i < ntokensn; i++) {
            id = "";
            kind = "";
            component = stname.nextToken();
            StringTokenizer stcomp = new StringTokenizer(component, ".", false);
            ntokenscmp = stcomp.countTokens();
            if (ntokenscmp > 2)
                throw new InvalidName();
            if (!(ntokenscmp <= 1 && component.charAt(0) == '.'))
                id = TranslateURLEscapes.resolveEscapes(stcomp.nextToken());
            if (stcomp.countTokens() == 1)
                kind = TranslateURLEscapes.resolveEscapes(stcomp.nextToken());
            n[i] = new NameComponent(id, kind);
        }
        return n;
    }

    public static String toString(org.omg.CosNaming.NameComponent[] n)
    {
        String id;
        String kind;
        String sn = "";
        for (int i = 0; i < n.length; i++) {
            id = TranslateURLEscapes.stringfy(n[i].id);
            kind = TranslateURLEscapes.stringfy(n[i].kind);
            sn = sn + id;
            if (!kind.equals(""))
                sn = sn + "." + kind;
            if (i < n.length - 1)
                sn = sn + "/";
        }
        return sn;
    }

}