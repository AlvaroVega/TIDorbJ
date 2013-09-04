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

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.comm.iiop.TargetAddress;

/**
 * Represents the 1.0, 1.1 and 1.2 GIOP LocateRequest version messages.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class GIOPLocateRequestMessage extends GIOPFragmentedMessage
{

    public GIOPLocateRequestMessage(GIOPHeader header)
    {
        super(header);
    }

    public GIOPLocateRequestMessage(GIOPVersion ver, RequestId id, int fragmentSize )
    {
        super(new GIOPHeader(ver, MsgType.LocateRequest), id, fragmentSize );
    }

    public TargetAddress extractAddress()
    {
        if (!m_message_completed)
            throw new INTERNAL("Uncompleted message.");

        TargetAddress target = new TargetAddress();

        if (m_header.getVersion().equal(GIOPVersion.VERSION_1_2)) {
            // request_id has been readed by father's receive_body

            target.read(m_message_buffer_in);
        } else {
            m_request_id = new RequestId(m_message_buffer_in.read_ulong());
            ObjectKey key = new ObjectKey();
            key.read(m_message_buffer_in);
            target.setObjectKey(key);
        }
        return target;
    }

    public void insertAddress(es.tid.TIDorbj.core.TIDORB orb, ObjectKey key)
    {
        createMessageBufferOutput(orb);

        if (m_header.getVersion().equal(GIOPVersion.VERSION_1_2)) {
            TargetAddress target = new TargetAddress();
            target.setObjectKey(key);
            target.write(m_message_buffer_out);
        } else {
            m_message_buffer_out.write_ulong(m_request_id.value());
            key.write(m_message_buffer_out);
        }

        m_message_completed = true;
    }

    public void insertAddress(es.tid.TIDorbj.core.TIDORB orb,
                              TargetAddress target)
    {
        createMessageBufferOutput(orb);

        if (m_header.getVersion().equal(GIOPVersion.VERSION_1_2)) {
            // request_id marshaled in create_message_buffer_output()
            target.write(m_message_buffer_out);
        } else {
            m_message_buffer_out.write_ulong(m_request_id.value());
            ObjectKey key = target.getObjectKey();

            if (key == null)
                throw new INTERNAL("Invalid Object Key");

            key.write(m_message_buffer_out);
        }

        m_message_completed = true;
    }

}