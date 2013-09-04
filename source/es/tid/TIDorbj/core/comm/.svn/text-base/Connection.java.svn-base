/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 385 $
* Date: $Date: 2009-04-16 10:15:52 +0200 (Thu, 16 Apr 2009) $
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
package es.tid.TIDorbj.core.comm;

import java.io.IOException;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.Bounds;
import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_PERMISSION;
import org.omg.CORBA.NO_RESPONSE;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.Object;
import org.omg.CORBA.Request;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TRANSIENT;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.Messaging.ReplyHandler;
import org.omg.Messaging.ReplyHandlerHelper;
import org.omg.Messaging._ReplyHandlerStub;
import org.omg.Compression.COMPRESSORID_NONE;
import org.omg.Compression.CompressorIdLevel;
import org.omg.Compression.COMPRESSORID_ZLIB;

import es.tid.TIDorbj.core.AnyImpl;
import es.tid.TIDorbj.core.NVListImpl;
import es.tid.TIDorbj.core.NamedValueImpl;
import es.tid.TIDorbj.core.ORBComponent;
import es.tid.TIDorbj.core.ObjectImpl;
import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.RequestImpl;
import es.tid.TIDorbj.core.ServerRequestImpl;
import es.tid.TIDorbj.core.StreamRequestImpl;
import es.tid.TIDorbj.core.cdr.BufferCDR;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.ChunkCDR;
import es.tid.TIDorbj.core.comm.CommunicationManager;
import es.tid.TIDorbj.core.comm.ForwardRequest;
import es.tid.TIDorbj.core.comm.iiop.*;
import es.tid.TIDorbj.core.comm.ssliop.*;

import es.tid.TIDorbj.core.comm.giop.AddressingDisposition;
import es.tid.TIDorbj.core.comm.giop.BiDirServiceContext;
import es.tid.TIDorbj.core.comm.giop.GIOPCancelRequestMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPFragmentMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPFragmentedMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPHeader;
import es.tid.TIDorbj.core.comm.giop.GIOPLocateReplyMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPLocateRequestMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPReplyMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPRequestMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPVersion;
import es.tid.TIDorbj.core.comm.giop.InvocationPoliciesContext;
import es.tid.TIDorbj.core.comm.giop.LocateReplyStatusType;
import es.tid.TIDorbj.core.comm.giop.MessageFactory;
import es.tid.TIDorbj.core.comm.giop.MsgType;
import es.tid.TIDorbj.core.comm.giop.ReplyStatusType;
import es.tid.TIDorbj.core.comm.giop.RequestId;
import es.tid.TIDorbj.core.comm.giop.ServiceContextList;
import es.tid.TIDorbj.core.comm.giop.ZIOPMessage;
import es.tid.TIDorbj.core.ziop.ZIOP;

import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.messaging.AMILock;
import es.tid.TIDorbj.core.messaging.AMILockEnumeration;
import es.tid.TIDorbj.core.messaging.AMILockList;
import es.tid.TIDorbj.core.messaging.QoS;
import es.tid.TIDorbj.core.policy.PolicyContext;
import es.tid.TIDorbj.core.util.Counter;
import es.tid.TIDorbj.core.util.FullUseTableException;
import es.tid.TIDorbj.core.util.RemovableObject;
import es.tid.TIDorbj.core.util.UseTable;
import es.tid.TIDorbj.util.Trace;

/**
 * TIDorb IIOP IIOPConnection: sends and receives IIOP Messages under the GIOP
 * protocol.
 * 
 * <p>
 * Copyright 2000 Telef&oacute;nica I+D. Printed in Spain (Europe). All Rights
 * Reserved.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

abstract public class Connection extends ORBComponent implements RemovableObject {
    //public class IIOPConnection extends Connection {

    /**
     * Client connection mode.
     */
    public final static int CLIENT_MODE = 0;

    /**
     * Server connection mode.
     */
    public final static int SERVER_MODE = 1;

    /**
     * Bidirectional connection mode.
     */
    public final static int BIDIRECTIONAL_MODE = 2;

    /**
     * IIOPConnection mode.
     */
    protected int m_mode;

    /**
     * IIOPConnection state.
     */
    protected ConnectionState m_state;

    /**
     * Pending requests in POA.
     */
    protected Counter m_requests_in_POA;

    /**
     * Actual id count. The ids will be given sequentialy.
     */
    private Counter id_count;

    /**
     * IIOPConnection identifier: the local port related to the connection socket.
     */

    protected int my_port = 0;

    /**
     * Server Object Listen Points assitiated.
     */

    protected java.util.Hashtable m_addresses;

    /**
     * First client address;
     */

    protected ListenPoint m_initial_point;

    /**
     * The IIOPConnectionManager.
     */

    protected IIOPConnectionManager m_manager;
    
    /**
     * Lock list where the pending requests are waiting for response.
     */

    protected LockList m_lock_list;

    /**
     * Lock list where the pending asynchronous requests are waiting for response.
     */

    protected AMILockList m_ami_lock_list;

    /**
     * Table with the uncompleted messages that has more fragments.
     */

    protected UseTable m_uncompleted_messages;

    /**
     * Maintains the actual request id for fragmented message in GIOP 1.1
     */

    RequestId m_current_request_id;

    /**
     * Bidirectional Service Context. It will be sent in the next
     * Request/Response.
     */

    protected ServiceContextList m_bidirectional_service;

    /**
     * Says if the bidirecitonal context has to been sent or not.
     */

    protected boolean m_send_bidirectional_service;

    /**
     * Write messages in socket monitor.
     */

    protected ConnectionWriteMonitor m_write_monitor;

    /**
     * IIOPConnection buffer for writting headers.
     */
    byte[] m_send_header_buffer;

    /**
     * IIOPConnection buffer for receiving headers.
     */
    byte[] m_receive_header_buffer;

    /**
     * The <code>toString()</code> return value.
     */
    protected String m_str = null;

    /**
     * Register the connection at the connection manager and initialize the
     * connection socket with the ORB configuration values.
     * 
     * @see es.tid.TIDorbj.core.ConfORB
     */

//     private boolean tcpNoDelay;
//     private int     soTimeout;
//     private int     soLinger;
    private int     maxUncompletedMessages;
