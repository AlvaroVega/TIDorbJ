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
package es.tid.TIDorbj.core.comm.giop;

import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.MARSHAL;

import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.comm.Connection;
import es.tid.TIDorbj.core.iop.DefaultIOR;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.util.exception.SystemExceptionEncoder;

/**
 * Represents the 1.0, 1.1 and 1.2 GIOP LocateReply version messages.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class GIOPLocateReplyMessage extends GIOPFragmentedMessage
{

    LocateReplyStatusType m_reply_status;

    public GIOPLocateReplyMessage(GIOPHeader header)
    {
        super(header);
        m_reply_status = null;
    }

    public GIOPLocateReplyMessage(GIOPVersion version, RequestId id, int fragmentSize)
    {
        super(new GIOPHeader(version, MsgType.LocateReply), id, fragmentSize );
        m_reply_status = null;
    }

    public String toString()
    {
        return super.toString() + " (" + m_reply_status.toString() + ")";
    }

    private void reset()
    {
        m_reply_status = null;
        m_message_completed = false;
        m_headers_marshaled = false;
    }

    public LocateReplyStatusType replyStatus()
    {
        return m_reply_status;
    }

    public void insertUnknownObject(TIDORB orb)
    {
        reset();
        m_reply_status = LocateReplyStatusType.UNKNOWN_OBJECT;

        createMessageBufferOutput(orb);

        m_message_completed = true;
    }

    public void insertObjectHere(TIDORB orb)
    {
        reset();
        m_reply_status = LocateReplyStatusType.OBJECT_HERE;

        createMessageBufferOutput(orb);

        m_message_completed = true;
    }

    public void insertSystemException(TIDORB orb,
                                      org.omg.CORBA.SystemException exception)
    {
        reset();
        m_reply_status = LocateReplyStatusType.LOC_SYSTEM_EXCEPTION;
        SystemExceptionEncoder.write(m_message_buffer_out, exception);

        createMessageBufferOutput(orb);

        m_message_completed = true;
    }

    public org.omg.CORBA.SystemException extractSystemException()
    {
        if (m_reply_status.value() 
            != LocateReplyStatusType._LOC_SYSTEM_EXCEPTION)
            throw new INTERNAL("Unexpected extract_arguments, "
                               + "SYSTEM_EXCEPTION is not the reply status");

        return SystemExceptionEncoder.read(m_message_buffer_in);
    }

    public void insertForward(TIDORB orb, org.omg.CORBA.Object obj)
    {
        reset();

        m_reply_status = LocateReplyStatusType.OBJECT_FORWARD;

        createMessageBufferOutput(orb);

        m_message_buffer_out.write_Object(obj);

        m_message_completed = true;
    }

    public IOR extractForward()
    {
        if (m_reply_status.value() != LocateReplyStatusType._OBJECT_FORWARD)
            throw new INTERNAL("Unexpected extract_arguments, "
                               + "OBJECT_FORWARD is not the reply status");

        IOR forward_ior = new DefaultIOR();
        forward_ior.read(m_message_buffer_in);
        return forward_ior;
    }

    public void insertForwardPerm(TIDORB orb, org.omg.CORBA.Object obj)
    {
        reset();
        m_reply_status = LocateReplyStatusType.OBJECT_FORWARD_PERM;

        createMessageBufferOutput(orb);

        m_message_buffer_out.write_Object(obj);

        m_message_completed = true;

    }

    public IOR extractForwardPerm()
    {
        if (m_reply_status.value() 
            != LocateReplyStatusType._OBJECT_FORWARD_PERM)
            throw new INTERNAL("Unexpected extract_arguments, " 
                               + "OBJECT_FORWARD_PERM is not the reply status");

        IOR forward_ior = new DefaultIOR();
        forward_ior.read(m_message_buffer_in);
        return forward_ior;
    }

    public void insertNeedsAddressingMode(TIDORB orb,
                                          AddressingDisposition disposition)
    {
        reset();

        m_reply_status = LocateReplyStatusType.LOC_NEEDS_ADDRESSING_MODE;

        createMessageBufferOutput(orb);

        m_message_buffer_out.write_ushort(disposition.value());

        m_message_completed = true;
    }

    public AddressingDisposition extractAddressingDisposition()
    {
        return m_reply_status.extractAddressingDisposition(this);
    }

    public void createMessageBufferOutput(es.tid.TIDorbj.core.TIDORB orb)
    {

        if (m_reply_status == null)
            throw new INTERNAL("Unitialized request");

        super.createMessageBufferOutput(orb);

        // header
        // request_id marshaled with father create_message_buffer_output() in
        // version 1.2

        if (!m_header.getVersion().equal(GIOPVersion.VERSION_1_2)) {
            m_message_buffer_out.write_ulong(m_request_id.value());
        }

        // status
        m_message_buffer_out.write_ulong(m_reply_status.m_value);

    }

    public void receiveBody(Connection conn, byte[] header_bytes)
    {
        super.receiveBody(conn, header_bytes);

        // request_id has been readen in father's method in version 1.2
        if (!m_header.getVersion().equal(GIOPVersion.VERSION_1_2)) {
            m_request_id = new RequestId(m_message_buffer_in.read_ulong());
        }

        // reply_status

        m_reply_status = 
            LocateReplyStatusType.from_int(m_message_buffer_in.read_ulong());

        if (m_reply_status == null)
            throw new MARSHAL("Invalid LocateReplyStatus value.");
    }

}
