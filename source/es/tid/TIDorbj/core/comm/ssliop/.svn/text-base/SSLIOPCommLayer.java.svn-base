/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 309 $
* Date: $Date: 2008-11-07 09:54:08 +0100 (Fri, 07 Nov 2008) $
* Last modified by: $Author: avega $
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
package es.tid.TIDorbj.core.comm.ssliop;

import java.util.Properties;

import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.NO_RESOURCES;
import org.omg.CORBA.Object;

import org.omg.SSLIOP.SSL;

import es.tid.TIDorbj.core.ConfORB;
import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.cdr.CDROutputStream;
import es.tid.TIDorbj.core.comm.ForwardRequest;
import es.tid.TIDorbj.core.comm.PropertyInfo;
import es.tid.TIDorbj.core.comm.giop.GIOPVersion;
import es.tid.TIDorbj.core.comm.giop.BiDirServiceContext;
import es.tid.TIDorbj.core.comm.giop.ServiceContextList;
import es.tid.TIDorbj.core.comm.iiop.*;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.iop.TaggedComponent;
import es.tid.TIDorbj.core.iop.TaggedProfile;
import es.tid.TIDorbj.core.poa.POAKey;
import es.tid.TIDorbj.core.policy.PolicyContext;
import es.tid.TIDorbj.util.Trace;

