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
package es.tid.TIDorbj.core.poa;

import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.NO_RESPONSE;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.ServerRequest;
import org.omg.CORBA.TIMEOUT;
import org.omg.CORBA.UNKNOWN;
import org.omg.PortableServer.DynamicImplementation;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.POAManagerPackage.State;
import org.omg.PortableServer.POAPackage.AdapterNonExistent;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.ServantLocatorPackage.CookieHolder;

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.messaging.QoS;
import es.tid.TIDorbj.core.policy.PolicyContext;
import es.tid.TIDorbj.util.Trace;

/**
 * Execution thread. Gets request from the request queue and executes them.
 * 
 * @autor Javier Fdz. Mejuto
 * @version 1.0
 */
public class ExecThread extends Thread
{

    private POAManagerImpl m_poa_manager = null;

    private RequestQueue m_queue = null;    

    private boolean m_deactivated = false;

    private ThreadStateListener m_thread_state_listener = null;

    private java.util.Stack m_current_info_stack = null;

    private String m_thread_name;

    private TIDORB m_orb;
    
    private Trace m_trace;

    private long m_max_response_blocked_time;

    private boolean m_qos_enabled;

    /**
     * Constructor.
     * 
     * @param poaManager
     *            POAManager to which this ExecThread belongs.
     * @param number
     *            Id number of this ExecThread.
     */
    protected ExecThread(POAManagerImpl poaManager)
    {
        m_poa_manager = poaManager;
        m_queue = m_poa_manager.getRequestQueue();
        m_current_info_stack = new java.util.Stack();
        //TODO: orb referecial by jprojas
        m_orb = m_poa_manager.m_orb;
        m_trace = m_orb.m_trace;
        m_qos_enabled = m_orb.m_conf.qos_enabled;
        m_max_response_blocked_time =  m_orb.m_conf.max_blocked_time;
    }

    /**
     * Set the ThreadStateListener. Any ExecThread should have a listener
     * (eventually the ThreadPool) before it is started.
     * 
     * @param l
     *            The listener.
     */
    protected void setThreadStateListener(ThreadStateListener l)
    {
        m_thread_state_listener = l;
    }

    /**
     * Checks the state of the POAManager. If the state is ACTIVE, then finish
     * returning true. If the state is HOLDING, then wait until state is
     * diferent from HOLDING. If the state is DISCARDING, then discard the
     * request and return false. If the state is INACTIVE, then reject the
     * request and return false.
     * 
     * @param request
     *            The request which is being processed.
     * @return Returns true if the request must be executed, otherwise returns
     *         false.
     */
    private boolean checkState( QueuedRequest request ) {

        boolean executeRequest = false;

        if (m_trace != null) {
            String[] msg = { toString(), " checking state of ",
                            m_poa_manager.toString() };
            m_trace.print(Trace.DEEP_DEBUG, msg);
        }

        synchronized (m_poa_manager.m_state_mutex) {
            boolean exit = false;
            while (!exit) {
            	org.omg.PortableServer.POAManagerPackage.State state = m_poa_manager.get_state();
                if ((state == org.omg.PortableServer.POAManagerPackage.State.ACTIVE) && !request.getMustDiscard()) {
                    exit = true;
                    executeRequest = true;
                } else if (state == org.omg.PortableServer.POAManagerPackage.State.HOLDING) {
                    try {
                        // wait until state changes
                        m_poa_manager.m_state_mutex.wait();
                    } catch (InterruptedException ie) {}
                    exit = false;
                } else if (state == org.omg.PortableServer.POAManagerPackage.State.INACTIVE) {
                    // MISSING: define strategy for deactivated POAManagers
                	if ( m_trace != null ) {
                        String[] msg = { toString(),
                                " Submitting TRANSIENT to request becase foreing POAManager is INACTIVE" };
                        m_trace.print( Trace.DEEP_DEBUG, msg );
                    }
                    request.submitResponse( new org.omg.CORBA.TRANSIENT() );
                    exit = true;
                    executeRequest = false;
                } else if ( state == org.omg.PortableServer.POAManagerPackage.State.DISCARDING || 
                            request.getMustDiscard()  ){
                	if ( m_trace != null ) {
                        String[] msg = { toString(),
                                " Submitting TRANSIENT to request becase foreing POAManager is DISCARDING" };
                        m_trace.print( Trace.DEEP_DEBUG, msg );
                    }
                    //TODO: change method signature to return possible errors
                    //in state check
                    request.submitResponse( new org.omg.CORBA.TRANSIENT() );
                    exit = true;
                    executeRequest = false;
                }
            }
        }
        return executeRequest;
    }

