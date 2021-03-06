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

import es.tid.TIDorbj.core.comm.Connection;

/**
 * 
 * Maintains the connection opening lock.
 * 
 * @author Juan A. Caceres
 * @version 1.0
 */

class OpeningLock
{

    public final static int OPENING = 0;

    public final static int OPENED = 1;

    public final static int ERROR = 2;

    private int m_status;

    //private IIOPConnection m_opened_connection;
    private Connection m_opened_connection;

    private org.omg.CORBA.COMM_FAILURE m_error;

    public OpeningLock()
    {
        m_status = OPENING;
        m_opened_connection = null;
        m_error = null;
    }

    //public synchronized IIOPConnection waitOpening(long time)
    public synchronized Connection waitOpening(long time)
    {
        if (m_status == OPENING) {
            try {
                wait(time);
            }
            catch (InterruptedException ioe) {}
        }

        if (m_status == OPENED)
            return m_opened_connection;

        else if (m_status == ERROR)
            throw m_error;
        else
            //throw new org.omg.CORBA.COMM_FAILURE("IIOPConnection timout");
            throw new org.omg.CORBA.COMM_FAILURE("Connection timout");
    }

    //public synchronized void setOpened(IIOPConnection conn)
    public synchronized void setOpened(Connection conn)
    {
        m_status = OPENED;
        m_opened_connection = conn;
        notifyAll();
    }

    public synchronized void setError(org.omg.CORBA.COMM_FAILURE failure)
    {
        m_status = ERROR;
        m_error = failure;
        notifyAll();
    }

}
