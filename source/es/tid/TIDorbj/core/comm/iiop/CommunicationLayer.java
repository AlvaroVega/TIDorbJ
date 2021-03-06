/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 8 $
* Date: $Date: 2006-01-24 17:36:46 +0100 (Tue, 24 Jan 2006) $
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
package es.tid.TIDorbj.core.comm.iiop;

import org.omg.CORBA.Object;

import es.tid.TIDorbj.core.comm.ForwardRequest;

/**
 * Base class for the ORB communications layers.
 * 
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public abstract class CommunicationLayer extends es.tid.TIDorbj.core.ORBComponent
{
    public CommunicationLayer(es.tid.TIDorbj.core.TIDORB orb)
    {
        super(orb);
    }

    /**
     * Sends a request.
     * 
     * @param request
     *            the CORBA request.
     */
    public abstract void request(
            es.tid.TIDorbj.core.RequestImpl request,
            IIOPIOR ior ) 
        throws ForwardRequest;

    /**
     * Sends a oneway request.
     * 
     * @param request
     *            the CORBA request.
     */
    public abstract void onewayRequest(
            es.tid.TIDorbj.core.RequestImpl request,
            IIOPIOR ior);
    
    /**
     * Sends a asynchronous request.
     * 
     * @param request
     *            the CORBA request.
     */
    public abstract void asyncRequest(
            es.tid.TIDorbj.core.RequestImpl request,
            IIOPIOR ior /* *opc1* , Object ami_handler*/) throws ForwardRequest;

    /**
     * Sends a object existence request.
     * 
     * @param ior
     *            the object IOR.
     *  
     */
    public abstract boolean 
    objectExists(
            IIOPIOR ior,
            es.tid.TIDorbj.core.policy.PolicyContext policy_context )
        throws ForwardRequest;

    /**
     * Prepare a stream based request.
     * 
     * @param request
     *            the CORBA request.
     */
    public abstract void prepareRequest(
            es.tid.TIDorbj.core.StreamRequestImpl request );

    public abstract org.omg.CORBA.portable.InputStream request(
            IIOPIOR ior,
    	    es.tid.TIDorbj.core.cdr.CDROutputStream stream,
    	    es.tid.TIDorbj.core.policy.PolicyContext policy_context )
        throws ForwardRequest,
               org.omg.CORBA.portable.ApplicationException,
               org.omg.CORBA.portable.RemarshalException;
}