    /**
     * Execution loop.
     */
    public void run()
    {        
        
        while (!m_deactivated) {
            try {
                
                if (m_trace != null) {
                    String[] msg = { toString(), ": getting request..." };
                    m_trace.print(Trace.DEEP_DEBUG, msg);
                    
                }

                QueuedRequest thisRequest = m_queue.get();
                
                

                if (thisRequest != null) {
                    // If there is a request, then go!!
                    m_thread_state_listener.setActive(this);
                    processRequest(thisRequest);
                    m_thread_state_listener.setInactive(this);
                    
                } else if (m_thread_state_listener.threadCanDie(this)) {
                    // If restarted and no request, then commit suicide
                    m_deactivated = true;
                }

            }
            catch (Throwable unhandledException) {
                // Unhandled exception. Should never happen!!
                if (m_trace != null) {
                    m_trace.printStackTrace(Trace.DEBUG,
                                            toString() + "Unhanled Exception ",
                                            unhandledException);
                    m_trace.print(Trace.DEBUG, toString() + " dies...");
                }
                m_deactivated = true;
            }
            
            
            
            
        }
        m_thread_state_listener.threadHasDied(this);
    }

    /**
     * Process current request.
     * 
     * @return <code>true</code> wherther the request has been queued in
     *         another POAManager.
     */
    private void processRequest(QueuedRequest thisRequest) {        

        try {            
            if (m_trace != null) {
                String[] msg = { toString(), " executing request ", thisRequest.toString()};
                m_trace.print(Trace.DEEP_DEBUG, msg);
            }
            
            ObjectKey objectKey;
            objectKey = thisRequest.getObjectKey();
            
            POAKey poaKey;
            try {
                poaKey = m_orb.resolvePOAKey( objectKey );
                //TODO: review 'poa's request hierarchy' at this moment, POAKey
                // and objectkey ar stored
            } catch ( Throwable th ){
                poaKey = null;
            }
            
            if ( poaKey != null ) {
                thisRequest.setPOAKey( poaKey );
            } else {
                throw new OBJECT_NOT_EXIST();
            }
            
            if ( checkState( thisRequest ) ) {
                // find poa
                POAImpl poa = null;
                try {
                    poa = findPOA( thisRequest );
                } catch ( AdapterNonExistent ane) {
                    if (m_trace != null) {
                        m_trace.printStackTrace(
                            Trace.DEBUG, 
                            new String[]{ 
                                toString(), 
                                "POA ",
                                thisRequest.getCurrentChildPOAName(),
                                " not found" 
                            }, 
                            ane
                        );
                    }
                    throw new org.omg.CORBA.OBJECT_NOT_EXIST();
                }

                if (poa != null) {
                    execute(thisRequest, poa);
                }
            }
        } catch (org.omg.CORBA.SystemException e) {
        	if ( m_trace != null ) {
                String[] msg = { toString(),
                        " Submitting a system exception to Request" };
                m_trace.printStackTrace(Trace.DEEP_DEBUG, msg, e);
            }
            thisRequest.submitResponse( e );
        } catch (java.lang.Throwable th) {
        	th.printStackTrace( System.err) ; 
            thisRequest.submitResponse(
                new INTERNAL(th.toString(), 0, thisRequest.getStatus() )
            );
        }        
    }

