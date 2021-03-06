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
 * MsgType structure defined in the GIOP Module.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */
public class MsgType
{

    public int m_value;

    private MsgType(int val)
    {
        m_value = val;
    }

    public static MsgType from_int(int type)
    {
        switch (type)
        {
            case _Request:
                return Request;
            case _Reply:
                return Reply;
            case _CancelRequest:
                return CancelRequest;
            case _LocateRequest:
                return LocateRequest;
            case _LocateReply:
                return LocateReply;
            case _CloseConnection:
                return CloseConnection;
            case _MessageError:
                return MessageError;
            case _Fragment:
                return Fragment;
            default:
                return null;
        }
    }

    public static String msgName(int type)
    {
        switch (type)
        {
            case _Request:
                return "Request";
            case _Reply:
                return "Reply";
            case _CancelRequest:
                return "CancelRequest";
            case _LocateRequest:
                return "LocateRequest";
            case _LocateReply:
                return "LocateReply";
            case _CloseConnection:
                return "CloseConnection";
            case _MessageError:
                return "MessageError";
            case _Fragment:
                return "Fragment";
            default:
                return null;
        }
    }

    public static final int _Request = 0;

    public static final MsgType Request = new MsgType(_Request);

    public static final int _Reply = 1;

    public static final MsgType Reply = new MsgType(_Reply);

    public static final int _CancelRequest = 2;

    public static final MsgType CancelRequest = new MsgType(_CancelRequest);

    public static final int _LocateRequest = 3;

    public static final MsgType LocateRequest = new MsgType(_LocateRequest);

    public static final int _LocateReply = 4;

    public static final MsgType LocateReply = new MsgType(_LocateReply);

    public static final int _CloseConnection = 5;

    public static final MsgType CloseConnection = new MsgType(_CloseConnection);

    public static final int _MessageError = 6;

    public static final MsgType MessageError = new MsgType(_MessageError);

    public static final int _Fragment = 7;

    public static final MsgType Fragment = new MsgType(_Fragment);

}