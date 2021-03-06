/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 2 $
* Date: $Date: 2005-12-19 08:58:21 +0100 (Mon, 19 Dec 2005) $
* Last modified by: $Author: caceres $
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

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.Object;
import org.omg.CORBA.ServerRequest;
import org.omg.CORBA.SystemException;

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.poa.QueuedLocateResponseHandler;
import es.tid.TIDorbj.core.policy.PolicyContext;
import es.tid.TIDorbj.core.util.OperationCompletion;
import es.tid.TIDorbj.util.Trace;

/**
 * @author jprojas
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LocalLocateResponseHandler extends QueuedLocateResponseHandler {

    private OperationCompletion completionStatus;
    
    private ObjectKey objectKey;
    
    //To store whether the object was found or not
    private boolean locationResult;

    private Object forwardReference;

    private SystemException exception;

    private PolicyContext requestPolicyContext;
    

    public LocalLocateResponseHandler( ObjectKey key, PolicyContext ctx){
        this.completionStatus = new OperationCompletion();
        this.objectKey = key;
        this.requestPolicyContext = ctx;
    }
    
    /**
     * OWN METHODS
     */
    public boolean getResult(){
        return this.locationResult;
    }
    
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
    
    
    /**
     * INHERITED METHODS
     */

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#getPolicyContext()
     */
    public PolicyContext getPolicyContext() {
        return this.requestPolicyContext;
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
        throw new NO_IMPLEMENT( 
            "LocateRequest doesn't have a peer ServerRequest",
            0,
            CompletionStatus.COMPLETED_NO
        );
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#submitResponse(org.omg.CORBA.Object)
     */
    public void submitResponse( Object reference ) {
        this.forwardReference = reference;
        this.completionStatus.setCompleted();
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#submitResponse(org.omg.CORBA.SystemException)
     */
    public void submitResponse( SystemException systemException ) {
        this.exception = systemException;
        this.completionStatus.setCompleted();
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.LocateResponseHandler#submitResponse(boolean)
     */
    public void submitResponse( boolean value ) {
        this.locationResult = value;
        this.completionStatus.setCompleted();
    }
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#submitResponse()
     */
    public void submitResponse() {
        throw new INTERNAL( "LocateRequest requires a return value" );
    }

    /*TODO: remove from here upon refactoring completion*/
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.poa.QueuedRequest#sendError(org.omg.CORBA.SystemException, es.tid.TIDorbj.util.Trace)
     */
    public void sendError( SystemException e, Trace trace ) {
        throw new NO_IMPLEMENT();
    }


    /**/
    
}
