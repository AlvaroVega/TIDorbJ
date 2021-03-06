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

import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.Object;
import org.omg.CORBA.ServerRequest;
import org.omg.CORBA.SystemException;

import es.tid.TIDorbj.core.ObjectKey;
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

public class LocalResponseHandler extends QueuedResponseHandler {


    private LocalServerRequest serverRequest;
    private ObjectKey objectKey;

    private OperationCompletion completionStatus;
    
    private Object forwardReference;
    private SystemException exception;

    private PolicyContext requestPolicyContext;

    public LocalResponseHandler( 
            ObjectKey objectKey, 
            LocalServerRequest serverRequest ){
        this.objectKey = objectKey;
        this.serverRequest = serverRequest;
        this.completionStatus = new OperationCompletion();
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
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#submitResponse(org.omg.CORBA.SystemException)
     */
    public void submitResponse( SystemException e ) {
        this.exception = e;
        this.serverRequest.setSystemException( e );
        this.completionStatus.setCompleted();
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#submitResponse()
     */
    public void submitResponse() {
        this.completionStatus.setCompleted();
    }

    /* 
     * remove from here
     */
    public void sendError( SystemException e, Trace trace ) {
        // TODO Auto-generated method stub

    }



}
