/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 453 $
* Date: $Date: 2010-04-27 16:52:41 +0200 (Tue, 27 Apr 2010) $
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
package es.tid.TIDorbj.core.messaging;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.Object;
import org.omg.CORBA.Request;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.Streamable;
import org.omg.CORBA.portable.ValueFactory;
import org.omg.Messaging.ReplyHandler;
import org.omg.Messaging.ReplyHandlerHelper;
import org.omg.Messaging._ExceptionHolder;
import org.omg.Messaging._ExceptionHolderHelper;

import es.tid.CORBA.Any;
import es.tid.TIDorbj.core.NVListImpl;
import es.tid.TIDorbj.core.RequestImpl;
import es.tid.TIDorbj.core.comm.ForwardRequest;
import es.tid.TIDorbj.core.comm.giop.GIOPFragmentedMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPReplyMessage;
import es.tid.TIDorbj.core.comm.giop.MsgType;
import es.tid.TIDorbj.core.comm.giop.ReplyStatusType;
import es.tid.TIDorbj.core.comm.giop.RequestId;
import es.tid.TIDorbj.core.comm.iiop.IIOPConnection;
import es.tid.TIDorbj.core.comm.iiop.IIOPIOR;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.util.OperationCompletion;

/**
 * Lock where the asynchronous requests stay until the complete response has been
 * sent to ReplyHandler or the asynchronous response timeout has expired.
 * 
 * 
 * 
 * @autor Irenka Redondo Granados
 * @version 1.0
 */

public class AMILock
{

    /**
     * The Operation Completion Status.
     */
    OperationCompletion m_state;

    /**
     * Request identifier. This identifier will be the key for the connection
     * thread to
     */
    RequestId m_request_id;

    /**
     * Asynchronous request
     */
    RequestImpl m_request;

    GIOPFragmentedMessage message;
    
    /**
     * Reference to ReplyHandler object which the response will be sent
     */
    Object m_ami_handler;
    
    /**
     * Asynchronous request's processing time
     */
    long time;
    
    IIOPIOR m_ior; // ?????????? no se descripcion
    
    IIOPConnection m_conn;

    public AMILock()
    {
        m_request_id = null;
        m_request = null;
        message = null;
        m_state = new OperationCompletion();
        m_ami_handler = null;
        time = 0;
        m_ior = null;
        m_conn = null;
    }

    public RequestId requestId()
    {
        return m_request_id;
    }

    public void requestId(RequestId id)
    {
        m_request_id = id;
    }

    public RequestImpl getRequest()
    {
        return m_request;
    }

    public void setRequest(RequestImpl request)
    {
        m_request = request;
    }

    public boolean getCompleted()
    {
        return m_state.isCompleted();
    }

    public void setCompleted()
    {
        m_state.setCompleted();
    }

    public void setMessage(GIOPFragmentedMessage msg)
    {
        message = msg;
    }

    public GIOPFragmentedMessage getMessage()
    {
        return message;
    }

    public void setHandler(Object handler)
    {
        m_ami_handler = handler;
    }

    public Object getHandler()
    {
        return m_ami_handler;
    }
    
    public void incrementTime (long inc)
    {
    	time = +inc;
    }
    
    public long getTime()
    {
    	return time;
    }
    
    public void setIor(IIOPIOR ior)
    {
        m_ior = ior;
    }

    public IIOPIOR getIor()
    {
        return m_ior;
    }

