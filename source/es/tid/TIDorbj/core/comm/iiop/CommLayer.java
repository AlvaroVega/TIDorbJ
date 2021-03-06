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
package es.tid.TIDorbj.core.comm.iiop;

import org.omg.CORBA.Object;

import es.tid.TIDorbj.core.comm.local.LocalCommLayer;
import es.tid.TIDorbj.core.comm.ssliop.SSLIOPCommLayer;
import es.tid.TIDorbj.core.policy.PolicyContext;

/**
 * Communication Layer. It will manage requests, dispatching these to the local
 * or the internet communication layers.
 * 
 * <p>
 * Copyright 2000 Telef&oacute;nica I+D. Printed in Spain (Europe). All Rights
 * Reserved.
 * 
 * @see es.tid.TIDorbj.core.comm.local.LocalCommLayer
 * @see es.tid.TIDorbj.core.comm.iiopCommLayer
 * 
 * <p>
 * Copyright 2000 Telef&oacute;nica I+D. Printed in Spain (Europe). All Rights
 * Reserved.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

// TODO: move comm.iiop.CommLayer to comm.CommLayer

public class CommLayer extends CommunicationLayer
{
    /**
     * the internet layer for sending remote request
     */
    IIOPCommLayer m_external_layer;

    /**
     * the local layer for sending request to local objects.
     */
    es.tid.TIDorbj.core.comm.local.LocalCommLayer m_internal_layer;

    public CommLayer(es.tid.TIDorbj.core.TIDORB orb)
    {
        super(orb);

        if ( orb.m_conf.comm_layers.indexOf("es.tid.TIDorbj.core.comm.ssliop.SSLIOPCommunicationLayer" ) > -1 ) {
            m_external_layer = new SSLIOPCommLayer(orb);
        } else {
            m_external_layer = new IIOPCommLayer(orb);
        }

        m_internal_layer = new LocalCommLayer(orb);
    }

    /**
     * Sends a request.
     * 
     * @param request
     *            the CORBA request.
     */
    public void request(
            es.tid.TIDorbj.core.RequestImpl request,
            IIOPIOR ior)
        throws es.tid.TIDorbj.core.comm.ForwardRequest
    {
        // check policy context       
        
        try {

            if (isLocal(ior)) {
                // si es local
                m_internal_layer.request(request, ior);
            } else {
                m_external_layer.request(request, ior);
            }
        }
        catch (java.lang.OutOfMemoryError ome) {
            throw new org.omg.CORBA.NO_MEMORY();
        }
    }

    /**
     * Sends a oneway request.
     * 
     * @param request
     *            the CORBA request.
     */
    public void onewayRequest(
            es.tid.TIDorbj.core.RequestImpl request,
            IIOPIOR ior)
    {
        try {
            if (isLocal(ior)) {
                // si es local
                m_internal_layer.onewayRequest(request, ior);
            } else {
                m_external_layer.onewayRequest(request, ior);
            }
        }
        catch (java.lang.OutOfMemoryError ome) {
            throw new org.omg.CORBA.NO_MEMORY();
        }
    }

    // AMI callback operations
    
    /**
     * Sends a asynchronous request.
     * 
     * @param request
     *            the CORBA request.
     */
    public void asyncRequest(
            es.tid.TIDorbj.core.RequestImpl request,
            IIOPIOR ior) //*opc1*, Object ami_handler)
        throws es.tid.TIDorbj.core.comm.ForwardRequest
    {
        // check policy context       
        
        try {

            if (isLocal(ior)) {
                // si es local
                m_internal_layer.asyncRequest(request, ior); //*opc1*, ami_handler);
            } else {
                m_external_layer.asyncRequest(request, ior); //*opc1*, ami_handler);
            }
        }
        catch (java.lang.OutOfMemoryError ome) {
            throw new org.omg.CORBA.NO_MEMORY();
        }
    }
    
    /**
     * Sends a object existence request using the local or the IIOP
     * communication layers.
     * 
     * @param ior
     *            the object IOR.
     */

    public boolean objectExists( IIOPIOR ior,
                                PolicyContext policy_context)
        throws es.tid.TIDorbj.core.comm.ForwardRequest
    {     
         
        try {
            if (isLocal(ior)) {
                return m_internal_layer.objectExists(ior, policy_context);
            } else {
                return m_external_layer.objectExists(ior, policy_context);
            }
        }
        catch (java.lang.OutOfMemoryError ome) {
            throw new org.omg.CORBA.NO_MEMORY();
        }
    }

    /**
     * Test whether an ior is local to the ORB or not.
     * 
     * @param ior
     *            the object IOR.
     */
    public boolean isLocal( IIOPIOR ior)
    {
        return m_external_layer.isLocal(ior);
    }

    public void shutdown()
    {
        m_external_layer.shutdown();
    }

    public void destroy()
    {
        m_external_layer.destroy();
    }

    public IIOPCommLayer getExternalLayer()
    {
        return m_external_layer;
    }

    /**
     * Prepare a stream based request.
     * 
     * @param request
     *            the CORBA request.
     */
    public void prepareRequest(es.tid.TIDorbj.core.StreamRequestImpl request)
    {
        m_external_layer.prepareRequest(request);
    }

    public org.omg.CORBA.portable.InputStream 
    	request(IIOPIOR ior,
    	        es.tid.TIDorbj.core.cdr.CDROutputStream stream,
    	        PolicyContext policy_context)
        throws es.tid.TIDorbj.core.comm.ForwardRequest,
        org.omg.CORBA.portable.ApplicationException,
        org.omg.CORBA.portable.RemarshalException
    {
        return m_external_layer.request(ior, stream, policy_context);
    }
}