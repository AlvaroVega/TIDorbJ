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
package es.tid.TIDorbj.core.comm.giop;

import org.omg.CORBA.Any;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NVList;
import org.omg.Messaging._ExceptionHolder;

import es.tid.TIDorbj.core.NVListImpl;
import es.tid.TIDorbj.core.RequestImpl;
import es.tid.TIDorbj.core.ServerRequestImpl;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.cdr.CDR;
import es.tid.TIDorbj.core.comm.Connection;
import es.tid.TIDorbj.core.iop.DefaultIOR;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.util.exception.SystemExceptionEncoder;

/**
 * Represents the 1.0, 1.1 and 1.2 GIOP Reply version messages.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class GIOPReplyMessage extends GIOPFragmentedMessage
{

    ReplyStatusType m_reply_status;

    ServiceContextList m_service_context_list;

    public GIOPReplyMessage(GIOPVersion version)
    {
        super(new GIOPHeader(version, MsgType.Reply));
        m_reply_status = null;
    }

    public GIOPReplyMessage(GIOPHeader header)
    {
        super(header);
        m_reply_status = null;
        m_service_context_list = null;
    }

    public GIOPReplyMessage(GIOPVersion ver, RequestId id, int fragmentSize )
    {
        super(new GIOPHeader(ver, MsgType.Reply), id, fragmentSize );
        m_reply_status = null;
        m_service_context_list = null;
    }

    public String toString()
    {
        if (m_reply_status == null) 
            return super.toString() + " ( no reply status defined! )";
        else
            return super.toString() + " (" + m_reply_status.toString() + ")";
    }

    private void reset()
    {
        m_reply_status = null;
        m_message_completed = false;
        m_headers_marshaled = false;
        m_service_context_list = null;
    }

    public ReplyStatusType replyStatus()
    {
        return m_reply_status;
    }

    public void setServiceContextList(ServiceContextList list)
    {
        m_service_context_list = list;
    }

    public ServiceContextList getServiceContextList()
    {
        return m_service_context_list;
    }

    public void insertResultRequest(TIDORB orb, ServerRequestImpl request)
    {
        switch (request.getReturnState())
        {
            case ServerRequestImpl.NO_EXCEPTION:
                insertArguments(orb, request);
                return;
            case ServerRequestImpl.USER_EXCEPTION:
                insertUserException(orb, request.getException());
                return;
            case ServerRequestImpl.SYSTEM_EXCEPTION:
                insertSystemException(orb, request.getSystemException());
                return;
            case ServerRequestImpl.LOCATION_FORWARD:
                insertForward(orb, request.getForward());
                return;
        }
    }

    protected void insertArguments(TIDORB orb, ServerRequestImpl request)
    {
        reset();
        m_request_id = request.getId();
        m_reply_status = ReplyStatusType.NO_EXCEPTION;

        createMessageBufferOutput(orb);

        Any result = request.getResult();

        if (result != null)
            result.write_value(m_message_buffer_out);
        NVList list = request.getParameters();

        if (list != null)
            NVListImpl.writeOutParams(list, m_message_buffer_out);

        m_message_completed = true;
    }

    public void extractArguments(RequestImpl request)
    {
        if (m_reply_status.value() != ReplyStatusType._NO_EXCEPTION)
            throw new INTERNAL("Unexpected extract_arguments, NO_EXCEPTION "
                               +"is not the reply status");

        request.readResult(m_message_buffer_in);

        NVList list = request.arguments();

        if (list != null)
            NVListImpl.readOutParams(list, m_message_buffer_in);
    }

    protected void insertUserException(TIDORB orb, Any exception)
    {
        reset();
        m_reply_status = ReplyStatusType.USER_EXCEPTION;

        createMessageBufferOutput(orb);

        exception.write_value(m_message_buffer_out);

        m_message_completed = true;
    }

    public Any extractUserException(ExceptionList list)
    {
        if (m_reply_status.value() != ReplyStatusType._USER_EXCEPTION)
            throw new INTERNAL("Unexpected extract_arguments, USER_EXCEPTION " 
                               +"is not the reply status");

        // lectura adelantada del repository_id de la excepcion

        m_message_buffer_in.fixStarting();

        String name = m_message_buffer_in.read_string();

        m_message_buffer_in.rewind();

        // busqueda del typecode y lectura de sus componentes

        if (list != null) {

            int count = list.count();

            try {
                for (int i = 0; i < count; i++) {
                    if (name.equals(list.item(i).id())) {
                        Any any = m_message_buffer_in.orb().create_any();
                        any.read_value(m_message_buffer_in, list.item(i));
                        return any;
                    }
                }
            }
            catch (org.omg.CORBA.Bounds bds) {}
            catch (org.omg.CORBA.TypeCodePackage.BadKind bk) {}

        }

        throw new org.omg.CORBA.UNKNOWN("Unkown user exception");

    }

    protected void 
    	insertSystemException(TIDORB orb,
    	                      org.omg.CORBA.SystemException exception)
    {
        reset();
        m_reply_status = ReplyStatusType.SYSTEM_EXCEPTION;

        createMessageBufferOutput(orb);

        SystemExceptionEncoder.write(m_message_buffer_out, exception);

        m_message_completed = true;
    }

    public org.omg.CORBA.SystemException extractSystemException()
    {
        if (m_reply_status.value() != ReplyStatusType._SYSTEM_EXCEPTION)
            throw new INTERNAL("Unexpected extract_arguments, SYSTEM_EXCEPTION " 
                               +"is not the reply status");

        return SystemExceptionEncoder.read(m_message_buffer_in);
    }

    public void insertForward(TIDORB orb, org.omg.CORBA.Object obj)
    {
        reset();

        m_reply_status = ReplyStatusType.LOCATION_FORWARD;

        createMessageBufferOutput(orb);

        m_message_buffer_out.write_Object(obj);

        m_message_completed = true;
    }

    public IOR extractForward()
    {
        if (m_reply_status.value() != ReplyStatusType._LOCATION_FORWARD)
            throw new INTERNAL("Unexpected extract_arguments, LOCAL_FORWARD " 
                               + "is not the reply status");

        IOR ior = new DefaultIOR();
        ior.read(m_message_buffer_in);
        return ior;
    }

    public void inserForwardPerm(TIDORB orb, org.omg.CORBA.Object obj)
    {
        reset();
        m_reply_status = ReplyStatusType.LOCATION_FORWARD_PERM;

        createMessageBufferOutput(orb);

        m_message_buffer_out.write_Object(obj);

        m_message_completed = true;
    }

    public IOR extractForwardPerm()
    {
        if (m_reply_status.value() != ReplyStatusType._LOCATION_FORWARD_PERM)
            throw new 
                INTERNAL("Unexpected extract_arguments, LOCAL_FORWARD_PERM " 
                         + "is not the reply status");

        IOR ior = new DefaultIOR();
        ior.read(m_message_buffer_in);
        return ior;
    }

    public void insertNeedsAddressingMode(TIDORB orb,
                                          AddressingDisposition disposition)
    {
        reset();

        m_reply_status = ReplyStatusType.NEEDS_ADDRESSING_MODE;

        createMessageBufferOutput(orb);

        m_message_buffer_out.write_ushort(disposition.value());

        m_message_completed = true;
    }

    public es.tid.TIDorbj.core.cdr.CDRInputStream getDataInput()
    {
        return m_message_buffer_in.copy();
    }

    public AddressingDisposition extractAddressingDisposition()
    {
        if (m_reply_status.value() != ReplyStatusType._NEEDS_ADDRESSING_MODE)
            throw new 
                INTERNAL("Unexpected extract_arguments, NEEDS_ADDRESSING_MODE"
                         + "is not the reply status");

        short value = m_message_buffer_in.read_ushort();

        AddressingDisposition disposition = 
            AddressingDisposition.from_short(value);

        if (disposition == null)
            throw new MARSHAL("Invalid AddressingDisposition value.");

        return disposition;
    }

    public void createMessageBufferOutput(TIDORB orb)
    {
        super.createMessageBufferOutput(orb);

        switch (m_header.getVersion().getMinor())
        {
            case 0:
            case 1:
                marshalReplyHeader1_1();
            break;
            case 2:
                marshalReplyHeader1_2();
            break;
        }
    }

    /**
     * Marshals the reply header in GIOP 1.1 and 1.0 version.
     */

    protected void marshalReplyHeader1_1()
    {
        // Service Context

        ServiceContextList.write(m_service_context_list, m_message_buffer_out);

        // request_id

        m_message_buffer_out.write_ulong(m_request_id.value());

        // reply_status

        m_message_buffer_out.write_ulong(m_reply_status.value());

    }

    /**
     * Marshals the reply header in GIOP 1.2 version.
     */

    protected void marshalReplyHeader1_2()
    {

        // request_id marshaled with father create_body()

        // message_buffer_out.write_ulong(request_id);

        // reply_status

        m_message_buffer_out.write_ulong(m_reply_status.value());

        // Service Context

        ServiceContextList.write(m_service_context_list, m_message_buffer_out);

        // aligment to 8 in version 1.2

        m_message_buffer_out.fixNextAlignedPosition(CDR.LONGLONG_SIZE);
    }

    public void receiveBody(Connection conn, byte[] header_bytes)
    {
        super.receiveBody(conn, header_bytes);

        switch (m_header.getVersion().getMinor())
        {
            case 0:
            case 1:
                unmarshalReplyHeader1_1();
            break;
            case 2:
                unmarshalReplyHeader1_2();
            break;
        }

        m_message_buffer_in.fixStarting();

    }

    /**
     * Marshals the reply header in GIOP 1.1 version.
     */

    protected void unmarshalReplyHeader1_1()
    {
        // Service Context

        m_service_context_list = ServiceContextList.read(m_message_buffer_in);

        // request_id

        m_request_id = new RequestId(m_message_buffer_in.read_ulong());

        // reply_status

        m_reply_status =
            ReplyStatusType.from_int(m_message_buffer_in.read_ulong());

        if (m_reply_status == null)
            throw new MARSHAL("Invalid ReplyStatus value.");
        if (m_reply_status.value() > ReplyStatusType._LOCATION_FORWARD)
            throw new MARSHAL("GIOPVersion 1.0 or 1.1: Invalid ReplyStatus value.");

    }

    /**
     * Marshals the reply header in GIOP 1.2 version.
     */

    protected void unmarshalReplyHeader1_2()
    {
        // request_id unmarshaled in father's receive_body()

        // request_id = new RequestId(message_buffer_in.read_ulong());

        // reply_status

        m_reply_status = 
            ReplyStatusType.from_int(m_message_buffer_in.read_ulong());

        if (m_reply_status == null)
            throw new MARSHAL("Invalid ReplyStatus value.");

        // Service Context

        m_service_context_list = ServiceContextList.read(m_message_buffer_in);

        // VERSION 1.2 aligment to 8
        try {
            m_message_buffer_in.goNextAlignedPosition(CDR.LONGLONG_SIZE);
        }
        catch (org.omg.CORBA.MARSHAL M) {

        }

    }
    
    // AMI operations

    public void extractArgumentsForReplyHandler(RequestImpl request)
    {
        if (m_reply_status.value() != ReplyStatusType._NO_EXCEPTION)
            throw new INTERNAL("Unexpected extract_arguments, NO_EXCEPTION "
                               +"is not the reply status");

        request.readResult(m_message_buffer_in);

        NVList list = request.arguments();

        if (list != null)
            NVListImpl.readInParams(list, m_message_buffer_in);
    }

    public void extractUserException(es.tid.TIDorbj.core.messaging._ExceptionHolderImpl excepHolder)
    {
        if (m_reply_status.value() != ReplyStatusType._USER_EXCEPTION)
            throw new INTERNAL("Unexpected extract_arguments, USER_EXCEPTION " 
                               +"is not the reply status");
      
        excepHolder.read(m_message_buffer_in);

    }
    
    public void extractSystemException(_ExceptionHolder excepHolder)
    {
        if (m_reply_status.value() != ReplyStatusType._SYSTEM_EXCEPTION)
            throw new INTERNAL("Unexpected extract_arguments, SYSTEM_EXCEPTION " 
                               +"is not the reply status");

        excepHolder._read(m_message_buffer_in);
    }
    

}