/**
 * IIOP Communications layer. It will manage the request for a remote object
 * using the IIOP Protocol.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class SSLIOPCommLayer extends es.tid.TIDorbj.core.comm.iiop.IIOPCommLayer
{


    /**
     * Server Socket listening thread.
     */
    SSLServerListener m_ssl_server_listener;

    private SSLIOPCommunicationLayerPropertiesInfo propertiesInfo;

    public SSLIOPCommLayer(TIDORB orb)
    {
        // crear la conexion servidora y lanzar threads de escucha
        super(orb, null);

        this.propertiesInfo = SSLIOPCommunicationLayerPropertiesInfo.getInstance();

        orb.m_conf.fillPropertyInfo( 
                (PropertyInfo[])
                    this.propertiesInfo.map.values().toArray( 
                        new PropertyInfo[]{} 
                                                              ));


    }

    public boolean isLocal( IIOPIOR ior)
    {
        if (m_ssl_server_listener == null) // no object adaptor initialized
            return false;

        // Check SSLComponent of IOR...
        es.tid.TIDorbj.core.comm.ssliop.SSLComponent ior_ssl_component = 
            ior.profileIIOP().getSSLComponent();

        if (ior_ssl_component == null)
            return false;
  

        org.omg.SSLIOP.SSL ssl = ior_ssl_component.getSSL();

        es.tid.TIDorbj.core.comm.iiop.ListenPoint server_listen_point = 
            m_ssl_server_listener.getListenPoint();

        es.tid.TIDorbj.core.comm.iiop.ListenPoint ior_listen_point = 
            ior.profileIIOP().getListenPoint();

        return ( server_listen_point.m_host.equals(ior_listen_point.m_host) 
                 && (server_listen_point.m_port == ssl.port) );

    }

    public boolean hasServerListener()
    {
        return (m_ssl_server_listener != null);
    }

    /**
     * Sends a request (with response) allocating an active connection with the
     * corresponding server referenced by its target address.
     * <p>
     * If necessary, tries to do a recovery loop.
     * 
     * @param request
     *            the CORBA request.
     */
    protected void sendRequest(es.tid.TIDorbj.core.RequestImpl request, IIOPIOR ior)
        throws ForwardRequest
    {

        PolicyContext policy_context = request.getPolicyContext();
        
       
        //  PolicyContext policy_context = request
        IIOPProfile profile = ior.profileIIOP();

        if (profile == null) {
            throw new org.omg.CORBA.INTERNAL( "Can not get IIOP Profile." );
        }

        SSLConnection conn;
        int recover_count = max_recover_count;

        // recovering loop

        while (true) {

            try {
                conn = m_connection_manager.getSSLClientConnection(
                	profile.getListenPoint(),
					policy_context
				);
            }
            catch (org.omg.CORBA.COMM_FAILURE ce) {

                recover_count--;

                if (recover_count <= 0) {
                    if (m_orb.m_trace != null) {
                        String[] msg =
                            {"Can not recover the communication any more: ",
                             ce.toString() };

                        m_orb.printTrace(Trace.DEBUG, msg);
                    }

                    throw ce;
                }

                if (m_orb.m_trace != null) {
                    m_orb.printTrace(Trace.DEBUG,
                                     "CORBA::COMM_FAILURE -> Communication "
                                     + "recovered, waiting "
                                     + recover_time
                                     + " milliseconds.");
                }

                try {
                    Thread.sleep( recover_time );
                }
                catch (InterruptedException e) {}

                continue;
            }

            try {
                conn.sendRequest(request, ior);
                return;
            }
            catch (RECOVERABLE_COMM_FAILURE rcf) {

                recover_count--;

                if (recover_count <= 0) {
                    if (m_orb.m_trace != null) {
                        String[] msg =
                        	{
                        	 "Can not recover the communication any more: ",
                        	 rcf.m_comm_failure.toString()
                        	 };

                        m_orb.printTrace(Trace.DEBUG, msg);
                    }

                    throw rcf.m_comm_failure;
                }

                if (m_orb.m_trace != null) {
                    m_orb.printTrace(Trace.DEBUG,
                                     "CORBA::COMM_FAILURE -> Communication " 
                                     + "recovered, waiting "
                                     + recover_time
                                     + " milliseconds.");
                }	

                try {
                    Thread.sleep( recover_time );
                }
                catch (InterruptedException e) {}

            }
            catch (RuntimeException re) {
                if (m_orb.m_trace != null) {
                    m_orb.printTrace(Trace.ERROR,
                                     "Exception in remote invocation", re);
                }
                throw re;
            }
        }
    }

    /**
     * Sends a request using the IIOP protocol.
     * 
     * @param request
     *            the CORBA request.
     */
    public void request(es.tid.TIDorbj.core.RequestImpl request,
                        IIOPIOR ior)
        throws ForwardRequest
    {
        request.withResponse(true);
        sendRequest(request, ior);
    }

    /**
     * Sends a oneway request using the IIOP protocol allocating an active
     * IIOPConnection with the server referenced in the request target address.
     * 
     * @param request
     *            the CORBA request.
     */
    public void onewayRequest(es.tid.TIDorbj.core.RequestImpl request,
                              IIOPIOR ior)
    {
        if ( reliable_oneway ) {
            try {
                ReliableOnewayThread th = 
                    new ReliableOnewayThread(this, request, ior);
                th.start();
                return;
            }
            catch (Throwable thw) {
                throw new NO_RESOURCES("Can't create thread: "
                                       + thw.toString());
            }
        } else {

            try {

                request.withResponse(false);

                IIOPProfile profile = ior.profileIIOP();

                if (profile == null)
                    throw new INTERNAL("Can not get IIOP Profile.");

                SSLConnection conn;

                conn = 
                    m_connection_manager
                    .getSSLClientConnection(profile.getListenPoint(),
                                         request.getPolicyContext());

                conn.sendOnewayRequestAsync(request, ior);

            }
            catch (Throwable th) {
                if (m_orb.m_trace != null)
                    m_orb.printTrace(Trace.DEBUG,
                                     "Exception in oneway remote invocation",
                                     th);
            }
        }
    }

    /**
     * Sends a asynchronous request using the IIOP protocol allocating an active 
     * connection with the corresponding server referenced by its target address.
     * <p>
     * If necessary, tries to do a recovery loop.
     * 
     * @param request
     *            the CORBA request.
     */
    public void asyncRequest(es.tid.TIDorbj.core.RequestImpl request, IIOPIOR ior) 
        throws ForwardRequest
    {

        request.withResponse(true);
        
        PolicyContext policy_context = request.getPolicyContext();
        
        //  PolicyContext policy_context = request
        IIOPProfile profile = ior.profileIIOP();

        if (profile == null) {
            throw new org.omg.CORBA.INTERNAL( "Can not get IIOP Profile." );
        }

        SSLConnection conn;
        int recover_count = max_recover_count;

        // recovering loop

        while (true) {

            try {
                conn = m_connection_manager.getSSLClientConnection(
                	profile.getListenPoint(),
					policy_context
				);
            }
            catch (org.omg.CORBA.COMM_FAILURE ce) {

                recover_count--;

                if (recover_count <= 0) {
                    if (m_orb.m_trace != null) {
                        String[] msg =
                            {"Can not recover the communication any more: ",
                             ce.toString() };

                        m_orb.printTrace(Trace.DEBUG, msg);
                    }

                    throw ce;
                }

                if (m_orb.m_trace != null) {
                    m_orb.printTrace(Trace.DEBUG,
                                     "CORBA::COMM_FAILURE -> Communication "
                                     + "recovered, waiting "
                                     + recover_time
                                     + " milliseconds.");
                }

                try {
                    Thread.sleep( recover_time );
                }
                catch (InterruptedException e) {}

                continue;
            }

            try {
                conn.sendAsyncRequest(request, ior); //*opc1*, ami_handler);
                return;
            }
            catch (RECOVERABLE_COMM_FAILURE rcf) {

                recover_count--;

                if (recover_count <= 0) {
                    if (m_orb.m_trace != null) {
                        String[] msg =
                        	{
                        	 "Can not recover the communication any more: ",
                        	 rcf.m_comm_failure.toString()
                        	 };

                        m_orb.printTrace(Trace.DEBUG, msg);
                    }

                    throw rcf.m_comm_failure;
                }

                if (m_orb.m_trace != null) {
                    m_orb.printTrace(Trace.DEBUG,
                                     "CORBA::COMM_FAILURE -> Communication " 
                                     + "recovered, waiting "
                                     + recover_time
                                     + " milliseconds.");
                }	

                try {
                    Thread.sleep( recover_time );
                }
                catch (InterruptedException e) {}

            }
            catch (RuntimeException re) {
                if (m_orb.m_trace != null) {
                    m_orb.printTrace(Trace.ERROR,
                                     "Exception in remote invocation", re);
                }
                throw re;
            }
        }
    }

    
    /**
     * Sends a oneway request using the IIOP protocol allocating an active
     * IIOPConnection with the server referenced in the request target address.
     * 
     * @param request
     *            the CORBA request.
     */
    public void reliableOnewayRun(
            es.tid.TIDorbj.core.RequestImpl request,
            IIOPIOR ior ) {
        try {

            PolicyContext policy_context =
                request.getPolicyContext();

            request.reliableOneway(true);

            request.withResponse(false);

            IIOPProfile profile = ior.profileIIOP();

            if (profile == null)
                throw new org.omg.CORBA.INTERNAL("Can not get IIOP Profile.");

            SSLConnection conn;
            int recover_count = max_recover_count;

            // recovering loop
        
            while (true) {

                try {

                    conn =
                        m_connection_manager
                        .getSSLClientConnection(profile.getListenPoint(),
                                             policy_context);

                    conn.sendOnewayRequestSync(request, ior);

                    return;
                }
                catch (RECOVERABLE_COMM_FAILURE rcf) {

                    recover_count--;

                    if (recover_count <= 0)
                        throw rcf.m_comm_failure;

                    if (m_orb.m_trace != null) {
                        m_orb.printTrace(Trace.DEBUG,
                                         "CORBA::COMM_FAILURE -> Communication"
                                         +" recovered, waiting "
                                         + this.recover_time
                                         + " milliseconds.");
                    }	

                    try {
                        Thread.sleep( this.recover_time );
                    }
                    catch (InterruptedException e) {}

                }
                catch (ForwardRequest fr) {
                    
                    recover_count--;

                    if (m_orb.m_trace != null) {
                        String[] msg = { "Communication forwarded: ",
                                        fr.forward_reference.toString() };
                        m_orb.printTrace(Trace.DEBUG, msg);
                    }
                }
            }
        }
        catch (Throwable e) {
            if (m_orb.m_trace != null)
                m_orb.printTrace(
                    Trace.DEBUG,
                    "Exception in reliable remote oneway invocation :",
                    e);
        }
    }

    /**
     * Sends a object existence request.
     * 
     * @param ior
     *            the object IOR.
     */
    public boolean objectExists(IIOPIOR ior,
                                PolicyContext policy_context)
        throws ForwardRequest
    {

        IIOPProfile profile = ior.profileIIOP();

        if (profile == null)
            throw new org.omg.CORBA.INTERNAL("Can not get IIOP Profile.");

        SSLConnection conn;
        int recover_count = max_recover_count;

        // recovering loop

        while (true) {

            try {
                conn = 
                    m_connection_manager.getSSLClientConnection(
                        profile.getListenPoint(),
                        policy_context);
            }
            catch (org.omg.CORBA.COMM_FAILURE ce) {

                recover_count--;

                if (recover_count <= 0) {
                    if (m_orb.m_trace != null) {
                        String[] msg = 
                        	{
                        	 "Can not recover the communication any more: ",
                        	 ce.toString()
                        	};

                        m_orb.printTrace(Trace.DEBUG, msg);
                    }

                    throw ce;
                }

                if (m_orb.m_trace != null) {
                    m_orb.printTrace(
                                     Trace.DEBUG,
                                     "CORBA::COMM_FAILURE -> Communication " 
                                     + "recovered, waiting "
                                     + this.recover_time
                                     + " milliseconds.");
                }

                try {
                    Thread.sleep( this.recover_time );
                }
                catch (InterruptedException e) {}

                continue;
            }

            try {

                return conn.sendLocateRequest(ior, policy_context);

            }
            catch (RECOVERABLE_COMM_FAILURE rcf) {

                recover_count--;

                if (recover_count <= 0) {
                    if (m_orb.m_trace != null)
                        m_orb.printTrace(
                            Trace.DEBUG,
                            "Can not recover the communication any more: ",
                            rcf.m_comm_failure);

                    throw rcf.m_comm_failure;
                }

                if (m_orb.m_trace != null) {
                    m_orb.printTrace(Trace.DEBUG,
                                     "CORBA::COMM_FAILURE -> Communication " 
                                     + "recovered, waiting "
                                     + this.recover_time
                                     + " milliseconds.");
                }

                try {
                    Thread.sleep( this.recover_time );
                }
                catch (InterruptedException e) {}

            }
            catch (ForwardRequest fr) {
                throw fr;
            }
            catch (RuntimeException re) {
                if (m_orb.m_trace != null) {
                    m_orb.printTrace(Trace.ERROR,
                                     "Exception in remote invocation", re);
                }
                throw re;
            }
        }
    }

    public void prepareRequest(es.tid.TIDorbj.core.StreamRequestImpl request)
    {
        org.omg.CORBA.portable.ObjectImpl obj = request.getTarget();
        es.tid.TIDorbj.core.comm.CommunicationDelegate delegate =
            (es.tid.TIDorbj.core.comm.CommunicationDelegate) obj._get_delegate();
        es.tid.TIDorbj.core.iop.IOR ior = delegate.getReference();

        IIOPIOR iiopIOR;
        if ( ior instanceof IIOPIOR ){
            iiopIOR = ( IIOPIOR )ior;
        } else {
            throw new org.omg.CORBA.INTERNAL("Not an IIOP IOR.");
        }
        
        PolicyContext policy_context =
            delegate.createRequestPolicyContext();
        
        IIOPProfile profile;
        profile = iiopIOR.profileIIOP();

        if (profile == null) {
            throw new org.omg.CORBA.INTERNAL("Can not get IIOP Profile.");
        }

        SSLConnection conn = null;
        int recover_count = max_recover_count;

        while (conn == null) {

            try {
                conn = 
                    m_connection_manager.getSSLClientConnection(
                        profile.getListenPoint(),
                        policy_context);

            }
            catch (org.omg.CORBA.COMM_FAILURE ce) {

                recover_count--;

                if (recover_count <= 0) {
                    if (m_orb.m_trace != null) {
                        String[] msg =
                        	{
                        	 "Can not recover the communication any more: ",
                             ce.toString()
                            };

                        m_orb.printTrace(Trace.DEBUG, msg);
                    }

                    throw ce;
                }

                if (m_orb.m_trace != null) {
                    m_orb.printTrace(
                                     Trace.DEBUG,
                                     "CORBA::COMM_FAILURE -> Communication " 
                                     + "recovered, waiting "
                                     + this.recover_time
                                     + " milliseconds.");
                }

                try {
                    Thread.sleep( this.recover_time );
                }
                catch (InterruptedException e) {}

                continue;
            }
        }

        conn.prepareRequest(request, iiopIOR);

    }

    public org.omg.CORBA.portable.InputStream request(
            IIOPIOR ior,
            CDROutputStream stream,
            PolicyContext policy_context
        ) throws ForwardRequest,
                 org.omg.CORBA.portable.ApplicationException,
                 org.omg.CORBA.portable.RemarshalException {

        IIOPProfile profile = ior.profileIIOP();

        if (profile == null)
            throw new org.omg.CORBA.INTERNAL("Can not get IIOP Profile.");

        SSLConnection conn = null;
        int recover_count = max_recover_count;

        while (true) {
            try {

                conn = 
                    m_connection_manager.getSSLClientConnection(
                        profile.getListenPoint(),
                        policy_context);

            }
            catch (org.omg.CORBA.COMM_FAILURE ce) {

                recover_count--;

                if (recover_count <= 0) {
                    if (m_orb.m_trace != null) {
                        String[] msg = {
                        	 "Can not recover the communication any more: ",
                        	 ce.toString()
                        	};

                        m_orb.printTrace(Trace.DEBUG, msg);
                    }

                    throw ce;
                }

                if (m_orb.m_trace != null) {
                    m_orb.printTrace(
                       Trace.DEBUG,
                       "CORBA::COMM_FAILURE->Communication recovered, waiting "
                       + this.recover_time
                       + " milliseconds.");
                }

                try {
                    Thread.sleep( this.recover_time );
                }
                catch (InterruptedException e) {}

                continue;
            }

            try {

                return conn.sendRequest(ior, stream, policy_context);

            }
            catch (RECOVERABLE_COMM_FAILURE rcf) {

                recover_count--;

                if (recover_count <= 0) {
                    if (m_orb.m_trace != null)
                        m_orb.printTrace(
                           Trace.DEBUG,
                           "Can not recover the communication any more: ",
                           rcf.m_comm_failure);

                    throw rcf.m_comm_failure;
                }

                if (m_orb.m_trace != null) {
                    m_orb.printTrace(
                        Trace.DEBUG,
                        "CORBA::COMM_FAILURE->Communication recovered, waiting "
                        + this.recover_time
                        + " milliseconds.");
                }

                try {
                    Thread.sleep( this.recover_time );
                }
                catch (InterruptedException e) {}

            }
            catch (ForwardRequest fr) {
                throw fr;
            }
            catch (RuntimeException re) {
                if (m_orb.m_trace != null) {
                    m_orb.printTrace(Trace.ERROR,
                                     "Exception in remote invocation", re);
                }
                throw re;
            }
        }
    }

    /**
     * ORB Server IIOPConnection part shutdown.
     */
    public synchronized void shutdown()
    {
        try {
            if (m_ssl_server_listener != null) {
                if (m_orb.m_trace != null) {
                    m_orb.printTrace(Trace.DEBUG,
                                     "Shutdown SSLIOPCommLayer .SSLServerListener");
                }
                m_ssl_server_listener.shutdown();
                m_ssl_server_listener = null;
            }
        }
        catch (Throwable e) {}

        ((es.tid.TIDorbj.core.comm.iiop.IIOPCommLayer)this).shutdown();
    }

    /**
     * IIOP Layer close.
     */
    public synchronized void destroy()
    {
        if (!m_destroyed) {

            if (m_orb.m_trace != null) {
                m_orb.printTrace(Trace.DEBUG, "Destroying SSLIOPCommLayer");
            }

            try {
                if (m_ssl_server_listener != null) {
                    m_ssl_server_listener.shutdown();
                    m_ssl_server_listener = null;
                }
            }
            catch (Throwable e) {}

            ((es.tid.TIDorbj.core.comm.iiop.IIOPCommLayer)this).destroy();
        }
    }

    public synchronized void initServerListener()
    {
        if (m_ssl_server_listener == null) {
            m_ssl_server_listener = new SSLServerListener(m_connection_manager);
            m_ssl_server_listener.setDaemon(false);
            try {

                m_ssl_server_listener.start();

            }
            catch (Throwable thw) {
                m_ssl_server_listener = null;
                throw new org.omg.CORBA.NO_RESOURCES("Can't create thread: "
                                                     + thw.toString());
            }
        }

        // Also init IIOP server listener. It will always repond "NO_PERMISSION"
        super.initServerListener();

    }

    public synchronized IOR createIOR(String id,
                                      POAKey key, 
                                      TaggedComponent[] extraComponents)
    {
        if (m_ssl_server_listener == null)
            throw new org.omg.CORBA.INTERNAL("SSLServerListener not initialized");
        
        TaggedComponent[] components = null;
        int num_components = 1; // SSLComponent

        if(extraComponents != null) {
            components = new TaggedComponent[extraComponents.length + num_components];
            System.arraycopy(extraComponents, 0, components, 0, extraComponents.length);
        } else {
            components = new TaggedComponent[num_components];
        }
        
        // Add SSLComponent
        org.omg.SSLIOP.SSL ssl = new org.omg.SSLIOP.SSL (
                                   (short) (org.omg.CSIIOP.Integrity.value | 
                                            org.omg.CSIIOP.Confidentiality.value |
                                            org.omg.CSIIOP.NoDelegation.value), 
                                   (short) (org.omg.CSIIOP.Integrity.value | 
                                            org.omg.CSIIOP.Confidentiality.value |
                                            org.omg.CSIIOP.NoDelegation.value),
                                   (short) m_ssl_server_listener.getListenPoint().m_port);

        SSLComponent ssl_component = new SSLComponent(ssl);
        components[num_components - 1] = ssl_component;


        return super.createIOR(id, key, components);

    }
    
    public synchronized IOR createIOR(String id, 
                                      ObjectKey key, 
                                      TaggedComponent[] extraComponents)
    {
        if (m_ssl_server_listener == null)
            throw new org.omg.CORBA.INTERNAL("SSLServerListener not initialized");

        TaggedComponent[] components = null;
        int num_components = 1; // SSLComponent

        if(extraComponents != null) {
            components = new TaggedComponent[extraComponents.length + num_components];
            System.arraycopy(extraComponents, 0, components, 0, extraComponents.length);
        } else {
            components = new TaggedComponent[num_components];
        }

        // Add SSLComponent
        org.omg.SSLIOP.SSL ssl = new org.omg.SSLIOP.SSL (
                                   (short) (org.omg.CSIIOP.Integrity.value | 
                                            org.omg.CSIIOP.Confidentiality.value | 
                                            org.omg.CSIIOP.NoDelegation.value), 
                                   (short) (org.omg.CSIIOP.Integrity.value | 
                                            org.omg.CSIIOP.Confidentiality.value |
                                            org.omg.CSIIOP.NoDelegation.value),
                                   (short) m_ssl_server_listener.getListenPoint().m_port);

        SSLComponent ssl_component = new SSLComponent(ssl);
        components[num_components - 1] = ssl_component;

        return super.createIOR(id, key, components);

    }
    

//     public synchronized ServiceContextList getBidirectionalService()
//     {
//         if (m_destroyed)
//             throw new org.omg.CORBA.BAD_INV_ORDER("ORB is destroying");

//         if (m_bidirectional_service == null) {
//             if (m_ssl_server_listener == null)
//                 throw new INTERNAL("Trying to create a bidirectional context " 
//                                    + "without ListenPoint");

//             BiDirServiceContext bidir_context = new BiDirServiceContext(1);

//             bidir_context.m_listen_points[0] = 
//                 m_ssl_server_listener.getListenPoint();

//             m_bidirectional_service = new ServiceContextList(1);
//             m_bidirectional_service.m_components[0] = bidir_context;

//         }

//         return m_bidirectional_service;
//     }
}
