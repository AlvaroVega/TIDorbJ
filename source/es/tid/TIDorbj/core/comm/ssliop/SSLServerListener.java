/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 379 $
* Date: $Date: 2009-04-07 12:17:19 +0200 (Tue, 07 Apr 2009) $
* Last modified by: $Author: avega $
*
* (C) Copyright 2010 Telefónica Investigación y Desarrollo
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
package es.tid.TIDorbj.core.comm.ssliop;

import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLContext;

import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

import java.io.FileInputStream;

import es.tid.TIDorbj.core.ConfORB;
import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.comm.giop.GIOPVersion;
import es.tid.TIDorbj.core.comm.giop.ServiceContextList;
import es.tid.TIDorbj.core.comm.iiop.*;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.iop.TaggedComponent;
import es.tid.TIDorbj.core.iop.TaggedProfile;
import es.tid.TIDorbj.core.poa.POAKey;
import es.tid.TIDorbj.util.Trace;

import org.omg.SSLIOP.SSL;
import org.omg.CSIIOP.*;


public class SSLServerListener extends Thread
{

    /**
     * SSLServerSocket state
     */

    boolean m_shutdowned;

    boolean m_connected;

    /**
     * SSLServerSocket where the communication layer will accept connections. This
     * socket will be only avalilable when a the ORB's <code>run</code> method
     * had been invoked.
     */
    SSLServerSocket m_server_socket;

    /**
     * Conection manager.
     */
    IIOPConnectionManager m_manager;


    /**
     * Listen point where the SSLServerSocket will be listening. This
     * point(host,port) will be used to create de local IOR's.
     */
    ListenPoint m_listen_point;

    /**
     * Service context that contains the information for activating the
     * bidirectional service.
     */
    ServiceContextList m_bidirectional_service;

    es.tid.TIDorbj.core.TIDORB m_orb;
    
    String hostAddress;
    String hostName;
    int port;
    int backlog;
    int reconnect;
    GIOPVersion giopVersion;
    
    String ssl_key_store_file;
    String ssl_key_store_passwd;
    String ssl_key_store_type;

    String ssl_trust_store_file;
    String ssl_trust_store_passwd;
    String ssl_trust_store_type;

    String ssl_version;


    public SSLServerListener(IIOPConnectionManager manager)
    {
        m_shutdowned = true;
        m_connected = false;

        this.m_manager = manager;
        m_orb = manager.orb();
        
        this.hostAddress = 
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.HOST_ADDRESS )
            .getValue(); 
        this.hostName =
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.HOST_NAME )
            .getValue(); 
