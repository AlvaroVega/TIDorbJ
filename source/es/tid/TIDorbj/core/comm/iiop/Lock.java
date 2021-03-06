/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 478 $
* Date: $Date: 2011-04-29 16:42:47 +0200 (Fri, 29 Apr 2011) $
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
package es.tid.TIDorbj.core.comm.iiop;

import org.omg.CORBA.INTERNAL;

import es.tid.TIDorbj.core.comm.giop.GIOPFragmentedMessage;
import es.tid.TIDorbj.core.comm.giop.RequestId;
import es.tid.TIDorbj.core.util.OperationCompletion;

/**
 * Lock where the the threads that have invoked a request must be locket until
 * the complete response has been replied or the response timeout has expired.
 * 
 * 
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class Lock
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

    GIOPFragmentedMessage message;

    public Lock()
    {
        m_request_id = null;
        message = null;
        m_state = new OperationCompletion();
    }

    public RequestId requestId()
    {
        return m_request_id;
    }

    public void requestId(RequestId id)
    {
        m_request_id = id;
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

    public void waitForCompletion(long how_long)
    {
        try {
            m_state.waitForCompletion(how_long);
        }
        catch (java.lang.InterruptedException ie) {
            throw new INTERNAL("Unexpected Interrupted Exception");
        }
        catch (es.tid.TIDorbj.core.util.OnlyOneThreadCanWait only) {
            throw new INTERNAL("Only one Thread can wait in the request lock.");
        }
    }
    
    public void cancelWaiting()
    {
        m_state.interruptWaiting();
    }

}
