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

import org.omg.CORBA.COMM_FAILURE;

/**
 * TIDorb IIOP IIOPConnection State.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 0.1
 */

public class ConnectionState
{

    /**
     * Open connection state.
     */
    public final static short OPEN_STATE = 0;

    /**
     * Open connection state.
     */
    public final static short CLOSING_STATE = 1;

    /**
     * Open connection state.
     */
    public final static short ERROR_STATE = 2;

    /**
     * IIOPConnection state.
     */
    private short m_state;

    /**
     * IIOPConnection error that unhabilitates the connection.
     */
    protected COMM_FAILURE m_connection_error;

    public ConnectionState()
    {
        m_state = OPEN_STATE;
        m_connection_error = null;
    }

    synchronized public boolean setClosing()
    {
        if (m_state != OPEN_STATE)
            return false;

        m_state = CLOSING_STATE;

        return true;
    }

    synchronized public boolean isOpen()
    {
        return m_state == OPEN_STATE;
    }

    synchronized public boolean seterror(COMM_FAILURE error)
    {
        if (m_state == ERROR_STATE) {
            return false;
        } else if (m_state == CLOSING_STATE) {
            m_state = ERROR_STATE;
            m_connection_error = error;
            return false;
        } else { // OPEN_STATE
            m_state = ERROR_STATE;
            m_connection_error = error;
            return true;
        }
    }

    synchronized public short getValue()
    {
        return m_state;
    }

    public COMM_FAILURE getError()
    {
        return m_connection_error;
    }

    /**
     * Verify if the reply can be sent.
     */
    synchronized public void verifyForReply()
    {
        switch (m_state)
        {
            case OPEN_STATE:
                return;
            case CLOSING_STATE:
                throw new COMM_FAILURE("IIOPConnection is closed.");
            case ERROR_STATE:
                throw m_connection_error;
        }
    }

    /**
     * Verify if the request can be sent.
     */
    synchronized public void verifyForRequest()
        throws RECOVERABLE_COMM_FAILURE
    {
        switch (m_state)
        {
            case OPEN_STATE:
                return;
            case CLOSING_STATE:
                throw new RECOVERABLE_COMM_FAILURE(
                   new COMM_FAILURE("IIOPConnection is closed."));
            case ERROR_STATE:
                throw new RECOVERABLE_COMM_FAILURE(m_connection_error);
        }
    }

}

