/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 395 $
* Date: $Date: 2009-05-27 16:10:32 +0200 (Wed, 27 May 2009) $
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
package es.tid.TIDorbj.core.comm.giop;

import org.omg.CORBA.INTERNAL;
import org.omg.IOP.INVOCATION_POLICIES;

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.RequestImpl;
import es.tid.TIDorbj.core.ServerRequestImpl;
import es.tid.TIDorbj.core.StreamRequestImpl;
import es.tid.TIDorbj.core.cdr.CDR;
import es.tid.TIDorbj.core.comm.iiop.TargetAddress;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.policy.PolicyContext;


/**
 * Represents the 1.0, 1.1 and 1.2 GIOP Request version messages.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class GIOPRequestMessage extends GIOPFragmentedMessage
{

    ServiceContextList m_service_context_list;

    public GIOPRequestMessage(GIOPHeader header)
    {
        super(header);
        m_service_context_list = null;
    }

    public GIOPRequestMessage(GIOPVersion ver)
    {
        super(new GIOPHeader(ver, MsgType.Request));
        m_service_context_list = null;
    }

    public GIOPRequestMessage(GIOPVersion ver, RequestId id, int fragmentSize )
    {
        super(new GIOPHeader(ver, MsgType.Request), id, fragmentSize );
        m_service_context_list = null;
    }

    public void setServiceContextList(ServiceContextList list)
    {
        m_service_context_list = list;
    }

    public ServiceContextList getServiceContextList()
    {
        return m_service_context_list;
    }

    public ServerRequestImpl extractRequest(es.tid.TIDorbj.core.TIDORB orb)
    {
        if (!m_message_completed)
            throw new INTERNAL("Uncompleted message: no message_buffer.");

        this.createMessageBufferInput(orb);

        ServerRequestImpl request = null;
        switch (m_header.getVersion().getMinor())
        {
            case 0:
                return extractRequest1_0();                
            case 1:
                return extractRequest1_1();                
            case 2:
                return extractRequest1_2();                
        	default:
        	    return null;                    
        }              
      
    }

    /**
     * Get the Request Invocation policies sended by the Client. See QoS. 
     * @return 
     */
    public PolicyContext getRequestInvocationPolicies()
    {
        if(this.m_service_context_list != null) {
            for(int i = 0; i < this.m_service_context_list.m_components.length; i++) {
                if(m_service_context_list.m_components[i].m_context_id 
                    == INVOCATION_POLICIES.value ){
                    InvocationPoliciesContext context =  (InvocationPoliciesContext) 
                        m_service_context_list.m_components[i];
                    
                    return context.getPolicies();                    
                }
                
            }
        }
        return null;
    }

    public void insertRequest(RequestImpl request, IOR ior,
                              AddressingDisposition disposition)
    {
        m_request_id = request.getId();

        super.createMessageBufferOutput(request.orb());

        switch (m_header.getVersion().getMinor())
        {
            case 0:
                insertRequestHeader1_0(request, ior);
            break;
            case 1:
                insertRequestHeader1_1(request, ior);
            break;
            case 2:
                insertRequestHeader1_2(request, ior, disposition);
            break;
        }

        // in parmeters

        request.writeInParams(m_message_buffer_out);

        // context

        m_message_buffer_out.write_Context(request.ctx(), request.contexts());

        m_message_completed = true;
    }

    public void prepareRequest(es.tid.TIDorbj.core.StreamRequestImpl request,
                               IOR ior, AddressingDisposition disposition)
    {
        m_request_id = request.getId();

        super.createMessageBufferOutput(request.orb());

        switch (m_header.getVersion().getMinor())
        {
            case 0:
                insertRequestHeader1_0(request, ior);
            break;
            case 1:
                insertRequestHeader1_1(request, ior);
            break;
            case 2:
                insertRequestHeader1_2(request, ior, disposition);
            break;
        }

        m_message_completed = true;

        // make insert the message header

        this.getMessageBuffer();

        request.setOutputStream(m_message_buffer_out);

        m_message_buffer_out = null;

    }

    /**
     * Marshals the request header in GIOP 1.0 version.
     */

    protected void insertRequestHeader1_0(RequestImpl request, IOR ior)
    {
        // service context

        ServiceContextList.write(m_service_context_list, m_message_buffer_out);

        // request_id

        m_message_buffer_out.write_ulong(m_request_id.value());

        // with response ?

        m_message_buffer_out.write_boolean(request.withResponse());

        // object key

        ior.getObjectKey().write(m_message_buffer_out);

        // operation name
        m_message_buffer_out.write_string(request.operation());

        // principal: not implemented

        m_message_buffer_out.write_string("");
    }

    /**
     * Marshals the request header in GIOP 1.1 version.
     */

    protected void insertRequestHeader1_1(RequestImpl request, IOR ior)
    {
        // service context

        ServiceContextList.write(m_service_context_list, m_message_buffer_out);

        // request_id

        m_message_buffer_out.write_ulong(m_request_id.value());

        // with response ?

        m_message_buffer_out.write_boolean(request.withResponse());

        // reserved[3]
        m_message_buffer_out.write_octet((byte) 0);
        m_message_buffer_out.write_octet((byte) 0);
        m_message_buffer_out.write_octet((byte) 0);

        // object key

        ior.getObjectKey().write(m_message_buffer_out);

        // operation name

        m_message_buffer_out.write_string(request.operation());

        // principal: not implemented

        m_message_buffer_out.write_string("");

    }

    protected void insertRequestHeader1_2(RequestImpl request, IOR ior,
                                          AddressingDisposition disposition)
    {

        // request_id marshaled in father create_message_buffer_out

        // message_buffer_out.write_ulong(request.get_id().value());

        // with response ?

        byte response_octet;

        if (request.withResponse() || request.reliableOneway())
            response_octet = (byte) 0x03; // Fix to bug #545 (changed from 0x01)
        else
            response_octet = (byte) 0x00;

        m_message_buffer_out.write_octet(response_octet);

        // reserved[3]
        m_message_buffer_out.write_octet((byte) 0);
        m_message_buffer_out.write_octet((byte) 0);
        m_message_buffer_out.write_octet((byte) 0);

        // target object

        switch (disposition.value())
        {
            //TODO: IIOPIOR... buff...
            case AddressingDisposition._KeyAddr:
                ior.toObjectKeyAddress().write(m_message_buffer_out);
            break;
            case AddressingDisposition._ProfileAddr:
                ior.toProfileAddress().write(m_message_buffer_out);
            break;
            case AddressingDisposition._ReferenceAddr:
                ior.toIORAddress().write(m_message_buffer_out);
        }

        // operation name

        m_message_buffer_out.write_string(request.operation());

        // service context list

        ServiceContextList.write(m_service_context_list, m_message_buffer_out);

        // parameters aligment for GIOPVersion 1.2

        m_message_buffer_out.fixNextAlignedPosition(CDR.LONGLONG_SIZE);

    }

    /**
     * Marshals the request header in GIOP 1.0 version.
     */

    protected void insertRequestHeader1_0(StreamRequestImpl request, IOR ior)
    {
        // service context

        ServiceContextList.write(m_service_context_list, m_message_buffer_out);

        // request_id

        m_message_buffer_out.write_ulong(m_request_id.value());

        // with response ?

        m_message_buffer_out.write_boolean(request.withResponse());

        // object key

        ior.getObjectKey().write(m_message_buffer_out);

        // operation name
        m_message_buffer_out.write_string(request.operation());

        // principal: not implemented

        m_message_buffer_out.write_string("");
    }

    /**
     * Marshals the request header in GIOP 1.1 version.
     */

    protected void insertRequestHeader1_1(StreamRequestImpl request, IOR ior)
    {
        // service context

        ServiceContextList.write(m_service_context_list, m_message_buffer_out);

        // request_id

        m_message_buffer_out.write_ulong(m_request_id.value());

        // with response ?

        m_message_buffer_out.write_boolean(request.withResponse());

        // reserved[3]
        m_message_buffer_out.write_octet((byte) 0);
        m_message_buffer_out.write_octet((byte) 0);
        m_message_buffer_out.write_octet((byte) 0);

        // object key

        ior.getObjectKey().write(m_message_buffer_out);

        // operation name

        m_message_buffer_out.write_string(request.operation());

        // principal: not implemented

        m_message_buffer_out.write_string("");

    }

    protected void insertRequestHeader1_2(StreamRequestImpl request, IOR ior,
                                          AddressingDisposition disposition)
    {

        // request_id marshaled in father create_message_buffer_out

        // message_buffer_out.write_ulong(request.get_id().value());

        // with response ?

        byte response_octet;

        if (request.withResponse())
            response_octet = (byte) 0x03; // Fix to bug #545 (changed from 0x01)
        else
            response_octet = (byte) 0x00;

        m_message_buffer_out.write_octet(response_octet);

        // reserved[3]
        m_message_buffer_out.write_octet((byte) 0);
        m_message_buffer_out.write_octet((byte) 0);
        m_message_buffer_out.write_octet((byte) 0);

        // target object

        switch (disposition.value())
        {
            case AddressingDisposition._KeyAddr:
                ior.toObjectKeyAddress().write(m_message_buffer_out);
            break;
            case AddressingDisposition._ProfileAddr:
                ior.toProfileAddress().write(m_message_buffer_out);
            break;
            case AddressingDisposition._ReferenceAddr:
                ior.toIORAddress().write(m_message_buffer_out);
        }

        // operation name

        m_message_buffer_out.write_string(request.operation());

        // service context list

        ServiceContextList.write(m_service_context_list, m_message_buffer_out);

        // parameters aligment for GIOPVersion 1.2

        m_message_buffer_out.fixNextAlignedPosition(CDR.LONGLONG_SIZE);

    }

    /**
     * Unmarshals the request header in GIOP 1.0 version.
     */

    protected ServerRequestImpl extractRequest1_0()
    {
        // service_context_list

        m_service_context_list = ServiceContextList.read(m_message_buffer_in);

        // request_id

        m_request_id = new RequestId(m_message_buffer_in.read_ulong());

        // with response ?

        boolean with_response = m_message_buffer_in.read_boolean();

        // object key

        TargetAddress target = new TargetAddress();

        ObjectKey key = new ObjectKey();

        key.read(m_message_buffer_in);

        target.setObjectKey(key);

        // operation name

        String operation = m_message_buffer_in.read_string();

//      principal: not implemented

        int principal_length = m_message_buffer_in.read_ulong();
        
        if(principal_length > 0) {
            m_message_buffer_in.skip(principal_length);
        }

        m_message_buffer_in.fixStarting();

        ServerRequestImpl request = new ServerRequestImpl(GIOPVersion.VERSION_1_0,
                                                          m_request_id,
                                                          operation,
                                                          m_message_buffer_in,
                                                          target);

        request.withResponse(with_response);

        return request;
    }

    /**
     * Unmarshals the request header in GIOP 1.1 version.
     */

    protected ServerRequestImpl extractRequest1_1()
    {

        // service_context_list

        m_service_context_list = ServiceContextList.read(m_message_buffer_in);

        // request_id

        m_request_id = new RequestId(m_message_buffer_in.read_ulong());

        // with response ?

        boolean with_response = m_message_buffer_in.read_boolean();

        // reserved octets

        m_message_buffer_in.skipOctetArray(3);

        // object key

        TargetAddress target = new TargetAddress();

        ObjectKey key = new ObjectKey();

        key.read(m_message_buffer_in);

        target.setObjectKey(key);

        // operation name

        String operation = m_message_buffer_in.read_string();

        // principal: not implemented

        int principal_length = m_message_buffer_in.read_ulong();
        
        if(principal_length > 0) {
            m_message_buffer_in.skip(principal_length);
        }
        

        m_message_buffer_in.fixStarting();

        ServerRequestImpl request = new ServerRequestImpl(GIOPVersion.VERSION_1_1,
                                                          m_request_id,
                                                          operation,
                                                          m_message_buffer_in,
                                                          target);

        request.withResponse(with_response);

        return request;
    }

    /**
     * Unmarshals the request in GIOP 1.2 version.
     */

    protected ServerRequestImpl extractRequest1_2()
    {
        // request_id extracted in father receive_message_buffer

        // request_id = new RequestId(message_buffer_in.read_ulong());

        // with response ?

        byte response_octet = m_message_buffer_in.read_octet();

        boolean with_response = (response_octet & 0x01) != 0;

        // reserved octets

        m_message_buffer_in.skipOctetArray(3);

        // object key

        TargetAddress target = new TargetAddress();

        target.read(m_message_buffer_in);

        // operation name

        String operation = m_message_buffer_in.read_string();

        // service_context_list

        m_service_context_list = ServiceContextList.read(m_message_buffer_in);

        try {
            m_message_buffer_in.goNextAlignedPosition(CDR.LONGLONG_SIZE);
        }
        catch (org.omg.CORBA.MARSHAL M) {}

        m_message_buffer_in.fixStarting();

        ServerRequestImpl request = new ServerRequestImpl(GIOPVersion.VERSION_1_2,
                                                          m_request_id,
                                                          operation,
                                                          m_message_buffer_in,
                                                          target); 
        request.set_compressor(this.get_compressor());
        request.withResponse(with_response);

        return request;
    }

}
