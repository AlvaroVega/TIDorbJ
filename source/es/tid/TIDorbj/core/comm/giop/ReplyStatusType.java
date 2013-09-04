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
 * ReplyStatusType class defined in the GIOP Module.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */
public class ReplyStatusType
{

    public int m_value;

    private ReplyStatusType(int val)
    {
        m_value = val;
    }

    public int value()
    {
        return m_value;
    }

    public static ReplyStatusType from_int(int type)
    {
        switch (type)
        {
            case _NO_EXCEPTION:
                return NO_EXCEPTION;
            case _USER_EXCEPTION:
                return USER_EXCEPTION;
            case _SYSTEM_EXCEPTION:
                return SYSTEM_EXCEPTION;
            case _LOCATION_FORWARD:
                return LOCATION_FORWARD;
            case _LOCATION_FORWARD_PERM:
                return LOCATION_FORWARD_PERM;
            case _NEEDS_ADDRESSING_MODE:
                return NEEDS_ADDRESSING_MODE;
            default:
                return null;
        }
    }

    public String toString()
    {
        switch (m_value)
        {
            case _NO_EXCEPTION:
                return "ReplyStatusType: NO_EXCEPTION";
            case _USER_EXCEPTION:
                return "ReplyStatusType:USER_EXCEPTION";
            case _SYSTEM_EXCEPTION:
                return "ReplyStatusType:SYSTEM_EXCEPTION";
            case _LOCATION_FORWARD:
                return "ReplyStatusType:LOCATION_FORWARD";
            case _LOCATION_FORWARD_PERM:
                return "ReplyStatusType:LOCATION_FORWARD_PERM";
            case _NEEDS_ADDRESSING_MODE:
                return "ReplyStatusType:NEEDS_ADDRESSING_MODE";
        }
        return "";
    }

    public static final int _NO_EXCEPTION = 0;

    public static final ReplyStatusType NO_EXCEPTION =
        new ReplyStatusType(_NO_EXCEPTION);

    public static final int _USER_EXCEPTION = 1;

    public static final ReplyStatusType USER_EXCEPTION =
        new ReplyStatusType(_USER_EXCEPTION);

    public static final int _SYSTEM_EXCEPTION = 2;

    public static final ReplyStatusType SYSTEM_EXCEPTION = 
        new ReplyStatusType(_SYSTEM_EXCEPTION);

    public static final int _LOCATION_FORWARD = 3;

    public static final ReplyStatusType LOCATION_FORWARD =
        new ReplyStatusType(_LOCATION_FORWARD);

    public static final int _LOCATION_FORWARD_PERM = 4;

    public static final ReplyStatusType LOCATION_FORWARD_PERM =
        new ReplyStatusType(_LOCATION_FORWARD_PERM);

    public static final int _NEEDS_ADDRESSING_MODE = 5;

    public static final ReplyStatusType NEEDS_ADDRESSING_MODE =
        new ReplyStatusType(_NEEDS_ADDRESSING_MODE);
}