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

/**
 * Factory to create GIOP Messages taking a GIOP Header.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class MessageFactory
{

    public static GIOPMessage fromHeader(GIOPHeader header)
    {
        switch (header.getMsgType().m_value)
        {
            case MsgType._Request:
                return new GIOPRequestMessage(header);
            case MsgType._Reply:
                return new GIOPReplyMessage(header);
            case MsgType._CancelRequest:
                return new GIOPCancelRequestMessage(header);
            case MsgType._LocateRequest:
                return new GIOPLocateRequestMessage(header);
            case MsgType._LocateReply:
                return new GIOPLocateReplyMessage(header);
            case MsgType._CloseConnection:
                return new GIOPMessage(header);
            case MsgType._MessageError:
                return new GIOPMessage(header);
            case MsgType._Fragment:
                return new GIOPFragmentMessage(header);
            default:
                return null; // unreachable
        }
    }
}