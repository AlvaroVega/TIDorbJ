/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 12 $
* Date: $Date: 2006-01-31 17:34:41 +0100 (Tue, 31 Jan 2006) $
* Last modified by: $Author: iredondo $
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
package es.tid.TIDorbj.core.comm.local;

import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.Object;
import org.omg.CORBA.Request;
import org.omg.CORBA.ServerRequest;
import org.omg.CORBA.SystemException;

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.RequestImpl;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.comm.iiop.IIOPCommunicationDelegate;
import es.tid.TIDorbj.core.comm.iiop.IIOPIOR;
import es.tid.TIDorbj.core.poa.QueuedResponseHandler;
import es.tid.TIDorbj.core.policy.PolicyContext;
import es.tid.TIDorbj.core.util.OperationCompletion;
import es.tid.TIDorbj.util.Trace;

/**
 * @author jprojas
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/*
 * TODO: this class and its peer LocalLocateResponseHandler are too similar,
 * check it! 
 */

public class AMILocalResponseHandler extends QueuedResponseHandler {


    private LocalServerRequest serverRequest;
    private ObjectKey objectKey;

    private OperationCompletion completionStatus;
    
    private Object forwardReference;
    private SystemException exception;

    private PolicyContext requestPolicyContext;
    
    private TIDORB m_orb;

    public AMILocalResponseHandler( 
            ObjectKey objectKey, 
            LocalServerRequest serverRequest, TIDORB orb ){
        this.objectKey = objectKey;
        this.serverRequest = serverRequest;
        this.completionStatus = new OperationCompletion();
        this.m_orb = orb;
    }
    
    /**
     * OWN METHODS
     */
    
    public boolean isForwarded(){
        return this.forwardReference != null;
    }
    
    public Object getForwardReference(){
        return this.forwardReference;
    }
    
    public boolean isCompleted(){
        return this.completionStatus.isCompleted();
    }
    
    public boolean isExceptionSet(){
        return this.exception != null;
    }
    
    public SystemException getException(){
        return this.exception;
    }
    
    
    public void waitForCompletion(long how_long)
    {
        try {
            this.completionStatus.waitForCompletion(how_long);
        }
        catch (java.lang.InterruptedException ie) {
            throw new INTERNAL("Unexpected Interrupted Exception");
        }
        catch (es.tid.TIDorbj.core.util.OnlyOneThreadCanWait only) {
            throw new INTERNAL("Only one Thread can wait in the request lock.");
        }
    }
    
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#getPolicyContext()
     */
    public PolicyContext getPolicyContext() {
        return this.serverRequest.getLocalRequest().getPolicyContext();
    }    

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#getObjectKey()
     */
    public ObjectKey getObjectKey() {
        return this.objectKey;
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#getServerRequest()
     */
    public ServerRequest getServerRequest() {
        return this.serverRequest;
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#submitResponse(org.omg.CORBA.Object)
     */
    public void submitResponse( Object reference ) {
        this.forwardReference = reference;
        this.serverRequest.setForward( reference );
        this.completionStatus.setCompleted();
        this.putReply();
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#submitResponse(org.omg.CORBA.SystemException)
     */
    public void submitResponse( SystemException e ) {
        this.exception = e;
        this.serverRequest.setSystemException( e );
        this.completionStatus.setCompleted();
        this.putReply();
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#submitResponse()
     */
    public void submitResponse() {
        this.completionStatus.setCompleted();
        this.putReply();
    }

    /* 
     * remove from here
     */
    public void sendError( SystemException e, Trace trace ) {
        // TODO Auto-generated method stub

    }

    private void putReply() {
    	RequestImpl request = serverRequest.getLocalRequest();
    	Object ami_handler = request.get_ami_handler();
   		RequestImpl replyHandlerRequest = (RequestImpl) ami_handler._request(request.operation());
   		if (request.result() != null) {
   			org.omg.CORBA.Any $ami_return_val = replyHandlerRequest.add_in_arg();
   			$ami_return_val.read_value(request.return_value().create_input_stream(), request.return_value().type());
   		}
   		try {
   			replyHandlerRequest.invoke();
   		} catch (SystemException e) {
   			String operationName = replyHandlerRequest.operation();
   			operationName = operationName.substring(1, operationName.length());
   			replyHandlerRequest.setOperationName(operationName);
   			replyHandlerRequest.invoke();
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
				forwarded_ior = ( ( IIOPCommunicationDelegate )forward_delegate ).getIIOPReference();
			} else { // write ior from anothers's ORB Object
				forwarded_ior = IIOPIOR.fromString(m_orb, m_orb.object_to_string( forward ) 
				);
			}

			//throw new es.tid.TIDorbj.core.comm.ForwardRequest( forwarded_ior );
		}	

    }
}
