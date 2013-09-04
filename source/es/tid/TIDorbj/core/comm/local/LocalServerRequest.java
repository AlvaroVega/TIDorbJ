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
package es.tid.TIDorbj.core.comm.local;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.NVList;

import es.tid.TIDorbj.core.AnyImpl;
import es.tid.TIDorbj.core.NVListImpl;
import es.tid.TIDorbj.core.RequestImpl;

public class LocalServerRequest extends org.omg.CORBA.ServerRequest
{

    RequestImpl m_local_request;

    NVList m_server_parameters;

    org.omg.CORBA.Object m_forward_obj;

    public LocalServerRequest(RequestImpl request)
    {
        m_local_request = request;
        m_server_parameters = null;
        m_forward_obj = null;
    }

    public String operation()
    {
        return m_local_request.operation();
    }

    public org.omg.CORBA.Context ctx()
    {
        return m_local_request.ctx();
    }

    public void arguments(org.omg.CORBA.NVList nv)
    {
        if (nv == null)
            throw new BAD_PARAM("Null NVList reference", 0,
                                CompletionStatus.COMPLETED_NO);

        m_server_parameters = nv;
        // performance improvement: wrap the parameter anys and do not copy then
        //                           ONLY WITH NO ONEWAY REQUEST!!!!!

        boolean wrap_anys = m_local_request.withResponse();

        NVListImpl.assignInArguments(m_local_request.arguments(),
                                     m_server_parameters, wrap_anys);
    }

    public void set_result(Any result)
    {
        if (result == null)
            throw new BAD_PARAM("Null any reference", 0,
                                CompletionStatus.COMPLETED_NO);

        Any result_any = m_local_request.return_value();
        AnyImpl.assignValue(result, result_any);
    }

    public void set_exception(Any except)
    {
        if (except == null)
            throw new BAD_PARAM("Null any reference", 0,
                                CompletionStatus.COMPLETED_NO);

        m_local_request.setUserException(except);
    }

    // TIDorb operations

    public void fixOutArguments()
    {
        // performance improvement: wrap the parameter anys and do not copy them
        //                           ONLY WITH NO ONEWAY REQUEST!!!!!
        NVListImpl.assignOutArguments(m_server_parameters,
                                      m_local_request.arguments(), true);
    }

    public void setSystemException(org.omg.CORBA.SystemException except)
    {
        if (except == null)
            throw new BAD_PARAM("Null any reference", 0,
                                CompletionStatus.COMPLETED_NO);

        m_local_request.setSystemException(except);
    }

    public boolean isForwarded()
    {
        return m_forward_obj != null;
    }

    public void setForward(org.omg.CORBA.Object obj)
    {
        m_forward_obj = obj;
    }

    public org.omg.CORBA.Object getForward()
    {
        return m_forward_obj;
    }

    public void destroy()
    {
        NVListImpl.destroy(m_server_parameters);
    }

    public RequestImpl getLocalRequest()
    {
        return m_local_request;
    }

}