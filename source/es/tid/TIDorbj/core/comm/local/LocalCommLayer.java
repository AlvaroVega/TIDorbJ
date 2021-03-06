/*
 * MORFEO Project
 * http://www.morfeo-project.org
 *
 * Component: TIDorbJ
 * Programming Language: Java
 *
 * File: $Source$
 * Version: $Revision: 395 $
 * Date: $Date: 2009-05-27 16:10:32 +0200 (Wed, 27 May 2009) $
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
package es.tid.TIDorbj.core.comm.local;

import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.NO_RESPONSE;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.ValueBase;

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.RequestImpl;
import es.tid.TIDorbj.core.comm.CommunicationManager;
import es.tid.TIDorbj.core.comm.ForwardRequest;
import es.tid.TIDorbj.core.comm.iiop.IIOPCommunicationDelegate;
import es.tid.TIDorbj.core.comm.iiop.IIOPIOR;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.messaging.QoS;
import es.tid.TIDorbj.core.poa.ExecThread;
import es.tid.TIDorbj.core.poa.POAManagerImpl;
import es.tid.TIDorbj.core.policy.PolicyContext;
import es.tid.TIDorbj.util.Trace;

/**
 * Layer that dispatchs the request to local objects.
 * 
 * <p>
 * Copyright 2000 Telef&oacute;nica I+D. Printed in Spain (Europe). All Rights
 * Reserved.
 * 
 * @autor Javier Fdez. Mejuto
 * @version 1.0
 */

