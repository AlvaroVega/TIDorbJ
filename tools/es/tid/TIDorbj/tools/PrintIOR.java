/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 453 $
* Date: $Date: 2010-04-27 16:52:41 +0200 (Tue, 27 Apr 2010) $
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
package es.tid.TIDorbj.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.StringWriter;
import java.io.PrintWriter;

import es.tid.TIDorbj.core.iop.DefaultIOR;
import es.tid.TIDorbj.core.iop.IOR;

import org.omg.IOP.TAG_INTERNET_IOP;
import es.tid.TIDorbj.core.iop.TaggedProfile;
import es.tid.TIDorbj.core.comm.iiop.IIOPProfile;

public class PrintIOR
{

    public static String get_repository_id(String ior_str)
    {
        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);

        try {
            IOR ior = null;

            ior = DefaultIOR.fromString(null, ior_str);
            
            print_buffer.println(ior.getTypeId());

        }
        catch (Throwable th) {
            System.out.println("Invalid IOR");
        }

        return buffer.toString();
    }

    public static String get_giop_version(String ior_str)
    {
        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);

        try {
            IOR ior = null;

            ior = DefaultIOR.fromString(null, ior_str);
            
            int size = ior.memberCount();
            for (int i = 0; i < size; i++) {
                TaggedProfile  profile = ior.getProfile(i);
                if (profile.tag == TAG_INTERNET_IOP.value) {
                    IIOPProfile iiop_profile = (IIOPProfile) profile;
                    print_buffer.println(iiop_profile.getVersion().toString());
                    break;
                }
            }
            
        }
        catch (Throwable th) {
            System.out.println("Invalid IOR");
        }

        return buffer.toString();
    }

    public static String get_host(String ior_str)
    {
        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);

        try {
            IOR ior = null;

            ior = DefaultIOR.fromString(null, ior_str);
            
            int size = ior.memberCount();
            for (int i = 0; i < size; i++) {
                TaggedProfile  profile = ior.getProfile(i);
                if (profile.tag == TAG_INTERNET_IOP.value) {
                    IIOPProfile iiop_profile = (IIOPProfile) profile;
                    print_buffer.println(iiop_profile.getListenPoint().m_host);
                    break;
                }
            }

        }
        catch (Throwable th) {
            System.out.println("Invalid IOR");
        }

        return buffer.toString();
    }


    public static String get_port(String ior_str)
    {
        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);

        try {
            IOR ior = null;

            ior = DefaultIOR.fromString(null, ior_str);
            
            int size = ior.memberCount();
            for (int i = 0; i < size; i++) {
                TaggedProfile  profile = ior.getProfile(i);
                if (profile.tag == TAG_INTERNET_IOP.value) {
                    IIOPProfile iiop_profile = (IIOPProfile) profile;
                    print_buffer.println(iiop_profile.getListenPoint().m_port);
                    break;
                }
            }
            
        }
        catch (Throwable th) {
            System.out.println("Invalid IOR");
        }

        return buffer.toString();
    }


    public static String get_object_key(String ior_str)
    {
        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);

        try {
            IOR ior = null;

            ior = DefaultIOR.fromString(null, ior_str);
            
            int size = ior.memberCount();
            for (int i = 0; i < size; i++) {
                TaggedProfile  profile = ior.getProfile(i);
                if (profile.tag == TAG_INTERNET_IOP.value) {
                    IIOPProfile iiop_profile = (IIOPProfile) profile;
                    print_buffer.println(iiop_profile.getObjectKey().toString());
                    break;
                }
            }
            
        }
        catch (Throwable th) {
            System.out.println("Invalid IOR");
        }

        return buffer.toString();
    }

    public static String print(String ior_str)
    {
        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);

        try {
            IOR ior = null;

            ior = DefaultIOR.fromString(null, ior_str);

            int size = ior.memberCount();

            if (size == 0) {
                print_buffer.println("IOR: nil");
            } else {
                print_buffer.println("IOR:");

                print_buffer.println("Repository Id: " + ior.getTypeId());

                print_buffer.println("(" + size + " Profiles)");

                for (int i = 0; i < size; i++)
                    print_buffer.println(ior.getProfile(i).toString());
            }

            //System.out.flush();
        }
        catch (Throwable th) {
            System.out.println("Invalid IOR");
        }

        return buffer.toString();
    }

    public static void main(String[] argv)
    {
        System.out.println("TIDorbJ printIOR version " + 
                           es.tid.TIDorbj.core.TIDORB.st_version);

        if ((argv == null) || (argv.length != 1)) {

            System.out.println("Usage: printIOR [ior|file]");
            return;
        }

        String ior_str = null;

        try { // try to read in a file
            FileReader file = new FileReader(argv[0]);
            BufferedReader reader = new BufferedReader(file);

            ior_str = reader.readLine();
            file.close();
        }
        catch (java.io.FileNotFoundException ioe) {
            // Argument is the IOR, not a file
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        try {
            String ior_text = null;
            if (ior_str == null)
                ior_text = es.tid.TIDorbj.tools.PrintIOR.print(argv[0]);
            else
                ior_text = es.tid.TIDorbj.tools.PrintIOR.print(ior_str);
             
            System.out.print(ior_text);
            System.out.flush();
        }
        catch (Throwable th) {
            System.out.println("Invalid IOR");
        }
    }
}
