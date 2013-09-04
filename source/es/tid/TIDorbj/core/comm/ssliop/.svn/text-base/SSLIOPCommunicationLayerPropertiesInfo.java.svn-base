/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 274 $
* Date: $Date: 2008-04-12 16:33:40 +0200 (Sat, 12 Apr 2008) $
* Last modified by: $Author: avega $
*
* (C) Copyright 2004 Telefnica Investigacin y Desarrollo
*     S.A.Unipersonal (Telefnica I+D)
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
package es.tid.TIDorbj.core.comm.ssliop;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import es.tid.TIDorbj.core.comm.PropertyInfo;
import es.tid.TIDorbj.core.comm.iiop.IIOPCommunicationLayerPropertiesInfo;

/**
 * @author avega
 *
 * 
 * 
 */
public class SSLIOPCommunicationLayerPropertiesInfo 
    extends IIOPCommunicationLayerPropertiesInfo{
    


    /*
     * Key Store options
     */
    public final static String DEFAULT_SSL_KEY_STORE_FILE = null;

    public final static String ssl_key_store_file_name =
        "es.tid.TIDorbj.ssliop.key_store_file";


    public final static String DEFAULT_SSL_KEY_STORE_PASSWD = null;

    public final static String ssl_key_store_passwd_name =
        "es.tid.TIDorbj.ssliop.key_store_passwd";


    public final static String DEFAULT_SSL_KEY_STORE_TYPE = "JKS";

    public final static String ssl_key_store_type_name =
        "es.tid.TIDorbj.ssliop.key_store_type";


    /*
     * TrustManager options
     */
    public final static String DEFAULT_SSL_TRUST_STORE_FILE = null;

    public final static String ssl_trust_store_file_name =
        "es.tid.TIDorbj.ssliop.trust_store_file";


    public final static String DEFAULT_SSL_TRUST_STORE_PASSWD = null;

    public final static String ssl_trust_store_passwd_name =
        "es.tid.TIDorbj.ssliop.trust_store_passwd";


    public final static String DEFAULT_SSL_TRUST_STORE_TYPE = "JKS";

    public final static String ssl_trust_store_type_name =
        "es.tid.TIDorbj.ssliop.trust_store_type";

    
    /**
     * SSL port used for generated IORs and URLs. Default value: 0 
     */
    public final static String DEFAULT_SSL_PORT = "0";

    public final static String ssl_port_name = 
        "es.tid.TIDorbj.ssliop.port";




    /**
     * SSL version to be used by SSLIOP layer
     * SSLv2 is not recomended due to security flaws
     * Values must be 0 (SSLv2), 1 (SSLv3), 2 (SSLv23), 3 (TLSv1)
     * Default value: 2 
     */
    public final static String DEFAULT_SSL_VERSION = "SSLv3";

    public final static String ssl_version_name =
        "es.tid.TIDorbj.ssliop.ssl_version";



    public PropertyInfo ssl_key_store_file;
    public PropertyInfo ssl_key_store_passwd;
    public PropertyInfo ssl_key_store_type; /* JKS or PKCS12 */

    public PropertyInfo ssl_trust_store_file;
    public PropertyInfo ssl_trust_store_passwd;
    public PropertyInfo ssl_trust_store_type; /* JKS or PKCS12 */

    public PropertyInfo ssl_port;
    public PropertyInfo ssl_version;

    //public HashMap map;

    public SSLIOPCommunicationLayerPropertiesInfo() {
        
        super();
        
        // TODO: resize map ??
        
        /**
         * SSL KEY STORE FILE
         */
        ssl_key_store_file = new PropertyInfo( 
            ssl_key_store_file_name,
            DEFAULT_SSL_KEY_STORE_FILE
        );
        ssl_key_store_file.setDescription( 
            "Key Store file name." 
        );
        ssl_key_store_file.setRequired( false );
        ssl_key_store_file.setChoices ( null );
        map.put( ssl_key_store_file_name, ssl_key_store_file );
        

        /**
         * SSL KEY STORE PASSWD
         */
        ssl_key_store_passwd = new PropertyInfo( 
            ssl_key_store_passwd_name,
            DEFAULT_SSL_KEY_STORE_PASSWD
        );
        ssl_key_store_passwd.setDescription( 
            "Key Store passwd." 
        );
        ssl_key_store_passwd.setRequired( false );
        ssl_key_store_passwd.setChoices ( null );
        map.put( ssl_key_store_passwd_name, ssl_key_store_passwd );


        /**
         * SSL TRUST STORE TYPE
         */
        ssl_key_store_type = new PropertyInfo( 
            ssl_key_store_type_name,
            DEFAULT_SSL_KEY_STORE_TYPE
        );
        ssl_key_store_passwd.setDescription( 
            "Key Store type." 
        );
        ssl_key_store_passwd.setRequired( false );
        ssl_key_store_passwd.setChoices ( new String[]{ "JKS", "PCKS12" } );
        map.put( ssl_key_store_type_name, ssl_key_store_type );




        /**
         * SSL TRUST STORE FILE
         */
        ssl_trust_store_file = new PropertyInfo( 
            ssl_trust_store_file_name,
            DEFAULT_SSL_TRUST_STORE_FILE
        );
        ssl_trust_store_file.setDescription( 
            "Trust Store file name." 
        );
        ssl_trust_store_file.setRequired( false );
        ssl_trust_store_file.setChoices ( null );
        map.put( ssl_trust_store_file_name, ssl_trust_store_file );
        

        /**
         * SSL TRUST STORE PASSWD
         */
        ssl_trust_store_passwd = new PropertyInfo( 
            ssl_trust_store_passwd_name,
            DEFAULT_SSL_TRUST_STORE_PASSWD
        );
        ssl_trust_store_passwd.setDescription( 
            "Key Store passwd." 
        );
        ssl_trust_store_passwd.setRequired( false );
        ssl_trust_store_passwd.setChoices ( null );
        map.put( ssl_trust_store_passwd_name, ssl_trust_store_passwd );


        /**
         * SSL TRUST STORE TYPE
         */
        ssl_trust_store_type = new PropertyInfo( 
            ssl_trust_store_type_name,
            DEFAULT_SSL_TRUST_STORE_TYPE
        );
        ssl_trust_store_passwd.setDescription( 
            "Trust Store type." 
        );
        ssl_trust_store_passwd.setRequired( false );
        ssl_trust_store_passwd.setChoices ( new String[]{ "JKS", "PCKS12" } );
        map.put( ssl_trust_store_type_name, ssl_trust_store_type );



        /**
         * SSL PORT
         */
        ssl_port = new PropertyInfo( 
            ssl_port_name,
            DEFAULT_SSL_PORT
        );
        ssl_port.setDescription( 
            "SSL port." 
        );
        ssl_port.setRequired( false );
        ssl_port.setChoices ( null );
        map.put( ssl_port_name, ssl_port );


        /**
         * SSL VERSION
         */
        ssl_version = new PropertyInfo( 
            ssl_version_name,
            DEFAULT_SSL_VERSION
        );
        ssl_version.setDescription( 
            "SSL Version" 
        );
        ssl_version.setRequired( false );
        ssl_version.setChoices ( new String[]{ "SSLv2", "SSLv3", "SSLv23", "TLSv1" }  );
        map.put( ssl_version_name, ssl_version );



    }
    
    public static SSLIOPCommunicationLayerPropertiesInfo getInstance(){
        return new SSLIOPCommunicationLayerPropertiesInfo();
    }

    public void dump(java.io.PrintWriter writer)
    {
        writer.println("SSLIOPCommunicationLayer properties:");

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            writer.print('\t');
            writer.print(e.getKey());
            writer.print('=');
            writer.print( ((PropertyInfo)e.getValue()).getValue());
            writer.println();
        }
    }
}
