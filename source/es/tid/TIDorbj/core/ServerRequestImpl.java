/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 395 $
* Date: $Date: 2009-05-27 16:10:32 +0200 (Wed, 27 May 2009) $
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
package es.tid.TIDorbj.core;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TCKind;
import org.omg.Compression.CompressorIdLevel;

import es.tid.TIDorbj.core.ContextImpl;
import es.tid.TIDorbj.core.NVListImpl;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.comm.giop.GIOPVersion;
import es.tid.TIDorbj.core.comm.giop.RequestId;
import es.tid.TIDorbj.core.comm.iiop.TargetAddress;
//TODO: make TargetAddress generic and move it to giop package
/**
 * TIDorb DSI ServerRequest implementation.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class ServerRequestImpl extends org.omg.CORBA.ServerRequest
{

    private final static int NO_ARGUMENTS_FIXED = 0;

    private final static int ARGUMENTS_FIXED = 1;

    private final static int RESULT_FIXED = 2;

    public final static int NO_EXCEPTION = 0;

    public final static int USER_EXCEPTION = 1;

    public final static int SYSTEM_EXCEPTION = 2;

    public final static int LOCATION_FORWARD = 3;

    private int m_completion_state;

    private int m_return_state;

    RequestId m_request_id;

    boolean m_with_response = false;

    String m_operation_name;

    TargetAddress m_target;

    GIOPVersion m_version;

    org.omg.CORBA.Context m_context;

    org.omg.CORBA.NVList m_server_parameters;

    Any m_result;

    Any m_exception;

    SystemException m_system_exception;

    org.omg.CORBA.Object m_forward_obj;

    CDRInputStream m_marshaled_parameters;

    CompressorIdLevel m_compressor;

    public ServerRequestImpl(GIOPVersion ver, 
                             RequestId id, 
                             String operation,
                             CDRInputStream cdr_parameters, 
                             TargetAddress target)
    {
        m_completion_state = NO_ARGUMENTS_FIXED;
        m_return_state = NO_EXCEPTION;

        m_version = ver;
        m_request_id = id;
        m_operation_name = operation;
        m_context = null;
        m_server_parameters = null;
        m_marshaled_parameters = cdr_parameters;
        m_target = target;
        m_forward_obj = null;
        m_compressor = new CompressorIdLevel((short)0,(short)0);
    }

    public String operation()
    {
        return m_operation_name;
    }

    public org.omg.CORBA.Context ctx()
    {
        if (m_completion_state != ARGUMENTS_FIXED)
            throw new BAD_INV_ORDER(
                         "ctx() called after arguments() or set_result().");

        return m_context;
    }

    public void arguments(org.omg.CORBA.NVList nv)
    {
        if (m_completion_state != NO_ARGUMENTS_FIXED)
            throw new BAD_INV_ORDER("arguments() called again.");

        m_completion_state = ARGUMENTS_FIXED;

        m_server_parameters = nv;
        NVListImpl.readInParams(m_server_parameters, m_marshaled_parameters);
        try {
            m_context = (ContextImpl) m_marshaled_parameters.read_Context();
        }
        catch (org.omg.CORBA.MARSHAL me) {}
    }

    public void set_result(org.omg.CORBA.Any result)
    {
        if (m_completion_state != ARGUMENTS_FIXED)
            throw new BAD_INV_ORDER("arguments() called again.");

        m_completion_state = RESULT_FIXED;

        m_return_state = NO_EXCEPTION;

        this.m_result = result;
    }

    public void set_exception(org.omg.CORBA.Any except)
    {
        if (except.type().kind().value() != TCKind._tk_except)
            throw new BAD_PARAM("Any value must be a user exception.");

        m_completion_state = RESULT_FIXED;
        m_return_state = USER_EXCEPTION;

        m_exception = except;
    }

    // TIDorb operations

    public void setSystemException(org.omg.CORBA.SystemException except)
    {
        m_completion_state = RESULT_FIXED;
        m_return_state = SYSTEM_EXCEPTION;

        m_system_exception = except;
    }

    public void setForward(org.omg.CORBA.Object obj)
    {
        m_completion_state = RESULT_FIXED;
        m_return_state = LOCATION_FORWARD;

        m_forward_obj = obj;
    }

    public TargetAddress getTarget()
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

    public void withResponse(boolean value)
    {
        m_with_response = value;
    }

    public boolean withResponse()
    {
        return m_with_response;
    }

    public int getReturnState()
    {
        return m_return_state;

    }

    public Any getResult()
    {
        return m_result;
    }

    public Any getException()
    {
        return m_exception;
    }

    public SystemException getSystemException()
    {
        return m_system_exception;
    }

    public org.omg.CORBA.NVList getParameters()
    {
        return m_server_parameters;
    }

    public org.omg.CORBA.Object getForward()
    {
        return m_forward_obj;
    }

    public GIOPVersion getVersion()
    {
        return m_version;
    }

    public void destroy()
    {
        NVListImpl.destroy(m_server_parameters);
    }

    public CompressorIdLevel get_compressor() 
    {
        return m_compressor;
    }

    public void set_compressor(CompressorIdLevel compressor) 
    {
        m_compressor = compressor;
    }

}