    /**
     * Finds the POA which corresponds to
     * 
     * @param request.
     *            If the request belongs to another POAManager it is bypassed to
     *            it, returning null.
     * @param request
     *            The queued request to be executed.
     * @returns POAImpl POA where the request must be executed.
     * @exception org.omg.PortableServer.POAPackage.AdapterNonExistent
     *                If it fails finding the POA.
     */
    private POAImpl findPOA( QueuedRequest request )
        throws org.omg.PortableServer.POAPackage.AdapterNonExistent
    {
        
        POAImpl current_poa;
        
        current_poa = request.getCurrentPOA();
        if( current_poa == null ){
            current_poa = this.m_orb.initPOA();
            request.setCurrentPOA( current_poa );
        }
        
        boolean bypassed = false;
        POAManagerImpl nextPOAManager;
        while ( !( bypassed || request.isFinalPOA() ) ) {
            current_poa = (POAImpl) current_poa.find_POA(
                request.getCurrentChildPOAName(),true
            );
            
            request.nextChildPOA(current_poa);
            
            nextPOAManager = (POAManagerImpl) current_poa.the_POAManager();

            if (nextPOAManager != m_poa_manager) {
                // bypass request to nextPOAManager
                if (m_trace != null) {
                    m_trace.print(
                        Trace.DEEP_DEBUG,
                        new String[]{
                           toString(), " bypassing request ", request.toString(), 
                           " to ", current_poa.toString()
                        }
                    );
                }
                nextPOAManager.put(request);
                bypassed = true;
            }
        }
        return ( bypassed )?null:current_poa;
    }

    /**
     * Process local Request in the given POAManager.
     * 
     * @param localRequest
     *            the Local request
     * @param current_manager
     *            the current POA POAManager
     */
    public void processLocalRequest( QueuedRequest localRequest )
    {
        if (m_trace != null) {
            m_trace.print(
                Trace.DEEP_DEBUG, 
                new String[]{ 
                    toString(), 
                    " executing local request ",
                    localRequest.toString() 
                }
            );
        }
        
        try {
            
            ObjectKey objectKey;
            objectKey = localRequest.getObjectKey();
            
            POAKey poaKey;
            try {
                poaKey = m_orb.resolvePOAKey( objectKey );
                //TODO: review 'poa's request hierarchy' at this moment, POAKey
                // and objectkey ar stored
            } catch ( Throwable th ){
                poaKey = null;
            }
            
            if ( poaKey != null ) {
                localRequest.setPOAKey( poaKey );
            } else {
                throw new OBJECT_NOT_EXIST();
            }
            
            POAImpl currentPOA = localRequest.getCurrentPOA();
            if ( currentPOA == null ){
                // Start from rootPOA
                currentPOA = this.m_orb.initPOA();
                localRequest.setCurrentPOA( currentPOA );
            }
            
            POAManager currentPOAManager;
            currentPOAManager = currentPOA.the_POAManager();
            
            if ( currentPOAManager == this.m_poa_manager ) {
                if (!checkState(localRequest)) {
                    return;
                }
            } else {
                if ( !checkForeingState( localRequest ) ) {
                    return;
                }
            }

            // find poa

            POAImpl current_poa = localRequest.getCurrentPOA();

            try {
                while (!localRequest.isFinalPOA()) {
                    current_poa = (POAImpl) 
                   current_poa.find_POA(localRequest.getCurrentChildPOAName(),
                                        true);

                    localRequest.nextChildPOA(current_poa);

                    POAManagerImpl nextPOAManager = (POAManagerImpl)
                        current_poa.the_POAManager();

                    if (nextPOAManager != m_poa_manager) {
                        // bypass request to nextPOAManager

                        if (m_trace != null) {
                            String[] msg = 
                                { toString(), 
                                  " bypassing request ",
                                  localRequest.toString(),
                                  " through ", 
                                  current_poa.toString()};

                            m_trace.print(Trace.DEEP_DEBUG, msg);
                        }

                        processLocalRequest( localRequest );
                        return;
                    }
                }
            }
            catch (org.omg.PortableServer.POAPackage.AdapterNonExistent ane) {
                if (m_trace != null) {
                    String[] msg = 
                      { toString(), 
                        "POA ",
                        localRequest.getCurrentChildPOAName(),
                      " not found, org.omg.CORBA.OBJECT_NOT_EXIST throwed" };
                    
                    m_trace.printStackTrace(Trace.DEBUG, msg, ane);
                }
                localRequest.submitResponse(new OBJECT_NOT_EXIST() );
                return;
            }

            if (current_poa != null) {
                execute(localRequest, current_poa);
            }

        } catch (org.omg.CORBA.SystemException se) {
        	if ( m_trace != null ) {
                String[] msg = { toString(),
                        " Submitting a system exception to localRequest" };
                m_trace.printStackTrace(Trace.DEEP_DEBUG, msg, se);
            }
            localRequest.submitResponse( se );
        } catch (java.lang.Throwable tw) {
        	tw.printStackTrace();
            localRequest.submitResponse(
                new INTERNAL(tw.toString(), 0, localRequest.getStatus() ) 
            );
        }        
    }
    