//TODO: maybe local comm layer should be a CommunicationLayer itself
public class LocalCommLayer extends
        es.tid.TIDorbj.core.comm.iiop.CommunicationLayer {
    public LocalCommLayer( es.tid.TIDorbj.core.TIDORB orb ) {
        super( orb );
    }

    public boolean objectExists( IIOPIOR ior, PolicyContext policy_context )
            throws es.tid.TIDorbj.core.comm.ForwardRequest {

        try {
            
            long timeout = QoS.checkRequestTime(this.m_orb, 
                                                policy_context);
            
            ObjectKey objectKey = ior.getObjectKey();

            LocalLocateResponseHandler responseHandler;
            responseHandler = new LocalLocateResponseHandler( 
                objectKey,
                policy_context
            );
            
            responseHandler.setSerial(m_orb.getRequestCounter().next());
            
            /*
             * Local request cannot be dispatched regularly because the
             * dispatcher should wait for them... think of this later
             * CommunicationManager.getInstance( this.m_orb )
                .getRequestDispatcher().dispatchRequest( responseHandler );
             */
            
            // Check if the the thread that invoked the request is an ExecThread
            Thread currentThread = Thread.currentThread();

            if ( currentThread instanceof ExecThread ) {
                ExecThread currentExecThread = ( ExecThread )currentThread; 
                currentExecThread.processLocalRequest( responseHandler );
            } else { 
                // application invocation
                CommunicationManager.getInstance( this.m_orb )
                .getRequestDispatcher().dispatchRequest( responseHandler );
                                                
                
                responseHandler.waitForCompletion(timeout);
                
                    
            }

            // Invoking poaManager

            if ( ! responseHandler.isCompleted() ) {
                throw new NO_RESPONSE();
            }

            if ( responseHandler.isExceptionSet() ) {
                throw responseHandler.getException();
            }

            if ( responseHandler.isForwarded() ) {
                IIOPIOR forwardIOR;

                org.omg.CORBA.Object forwardReference; 
                forwardReference = responseHandler.getForwardReference();

                if ( forwardReference instanceof ValueBase ) {
                    throw new org.omg.CORBA.NO_IMPLEMENT();
                }

                if ( forwardReference instanceof ObjectImpl ){
                    ObjectImpl reference;
                    reference = ( ( ObjectImpl )forwardReference );
                    
                    Delegate forwardDelegate; 
                    forwardDelegate = reference._get_delegate();

                    if ( forwardDelegate instanceof IIOPCommunicationDelegate ) {
                        forwardIOR = ( ( IIOPCommunicationDelegate )forwardDelegate )
                                .getIIOPReference();
                    } else {
                        //TODO: ask juan: this could happen?
                        // write ior from anothers's ORB Object
                        forwardIOR = IIOPIOR.fromString( m_orb, m_orb
                                .object_to_string( forwardReference ) );
                    }
                    throw new ForwardRequest( forwardIOR );
                } else {
                    throw new org.omg.CORBA.INV_OBJREF();
                }
            }

            // Return results
            return responseHandler.getResult();
        } catch ( INTERNAL e ) {
            if ( m_orb.m_trace != null ) {
                m_orb.printTrace( 
                    Trace.ERROR,
                    "INTERNAL Exception in local oneway invocation", 
                    e
                );
            }
            throw e;
        } catch ( RuntimeException e ) {
            if ( m_orb.m_trace != null ) {
                m_orb.printTrace( 
                    Trace.ERROR,
                    "Exception in local location invocation", 
                    e 
                );
            }
            throw e;
        }
    }

    public void request( es.tid.TIDorbj.core.RequestImpl request,
            IIOPIOR ior )
            throws es.tid.TIDorbj.core.comm.ForwardRequest {
        try {

            request.withResponse( true );
            sendRequest( request, ior );

        } catch ( es.tid.TIDorbj.core.comm.ForwardRequest fr ) {
            throw fr;
        } catch ( RuntimeException e ) {
            if ( m_orb.m_trace != null ) {
                m_orb.printTrace( Trace.ERROR, "Exception in local invocation",
                    e );
            }
            throw e;
        }
    }

    public void onewayRequest( es.tid.TIDorbj.core.RequestImpl request,
            IIOPIOR ior ) {
        try {

            request.withResponse( false );
            sendRequest( request, ior );

        } catch ( es.tid.TIDorbj.core.comm.ForwardRequest fr ) {
            // unreachable
        } catch ( RuntimeException e ) {
            if ( m_orb.m_trace != null ) {
                m_orb.printTrace( Trace.ERROR,
                    "Exception in local oneway invocaton", e );
            }
            throw e;
        }
    }

    //corregir
    public void asyncRequest( es.tid.TIDorbj.core.RequestImpl request,
                              IIOPIOR ior)
            throws es.tid.TIDorbj.core.comm.ForwardRequest {
        try {

            request.withResponse( true );
            sendAsyncRequest( request, ior );

        } catch ( es.tid.TIDorbj.core.comm.ForwardRequest fr ) {
            throw fr;
        } catch ( RuntimeException e ) {
            if ( m_orb.m_trace != null ) {
                m_orb.printTrace( Trace.ERROR, "Exception in local asynchronous invocation",
                    e );
            }
            throw e;
        }
    }
    
    protected void sendRequest( RequestImpl request, IOR ior )
            throws es.tid.TIDorbj.core.comm.ForwardRequest {
        
        long timeout = QoS.checkRequestTime(this.m_orb, 
                                            request.getPolicyContext());

        ObjectKey objectKey = ior.getObjectKey();

        PolicyContext policyContext; 
        policyContext = request.getPolicyContext();

        // Creating ServerRequest
        LocalServerRequest serverRequest;
        serverRequest = new LocalServerRequest( request );

        
        //CreatingResponseHandler
        LocalResponseHandler responseHandler;
        responseHandler = new LocalResponseHandler(
            objectKey,
            serverRequest );
        
        responseHandler.setSerial(m_orb.getRequestCounter().next());
        
        POAManagerImpl poaManager; 
        poaManager = ( POAManagerImpl ) m_orb.initPOA().the_POAManager();
        if ( ! request.withResponse() ) {
            // Invoking poaManager
            poaManager.put( responseHandler );
        } else {
            // Check if the the thread that invoked the request is an ExecThread

            Thread currentThread = Thread.currentThread();

            if ( currentThread instanceof ExecThread ) {
                ExecThread currentExecThread;
                currentExecThread = ( ExecThread )currentThread;
                currentExecThread.processLocalRequest( responseHandler );
            } else { // application invocation
                // Invoking poaManager
                poaManager.put( responseHandler );
                
	            responseHandler.waitForCompletion(timeout );
        	}

            if ( ! responseHandler.isCompleted() ) {
                throw new NO_RESPONSE();
            }

            if ( serverRequest.isForwarded() ) {

                IIOPIOR forwarded_ior;

                org.omg.CORBA.Object forward = serverRequest.getForward();

                if ( forward instanceof org.omg.CORBA.portable.ValueBase )
                    throw new org.omg.CORBA.NO_IMPLEMENT();

                if ( ! ( forward instanceof org.omg.CORBA.portable.ObjectImpl ) )
                    throw new org.omg.CORBA.INV_OBJREF();

                org.omg.CORBA.portable.Delegate forward_delegate = 
                    ( ( org.omg.CORBA.portable.ObjectImpl )forward )._get_delegate();

                if ( forward_delegate instanceof IIOPCommunicationDelegate ) {
                    forwarded_ior = 
                        ( ( IIOPCommunicationDelegate )forward_delegate ).getIIOPReference();
                } else { // write ior from anothers's ORB Object
                    forwarded_ior = IIOPIOR.fromString( 
                        m_orb, 
                        m_orb.object_to_string( forward ) 
                    );
                }

                throw new es.tid.TIDorbj.core.comm.ForwardRequest( forwarded_ior );
            }
        }
    }
    

    protected void sendAsyncRequest( RequestImpl request, IOR ior )
    	throws es.tid.TIDorbj.core.comm.ForwardRequest {

    	long timeout = QoS.checkRequestTime(this.m_orb, 
                                    request.getPolicyContext());

    	ObjectKey objectKey = ior.getObjectKey();

    	PolicyContext policyContext; 
    	policyContext = request.getPolicyContext();

    	// Creating ServerRequest
    	LocalServerRequest serverRequest;
    	serverRequest = new LocalServerRequest( request );


    	//CreatingResponseHandler
    	AMILocalResponseHandler amiResponseHandler;
    	amiResponseHandler = new AMILocalResponseHandler(objectKey, serverRequest, m_orb );
    	amiResponseHandler.setSerial(m_orb.getRequestCounter().next());

    	POAManagerImpl poaManager; 
    	poaManager = ( POAManagerImpl ) m_orb.initPOA().the_POAManager();
    	if ( ! request.withResponse() ) {
    		// Invoking poaManager
    		poaManager.put( amiResponseHandler );
    	} else {
    		// Check if the the thread that invoked the request is an ExecThread

    		Thread currentThread = Thread.currentThread();

    		if ( currentThread instanceof ExecThread ) {
    			ExecThread currentExecThread;
    			currentExecThread = ( ExecThread )currentThread;
    			currentExecThread.processLocalRequest( amiResponseHandler );
    		} else { // application invocation
    			// Invoking poaManager
    			poaManager.put( amiResponseHandler );
    		}


    		/*if ( ! amiResponseHandler.isCompleted() ) {
    			throw new NO_RESPONSE();
    		}*/

    	}
    }

    
   /**
   
    protected void sendRequest( RequestImpl request, IOR ior )
            throws es.tid.TIDorbj.core.ForwardRequest {

        ObjectKey key = ior.getObjectKey();
        POAKey poaKey;
        if ( key instanceof POAKey ) {
            poaKey = ( POAKey ) key;
        } else {
            poaKey = new POAKey( key.getMarshaledKey() );
        }

        PolicyContext policy_context = request.getPolicyContext();

        // Creating ServerRequest
        LocalServerRequest serverRequest = new LocalServerRequest( request );

        // Creating LocalRequest
        LocalRequest localRequest = new LocalRequest( poaKey, m_orb.initPOA(),
                                                      serverRequest );

        if ( !request.withResponse() ) {
            // Invoking poaManager
            POAManagerImpl poaManager = ( POAManagerImpl ) m_orb.initPOA()
                    .the_POAManager();
            poaManager.put( localRequest );
        } else {
            // Check if the the thread that invoked the request is an ExecThread

            Thread this_thread = Thread.currentThread();

            POAManagerImpl poaManager = ( POAManagerImpl ) m_orb.initPOA()
                    .the_POAManager();

            if ( this_thread instanceof ExecThread ) {
                ( ( ExecThread ) this_thread ).processLocalRequest(
                    localRequest, poaManager );
            } else { // application invocation
                // Invoking poaManager
                poaManager.put( localRequest );
                localRequest.waitForCompletion( PolicyUtil
                        .getRelativeRequestTimeoutValue( policy_context ) );
            }

            if ( !localRequest.getCompleted() ) {
                throw new NO_RESPONSE();
            }

            if ( serverRequest.isForwarded() ) {

                es.tid.TIDorbj.core.iop.IOR forwarded_ior;

                org.omg.CORBA.Object forward = serverRequest.getForward();

                if ( forward instanceof org.omg.CORBA.portable.ValueBase )
                    throw new org.omg.CORBA.NO_IMPLEMENT();

                if ( ! ( forward instanceof org.omg.CORBA.portable.ObjectImpl ) )
                    throw new org.omg.CORBA.INV_OBJREF();

                org.omg.CORBA.portable.Delegate forward_delegate = ( ( org.omg.CORBA.portable.ObjectImpl ) forward )
                        ._get_delegate();

                if ( forward_delegate instanceof IIOPCommunicationDelegate ) {
                    forwarded_ior = ( ( IIOPCommunicationDelegate ) forward_delegate )
                            .getReference();
                } else { // write ior from anothers's ORB Object
                    forwarded_ior = IOR.fromString( m_orb, m_orb
                            .object_to_string( forward ) );
                }

                throw new es.tid.TIDorbj.core.ForwardRequest( forwarded_ior );
            }
        }
    }

    */
    
    public void prepareRequest( es.tid.TIDorbj.core.StreamRequestImpl request ) {
        throw new org.omg.CORBA.INTERNAL();
    }

    public org.omg.CORBA.portable.InputStream request(
            IIOPIOR ior,
            es.tid.TIDorbj.core.cdr.CDROutputStream stream,
            es.tid.TIDorbj.core.policy.PolicyContext policy_context )
            throws es.tid.TIDorbj.core.comm.ForwardRequest,
            org.omg.CORBA.portable.ApplicationException,
            org.omg.CORBA.portable.RemarshalException {
        throw new org.omg.CORBA.INTERNAL();
    }

    public void dump(java.io.PrintWriter writer){
    }
}