//         this.port = 
//             m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
//             .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.PORT )
//             .getInt(); 
        this.backlog = 
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.SERVER_SOCKET_BACKLOG )
            .getInt();
        this.reconnect =
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.SERVER_SOCKET_RECONNECT )
            .getInt(); 
        this.giopVersion =
            GIOPVersion.fromString(
                m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
                .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.GIOP_VERSION )
                .getString()
            ); 

        
        // SSL options

        this.ssl_key_store_file =
            m_orb.getCommunicationManager().getLayerById( SSLIOPCommunicationLayer.ID )
                .getPropertyInfo( SSLIOPCommunicationLayerPropertiesInfo.ssl_key_store_file_name )
                .getValue();

        this.ssl_key_store_passwd =
            m_orb.getCommunicationManager().getLayerById( SSLIOPCommunicationLayer.ID )
                .getPropertyInfo( SSLIOPCommunicationLayerPropertiesInfo.ssl_key_store_passwd_name )
                .getValue();

        this.ssl_key_store_type =
            m_orb.getCommunicationManager().getLayerById( SSLIOPCommunicationLayer.ID )
                .getPropertyInfo( SSLIOPCommunicationLayerPropertiesInfo.ssl_key_store_type_name )
                .getValue();

        this.ssl_trust_store_file =
            m_orb.getCommunicationManager().getLayerById( SSLIOPCommunicationLayer.ID )
                .getPropertyInfo( SSLIOPCommunicationLayerPropertiesInfo.ssl_trust_store_file_name )
                .getValue();

        this.ssl_trust_store_passwd =
            m_orb.getCommunicationManager().getLayerById( SSLIOPCommunicationLayer.ID )
                .getPropertyInfo( SSLIOPCommunicationLayerPropertiesInfo.ssl_trust_store_passwd_name )
                .getValue();

        this.ssl_trust_store_type =
            m_orb.getCommunicationManager().getLayerById( SSLIOPCommunicationLayer.ID )
                .getPropertyInfo( SSLIOPCommunicationLayerPropertiesInfo.ssl_trust_store_type_name )
                .getValue();

        this.ssl_version =
            m_orb.getCommunicationManager().getLayerById( SSLIOPCommunicationLayer.ID )
                .getPropertyInfo( SSLIOPCommunicationLayerPropertiesInfo.ssl_version_name )
                .getValue();

        // port is ssl_port
        this.port =
            m_orb.getCommunicationManager().getLayerById( SSLIOPCommunicationLayer.ID )
                .getPropertyInfo( SSLIOPCommunicationLayerPropertiesInfo.ssl_port_name )
                .getInt(); 







        SSLServerSocketFactory ssf = null;

        if ( (!ssl_key_store_file.equals("")) &&
             (!ssl_key_store_passwd.equals("")) ) {

            try {

                SSLContext sc = SSLContext.getInstance(ssl_version);
                
                //
                // Init KeyStore
                //
                String ksName = ssl_key_store_file;
                char ksPass[] = ssl_key_store_passwd.toCharArray();
                
                KeyStore ks = KeyStore.getInstance(ssl_key_store_type);
                ks.load(new FileInputStream(ksName), ksPass); 
                
                KeyManagerFactory kmf = 
                    KeyManagerFactory.getInstance("SunX509");
                kmf.init(ks, ksPass);    
                

                //
                // Init TrustManager
                //
                String tsName = ssl_trust_store_file;
                char tsPass[] = ssl_trust_store_passwd.toCharArray();
                KeyStore ts = KeyStore.getInstance(ssl_trust_store_type);
                ts.load(new FileInputStream(tsName), tsPass);
                
                TrustManagerFactory tmf =
                    TrustManagerFactory.getInstance("PKIX"); // TODO: put in param
                tmf.init(ts);

                
                // Init SSLContext
                sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
                
                ssf = sc.getServerSocketFactory();
                
            } catch (Exception e) {
                throw new org.omg.CORBA.INITIALIZE("Can init SSLServerSocketFactory: " +
                                                   e.toString() );
            }
            
        } else {
            // TODO: bad initialized??
            ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        }
        
        try {
            if ( hostAddress == null) {
                m_server_socket = (SSLServerSocket) ssf.createServerSocket( 
                                        this.port , 
                                        this.backlog );

            } else {
                m_server_socket = (SSLServerSocket) ssf.createServerSocket(
                    this.port, 
                    this.backlog, 
                    InetAddress.getByName( this.hostAddress ) 
                );
            }
            m_server_socket.setReuseAddress(true);
            
            if ( this.hostName != null) {
                m_listen_point = new ListenPoint( 
                    this.hostName , 
                    m_server_socket.getLocalPort()
                );
            } else {
                m_listen_point = new ListenPoint(
                    InetAddress.getLocalHost().getHostAddress(),
                    m_server_socket.getLocalPort()
                );
            }

            m_shutdowned = false;
            m_connected = true;

        }
        catch (java.io.IOException e) {
            throw new org.omg.CORBA.INITIALIZE(
                "Can not open SSLServerSocket: " + e.toString() 
            );
        }

        m_orb.printTrace(
        	Trace.DEBUG, "SSLServerListener connected at: " + m_listen_point.toString()
		);
    }

    public ListenPoint getListenPoint() {
        return m_listen_point;
    }

    public synchronized void shutdown() {
        if (!m_shutdowned) {
            m_shutdowned = true;
            try {
                m_server_socket.close();
            } catch (Throwable th) {}

            m_server_socket = null;

            m_orb.printTrace(
                    Trace.DEBUG, "SSLServerListener at " + m_listen_point.toString() + " shutdown!"
			);
        }
    }

    public synchronized void resetServerSocket() {
        if (!m_shutdowned) {
            try {
                if (m_server_socket != null)
                    m_server_socket.close();
            }
            catch (Throwable th) {}

            SSLServerSocketFactory ssf = null;


            if ( (!ssl_key_store_file.equals("")) &&
                 (!ssl_key_store_passwd.equals("")) ) {

            try {

                SSLContext sc = SSLContext.getInstance(ssl_version); 
                
                //
                // Init KeyStore
                //
                String ksName = ssl_key_store_file;
                char ksPass[] = ssl_key_store_passwd.toCharArray();
                KeyStore ks = KeyStore.getInstance(ssl_key_store_type);
                ks.load(new FileInputStream(ksName), ksPass); 
                
                KeyManagerFactory kmf = 
                    KeyManagerFactory.getInstance("SunX509");
                kmf.init(ks, ksPass);    

                //
                // Init TrustManager
                //
                String tsName = ssl_trust_store_file;
                char tsPass[] = ssl_trust_store_passwd.toCharArray();
                KeyStore ts = KeyStore.getInstance(ssl_trust_store_type);
                ts.load(new FileInputStream(tsName), tsPass);
                
                TrustManagerFactory tmf =
                    TrustManagerFactory.getInstance("PKIX"); // TODO: put in param
                tmf.init(ts);
                
                // Init SSLContext
                sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
                
                ssf = sc.getServerSocketFactory();

                if ( this.hostAddress == null) {
                    m_server_socket = (SSLServerSocket) ssf.createServerSocket(
                        m_listen_point.m_port,
                        this.backlog
                    );
                } else {
                    m_server_socket = (SSLServerSocket) ssf.createServerSocket(
                        m_listen_point.m_port,
                        this.backlog,
                        InetAddress.getByName( this.hostAddress )
                    );
                }
                m_server_socket.setReuseAddress(true);
            } catch (Throwable th) {
                throw new org.omg.CORBA.INITIALIZE(
                    "Can not open SSLServerSocket: " + th.toString()
                );
            }	

            } else {
                ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            }
        }
    }

    public void run()
    {
        while (!m_shutdowned) {
            while (m_connected) {

            	Socket client_socket = null;
                try {
                    client_socket = m_server_socket.accept();
                } catch (Throwable se) {

                    if ( m_shutdowned ){
                        return;
                    }

                    m_connected = false;

                    m_orb.printTrace(
                        Trace.ERROR, "Error in SSLServerSocket.accept(): ", se
					);
                }

                try {
                    if (client_socket != null) {
                        m_manager.createSSLServerConnection((SSLSocket)client_socket);
                    }
                } catch (Throwable e) {
                    try {
                        client_socket.close();
                    } catch (Throwable t) {}

                    m_orb.printTrace( 
                    	Trace.ERROR, "Error creating SSLServerConnection: ", e 
					);
                }
            }

            if ((!m_connected) && (!m_shutdowned)) {

                // RECONNECT

            	//printTrace will check for a trace handler existence 
                m_orb.printTrace( Trace.DEBUG, "Trying to reconnect server socket ");

                try {
                    resetServerSocket();
                    m_connected = true;

                    if (m_orb.m_trace != null) {
                        m_orb.printTrace(
                        	Trace.DEBUG, "SSLServerSocket reconnected" 
						);
                    }
                } catch (Throwable th) {
                    m_orb.printTrace(
                    	Trace.ERROR, "SSLServerSocket reconnect error: ", th 
					);
                    try {
                        Thread.sleep( this.reconnect );
                    } catch (InterruptedException e) {}
                }
            }
        }
    }
    
}
