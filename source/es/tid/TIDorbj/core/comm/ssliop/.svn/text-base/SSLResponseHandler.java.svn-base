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
package es.tid.TIDorbj.core.comm.ssliop;


import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.Object;
import org.omg.CORBA.ServerRequest;
import org.omg.CORBA.SystemException;

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.ServerRequestImpl;
import es.tid.TIDorbj.core.comm.ResponseHandler;
import es.tid.TIDorbj.core.comm.giop.GIOPVersion;
import es.tid.TIDorbj.core.poa.QueuedResponseHandler;
import es.tid.TIDorbj.core.policy.PolicyContext;
import es.tid.TIDorbj.util.Trace;


/**
 * ResponseHandler implementation for the IIOPCommunicationLayer
 * 
 * @author jprojas
 *
 */
public class SSLResponseHandler extends QueuedResponseHandler implements ResponseHandler {

    private SSLConnection conn;
    private GIOPVersion version;
    private ServerRequestImpl serverRequest;
    private ObjectKey objectKey;
    private PolicyContext policySupplier;
    
    public SSLResponseHandler(  SSLConnection conn, 
                                GIOPVersion version, 
                                ServerRequestImpl 
                                serverRequest, 
                                ObjectKey objectKey,
                                PolicyContext policySupplier){
        setConnection( conn );
        setVersion( version );
        setServerRequest( serverRequest );
        setObjectKey( objectKey );
        setPolicyContext( policySupplier );
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
    
    public void setServerRequest( ServerRequestImpl serverRequest ){
        this.serverRequest = serverRequest;
    }
    
    public void setObjectKey( ObjectKey objectKey ){
        this.objectKey = objectKey;
    }
    
    /**
     * @param policySupplier The policySupplier to set.
     */
    public void setPolicyContext(PolicyContext policySupplier)
    {
        this.policySupplier = policySupplier;
    }
    /*
     *  (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#getPolicyContext()
     */
    public PolicyContext getPolicyContext(){
        return this.policySupplier;
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
        if ( this.conn != null ){
            if ( this.serverRequest.withResponse() ) {
                this.serverRequest.setForward( reference );
                this.conn.sendReply( this.serverRequest, null );
            }
        } else {
            throw new INTERNAL( "No IIOPConnection configured!" );
        }
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#submitResponse(org.omg.CORBA.SystemException)
     */
    public void submitResponse( SystemException e ) {
        if ( this.conn != null ){
            if( this.serverRequest.withResponse() ) {
                this.serverRequest.setSystemException( e );
                this.conn.sendReply( this.serverRequest, null );
            }
        } else {
            throw new INTERNAL( "No IIOPConnection configured!" );
        }
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.ResponseHandler#submitResponse()
     */
    public void submitResponse() {
        if ( this.conn != null ){
            if ( this.serverRequest.withResponse() ) {
                this.conn.sendReply( this.serverRequest, this.policySupplier );
            }
        } else {
            throw new INTERNAL( "No IIOPConnection configured!" );
        }
    }

    /**
     * remove from here
     * @author jprojas
     */
    public void sendError( SystemException e, Trace trace ) {
        throw new NO_IMPLEMENT();
        
    }

}
