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
package es.tid.TIDorbj.core.poa;

/**
 * Class to wait until there are no executing requests.
 * 
 * @autor Javier Fdz. Mejuto
 * @version 1.0
 */
class CompletionWaiter
{

    private int m_active_requests = 0;

    private boolean m_stop_waiting = false;

    private es.tid.TIDorbj.core.TIDORB m_orb;

    /**
     * Constructor.
     * 
     * @param orb
     *            The ORB.
     */
    public CompletionWaiter(es.tid.TIDorbj.core.TIDORB orb)
    {
        this.m_orb = orb;
    }

    /**
     * Tests if an operation should wait for completion.
     * 
     * @return Returns true if it must wait.
     */
    protected boolean conditionToWait()
    {
        try {
            CurrentImpl current = m_orb.initPOACurrent();
            POAImpl poa = (POAImpl) current.get_POA();
            return poa.m_orb != m_orb;
        }
        catch (org.omg.PortableServer.CurrentPackage.NoContext e) {
            return true;
        }
    }

    /**
     * Begins a new request (increments active request counter).
     */
    synchronized protected void beginRequest()
    {
        m_active_requests++;
    }

    /**
     * Ends a request (decrements active request counter and notifies).
     */
    synchronized protected void endRequest()
    {
        m_active_requests--;
        if (m_active_requests <= 0) {
            notify();
        }
    }

    /**
     * Waits until there are no active requests.
     */
    synchronized protected void waitForCompletion()
    {
        while ((m_active_requests != 0) && !m_stop_waiting) {
            try {
                wait();
            }
            catch (Exception e) {}
        }
        m_stop_waiting = false;
    }

    /**
     * Stops waiting for completion.
     */
    synchronized protected void stopWaiting()
    {
        m_stop_waiting = true;
        notifyAll();
    }

    /**
     * @return Number of active requests.
     */
    synchronized protected int getActiveRequests()
    {
        return m_active_requests;
    }
}