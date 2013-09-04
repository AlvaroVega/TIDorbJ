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

import es.tid.TIDorbj.core.cdr.CDR;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;
import es.tid.TIDorbj.core.comm.iiop.IIOPConnection;

/**
 * Represents the 1.1 and 1.2 GIOP FragmentRequest version messages.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class GIOPFragmentMessage extends GIOPMessage
{

    public static int FRAGMENT_HEADER_SIZE_1_1 = GIOPHeader.HEADER_SIZE;

    public static int FRAGMENT_HEADER_SIZE_1_2 = GIOPHeader.HEADER_SIZE
                                                 + CDR.ULONG_SIZE;

    private RequestId m_request_id;

    public GIOPFragmentMessage(GIOPVersion version, RequestId id)
    {
        super(new GIOPHeader(version, MsgType.Fragment));
        m_request_id = id;
    }

    public GIOPFragmentMessage(GIOPHeader header)
    {
        super(header);
    }

    public String toString()
    {
        return m_header.toString() + " (ID: " + m_request_id + ")";
    }

    public RequestId getRequestId()
    {
        return m_request_id;
    }

    public boolean hasBody()
    {
        return true;
    }

    //TODO: giop should not know anything about IIOPConnections!!
    public void receiveBody(IIOPConnection conn, byte[] header_bytes)
    {
        super.receiveBody(conn, header_bytes);

        if (m_header.getVersion().minor == 2) {
            CDRInputStream id_in = new CDRInputStream(null, m_message_buffer);
            id_in.setByteOrder(m_header.getByteOrder());
            id_in.skip(GIOPHeader.HEADER_SIZE);
            m_request_id = new RequestId(id_in.read_ulong());
            id_in = null;
        }
    }

    public static void writeHeader(es.tid.TIDorbj.core.cdr.CDROutputStream out,
                                   GIOPHeader header, RequestId request_id)
    {
        header.write(out);

        if (header.getVersion().minor == 2) {
            out.write_ulong(request_id.value());
            out.alignment(CDR.LONGLONG_SIZE); // force alingment to 8
        }
    }

    public void send(IIOPConnection conn)
    {
        throw new INTERNAL("Fragment message can not be send as itself");
    }

    public static void skipFragmentHeader1_2(CDRInputStream in)
    {
        //skip request_id (ulong)
        in.skipUlong();
        // force alingment to 8
        // in.alignment(CDR.LONGLONG_SIZE);

    }

    public static void skipFragmentHeader1_2(CDROutputStream out)
    {
        //skip request_id (ulong)
        out.skip(CDR.LONG_SIZE);
        // force alingment to 8
        // out.alignment(CDR.LONGLONG_SIZE);

    }

}