    /**
     * Checks the state of the given POAManager. If the state is ACTIVE, then
     * finish returning true. If the state is HOLDING, then wait until state
     * changes or the max_blocked_time timeout is reached. If the state is
     * DISCARDING, then discard the request and return false. If the state is
     * INACTIVE, then reject the request and return false.
     * 
     * @param request
     *            The request which is being processed.
     * @return Returns true if the request must be executed, otherwise returns
     *         false.
     */
    private boolean checkForeingState( QueuedRequest localRequest ) {
        if ( m_trace != null ) {
            m_trace.print( 
                Trace.DEEP_DEBUG, 
                new String[]{ 
                    toString(), 
                    " Checking foreing POAManager state" 
                }
            );
        }
        POAManagerImpl currentPOAManager;
        //TODO: add method "getPOAManager" returning a POAManagerImpl to avoid
        //castings
        currentPOAManager = 
            ( POAManagerImpl )localRequest.getCurrentPOA().the_POAManager();
        
        org.omg.PortableServer.POAManagerPackage.State state = currentPOAManager.get_state();

        // chech current_manager state
        if ( state == org.omg.PortableServer.POAManagerPackage.State.HOLDING ) {
            if ( m_trace != null ) {
                m_trace.print( 
                    Trace.DEEP_DEBUG,
                    new String[]{ 
                        toString(),
                        " Waiting in a foreing POAManager because it is HOLDING" 
                    }
                );
            }

            long timeout = m_max_response_blocked_time;

            if (m_qos_enabled) {

                PolicyContext policyContext; 
                policyContext = localRequest.getPolicyContext();

                if(policyContext != null) {
                
                    timeout = QoS.checkRequestTime(m_orb, policyContext);
                }
            }
                    
            if(timeout > 0) {
                synchronized ( currentPOAManager.m_state_mutex ) {
                    try {
                        //TODO: check if this could be done outside ExecThread's to
                        //avoid inclusion of requestPolicy in responsehandlers
                        currentPOAManager.m_state_mutex.wait(timeout);
                    } catch ( InterruptedException ie ) { }
                }
            }

            state = currentPOAManager.get_state();

            currentPOAManager.put( localRequest );
            if ( state == org.omg.PortableServer.POAManagerPackage.State.HOLDING ) {
                if ( m_trace != null ) {
                    String[] msg = { toString(),
                            " Submitting NO_RESPONSE because foreing POAManager is HOLDING" };
                    m_trace.print( Trace.DEEP_DEBUG, msg );
                }
                localRequest.submitResponse( new NO_RESPONSE()  );
                return false;
            }
        }

        if ( state == org.omg.PortableServer.POAManagerPackage.State.INACTIVE ) {
        	if ( m_trace != null ) {
                String[] msg = { toString(),
                        " Submitting TRANSIENT to localRequest becase foreing POAManager is INACTIVE" };
                m_trace.print( Trace.DEEP_DEBUG, msg );
            }
            localRequest.submitResponse( new org.omg.CORBA.TRANSIENT() );
            return false;
        }

        if ( ( state == org.omg.PortableServer.POAManagerPackage.State.DISCARDING ) || localRequest.getMustDiscard() ) {
        	if ( m_trace != null ) {
                String[] msg = { toString(),
                        " Submitting TRANSIENT to localRequest becase foreing POAManager is DISCARDING" };
                m_trace.print( Trace.DEEP_DEBUG, msg );
            }
            localRequest.submitResponse( new org.omg.CORBA.TRANSIENT() );
            return false;
        }

        return true;
    }


    
    /**
     * Executes a queued request depending on its type.
     * 
     * @param request
     *            The queued request to be executed.
     */
    private void execute( QueuedRequest request, POAImpl poa ) {
        // TODO:temporary check until refactoring complete

        POAKey poaKey = request.getPOAKey();
        if ( poaKey != null ) {
            long poaId;
            poaId = poaKey.getPOAId();
            /*
             * poaId -> persistent poa. Must check if it's not a persistent poa,
             * if it's the same 'instance' that holds the 'servant' right now
             */
            if ( poaId != 0 && poaId != poa.getId() ) {
              	if ( m_trace != null ) {
                    String[] msg = { toString(),
                            " Submitting OBJECT_NOT_EXIST to request becase poaId isn't int poa" };
                    m_trace.print( Trace.DEEP_DEBUG, msg );
                }
                request.submitResponse( new org.omg.CORBA.OBJECT_NOT_EXIST() );
            } else {
                // execute request
                CurrentInfo current;
                current = new CurrentInfo( poa, poaKey.getOID() );
                m_current_info_stack.push( current );

                try {
                    if ( request instanceof QueuedLocateResponseHandler ) {
                        execute( ( QueuedLocateResponseHandler ) request, poa );
                    } else if ( request instanceof QueuedResponseHandler ){
                        execute( ( QueuedResponseHandler )request, poa );
                    }
                    request.setStatus( CompletionStatus.COMPLETED_YES );
                } catch ( org.omg.CORBA.SystemException se ) {
                    request.submitResponse( se );
                } catch ( java.lang.Throwable tw ) {
                    request.submitResponse( 
                        new INTERNAL( tw.toString(), 0, request.getStatus() ) 
                    );
                } finally {
                    m_current_info_stack.pop();
                    if ( current.isRequestInPOA() ) {
                        try {
                            poa.removeUser( poaKey.getOID() );
                        } catch ( Exception e ) {
                        }
                        request.destroy();
                    }
                }
            }
        } else {
            if ( m_orb.m_trace != null ) {
                m_orb.printTrace( 
                    Trace.DEEP_DEBUG, 
                    new String[] { 
                        toString(),
                        " Invalid target ObjectKey in Request[ ",
                        request.getObjectKey().toString(), 
                        "]" 
                    }
                );
            }
        }
    }

