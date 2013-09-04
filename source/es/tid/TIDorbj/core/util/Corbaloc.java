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
package es.tid.TIDorbj.core.util;

import java.util.StringTokenizer;

import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.ORBPackage.InvalidName;

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.comm.giop.GIOPVersion;
import es.tid.TIDorbj.core.comm.iiop.IIOPIOR;
import es.tid.TIDorbj.core.comm.iiop.ListenPoint;
import es.tid.TIDorbj.core.comm.iiop.IIOPProfile;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.poa.OID;
import es.tid.TIDorbj.core.poa.POAKey;
import es.tid.TIDorbj.util.TranslateURLEscapes;

//TODO: put this together with into IIOPCorbaloc 
public class Corbaloc
{

    public static IOR getIOR(String corbaloc_URL)
        throws org.omg.CORBA.ORBPackage.InvalidName
    {
        String corbaloc;
        String addr;
        String path;
        String v;
        String l;

        ObjectKey objectkey;
        GIOPVersion version;
        ListenPoint listener;
        IIOPProfile[] profiles;
        int slashposition, ntokens;
        if (corbaloc_URL.startsWith("corbaloc:tidorbj:")) {
            corbaloc = corbaloc_URL.substring(17);
            slashposition = corbaloc.indexOf('/');
            addr = corbaloc.substring(0, slashposition);
            StringTokenizer staddr = new StringTokenizer(addr, "@", false);
            ntokens = staddr.countTokens();
            if (ntokens > 2)
                throw new InvalidName("Invalid Address Name");
            else {
                if (ntokens == 1) {
                    version = parseVersion("1.0");
                } else {
                    v = staddr.nextToken();
                    version = parseVersion(v);
                }
            }
            l = staddr.nextToken();
            listener = parseListenPoint(l);
            path = corbaloc.substring(slashposition + 1);
            objectkey = parsePOAPath(path);
        } else
            throw new InvalidName("Invalid TIDorbJ corbaloc");
        profiles = new IIOPProfile[1];
        profiles[0] = new IIOPProfile(version, listener, objectkey, null);
        return new IIOPIOR( "", profiles );
    }

    private static ObjectKey parsePOAPath(String poa_path)
        throws org.omg.CORBA.ORBPackage.InvalidName
    {
        String[] poa_path_a;
        String path;
        String oid_str;
        int ntokens;
        OID oid;
        POAKey poakey;
        ObjectKey objectkey;
        path = TranslateURLEscapes.putEscapes(poa_path);
        if (path.indexOf("//") != -1)
            throw new org.omg.CORBA.ORBPackage.InvalidName("Invalid POA Name");
        StringTokenizer stpath = new StringTokenizer(path, "/", false);
        ntokens = stpath.countTokens();
        poa_path_a = new String[ntokens - 1];
        for (int i = 0; i < ntokens - 1; i++) {
            poa_path_a[i] = 
                TranslateURLEscapes.translateUnicode(
                    TranslateURLEscapes.resolveEscapes(stpath.nextToken()));
        }
        oid_str = TranslateURLEscapes.resolveEscapes(stpath.nextToken());
        try {
            oid = OID.fromString(oid_str);
        }
        catch (java.lang.Exception e) {
            throw new org.omg.CORBA.ORBPackage.InvalidName("Invalid OID");
        }
        poakey = new POAKey(poa_path_a, 0L, oid);
        return poakey;
    }

    public static GIOPVersion parseVersion(String v)
        throws org.omg.CORBA.ORBPackage.InvalidName
    {
        int major, minor;
        int ntokens;
        GIOPVersion version;
        StringTokenizer stversion = new StringTokenizer(v, ".", false);
        ntokens = stversion.countTokens();
        if (ntokens == 2) {
            major = Integer.parseInt(stversion.nextToken());
            minor = Integer.parseInt(stversion.nextToken());
            if (major == 1) {
                if (minor == 0)
                    version = GIOPVersion.VERSION_1_0;
                else if (minor == 1)
                    version = GIOPVersion.VERSION_1_1;
                else if (minor == 2)
                    version = GIOPVersion.VERSION_1_2;
                else
                    throw new InvalidName("Invalid Minor GIOPVersion Number");

            } else
                throw new InvalidName("Invalid Major GIOPVersion Number");
        } else
            throw new InvalidName("Invalid GIOPVersion");
        return version;
    }


