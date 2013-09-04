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
package es.tid.TIDorbj.core.comm;

import org.omg.CORBA.Object;
import org.omg.CORBA.ServerRequest;
import org.omg.CORBA.SystemException;

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.policy.PolicyContext;


/**
 * Base interface used to return the response upon Request arrival.
 * In this case, it provides methods for accesing the incoming requests field's
 * necessary for it's correct execution, and methods to communicate the 
 * invocation result to the origin.
 * 
 * Actually, a CommunicationLayer implementation must provide an instance of
 * type QueuedResponseHandler, which extends this interface. Upon 
 * refactoring proccess is completed, this should be done just implementing this
 * one... TODO: Maybe a delegation aproach should work
 * 
 * @author Juan Pablo Rojas
 */
public interface ResponseHandler {

    
    
    /*
     * Methods included for local request's optimization
     */
    public abstract PolicyContext getPolicyContext();
    
    /*
     * Response origin ( read only -- implementation dependent )
     **/  
    public abstract ObjectKey   getObjectKey();
    
    public abstract ServerRequest getServerRequest();
    
    
    /*
     * Related request invocation result
     * */
    
    public abstract void submitResponse( Object reference );
    
    public abstract void submitResponse( SystemException e );
    
    public abstract void submitResponse();
    
    
}