    private void execute( QueuedLocateResponseHandler handler, POAImpl poa ){
        //TODO: send this to the request...

        BooleanHolder servantLocatorUsed = new org.omg.CORBA.BooleanHolder();
        try {
            // Current execution context
            CurrentInfo current; 
            current = (CurrentInfo)m_current_info_stack.peek();

            // Current servant's OID
            OID oid;
            oid = current.m_current_oid;

            // Servant location
            Servant servant;
            servant = poa.find_servant( oid, null, null, servantLocatorUsed );
            if (servant != null) {
                handler.submitResponse( true );
            } else {
                //This should never happen
                throw new INTERNAL(
                    "find_servant() returning null!!",
                    0,
                    CompletionStatus.COMPLETED_NO
                );
            }
        } catch (org.omg.CORBA.SystemException e) {
            throw e;
        } catch (org.omg.PortableServer.ForwardRequest e) {
            handler.submitResponse( e.forward_reference );
            m_poa_manager.m_orb.printTrace(Trace.DEBUG, "Forwarded: ", e);
        } catch (Exception e) {
            handler.submitResponse( false );
        }
    }
    
    private void execute( QueuedResponseHandler handler, POAImpl poa ){
        try {
                      
                
            
            /**/
            // get serverRequest (with params for invocation)
            ServerRequest serverRequest;
            serverRequest = handler.getServerRequest();

            //    QoS validation
            if (m_qos_enabled) {
                            
            if(!QoS.validateServerRequestEndTimePolicy(handler.getPolicyContext()))
            {
                if (m_trace != null) {
                    
                    String[] msg = { toString(), 
                                     " discarding ", 
                                     serverRequest.toString(),
                                     " due to Messaging::RequestEndTimePolicy: TIMEOUT"};
                    
                    m_trace.print(Trace.DEBUG, msg);
                }
                
                throw new TIMEOUT();
            }

            }
            /*
             * SERVANT LOCATION
             **/ 
            
            // Cookie holder to store "reference" to dynamic incarnated servants
            // when using servant locator
            CookieHolder cookieHolder; 
            cookieHolder = new CookieHolder();
            
            // Boolean holder, to store previous condition
            //TODO: verify with juan
            BooleanHolder servantLocatorUsed; 
            servantLocatorUsed = new org.omg.CORBA.BooleanHolder();
            
            // Current execution context
            CurrentInfo current; 
            current = (CurrentInfo)m_current_info_stack.peek();

            // Target servant's OID
            OID oid;
            oid = current.m_current_oid;
            

            org.omg.PortableServer.Servant servant = null;
            try {
                servant = poa.find_servant( 
                    oid,
                    serverRequest.operation(), 
                    cookieHolder,
                    servantLocatorUsed
                );

                poa.addUser( oid );

                current.setRequestInPOA(true);

            } catch ( ObjectNotActive one ) {
                this.m_orb.printTrace( Trace.DEBUG, "", one );
                //TODO: why is status in ServerRequestImpl
                throw new org.omg.CORBA.OBJECT_NOT_EXIST(
                    one.getMessage(), 1, handler.getStatus()
                );
            } catch ( ObjectAlreadyActive oae ) {
                this.m_orb.printTrace(Trace.DEBUG, "", oae);
                throw new org.omg.CORBA.OBJ_ADAPTER(
                    oae.getMessage(), 0, handler.getStatus()
                );
            } catch ( org.omg.PortableServer.ForwardRequest fr ) {
                this.m_orb.printTrace( Trace.DEBUG, "Forwarded: ", fr );
                handler.submitResponse( fr.forward_reference );
                return;
            }

            // set servant in current object
            current.setServant( servant );

            handler.setStatus( CompletionStatus.COMPLETED_MAYBE );

            if ( "_is_a".equals( serverRequest.operation() ) ) {
                this.invoke_is_a( poa, servant, serverRequest );
            } else {
                if (servant instanceof DynamicImplementation) {
                    // invoke servant using DII
                    DynamicImplementation dynServant; 
                    dynServant = (DynamicImplementation) servant;
                    
                    if ( poa.isSingleThread() ) {
                        synchronized ( dynServant._get_delegate() ) {
                            doInvoke(dynServant, serverRequest);
                        }
                    } else {
                        doInvoke(dynServant, serverRequest);
                    }
                } else {
                    throw new NO_IMPLEMENT(
                        "Stream based invocation not implemented",
                        0,
                        CompletionStatus.COMPLETED_NO
                    );
                }
            }

            handler.setStatus(CompletionStatus.COMPLETED_YES);
            
            // return results (if not oneway)
            handler.setPolicyContext(poa.getPolicyContext());
            handler.submitResponse();
            
            
            // Call postinvoke if necessary
            if ( servantLocatorUsed.value ) {
                poa.callPostinvoke(
                    oid.toByteArray(),
                    serverRequest.operation(), 
                    cookieHolder, 
                    servant
                );
            }
        /* TODO: why ForwardRequest isn't thrown?
        } catch (org.omg.PortableServer.ForwardRequest e) {
            handler.submitResponse( e.forward_reference );
            m_poa_manager.m_orb.printTrace(Trace.DEBUG, "Forwarded: ", e);
        */
        } catch (org.omg.CORBA.SystemException e) {
            throw e;
        } catch (Exception e) {
          	if ( m_trace != null ) {                
                m_trace.printStackTrace( Trace.DEEP_DEBUG, 
                			   			 " Submitting UNKNOWN exception to handler ", e);
            }

            handler.submitResponse(
                new UNKNOWN()
            );
        }
    }

    

