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
package es.tid.TIDorbj.core.comm.ssliop;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.Object;
import org.omg.CORBA.ServerRequest;
import org.omg.CORBA.SystemException;

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.comm.LocateResponseHandler;
import es.tid.TIDorbj.core.comm.giop.GIOPVersion;
import es.tid.TIDorbj.core.comm.giop.RequestId;
import es.tid.TIDorbj.core.poa.QueuedLocateResponseHandler;
import es.tid.TIDorbj.core.policy.PolicyContext;
import es.tid.TIDorbj.util.Trace;

/**
 * @author jprojas
 *
 */
public class SSLLocateResponseHandler extends QueuedLocateResponseHandler implements LocateResponseHandler {

    private SSLConnection conn;
    private GIOPVersion version;
    private RequestId requestId;
    private ObjectKey objectKey;
  
    
    public SSLLocateResponseHandler( SSLConnection conn, 
                                        GIOPVersion version, 
                                        RequestId requestId, 
                                        ObjectKey objectKey)
    {
        setConnection( conn );
        setVersion   ( version );
        setRequestId ( requestId );
        setObjectKey ( objectKey );      
    }
    
    public void setConnection( SSLConnection conn ){
        this.conn = conn;
    }
    
    public SSLConnection getConnection(){
        return this.conn;
    }
    
    public void setVersion( GIOPVersion version ){
        this.version = version;
    }
    
    public GIOPVersion getVersion(){
        return this.version;
    }
    
    public void setRequestId( RequestId requestId ){
        this.requestId = requestId;
    }
    
    public RequestId getRequestId(){
        return this.requestId;
    }
    
    public void setObjectKey( ObjectKey objectKey ){
        this.objectKey = objectKey;
    }
    
    
    /*
     *  (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#getPolicyContext()
     */
    public PolicyContext getPolicyContext(){
        throw new INTERNAL("Not supported");
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
        if ( this.conn != null ){
            this.conn.sendLocateReply( this.version, this.requestId, reference );
        } else {
            throw new INTERNAL( "No IIOPConnection configured" );
        }
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#submitResponse(org.omg.CORBA.SystemException)
     */
    public void submitResponse( SystemException e ) {
        if ( this.conn != null ){
            this.conn.sendLocateReply( this.version, this.requestId, e );
        } else {
            throw new INTERNAL( "No IIOPConnection configured" );
        }
        
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.LocateResponseHandler#setResponseValue(boolean)
     */
    public void submitResponse( boolean value ) {
       if ( this.conn != null ){
           this.conn.sendLocateReply( this.version, this.requestId, value );
       } else {
           throw new INTERNAL( "No IIOPConnection configured" );
       }
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#submitResponse()
     */
    public void submitResponse() {
        throw new INTERNAL( "LocateRequest requires a return value" );
    }

    /* 
     * remove from here
     */
    public void sendError( SystemException e, Trace trace ) {
        throw new NO_IMPLEMENT();
        
    }

}
