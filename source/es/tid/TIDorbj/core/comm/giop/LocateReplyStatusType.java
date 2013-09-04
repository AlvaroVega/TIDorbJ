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

/**
 * LocateReplyStatusType enumeration defined in the GIOP Module.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */
public class LocateReplyStatusType
{

    public int m_value;

    private LocateReplyStatusType(int val)
    {
        m_value = val;
    }

    public int value()
    {
        return m_value;
    }

    public static LocateReplyStatusType from_int(int type)
    {
        switch (type)
        {
            case _UNKNOWN_OBJECT:
                return UNKNOWN_OBJECT;
            case _OBJECT_HERE:
                return OBJECT_HERE;
            case _OBJECT_FORWARD:
                return OBJECT_FORWARD;
            case _OBJECT_FORWARD_PERM:
                return OBJECT_FORWARD_PERM;
            case _LOC_SYSTEM_EXCEPTION:
                return LOC_SYSTEM_EXCEPTION;
            case _LOC_NEEDS_ADDRESSING_MODE:
                return LOC_NEEDS_ADDRESSING_MODE;
            default:
                return null;
        }
    }

    public String toString()
    {
        switch (m_value)
        {
            case _UNKNOWN_OBJECT:
                return "LocateReplyStatusType: UNKNOWN_OBJECT";
            case _OBJECT_HERE:
                return "LocateReplyStatusType: OBJECT_HERE";
            case _OBJECT_FORWARD:
                return "LocateReplyStatusType: OBJECT_FORWARD";
            case _OBJECT_FORWARD_PERM:
                return "LocateReplyStatusType: OBJECT_FORWARD_PERM";
            case _LOC_SYSTEM_EXCEPTION:
                return "LocateReplyStatusType: LOC_SYSTEM_EXCEPTION";
            case _LOC_NEEDS_ADDRESSING_MODE:
                return "LocateReplyStatusType: LOC_NEEDS_ADDRESSING_MODE";
            default:
                return "";
        }
    }

    public static final int _UNKNOWN_OBJECT = 0;

    public static final LocateReplyStatusType UNKNOWN_OBJECT =
        new LocateReplyStatusType(_UNKNOWN_OBJECT);

    public static final int _OBJECT_HERE = 1;

    public static final LocateReplyStatusType OBJECT_HERE = 
        new LocateReplyStatusType(_OBJECT_HERE);

    public static final int _OBJECT_FORWARD = 2;

    public static final LocateReplyStatusType OBJECT_FORWARD = 
        new LocateReplyStatusType(_OBJECT_FORWARD);

    public static final int _OBJECT_FORWARD_PERM = 3;

    public static final LocateReplyStatusType OBJECT_FORWARD_PERM =
        new LocateReplyStatusType(_OBJECT_FORWARD_PERM);

    public static final int _LOC_SYSTEM_EXCEPTION = 4;

    public static final LocateReplyStatusType LOC_SYSTEM_EXCEPTION = 
        new LocateReplyStatusType(_LOC_SYSTEM_EXCEPTION);

    public static final int _LOC_NEEDS_ADDRESSING_MODE = 5;

    public static final LocateReplyStatusType LOC_NEEDS_ADDRESSING_MODE =
        new LocateReplyStatusType(_LOC_NEEDS_ADDRESSING_MODE);

    public AddressingDisposition 
    	extractAddressingDisposition(GIOPLocateReplyMessage reply_message)
    {
        if (value() != LocateReplyStatusType._LOC_NEEDS_ADDRESSING_MODE)
            throw new 
                INTERNAL("Unexpected extract_arguments, NEEDS_ADDRESSING_MODE "
                         + "is not the reply status");

        short value = reply_message.m_message_buffer_in.read_ushort();
        AddressingDisposition disposition = 
            AddressingDisposition.from_short(value);
        if (disposition == null)
            throw new MARSHAL("Invalid AddressingDisposition value.");
        return disposition;
    }

}