    public void putReply() {

        if (message == null) {
            throw new INTERNAL("No Reply message");
        }
        if (message.getHeader().getMsgType().m_value != MsgType._Reply) {
            throw new MARSHAL("No Reply message received");
        }

        GIOPReplyMessage reply_message = (GIOPReplyMessage) message;

        m_request.setCompletedYes();
 	
        RequestImpl replyHandlerRequest;
        org.omg.CORBA.Any $excep_holder;
        _ExceptionHolder excepHolder;
  	
        //Take the exception holder implementation class name
        String idExcepHolder = idExcepHolder(m_ami_handler);
        _ExceptionHolderImpl excepHolderFactory = 
            (_ExceptionHolderImpl) m_request.orb().lookup_value_factory(idExcepHolder);

  		
        switch (reply_message.replyStatus().value()) 
            {
            case ReplyStatusType._NO_EXCEPTION:
                //funciona
                /*
                reply_message.extractArguments(m_request);
                RequestImpl replyHandlerRequest = (RequestImpl) m_ami_handler._request(m_request.operation());
                if (m_request.result() != null) {
                    org.omg.CORBA.Any $ami_return_val = replyHandlerRequest.add_in_arg();
                    $ami_return_val.read_value(m_request.return_value().create_input_stream(), m_request.return_value().type());
                }
                try {
                    replyHandlerRequest.invoke();
                } catch (SystemException e) {
                    String operationName = replyHandlerRequest.operation();
                    operationName = operationName.substring(1, operationName.length());
                    replyHandlerRequest.setOperationName(operationName);
                    replyHandlerRequest.invoke();
                }*/

                replyHandlerRequest = (RequestImpl) m_ami_handler._request(m_request.operation());
                if (m_request.result() != null) {
                    org.omg.CORBA.Any $ami_return_val = replyHandlerRequest.add_named_in_arg("ami_return_val");
                    $ami_return_val.type(m_request.return_value().type());
                }
                NVListImpl.setOutParamsAsIn(m_request.arguments(), replyHandlerRequest);
                reply_message.extractArgumentsForReplyHandler(replyHandlerRequest);
                try {
                    replyHandlerRequest.invoke(); /// invoke or ther kind of invoke??
                } catch (SystemException e) {
                    String operationName = replyHandlerRequest.operation();
                    operationName = operationName.substring(1, operationName.length());
                    replyHandlerRequest.setOperationName(operationName);
                    replyHandlerRequest.invoke();
                }
                break;
            case ReplyStatusType._USER_EXCEPTION: 			
                replyHandlerRequest = 
                    (RequestImpl) m_ami_handler._request(m_request.operation() + "_excep");
                $excep_holder = replyHandlerRequest.add_named_in_arg("excep_holder");

                 _ExceptionHolderImpl excepHolder_impl = new _ExceptionHolderImpl();
                 $excep_holder.type(excepHolder_impl._type());
                 reply_message.extractUserException(excepHolder_impl);

                 NVListImpl.setOutParamsAsIn(m_request.arguments(), replyHandlerRequest);

                _ExceptionHolderHelper.insert($excep_holder, 
                                              (org.omg.Messaging._ExceptionHolder)excepHolder_impl);





                try {
                    replyHandlerRequest.invoke();
                } catch (SystemException e) {
                    e.printStackTrace();
                }
                break;
            case ReplyStatusType._SYSTEM_EXCEPTION:
                replyHandlerRequest = (RequestImpl) m_ami_handler._request(m_request.operation()+"_excep");
                $excep_holder = replyHandlerRequest.add_named_in_arg("excep_holder");
                excepHolder = new _ExceptionHolderImpl();
                $excep_holder.type(excepHolder._type());
                //reply_message.extractSystemException(excepHolder);
                $excep_holder.insert_Streamable(excepHolder);
                try {
                    replyHandlerRequest.invoke();
                } catch (SystemException e) {
                    String operationName = replyHandlerRequest.operation();
                    operationName = operationName.substring(1, operationName.length());
                    replyHandlerRequest.setOperationName(operationName);
                    replyHandlerRequest.invoke();
                }
                //m_request.setSystemException(reply_message.extractSystemException());
                break;
                /*
            case ReplyStatusType._LOCATION_FORWARD:
                {
                    IOR forward_ior = reply_message.extractForward();
                    throw new ForwardRequest(forward_ior);
                }
            case ReplyStatusType._LOCATION_FORWARD_PERM:
                {	
                    IOR forward_ior = reply_message.extractForwardPerm();
                    throw new ForwardRequest(forward_ior);
                }
            case ReplyStatusType._NEEDS_ADDRESSING_MODE:
                m_conn.sendRequest(m_request, m_ior, reply_message.extractAddressingDisposition());
                */
            }

    	
    }
    
    private String idExcepHolder (Object ami_handler) {

        if (ami_handler == null)
            throw new INTERNAL("AMILock.idExcepHolder ami_handler is NULL");
        
        String name = m_ami_handler.getClass().getName();
        String [] replyHandlerClassNameParts = name.split("\\.");
        String nameClass = replyHandlerClassNameParts[replyHandlerClassNameParts.length-1];
        replyHandlerClassNameParts[replyHandlerClassNameParts.length-1] = 
            nameClass.substring(1, nameClass.length()-4);
        String replyHandlerClassName = replyHandlerClassNameParts[0];
  	
        for (int i=1; i< replyHandlerClassNameParts.length; i++)
            replyHandlerClassName = replyHandlerClassName + "." + replyHandlerClassNameParts[i];
        
        Class replyHandlerClassHelper = null;
        String idReplyHandler = null;
        try {
            replyHandlerClassHelper = Class.forName(replyHandlerClassName.concat("Helper"), 
                                                    true, m_ami_handler.getClass().getClassLoader());
            Method methodId = replyHandlerClassHelper.getMethod("id",new Class[]{});
            idReplyHandler = (String) methodId.invoke(null,new java.lang.Object[]{});
            
            //ReplyHandlerHelper rh = (ReplyHandlerHelper) replyHandlerClassHelper.getClass();
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	
        String [] idReplyHandlerParts = idReplyHandler.split(":");
        String [] handlerPath = idReplyHandlerParts[1].split("/");
        String excepHolderName = handlerPath[handlerPath.length - 1];
        excepHolderName = "_" + excepHolderName.substring(0, excepHolderName.length()-7) + 
            "ExceptionHolder";
        String excepHolderPath = ""; 
        for (int i=0; i<handlerPath.length-1; i++) {
            excepHolderPath = excepHolderPath + handlerPath[i] + "/"; 
        }
        excepHolderPath = excepHolderPath + excepHolderName;
	
        String idExcepHolder = idReplyHandlerParts[0] + ":" + excepHolderPath + 
            ":" + idReplyHandlerParts[2];
        //String idExcepDolder = 
  	
        /*
        String [] replyHandlerClassName = m_ami_handler.getClass().getName().split(".");
        String idFactoryExcepHolder = replyHandlerClassName[replyHandlerClassName.length-1];
        idFactoryExcepHolder = idFactoryExcepHolder.substring(0, idFactoryExcepHolder.length()-11);
        idFactoryExcepHolder = idFactoryExcepHolder + "ExceptionHolderImpl";
        ValueFactory excepHolderFactory = m_request.orb().lookup_value_factory(idFactoryExcepHolder);
        */
            
        return idExcepHolder;
    }
    
}