    public static ListenPoint parseListenPoint(String listenp)
        throws org.omg.CORBA.ORBPackage.InvalidName
    {
        System.out.println("IIOPCorbaloc parseListenPoint");
        String host;
        int port;
        int ntokens;
        /*StringTokenizer stlistenpoint =
            new StringTokenizer(listenp, ":", false);
        ntokens = stlistenpoint.countTokens();
        if (ntokens > 2)
            throw new InvalidName("Invalid Listener Name");
        else {*/
        int ipv6SeperatorStart = -1;
        int ipv6SeperatorEnd = -1;
        int sep;
        ipv6SeperatorStart = listenp.indexOf('[');
        if (ipv6SeperatorStart != -1)
        {
           ipv6SeperatorEnd = listenp.indexOf(']');
            if (ipv6SeperatorEnd == -1)
            {
                throw new InvalidName("Invalid Listener Name");
            }
        }

        sep = listenp.indexOf(':');
        if( sep != -1 )
        {
           if (ipv6SeperatorStart != -1) //IPv6
           {
              host=listenp.substring(ipv6SeperatorStart+1, ipv6SeperatorEnd);
              if (listenp.charAt(ipv6SeperatorEnd+1) == ':')
              {
              	try
              	{
                  port=(short)Integer.parseInt(listenp.substring(ipv6SeperatorEnd+2));
                }
                catch( NumberFormatException ill )
                {
                  throw new InvalidName("Invalid Listener Name");
                }  
              }
              else
              {
                throw new InvalidName("Invalid Listener Name");
              }
           }
           else //IPv4 or hostname
           {
              try
              {
                 port =(short)Integer.parseInt(listenp.substring(sep+1));
                 host = listenp.substring(0, sep);
              }
              catch( NumberFormatException ill )
              {
                 throw new InvalidName("Invalid Listener Name");
              }
           }
        }
        //mcpg - end 
        else
        {
        	host = listenp;
        	port = 2809;
        }
            /*host = stlistenpoint.nextToken();
            if (ntokens == 1) {
                port = 2809;
            } else {
                port = Integer.parseInt(stlistenpoint.nextToken());
            }*/
        
        return new ListenPoint(host, port);
    }

    public static String toURL(IOR ior)
    {
        String corbaloc = "corbaloc:tidorbj:";
        IIOPProfile p = (IIOPProfile) ior.getProfile(0);
        GIOPVersion v = p.getVersion();
        ListenPoint l = p.getListenPoint();
        ObjectKey o = p.getObjectKey();
        
        POAKey k;
        try {
            k = POAKey.createKey( o.getMarshaledKey() );
        } catch ( Throwable th ){
            throw new BAD_PARAM( 
                "Non persistent reference", 0, CompletionStatus.COMPLETED_NO 
            );
        }
        
        if (k.getPOAId() != 0L) {
            throw new BAD_PARAM(
                "Non persistent reference", 0, CompletionStatus.COMPLETED_NO);
        }
        OID oid = k.getOID();
        corbaloc += v.getMajor() + "." + v.getMinor() + "@";
        corbaloc += l.m_host + ":" + l.m_port + "/";
        for (int i = 0; i < k.numberOfPOAs(); i++)
            corbaloc += TranslateURLEscapes.stringfy(k.getPOA(i)) + "/";
        corbaloc += TranslateURLEscapes.stringfy(oid.toString());
        return corbaloc;
    }

}
