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

class ProcessingState
{

    final static short CLIENT = 0;

    final static short RUNNING = 1;

    final static short SHUTDOWNING = 2;

    final static short SHUTDOWNED = 3;

    short m_state;

    TIDORB m_orb;

    public ProcessingState(TIDORB orb)
    {
        this.m_orb = orb;
        m_state = CLIENT;
    }

    public short state()
    {
        return m_state;
    }

    public synchronized void running()
    {
        if (m_state == CLIENT) {
            m_state = RUNNING;
        }
    }

    public boolean isShutdowned()
    {
        return m_state == SHUTDOWNED;
    }

    public synchronized void shutdown()
    {
        if (m_state == RUNNING) {
            m_state = SHUTDOWNING;
            ShutdownThread thread = new ShutdownThread(m_orb);
            thread.start();
        }
    }

    public synchronized void shutdowned()
    {
        if (m_state == SHUTDOWNING) {
            m_state = SHUTDOWNED;
            notifyAll();
        }
    }

    public synchronized void waitForShutdown()
    {
        if (m_state == SHUTDOWNING) {
            try {
                wait(m_orb.m_conf.max_time_in_shutdown);
            }
            catch (InterruptedException ie) {}
        } else if (m_state == RUNNING) {
            try {
                wait();
            }
            catch (InterruptedException ie) {}
        }
    }

    /**
     * Operation called by threads in the ORB that are listening for the ORB
     * shutdown
     * 
     * @return true if the ORB is shutdowning, of false if the timeout is over
     */

    public synchronized boolean waitForShutdown(long millis)
    {
        if ((m_state == RUNNING) || (m_state == CLIENT)) {
            try {
                wait(millis);
            }
            catch (InterruptedException ie) {}
        }

        return (m_state == RUNNING) ? false : true;
    }
}