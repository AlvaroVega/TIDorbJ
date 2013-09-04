/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 67 $
* Date: $Date: 2007-05-10 12:05:13 +0200 (Thu, 10 May 2007) $
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
package es.tid.TIDorbj.core.comm;

import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ServerRequest;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.UNKNOWN;
import org.omg.PortableServer.ForwardRequest;

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.poa.POAImpl;
import es.tid.TIDorbj.core.poa.POAKey;
import es.tid.TIDorbj.core.poa.POAManagerImpl;
import es.tid.TIDorbj.core.poa.QueuedLocateResponseHandler;
import es.tid.TIDorbj.core.poa.QueuedRequest;
import es.tid.TIDorbj.core.poa.QueuedResponseHandler;
import es.tid.TIDorbj.util.Trace;


/**
 * RequestDispather provides methods to each <code>CommunicationLayer</code>
 * implementation to enable Request processing and Response submission, allowing
 * disacoupling the implementation from the ORB's request processing implementa-
 * tion.
 *  
 * @author Juan Pablo Rojas
 *
 */
public class RequestDispatcher  {

	/*
	 * The orb instance
	 */
	private TIDORB orb;
    private POAImpl rootPOA;
    private POAManagerImpl rootPOAManager;
    private CommunicationManager communicationManager;
	
	/**
	 * @param orb
	 */
	public RequestDispatcher(TIDORB orb ) {
		if ( orb != null ){
			this.orb = orb;	
            this.rootPOA = this.orb.initPOA();
            this.rootPOAManager = ( POAManagerImpl )this.rootPOA.the_POAManager();
            this.communicationManager = CommunicationManager.getInstance( this.orb );
        } else {
			throw new IllegalArgumentException( "Parameter 'orb' cannot be null" );
		}
	}//RequestDispatcher
	
	
    /**
     * @param responseHandler
     */
    
    //TODO: Queued stuff should be only known here... see how to make a 
    //queue delegate to use from ExecThread
    public void dispatchRequest( QueuedLocateResponseHandler locateResponseHandler ) {
        if ( locateResponseHandler != null ){
            
            setPOAKey(locateResponseHandler);
            
            if ( this.orb.m_trace != null) {
                this.orb.printTrace(
                    Trace.DEEP_DEBUG, 
                    new String[]{
                       toString(),
                       "Dispatching LocateRequest ",
                       locateResponseHandler.getObjectKey().toString()
                    }
                );
            }
            this.rootPOAManager.put( locateResponseHandler );
        } else {
            //TODO: ask juan about the exception conventions inside TIDORB
            throw new BAD_PARAM( "null response handler" );
        }
    }//dispatchRequest
    
    public void dispatchRequest( QueuedResponseHandler responseHandler ) {
        if ( responseHandler != null ){           
            
            if(setPOAKey(responseHandler)) {

	            this.rootPOAManager.put( responseHandler );
	            
	            if ( this.orb.m_trace != null) {
	                ServerRequest request = responseHandler.getServerRequest(); 
	                this.orb.printTrace(
	                    Trace.DEEP_DEBUG, 
	                    new String[]{
	                       toString(),
	                       " Dispatching ",
	                       responseHandler.toString()
	                       }
	                );
	            }
            }
        } else {
            throw new BAD_PARAM( "null response handler" );
        }
    }//dispatchRequest
    
    /**
     * Search the POAKey to the request 
     * @param request
     * @return true if the key has been set
     */
    private boolean setPOAKey(QueuedRequest request) {
        
        ObjectKey objectKey;
        objectKey = request.getObjectKey();
        
        POAKey poaKey;
        try {
            poaKey = orb.resolvePOAKey( objectKey );
            //TODO: review 'poa's request hierarchy' at this moment, POAKey
            // and objectkey ar stored
            request.setPOAKey( poaKey );
            return true;
        } catch (ForwardRequest fr) {
            request.submitResponse(fr.forward_reference);            
        } catch (SystemException one) {
        	if ( this.orb.m_trace != null) {
                this.orb.printTrace(Trace.DEEP_DEBUG,                		
                		            "Submitting a system exception in request",
                		            one);
            }
            request.submitResponse(one);          
        } catch ( Throwable th ){
        	if ( this.orb.m_trace != null) {
                this.orb.printTrace(Trace.DEEP_DEBUG,                		
                		            "Submitting a CORBA UNKNOWN exception in request");                		            
            }
            request.submitResponse(new UNKNOWN());            
        }
        return false;
    }
    
    public void dispose(){
        
    }
    
}