//     private int     socketWriteTimeout;
    private GIOPVersion giopVersion;
    private int     giopFragmentSize;
    private boolean ipv6;
    
    /**
     * Says if QoS is enabled not.
     */
    long max_response_blocked_time;

    /**
     * Says if QoS is enabled not.
     */
    boolean qos_enabled;

    /**
     * Says if the client can sssume that server uses ziop or not.
     */
    boolean assume_ziop_server;
    
    protected Connection(IIOPConnectionManager mngr)
    {
        super(mngr.orb());

        m_manager = mngr;


        m_addresses = new Hashtable();
        m_bidirectional_service = null;
        m_send_bidirectional_service = false;

        maxUncompletedMessages = 
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.MAX_UNCOMPLETED_MESSAGES )
            .getInt(); 
        
        giopVersion =
            GIOPVersion.fromString(
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.GIOP_VERSION )
            .getValue()
        );
        
        giopFragmentSize = 
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.FRAGMENT_SIZE )
            .getInt(); 
 
        this.ipv6 =
            m_orb.getCommunicationManager().getLayerById( IIOPCommunicationLayer.ID )
            .getPropertyInfo( IIOPCommunicationLayerPropertiesInfo.IPV6 )
            .getBoolean();
        if (this.ipv6 == true)
        {
          System.setProperty("java.net.preferIPv6Address","true");
          System.setProperty("java.net.preferIPv4Stack","false");
        }

        max_response_blocked_time = m_orb.m_conf.max_blocked_time;
        qos_enabled = m_orb.m_conf.qos_enabled;
        assume_ziop_server = m_orb.m_conf.assume_ziop_server;

        m_state = new ConnectionState();

        m_requests_in_POA = new Counter();

        id_count = new Counter();

        m_current_request_id = null;

        m_lock_list = new LockList();
        
        m_ami_lock_list = m_orb.getAMIManager().getAMILockList();

        m_uncompleted_messages = new UseTable( maxUncompletedMessages );

        m_send_header_buffer    = new byte[GIOPHeader.HEADER_SIZE];
        m_receive_header_buffer = new byte[GIOPHeader.HEADER_SIZE];

    }

    /**
     * Start the connection thread execution.
     */
    public void init()
    {
        try {
            ConnectionThread conn_thread = new ConnectionThread(this);
            conn_thread.setDaemon(false);
            conn_thread.start();
        }
        catch (Throwable thw) {
            throw new org.omg.CORBA.NO_RESOURCES(
                "Can't create thread: " + thw.toString() );
        }

    }

    /**
     * IIOPConnection hash code.
     */
    public abstract int hashCode(); 

    public abstract boolean equals(Object obj); 

    public abstract String toString(); 


    /**
     * Add a new listen point for whose this connection can be uses to sending
     * request. This will be usefull if this connection is bidirectional.
     * 
     * @param listen_point
     *            the new listen point of an bidirectional connection
     */
    public void addListenPoint(ListenPoint listen_point)
    {
        m_addresses.put(listen_point, listen_point);
    }

    /**
     * @return the listen points associated to this connection
     */
    public Enumeration getListenPoints()
    {
        return m_addresses.elements();
    }

    /**
     * Changes the connection mode to BIDIRECTIONAL_MODE. This method is called
     * by the connection if it detects that a referenced object is defined in a
     * "Bidirectional POA". The contest will be sent in the next response.
     * 
     * @param context
     *            the list containing the <code>BiDirServiceContext</code>
     */
    public void setBidirectionalMode(ServiceContextList context)
    {
        if (m_mode != BIDIRECTIONAL_MODE) {
            m_mode = BIDIRECTIONAL_MODE;
            m_str = null; // reset the string
        }

        m_bidirectional_service = context;

        m_send_bidirectional_service = true;

        if (m_orb.m_trace != null) {
            m_orb.printTrace(Trace.DEBUG, toString()
                                          + ": Setting Bidirectional Mode.");
        }
    }

    /**
     * Changes the connection mode to BIDIRECTIONAL_MODE. This method is called
     * by the connection if it detects a bidirectional context in a request or
     * response sent by the peer.
     * 
     * @param context
     *            the bidirectional context
     */

    public void setBidirectionalModeByPeer(BiDirServiceContext context)
    {
        if (m_mode != BIDIRECTIONAL_MODE) {
            m_mode = BIDIRECTIONAL_MODE;
            m_str = null; // reset the string
        }

        if (m_orb.m_trace != null) {
            m_orb
                 .printTrace(
                             Trace.DEBUG,
                             toString()
                                                                                                                    + ": Setting Bidirectional Mode by Peer.");
        }

        for (int i = 0; i < context.m_listen_points.length; i++) {
            addListenPoint(context.m_listen_points[i]);
            m_manager.addBidirectionalConnection(context.m_listen_points[i],
                                                 (es.tid.TIDorbj.core.comm.Connection)this);
            if (m_orb.m_trace != null) {
                m_orb.printTrace(Trace.DEBUG, 
                                 toString()
                                 + " will be connected to"
                                 + context.m_listen_points[i].toString());
            }
        }

    }

    /**
     * Test whether the connection is still opened or not.
     * 
     * @return <code>true</code> if open or <code>false</code> if not.
     */

    public boolean isOpen()
    {
        return m_state.isOpen();
    }

    /**
     * @return the communication error (if exits)
     */
    public COMM_FAILURE getConnectionError()
    {
        return m_state.getError();
    }

    /**
     * @return <code>true</code> if the communication is running in client
     *         mode
     */
    public boolean isClientConnection()
    {
        return m_mode == CLIENT_MODE;
    }

    /**
     * @return <code>true</code> if the communication is running in client
     *         mode
     */
    public boolean isServerConnection()
    {
        return m_mode == SERVER_MODE;
    }

    /**
     * @return <code>true</code> if the communication is running in client
     *         mode
     */
    public boolean isBidirectionalConnection()
    {
        return m_mode == BIDIRECTIONAL_MODE;
    }

    /**
     * The connection has recived a close connection.
     */
    public void closeByPair()
    {
        if (m_state.setClosing()) {
            m_manager.closing(this);
            close();
        }

        if (m_orb.m_trace != null)
            m_orb.printTrace(Trace.DEBUG, toString() + " closed by pair");
    }

    /**
     * The connection will be closed by the connection manager.
     * 
     * @see es.tid.TIDorbj.core.comm.iiop.IIOPConnectionManager
     */

    public void closeByManager()
    {
        if (m_state.setClosing()) {
            if (m_mode != CLIENT_MODE)
                sendClose();

            if (m_lock_list.size() != 0)
                m_state.seterror(
                  new COMM_FAILURE("IIOPConnection Closed because the max. "
                                   + "connection number has been reached.",
                                   0,
                                   CompletionStatus.COMPLETED_NO));
            close();

            if (m_orb.m_trace != null)
                m_orb.printTrace(Trace.DEBUG,
                                 toString()
                                 + " closed by IIOPConnection Manager");
        }
    }

    /**
     * The connection is closed due to the socket is broken.
     * 
     * @param error
     *            the broken communication error
     */

    public void closeByBrokenConnection(COMM_FAILURE error)
    {
        if (m_state.seterror(error)) {
            m_manager.closing(this);
            close();

            if (m_orb.m_trace != null)
                m_orb.printTrace(Trace.DEBUG,
                                 toString()
                                 + " is closing due to: " + error);
        }
    }

    public void closeByError(COMM_FAILURE error)
    {
        if (m_state.seterror(error)) {
            m_manager.closing(this);
            close();

            if (m_orb.m_trace != null)
                m_orb.printTrace(Trace.DEBUG, 
                                 toString()
                                 + " is closing due to: " + error);
        }
    }

    protected abstract void close();

    public void sendClose()
    {
        try {
            GIOPHeader header; 
            header = new GIOPHeader( giopVersion, MsgType.CloseConnection);

            sendMessage(header);
        }
        catch (Exception e) {}
    }

    public void sendError() {
        if (m_orb.m_trace != null) {
            m_orb.printTrace(
                Trace.DEBUG, toString() + " : sending a GIOP Message error."
            );
        }

        GIOPHeader error; 
        error = new GIOPHeader( giopVersion , MsgType.MessageError);
        sendMessage(error);
    }

    public void sendHeader(GIOPHeader header) {
        header.toByteArray(m_send_header_buffer);
        write(m_send_header_buffer);
    }

    protected GIOPHeader receiveHeader() {
        read(m_receive_header_buffer);
        return GIOPHeader.fromByteArray(m_receive_header_buffer);
    }

    protected void sendMessage(GIOPHeader header) {
        try {
            m_write_monitor.initWrite();
        } catch (WriteTimeout to) {
            closeByError(m_write_monitor.getException());
            throw m_write_monitor.getException();
        }

        sendHeader(header);

        m_write_monitor.endWrite();
    }

    public void sendMessage(GIOPMessage message) {
        if (m_state.isOpen()) {
            m_manager.use(this);
        }

        message.send(this);

        if (m_orb.m_trace != null) {
            m_orb.printTrace(
                    Trace.DEEP_DEBUG, 
                    new String[]{
                    toString(),
                    ": ",
                    message.toString(),
                    " has been sent!"  
                } 
            );
        }
    }

    public void receiveMessage() {
        while (true) {

            if (m_state.isOpen()) {
                m_manager.use(this);
            } else {
                break;
            }

            try {

                // wait for reply
                GIOPHeader header = receiveHeader();


                // Attend for compressed messages
                if (header.getCompressed()){
                    ZIOPMessage ziop_message = new ZIOPMessage(header, giopFragmentSize);
                    
                    ziop_message.receiveBody(this, m_receive_header_buffer);
                    
                    ziop_message.connect_GIOPMessage(this);
                    continue;
                }


                GIOPMessage message = MessageFactory.fromHeader(header);

                if (message.hasBody()) {
                    message.receiveBody(this, m_receive_header_buffer);
                }

                if (m_orb.m_trace != null){
                    m_orb.printTrace(Trace.DUMP, "GIOP message chunk received - HEXDUMP " +
                                     message.getMessageBuffer().getChunk(0).getLength() +
                                     " bytes");
                    m_orb.printDump(Trace.DUMP,
                                    message.getMessageBuffer().getChunk(0).getBuffer(),
                                    message.getMessageBuffer().getChunk(0).getLength());
                                    
                    m_orb.printTrace(Trace.DEEP_DEBUG, toString() + ": "
                                                       + message.toString()
                                                       + " has been received!");
                }

                switch (header.getMsgType().m_value)
                {

                    case MsgType._Request:
                        manageMessage((GIOPRequestMessage) message);
                    break;
                    case MsgType._Reply:
                        manageMessage((GIOPReplyMessage) message);
                    break;
                    case MsgType._CancelRequest:
                        manageMessage((GIOPCancelRequestMessage) message);
                    break;
                    case MsgType._LocateRequest:
                        manageMessage((GIOPLocateRequestMessage) message);
                    break;
                    case MsgType._LocateReply:
                        manageMessage((GIOPLocateReplyMessage) message);
                    break;
                    case MsgType._Fragment:
                        manageMessage((GIOPFragmentMessage) message);
                    break;
                    case MsgType._CloseConnection:
                        closeByPair();
                        return;
                    case MsgType._MessageError:
                        closeByError(
                          new COMM_FAILURE("IIOPConnection closed due to pair "
                                           + "message error."));
                        return;
                }

            }
            catch (org.omg.CORBA.COMM_FAILURE comm) {
                closeByError(comm);
                return;
            }
            catch (Throwable excp) {
                if (m_orb.m_trace != null)
                    m_orb.printTrace(Trace.ERROR, toString(), excp);

                closeByError(new COMM_FAILURE(excp.toString()));
                return;
            }
        }
    }

    public void manageMessage(GIOPFragmentMessage message)
    {
        RequestId id = null;

        if (GIOPVersion.VERSION_1_2.equals(message.getHeader().getVersion()))
            id = message.getRequestId();
        else if (m_current_request_id == null) { // unexpected fragment message
            closeByError(new COMM_FAILURE("Unexpected fragment message.", 0,
                                          CompletionStatus.COMPLETED_NO));
            return;
        } else
            id = m_current_request_id;

        if (m_orb.m_trace != null) {
            m_orb.printTrace(
            	Trace.DEEP_DEBUG,
                toString() + " : GIOP Fragment Message has received."
			);
        }

        FragmentedMessageHolder holder = 
            (FragmentedMessageHolder) m_uncompleted_messages.get(id);

        if (holder == null) {
            // discard message
            return;
        }

        GIOPFragmentedMessage fragmented_message = holder.m_message;

        fragmented_message.addFragment(message);

        if (!message.getHeader().hasMoreFragments()) {

            //version 1.1 fragmentation: fragments are received.

            m_uncompleted_messages.remove(id);

            switch (fragmented_message.getHeader().getMsgType().m_value)
            {
                case MsgType._Request:
                    dispatchRequest((GIOPRequestMessage) fragmented_message);
                break;
                case MsgType._LocateRequest:
                    dispatchRequest((GIOPLocateRequestMessage) fragmented_message);
                break;
                // CLIENT MESSAGES
                case MsgType._Reply:
                    serviceContextReceived(
                      ((GIOPReplyMessage) 
                       fragmented_message).getServiceContextList());
                case MsgType._LocateReply:
                {
                    m_current_request_id = null;
                    Lock lock = m_lock_list.get(id);
                    AMILock ami_lock = m_ami_lock_list.get(id);
                    if (lock != null) { // the lock has not been discarded
                        lock.setMessage(fragmented_message);
                        lock.setCompleted();
                    } else if (ami_lock != null) {
                        ami_lock.setMessage(fragmented_message);
                    	m_ami_lock_list.setCompleted(ami_lock.requestId());
                    }
                }
            }
        }
    }

    public void manageMessage(GIOPReplyMessage message)
    {
        if (m_mode == SERVER_MODE) {
            closeByError(
             new COMM_FAILURE("Reply has been received by Server IIOPConnection"));
            return;
        }

        Lock lock = m_lock_list.get(message.getRequestId());
        AMILock ami_lock = m_ami_lock_list.get(message.getRequestId());

        if (lock == null && ami_lock == null) {
       		return; // discard
        }

        if (message.getHeader().hasMoreFragments()) {

            try {
                m_uncompleted_messages.append(
                  message.getRequestId(),
                  new FragmentedMessageHolder(message));
            }
            catch (FullUseTableException fute) {
                //unreachable: holder always can be removed
            }

            //version 1.1 fragmentation: fragments are received sequentialy
            if (GIOPVersion.VERSION_1_1.equals(message.getHeader().getVersion())) {
                if (m_current_request_id != null) { // Fragment message was
                                                    // expected
                    closeByError(
                      new COMM_FAILURE("Fragment message expeted, " +
                                       "but Reply received.",
                                       0,
                                       CompletionStatus.COMPLETED_NO));
                    return;
                }
                m_current_request_id = message.getRequestId();

            }
        } else {
            serviceContextReceived(message.getServiceContextList());
            if (lock != null) {
            	lock.setMessage(message);
            	lock.setCompleted();
            } else {         	//asynchronous invocation
            	ami_lock.setMessage(message);
            	m_ami_lock_list.setCompleted(ami_lock.requestId());
            }
        }
    }

    public void manageMessage(GIOPLocateReplyMessage message)
    {
        if (m_mode == SERVER_MODE) {
            closeByError(
              new COMM_FAILURE("LocateReply has been received by " 
                               + "Server IIOPConnection"));
            return;
        }

        Lock lock = m_lock_list.get(message.getRequestId());
        AMILock ami_lock = m_ami_lock_list.get(message.getRequestId());

        if (lock == null && ami_lock == null)
            return; // the lock hast expired, discard the message

        if (message.getHeader().hasMoreFragments()) {

            //version 1.1 fragmentation: fragments are received sequentialy
            if (GIOPVersion.VERSION_1_1.equals(message.getHeader().getVersion())) {
                closeByError(
                  new COMM_FAILURE(
                         "LocateReply can not be fragmented in GIOP 1.1.",
                         0, CompletionStatus.COMPLETED_NO));
                return;
            }

            try {
                m_uncompleted_messages.append(
                  message.getRequestId(),
                  new FragmentedMessageHolder(message));
            }
            catch (FullUseTableException fute) {
                //unreachable: holder always can be removed
            }

        } else {
        	if (lock != null) {
        		lock.setMessage(message);
        		lock.setCompleted();
        	} else {		//asynchronous invocation
        		ami_lock.setMessage(message);
            	m_ami_lock_list.setCompleted(ami_lock.requestId());
        	}
        }
    }

    public void manageMessage(GIOPCancelRequestMessage message)
    {
        if (m_mode == CLIENT_MODE) {
            closeByError(
              new COMM_FAILURE(
              	"CancelRequest has been received by Client IIOPConnection"));
            return;
        }

        m_uncompleted_messages.remove(message.getRequestId());
    }

    public void manageMessage(GIOPLocateRequestMessage message)
    {
        if (m_mode == CLIENT_MODE) {
            closeByError(
              new COMM_FAILURE(
                    "Request has been received by Client IIOPConnection"));
            return;
        }

        if (message.getHeader().hasMoreFragments()) {
            if (GIOPVersion.VERSION_1_1.equals(message.getHeader().getVersion())) {
                closeByError(
                  new COMM_FAILURE(
                       "LocateRequest can not be fragmented in GIOP 1.1.",
                       	0, CompletionStatus.COMPLETED_NO));
                return;
            }

            try {
                m_uncompleted_messages.append(
                    message.getRequestId(),
                    new FragmentedMessageHolder(message));
            }
            catch (FullUseTableException fute) {
                //unreachable: holder always can be removed
            }

        } else
            dispatchRequest(message);
    }

    protected abstract void dispatchRequest(GIOPLocateRequestMessage message); 

    
    protected abstract void dispatchRequest(GIOPRequestMessage message); 

    
    /**
     * Try to resolve the url received in a Request ObjectKey
     */

    protected org.omg.CORBA.Object resolveURL(String url) {
        try {
            // Backward compatibility, ObjectId now expected

            if (url.startsWith("corbaloc:tidorbj:")) {
                return m_orb.string_to_object(url);
            } else {

                String object_id = null;

                if (url.startsWith("corbaloc:ssliop:")) {
                    object_id = SSLIOPCorbaloc.getObjectId(url);
                } else if ((url.startsWith("corbaloc:iiop:"))
                    || (url.startsWith("corbaloc::"))) {
                    object_id = IIOPCorbaloc.getObjectId(url);
                } else {
                    // standard version
                    object_id = url;
                }

                return m_orb.resolve_initial_references(object_id);
            }
        }
        catch (Throwable th) {}

        return null;
    }

    public void manageMessage(GIOPRequestMessage message)
    {
        if (m_mode == CLIENT_MODE) {
            if (m_orb.m_trace != null) 	
                closeByError(
                new COMM_FAILURE(
                     "Request has been received by Client IIOPConnection"));
            return;
        }

        if (message.getHeader().hasMoreFragments()) {
            //version 1.1 fragmentation: fragments are received sequentialy
            if (GIOPVersion.VERSION_1_1.equals(message.getHeader().getVersion())) {
                if (m_current_request_id != null) { // Fragment message was
                                                    // expected
                    closeByError(
                      new COMM_FAILURE(
                              "Fragment message expeted, but Reply received.",
                              0,
                              CompletionStatus.COMPLETED_NO));
                    return;
                }
                m_current_request_id = message.getRequestId();
            }

            try {
                m_uncompleted_messages.append(
                    message.getRequestId(),
                    new FragmentedMessageHolder(message));
            }
            catch (FullUseTableException fute) {
                //unreachable: holder always can be removed
            }

        } else
            dispatchRequest(message);
    }

    public boolean sendLocateRequest(IIOPIOR ior,
                                     PolicyContext policy_context)
        throws RECOVERABLE_COMM_FAILURE, ForwardRequest
    {
        return sendLocateRequest(ior, AddressingDisposition.KeyAddr,
                                 policy_context);
    }

    private boolean sendLocateRequest(IIOPIOR ior,
                                      AddressingDisposition disposition,
                                      PolicyContext policy_context)
        throws RECOVERABLE_COMM_FAILURE, ForwardRequest
    {
        
        long timeout = QoS.checkRequestTime(this.m_orb, 
                                            policy_context);
        
        // verify if the request can be sent
        m_state.verifyForRequest();

        IIOPProfile profile = ior.profileIIOP();

        if (profile == null)
            throw new org.omg.CORBA.INTERNAL("Can not get IIOP Profile.");

        RequestId id = generateId();

        GIOPLocateRequestMessage message = 
            new GIOPLocateRequestMessage(profile.getVersion(),
                                         id,
                                         giopFragmentSize );
        
        // create the reply lock

        Lock lock = new Lock();

        lock.requestId(message.getRequestId());

        m_lock_list.put(lock);

        if (m_orb.m_trace != null)
            m_orb.printTrace(Trace.DEEP_DEBUG, toString()
                                               + ": sending LocationRequest "
                                               + id.toString());

        switch (disposition.value())
        {
            case AddressingDisposition._KeyAddr:
                message.insertAddress(m_manager.orb(), 
                                      ior.toObjectKeyAddress());
            break;
            case AddressingDisposition._ProfileAddr:
                message.insertAddress(m_manager.orb(), 
                                      ior.toProfileAddress());
            break;
            case AddressingDisposition._ReferenceAddr:
                message.insertAddress(m_manager.orb(),
                                      ior.toIORAddress());
        }

        sendMessage(message);

        // block the thread waiting for response
        
        lock.waitForCompletion(timeout);
  

        m_lock_list.remove(lock.requestId());

        if (lock.getCompleted()) {

            GIOPFragmentedMessage fragmented_message = lock.getMessage();

            if (fragmented_message == null) {
                throw new INTERNAL("No LocateReply message", 0,
                                   CompletionStatus.COMPLETED_NO);
            }
            if (fragmented_message.getHeader().getMsgType().m_value 
                != MsgType._LocateReply) {
                throw new MARSHAL("No LocateReply message received", 0,
                                  CompletionStatus.COMPLETED_NO);
            }

            GIOPLocateReplyMessage reply_message = 
                (GIOPLocateReplyMessage) fragmented_message;

            switch (reply_message.replyStatus().value())
            {
                case LocateReplyStatusType._UNKNOWN_OBJECT:
                    return false;
                case LocateReplyStatusType._OBJECT_HERE:
                    return true;
                case LocateReplyStatusType._OBJECT_FORWARD:
                {
                    IOR forward_ior = reply_message.extractForward();
                    throw new ForwardRequest(forward_ior);
                }
                case LocateReplyStatusType._OBJECT_FORWARD_PERM:
                {
                    IOR forward_ior = reply_message.extractForwardPerm();
                    throw new ForwardRequest(forward_ior);
                }
                case LocateReplyStatusType._LOC_SYSTEM_EXCEPTION:
                    throw reply_message.extractSystemException();
                case LocateReplyStatusType._LOC_NEEDS_ADDRESSING_MODE:
                    return sendLocateRequest(
                       ior,
                       reply_message.extractAddressingDisposition(),
                       policy_context);
            }
        } else {

            switch (m_state.getValue())
            {
	            case ConnectionState.CLOSING_STATE:
	            	throw new COMM_FAILURE("IIOPConnection closed by pair", 0, CompletionStatus.COMPLETED_MAYBE);	
	            case ConnectionState.ERROR_STATE:
	            	throw m_state.getError();
	            default:
	            	throw new NO_RESPONSE(0, CompletionStatus.COMPLETED_MAYBE);
            }
        }
        return false;
    }

    public void sendOnewayRequestAsync(es.tid.TIDorbj.core.RequestImpl request,
                                       IIOPIOR ior)
        throws RECOVERABLE_COMM_FAILURE
    {
        sendOnewayRequestAsync(request, ior, AddressingDisposition.KeyAddr);
    }

    private void sendOnewayRequestAsync(es.tid.TIDorbj.core.RequestImpl request,
                                        IIOPIOR ior,
                                        AddressingDisposition disposition)
        throws RECOVERABLE_COMM_FAILURE
    {
        // verify if the request can be sent
        m_state.verifyForRequest();

        request.setId(generateId());

        IIOPProfile profile = ior.profileIIOP();

        if (profile == null)
            throw new org.omg.CORBA.INTERNAL("Can not get IIOP Profile.");

        CompressorIdLevel compressor = new CompressorIdLevel((short)0, (short)0); 
        PolicyContext policy_context = null;

        if (qos_enabled) {
            policy_context = request.getPolicyContext();
            
            if (ior.is_ZIOP() || assume_ziop_server) {
                PolicyContext policies_context_ior = ior.policies();
                compressor = ZIOP.getClientCompressor(policy_context,
                                                      policies_context_ior,
                                                      assume_ziop_server);
            }
        }


        GIOPRequestMessage message = 
            new GIOPRequestMessage(profile.getVersion(),
                                   request.getId(),
                                   giopFragmentSize);

        try {
            message.insertRequest(request, ior, disposition);

            if (m_send_bidirectional_service) {
                message.setServiceContextList(m_bidirectional_service);
                m_bidirectional_service = null;
                m_send_bidirectional_service = false;
            }

            if (compressor.compressor_id != COMPRESSORID_NONE.value) {
                int low_value = ZIOP.getLowValue(policy_context);
                
                float min_ratio = ZIOP.getMinRatio(policy_context);
                
                ZIOPMessage ziop_message = new ZIOPMessage(message, giopFragmentSize);
                
                if (ziop_message.perform_compression(m_orb, compressor, 
                                                     low_value, min_ratio)) 
                    sendMessage(ziop_message); 
                else 
                    sendMessage(message); 
            }
            else { 

                sendMessage(message);
            }
        }
        catch (COMM_FAILURE comm) {
            throw new RECOVERABLE_COMM_FAILURE(comm);
        }

    }

    public void sendOnewayRequestSync(es.tid.TIDorbj.core.RequestImpl request,
                                      IIOPIOR ior)
        throws RECOVERABLE_COMM_FAILURE, ForwardRequest
    {
        sendOnewayRequestSync(request, ior, AddressingDisposition.KeyAddr);

    }

    private void sendOnewayRequestSync(es.tid.TIDorbj.core.RequestImpl request,
                                       IIOPIOR ior,
                                       AddressingDisposition disposition)

        throws RECOVERABLE_COMM_FAILURE, ForwardRequest
    {
        // verify if the request can be sent
        m_state.verifyForRequest();

        Lock lock = new Lock();

        lock.requestId(request.getId());
        m_lock_list.put(lock);

        IIOPProfile profile = ior.profileIIOP();

        if (profile == null)
            throw new org.omg.CORBA.INTERNAL("Can not get IIOP Profile.");

        long timeout = max_response_blocked_time;
        CompressorIdLevel compressor = new CompressorIdLevel((short)0, (short)0);
        PolicyContext policy_context = null;


        if (qos_enabled) {
            policy_context = request.getPolicyContext();
            
            timeout = QoS.checkRequestTime(this.m_orb, 
                                           policy_context);

            if (ior.is_ZIOP() || assume_ziop_server) {
                PolicyContext policies_context_ior = ior.policies();
                compressor = ZIOP.getClientCompressor(policy_context,
                                                      policies_context_ior,
                                                      assume_ziop_server);
            }
            
        }

        GIOPRequestMessage message =
            new GIOPRequestMessage(profile.getVersion(),
                                   request.getId(),
                                   giopFragmentSize);

        try {

            message.insertRequest(request, ior, disposition);

            if (m_send_bidirectional_service) {
                message.setServiceContextList(m_bidirectional_service);
                m_bidirectional_service = null;
                m_send_bidirectional_service = false;
            }

            if (compressor.compressor_id != COMPRESSORID_NONE.value) {
                int low_value = ZIOP.getLowValue(policy_context);
                
                float min_ratio = ZIOP.getMinRatio(policy_context);
                
                ZIOPMessage ziop_message = new ZIOPMessage(message, giopFragmentSize);
                
                if (ziop_message.perform_compression(m_orb, compressor, 
                                                     low_value, min_ratio)) 
                    sendMessage(ziop_message); 
                else 
                    sendMessage(message); 
            }
            else { 

                sendMessage(message);
            }
        }
        catch (COMM_FAILURE comm) {
            throw new RECOVERABLE_COMM_FAILURE(comm);
        }
      
        
        if(timeout > 0) {
            lock.waitForCompletion(timeout);
        }

        m_lock_list.remove(lock.requestId());

        if (lock.getCompleted()) {

            GIOPFragmentedMessage fragmented_message = lock.getMessage();

            if (fragmented_message == null)
                throw new INTERNAL("No Reply message");
            if (fragmented_message.getHeader().getMsgType().m_value 
                != MsgType._Reply)
                throw new MARSHAL("No LocateReply message received");

            GIOPReplyMessage reply_message = 
                (GIOPReplyMessage) fragmented_message;

            switch (reply_message.replyStatus().value())
            {
                case ReplyStatusType._NO_EXCEPTION: // traza
                case ReplyStatusType._USER_EXCEPTION: // traza
                case ReplyStatusType._SYSTEM_EXCEPTION: // traza
                break; // nothing to do, it is a oneway request
                case ReplyStatusType._LOCATION_FORWARD:
                {
                    IOR forward_ior = reply_message.extractForward();
                    throw new ForwardRequest(forward_ior);
                }
                case ReplyStatusType._LOCATION_FORWARD_PERM:
                {
                    IOR forward_ior = reply_message.extractForwardPerm();
                    throw new ForwardRequest(forward_ior);
                }
                case ReplyStatusType._NEEDS_ADDRESSING_MODE:
                    sendOnewayRequestSync(
                        request,
                        ior,
                        reply_message.extractAddressingDisposition());
            }
        }
    }

    public void sendRequest(es.tid.TIDorbj.core.RequestImpl request, IIOPIOR ior)
        throws RECOVERABLE_COMM_FAILURE, ForwardRequest
    {
        sendRequest(request, ior, AddressingDisposition.KeyAddr);
    }

    public void sendRequest(es.tid.TIDorbj.core.RequestImpl request, 
                             IIOPIOR ior,
                             AddressingDisposition disposition)
        throws RECOVERABLE_COMM_FAILURE, ForwardRequest

    {
        // verify if the request can be sent
        m_state.verifyForRequest();

        IIOPProfile profile = ior.profileIIOP();

        if (profile == null)
            throw new org.omg.CORBA.INTERNAL("Can not get IIOP Profile.");

        long timeout = max_response_blocked_time;
        CompressorIdLevel compressor = new CompressorIdLevel((short)0, (short)0); 
        PolicyContext policy_context = null;        

        if (qos_enabled) {
            policy_context = request.getPolicyContext();

            timeout = QoS.checkRequestTime(this.m_orb, 
                                           policy_context);

            if (ior.is_ZIOP() || assume_ziop_server) {
                PolicyContext policies_context_ior = ior.policies();
                compressor = ZIOP.getClientCompressor(policy_context,
                                                      policies_context_ior,
                                                      assume_ziop_server);
            }
        }

        request.setId(generateId());

        ////////////////////////////
        ///////////////////////

        GIOPVersion ver = profile.getVersion();
        GIOPVersion orb_ver = giopVersion;

        if (orb_ver.getMinor() < ver.getMinor()) {
            ver = orb_ver;
        }

        GIOPRequestMessage message = 
            new GIOPRequestMessage(ver, request.getId(), giopFragmentSize );

        //////////////////////
        //////////////////////

        Lock lock = new Lock();

        lock.requestId(message.getRequestId());

        m_lock_list.put(lock);

        if (m_orb.m_trace != null) {
            String[] msg = {
                            toString(),
                            ": Sending Request \"",
                            request.operation()
                            + " \""
                            + request.getId().toString() };
            
            m_orb.printTrace(Trace.DEEP_DEBUG, msg);

        }

        setServiceContextList(policy_context, message);

        message.insertRequest(request, ior, disposition);
        
        try {            

            if (compressor.compressor_id != COMPRESSORID_NONE.value) {
                int low_value = ZIOP.getLowValue(policy_context);
                
                float min_ratio = ZIOP.getMinRatio(policy_context);
                
                ZIOPMessage ziop_message = new ZIOPMessage(message, giopFragmentSize);
                
                if (ziop_message.perform_compression(m_orb, compressor, 
                                                     low_value, min_ratio)) 
                    sendMessage(ziop_message); 
                else 
                    sendMessage(message); 
            }
            else { 
                sendMessage(message);
            }

        }
        catch (COMM_FAILURE comm) {
            throw new RECOVERABLE_COMM_FAILURE(comm);
        }

        request.setCompletedMaybe();

        // block the thread waiting for response
            
       
        lock.waitForCompletion(timeout);
        
        m_lock_list.remove(lock.requestId());

        if (lock.getCompleted()) {

            GIOPFragmentedMessage fragmented_message = lock.getMessage();

            if (fragmented_message == null) {
                throw new INTERNAL("No Reply message");
            }
            if (fragmented_message.getHeader().getMsgType().m_value 
                != MsgType._Reply) {
                throw new MARSHAL("No Reply message received");
            }

            GIOPReplyMessage reply_message = 
                (GIOPReplyMessage) fragmented_message;

            request.setCompletedYes();

            switch (reply_message.replyStatus().value())
            {
                case ReplyStatusType._NO_EXCEPTION:
                    reply_message.extractArguments(request);
                break;
                case ReplyStatusType._USER_EXCEPTION:
                    request.setUserException(
                        reply_message.extractUserException(
                            request.exceptions()));
                break;
                case ReplyStatusType._SYSTEM_EXCEPTION:
                    request.setSystemException(
                        reply_message.extractSystemException());
                break;
                case ReplyStatusType._LOCATION_FORWARD:
                {
                    IOR forward_ior = reply_message.extractForward();
                    throw new ForwardRequest(forward_ior);
                }
                case ReplyStatusType._LOCATION_FORWARD_PERM:
                {
                    IOR forward_ior = reply_message.extractForwardPerm();
                    throw new ForwardRequest(forward_ior);
                }
                case ReplyStatusType._NEEDS_ADDRESSING_MODE:
                    sendRequest(request, ior,
                                reply_message.extractAddressingDisposition());
            }

        } else {

            switch (m_state.getValue())
            {
	            case ConnectionState.CLOSING_STATE:
	                throw new COMM_FAILURE("IIOPConnection closed by pair", 0, CompletionStatus.COMPLETED_MAYBE);	
	            case ConnectionState.ERROR_STATE:
	                throw m_state.getError();
	            default:
	                throw new NO_RESPONSE(0, CompletionStatus.COMPLETED_MAYBE);
            }
        }
    }

    public org.omg.CORBA.portable.InputStream 
    	sendRequest(es.tid.TIDorbj.core.iop.IOR ior,
                    es.tid.TIDorbj.core.cdr.CDROutputStream stream,
                    PolicyContext policy_context)
        throws RECOVERABLE_COMM_FAILURE, ForwardRequest,
        org.omg.CORBA.portable.RemarshalException,
        org.omg.CORBA.portable.ApplicationException
    {

        long timeout = QoS.checkRequestTime(this.m_orb, 
                                            policy_context);
        
        // write operation context: o conxtests
        stream.write_ulong(0);

        es.tid.TIDorbj.core.cdr.CDRInputStream header_input = 
            new es.tid.TIDorbj.core.cdr.CDRInputStream(null,
                                                       stream.getBuffer());

        GIOPHeader header = new GIOPHeader();

        header.read(header_input);

        header_input = null;

        GIOPMessage message = MessageFactory.fromHeader(header);

        if (!(message instanceof GIOPRequestMessage))
            throw new BAD_OPERATION("No Request message in stream");

        message.setMessageBuffer(stream.getBuffer());
        message.setMessageCompleted(true);

        GIOPRequestMessage request_msg = (GIOPRequestMessage) message;

        ServerRequestImpl srv_request = request_msg.extractRequest(m_orb);

        RequestId id = srv_request.getId();

        if (!srv_request.withResponse()) {
            if (m_orb.m_trace != null) {
                String[] msg = { toString(),
                                ": Sending Oneway Stream Request \"",
                                srv_request.operation() 
                                + " \"" 
                                + id.toString() };

                m_orb.printTrace(Trace.DEEP_DEBUG, msg);
            }

            sendMessage(message);

        } else {

            Lock lock = new Lock();

            lock.requestId(id);

            m_lock_list.put(lock);

            if (m_orb.m_trace != null) {
                String[] msg = { toString(), ": Sending Stream Request \"",
                                srv_request.operation() 
                                + " \"" 
                                + id.toString() };

                m_orb.printTrace(Trace.DEEP_DEBUG, msg);

            }

            try {
                sendMessage(message);

            }
            catch (COMM_FAILURE comm) {
                throw new RECOVERABLE_COMM_FAILURE(comm);
            }

            // block the thread waiting for response
     
            
            if(timeout > 0) {
                lock.waitForCompletion(timeout);
            }

            m_lock_list.remove(lock.requestId());

            if (lock.getCompleted()) {

                GIOPFragmentedMessage fragmented_message = lock.getMessage();

                if (fragmented_message == null) {
                    throw new INTERNAL("No Reply message");
                }

                if (fragmented_message.getHeader().getMsgType().m_value 
                    != MsgType._Reply) {
                    throw new MARSHAL("No Reply message received");
                }

                GIOPReplyMessage reply_message = 
                    (GIOPReplyMessage) fragmented_message;

                switch (reply_message.replyStatus().value())
                {
                    case ReplyStatusType._NO_EXCEPTION:
                    {
                        CDRInputStream input = reply_message.getDataInput();
                        input.fixStarting();
                        return input;
                    }
                    case ReplyStatusType._USER_EXCEPTION:
                    {
                        CDRInputStream input = reply_message.getDataInput();
                        input.fixStarting();
                        String excp_id = input.read_string();
                        input.rewind();
                        throw new ApplicationException(
                                     excp_id,
                                     reply_message.getDataInput());
                    }
                    case ReplyStatusType._SYSTEM_EXCEPTION:
                        throw reply_message.extractSystemException();
                    case ReplyStatusType._LOCATION_FORWARD:
                    {
                        IOR forward_ior = reply_message.extractForward();
                        throw new ForwardRequest(forward_ior);
                    }
                    case ReplyStatusType._LOCATION_FORWARD_PERM:
                    {
                        IOR forward_ior = reply_message.extractForwardPerm();
                        throw new ForwardRequest(forward_ior);
                    }
                    case ReplyStatusType._NEEDS_ADDRESSING_MODE:
                        throw new org.omg.CORBA.portable.RemarshalException();
                }

            } else {

                switch (m_state.getValue())
                {
	                case ConnectionState.CLOSING_STATE:
		                throw new COMM_FAILURE("IIOPConnection closed by pair", 0, CompletionStatus.COMPLETED_MAYBE);	
		            case ConnectionState.ERROR_STATE:
		                throw m_state.getError();
		            default:
		                throw new NO_RESPONSE(0, CompletionStatus.COMPLETED_MAYBE);
                }
            }
        }

        return null;
    }

    public void sendAsyncRequest(es.tid.TIDorbj.core.RequestImpl request, IIOPIOR ior) //*opc1*, Object ami_handler)
    throws RECOVERABLE_COMM_FAILURE, ForwardRequest
    {
    	sendAsyncRequest(request, ior, AddressingDisposition.KeyAddr);
    }

    private void sendAsyncRequest(es.tid.TIDorbj.core.RequestImpl request, 
                  			IIOPIOR ior, //*opc1* Object ami_handler, 
                         	AddressingDisposition disposition)
    	throws RECOVERABLE_COMM_FAILURE, ForwardRequest

    {
    	PolicyContext policy_context = request.getPolicyContext();

    	long timeout = QoS.checkRequestTime(this.m_orb, policy_context);

    	// verify if the request can be sent
    	m_state.verifyForRequest();

    	IIOPProfile profile = ior.profileIIOP();

    	if (profile == null)
    		throw new org.omg.CORBA.INTERNAL("Can not get IIOP Profile.");

    	request.setId(generateId());

    	////////////////////////////
    	///////////////////////

    	GIOPVersion ver = profile.getVersion();
    	GIOPVersion orb_ver = giopVersion;

    	if (orb_ver.getMinor() < ver.getMinor()) {
    		ver = orb_ver;
    	}

    	GIOPRequestMessage message = new GIOPRequestMessage(ver, request.getId(), giopFragmentSize );

    	//////////////////////
    	//////////////////////


    	AMILock ami_lock = new AMILock();
    	ami_lock.requestId(message.getRequestId());
    	ami_lock.setHandler(request.get_ami_handler());
    	ami_lock.setRequest(request);
    	ami_lock.setIor(ior);
    	
    	m_ami_lock_list.put(ami_lock);

    	if (m_orb.m_trace != null) {
    		String[] msg = {
    						toString(),
    						": Sending Asynchronous Request \"",
    						request.operation()
    						+ " \""
    						+ request.getId().toString() };
        
    		m_orb.printTrace(Trace.DEEP_DEBUG, msg);
    	}

    	setServiceContextList(policy_context, message);
    
    	try {            
    		message.insertRequest(request, ior, disposition);
    		sendMessage(message);
    	}
    	catch (COMM_FAILURE comm) {
    		throw new RECOVERABLE_COMM_FAILURE(comm);
    	}

    	request.setCompletedMaybe();
    	
    	
    	/* hacerlo dentro de thread k mira tiempo de las peticiones as�ncronas
       } else {
    	   switch (m_state.getValue())
    	   {
    	   		case ConnectionState.CLOSING_STATE:
    	   			request.setCompletedMaybe();
    	   			throw new RECOVERABLE_COMM_FAILURE(
    	   			new COMM_FAILURE("IIOPConnection closed by pair"));

    	   		case ConnectionState.ERROR_STATE:
    	   			throw m_state.getError();
    	   		default:
    	   			throw new NO_RESPONSE();
    	   }
       }*/
    }

 
    public void prepareRequest( StreamRequestImpl request, IIOPIOR ior ) {
        
        IIOPProfile profile = ior.profileIIOP();

        if (profile == null)
            throw new org.omg.CORBA.INTERNAL("Can not get IIOP Profile.");

        request.setId(generateId());

        ////////////////////////////
        ///////////////////////

        GIOPVersion ver = profile.getVersion();
        GIOPVersion orb_ver = giopVersion;

        if (orb_ver.getMinor() < ver.getMinor())
            ver = orb_ver;

        GIOPRequestMessage message = new GIOPRequestMessage(ver,
                                                            request.getId(),
                                                            giopFragmentSize);

        message.prepareRequest(request, ior, AddressingDisposition.KeyAddr);

    }

    public void sendReply(ServerRequestImpl request, PolicyContext policy_context)
    {
        m_requests_in_POA.dec();

        // verify if the request can be sent
        m_state.verifyForReply();

        GIOPReplyMessage message = new GIOPReplyMessage(request.getVersion(),
                                                        request.getId(),
                                                        giopFragmentSize);

        if (m_send_bidirectional_service) {
            message.setServiceContextList(m_bidirectional_service);
            m_bidirectional_service = null;
            m_send_bidirectional_service = false;
        }

        message.insertResultRequest(m_orb, request);

        CompressorIdLevel compressor = request.get_compressor();
        
        if (compressor.compressor_id != COMPRESSORID_NONE.value) {
                      
            int low_value = 0;
            float min_ratio = 0;


            if (policy_context != null) {
                low_value = ZIOP.getLowValue(policy_context);
                min_ratio = ZIOP.getMinRatio(policy_context);

                compressor.compression_level = 
                    ZIOP.getLevelCompressor(compressor.compressor_id,
                                            policy_context);
            }          
            
            ZIOPMessage ziop_message = new ZIOPMessage(message, giopFragmentSize);
            
            if (ziop_message.perform_compression(m_orb, compressor, 
                                                 low_value, min_ratio)) 
                sendMessage(ziop_message);
            else
                sendMessage(message);
            
        } else {
            sendMessage(message);
        }
    }

    public void sendLocateReply(GIOPVersion version, RequestId id, boolean here)
    {
        m_requests_in_POA.dec();

        // verify if the request can be sent
        m_state.verifyForReply();

        GIOPLocateReplyMessage message =
            new GIOPLocateReplyMessage(version, id, giopFragmentSize );

        if (here)
            message.insertObjectHere(m_orb);
        else
            message.insertUnknownObject(m_orb);

        sendMessage(message);
    }

    public void sendLocateReply(GIOPVersion version, RequestId id,
                                org.omg.CORBA.Object obj)
    {
        m_requests_in_POA.dec();

        // verify if the request can be sent
        m_state.verifyForReply();

        GIOPLocateReplyMessage message =
            new GIOPLocateReplyMessage(version, id, giopFragmentSize );

        message.insertForward(m_orb, obj);

        sendMessage(message);
    }

    public void sendLocateReply(GIOPVersion version, RequestId id,
                                SystemException excep)
    {
        m_requests_in_POA.dec();

        // verify if the request can be sent
        m_state.verifyForReply();

        GIOPLocateReplyMessage message =
            new GIOPLocateReplyMessage(version, id, giopFragmentSize);

        message.insertSystemException(m_orb, excep);

        sendMessage(message);
    }

    protected void setServiceContextList(PolicyContext policy_context,
                                         GIOPRequestMessage message)
    {
        Vector contexts = new Vector();        
        
        // Invocation Service Context
        
        InvocationPoliciesContext invocationPolicies = null;
        if (policy_context != null) {
            invocationPolicies = policy_context.getInvocationPolicyServiceContext();        
        }
        if(invocationPolicies != null) {
            contexts.add(invocationPolicies);
        }
        
        // Bidirectional Service context
        if (m_send_bidirectional_service) {
                
                contexts.add(m_bidirectional_service.m_components[0]);                
                m_bidirectional_service = null;
                m_send_bidirectional_service = false;
        }
        
        int num_services = contexts.size();
        if ( num_services > 0) {
           
            ServiceContextList services = new ServiceContextList(num_services);
            
            contexts.toArray(services.m_components);
            
            message.setServiceContextList(services);   
        }        
    }
    
    protected void serviceContextReceived(ServiceContextList services)
    {
        if (services == null)
            return;

        for (int i = 0; i < services.m_components.length; i++) {
            if ((services.m_components[i] != null)
                && (services.m_components[i].m_context_id 
                    == org.omg.IOP.BI_DIR_IIOP.value)) {
                setBidirectionalModeByPeer(
                    (BiDirServiceContext) services.m_components[i]);
            }
        }
    }

    /**
     * RemovableObject interface method
     */

    public boolean canBeRemoved()
    {
        return !hasPendingRequests();
    }

    public synchronized boolean hasPendingRequests()
    {
        if (m_mode == CLIENT_MODE) {
            return ((m_lock_list.size() > 0) 
                	|| (m_uncompleted_messages.getSize() > 0));
        } else {
            return ((m_lock_list.size() > 0)
                    || (m_uncompleted_messages.getSize() > 0) 
                    || (m_requests_in_POA.isNonZero()));
        }
    }

    public RequestId generateId()
    {
        return new RequestId(id_count.postInc());
    }

    public void read(byte[] buffer)
    {
        read(buffer, 0, buffer.length);
    }

    public abstract void read(byte[] buffer, int offset, int length);

    protected abstract void write(byte[] buffer);

    protected abstract void write(byte[] buffer, int offset, int length);

    /*
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! CAMBIAR PARA MANDAR UN MENSAJE CON
     * VARIOS CHUNKS
     */
    public void writeVersion1_0(BufferCDR buffer)
    {/*
      * int num_chunks = buffer.getNumAvailableChunks();
      * 
      * if(num_chunks > 1) throw new MARSHAL("GIOP 1.0 does not allow
      * fragmentation");
      * 
      * ChunkCDR chunk = null;
      * 
      * synchronized(write_mutex) { chunk = buffer.getChunk(0);
      * write(chunk.getBuffer(),0,chunk.getAvailable()); }
      */
        writeVersion1_1(buffer); // the buffer is only one message
    }

    public void writeVersion1_1(BufferCDR buffer)
    {
        int num_chunks = buffer.getNumAvailableChunks();

        ChunkCDR chunk = null;

        // Check write Monitor
        try {
            m_write_monitor.initWrite();
        }
        catch (WriteTimeout wt) { // WriteTimeout: close this connection
            closeByError(m_write_monitor.getException());
            throw m_write_monitor.getException();
        }

        // write loop

        for (int i = 0; i < num_chunks; i++) {
            chunk = buffer.getChunk(i);
            write(chunk.getBuffer(), 0, chunk.getAvailable());
            
            if (m_orb.m_trace != null) {
                m_orb.printTrace(Trace.DUMP, "GIOP message chunk sent - HEXDUMP " +
                                 chunk.getAvailable() + " bytes");
                m_orb.printDump(Trace.DUMP,
                                chunk.getBuffer(),
                                chunk.getAvailable());
            }

        }
        // free write monitor

        m_write_monitor.endWrite();
    }

    public void writeVersion1_2(BufferCDR buffer)
    {
        writeVersion1_1(buffer);
        /*
         * int num_chunks = buffer.getNumAvailableChunks();
         * 
         * ChunkCDR chunk = null;
         * 
         * for(int i = 0; i < num_chunks; i++) { chunk = buffer.getChunk(i);
         * 
         * synchronized(write_mutex) {
         * write(chunk.getBuffer(),0,chunk.getAvailable()); } }
         */
    }
}
