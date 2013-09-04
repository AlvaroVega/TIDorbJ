/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 478 $
* Date: $Date: 2011-04-29 16:42:47 +0200 (Fri, 29 Apr 2011) $
* Last modified by: $Author: avega $
*
* (C) Copyright 2004 Telef�nica Investigaci�n y Desarrollo
*     S.A.Unipersonal (Telef�nica I+D)
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
package es.tid.TIDorbj.core.comm.iiop;

import java.util.StringTokenizer;

import org.omg.CORBA.ORBPackage.InvalidName;

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.cdr.CDR;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;
import es.tid.TIDorbj.core.cdr.Encapsulation;
import es.tid.TIDorbj.core.comm.giop.GIOPVersion;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.util.Corbaloc;

public class IIOPCorbaloc
{

    public static String getObjectId(String corbaloc_URL)
        throws org.omg.CORBA.ORBPackage.InvalidName
    {
        String corbaloc, addr, path, v, l;
        GIOPVersion version;
        ListenPoint listener;
        IIOPProfile[] profiles;
        int slashposition, ntokens;
        if (corbaloc_URL.startsWith("corbaloc:iiop:")) {
            corbaloc = corbaloc_URL.substring(14);
        } else if (corbaloc_URL.startsWith("corbaloc::")) {
            corbaloc = corbaloc_URL.substring(10);
        } else {
            throw new InvalidName("Invalid corbaloc");
        }

        slashposition = corbaloc.indexOf('/');
        addr = corbaloc.substring(0, slashposition);
        StringTokenizer staddr = new StringTokenizer(addr, "@", false);
        ntokens = staddr.countTokens();
        if (ntokens > 2)
            throw new InvalidName("Invalid Address Name");
        else {
            if (ntokens == 1) {
                version = es.tid.TIDorbj.core.util.Corbaloc.parseVersion("1.0");
            } else {
                v = staddr.nextToken();
                version = es.tid.TIDorbj.core.util.Corbaloc.parseVersion(v);
            }

        }

        l = staddr.nextToken();

        listener = es.tid.TIDorbj.core.util.Corbaloc.parseListenPoint(l);

        return corbaloc.substring(slashposition + 1);
    }

    public static IOR getIOR(String corbaloc_URL)
        throws org.omg.CORBA.ORBPackage.InvalidName
    {
        String corbaloc, addr, url_key, v, l;
        GIOPVersion version;
        ListenPoint listener;
        IIOPProfile[] profiles;
        int slashposition, ntokens;
        if (corbaloc_URL.startsWith("corbaloc:iiop:")) {
            corbaloc = corbaloc_URL.substring(14);
        } else if (corbaloc_URL.startsWith("corbaloc::")) {
            corbaloc = corbaloc_URL.substring(10);
        } else {
            throw new InvalidName("Invalid corbaloc");
        }

        slashposition = corbaloc.indexOf('/');
        addr = corbaloc.substring(0, slashposition);
        StringTokenizer staddr = new StringTokenizer(addr, "@", false);
        ntokens = staddr.countTokens();
        if (ntokens > 2)
            throw new InvalidName("Invalid Address Name");
        else {
            if (ntokens == 1) {
                version = es.tid.TIDorbj.core.util.Corbaloc.parseVersion("1.0");
            } else {
                v = staddr.nextToken();
                version = es.tid.TIDorbj.core.util.Corbaloc.parseVersion(v);
            }

        }

        l = staddr.nextToken();

        listener = es.tid.TIDorbj.core.util.Corbaloc.parseListenPoint(l);

        url_key = corbaloc.substring(slashposition + 1);

        ObjectKey objectkey = createKey(url_key);

        profiles = new IIOPProfile[1];

        profiles[0] = new IIOPProfile(version, listener, objectkey, null);

        return new IIOPIOR("", profiles);
    }

    /**
     * Simulates an usual ObjectKey, that is marshaled in an Encapsulation, but
     * instead, the url ObjectKey is marshaled as a ulong value (the size) and
     * the char array of the string (without the end of string character *.
     * 
     * @param url
     * @return
     * @throws org.omg.CORBA.ORBPackage.InvalidName
     */
    private static ObjectKey createKey(String url_key)
        throws org.omg.CORBA.ORBPackage.InvalidName
    {

        CDROutputStream output = 
            new CDROutputStream(null,
                                new byte[url_key.length() + CDR.ULONG_SIZE]);

        char[] url_buffer = url_key.toCharArray();

        output.write_ulong(url_buffer.length);
        output.write_char_array(url_buffer, 0, url_buffer.length);

        CDRInputStream input = (CDRInputStream) output.create_input_stream();

        Encapsulation encap = input.readEncapsulation();

        return new ObjectKey(encap);
    }



}