    /**
     * Executes "_is_a" request on the ServantDelegate.
     * 
     * @param servant
     *            The target servant of the "_is_a" request.
     * @param request
     *            The queued request to be executed.
     */
    private void invoke_is_a(POAImpl poa,
                             org.omg.PortableServer.Servant servant,
                             org.omg.CORBA.ServerRequest request)
    {
        try {
            es.tid.TIDorbj.core.poa.ServantDelegate deleg =
                (es.tid.TIDorbj.core.poa.ServantDelegate) servant._get_delegate();
            org.omg.CORBA.ORB orb = poa.m_orb;
            org.omg.CORBA.NVList params = orb.create_list(1);
            org.omg.CORBA.Any $s = orb.create_any();
            $s.type(orb.get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
            params.add_value("s", $s, org.omg.CORBA.ARG_IN.value);
            request.arguments(params);
            String itf = $s.extract_string();
            boolean result = deleg.is_a(servant, itf);
            org.omg.CORBA.Any resultAny = orb.create_any();
            resultAny.insert_boolean(result);
            request.set_result(resultAny);
        } catch (org.omg.CORBA.SystemException se) {
            throw se;
        } catch (Throwable th) {
            if ( this.m_orb.m_trace != null) {
                m_trace.printStackTrace(
                    Trace.DEBUG, 
                    new String[]{
                        toString(),
                        " Exception in servant invoke \"_is_a\" method, ",
                        "UNKOWN thrown: "
                    },
                    th
                );
            }
            throw new org.omg.CORBA.UNKNOWN();
        }

    }

    /**
     * @return Returns the string representation of this Thread.
     */
    public synchronized String toString()
    {
        if (m_thread_name == null) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(super.toString());
            buffer.append(" in ");
            buffer.append(m_poa_manager.toString());
            m_thread_name = buffer.toString();
            buffer = null;
        }
        return m_thread_name;
    }

    /**
     * @return Returns the CurrentInfo in the top of the stack.
     */
    public CurrentInfo getCurrentInfo()
    {
        if (m_current_info_stack.empty()) {
            return null;
        }
        return (CurrentInfo) m_current_info_stack.peek();
    }

    private void doInvoke(DynamicImplementation servant,
                          org.omg.CORBA.ServerRequest request)
    {
        try {
            servant.invoke(request);
        }
        catch (org.omg.CORBA.SystemException se) {
            throw se;
        }
        catch (Throwable th) {
            if (m_poa_manager.m_orb.m_trace != null) {
                String[] msg =
                  { toString(),
                   " Exception in servant invoke method, UNKOWN thrown: "
                  };
                
                m_trace.printStackTrace(Trace.DEBUG, msg, th);
            }
            throw new org.omg.CORBA.UNKNOWN();
        }
    }
}
