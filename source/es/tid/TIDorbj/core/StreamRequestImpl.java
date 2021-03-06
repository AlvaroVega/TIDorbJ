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
package es.tid.TIDorbj.core;

import org.omg.CORBA.CompletionStatus;

import es.tid.TIDorbj.core.comm.giop.RequestId;

public class StreamRequestImpl
{
    /**
     * The orb.
     */
    TIDORB m_orb;

    /**
     * Target CORBA object.
     */
    org.omg.CORBA.portable.ObjectImpl m_target;

    /**
     * operation name.
     */
    String m_operation_name;

    /**
     * Request identifier.
     */
    RequestId m_request_id = null;

    /**
     * Needs response.
     */
    boolean m_with_response = false;

    es.tid.TIDorbj.core.cdr.CDRInputStream m_response = null;

    es.tid.TIDorbj.core.cdr.CDROutputStream m_request = null;

    /**
     * Completion status needed in exception throwing. Initialy, the status is
     * <code>COMPLETED_NO</code>
     */
    CompletionStatus m_completed;

    public StreamRequestImpl(TIDORB orb,
                             org.omg.CORBA.portable.ObjectImpl target,
                             String operation, boolean response)
    {
        m_orb = orb;
        m_target = target;
        m_operation_name = operation;
        m_with_response = response;
    }

    public TIDORB orb()
    {
        return m_orb;
    }

    public void setInputStream(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        m_response = input;
    }

    public es.tid.TIDorbj.core.cdr.CDRInputStream getInputStream()
    {
        return m_response;
    }

    public void setOutputStream(es.tid.TIDorbj.core.cdr.CDROutputStream output)
    {
        m_request = output;
    }

    public es.tid.TIDorbj.core.cdr.CDROutputStream getOutputStream()
    {
        return m_request;
    }

    public org.omg.CORBA.portable.ObjectImpl getTarget()
    {
        return m_target;
    }

    public RequestId getId()
    {
        return m_request_id;
    }

    public void setId(RequestId id)
    {
        m_request_id = id;
    }

    public boolean withResponse()
    {
        return m_with_response;
    }

    public String operation()
    {
        return m_operation_name;
    }
}