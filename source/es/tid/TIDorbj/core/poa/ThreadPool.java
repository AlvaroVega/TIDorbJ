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

import java.util.Vector;

import es.tid.TIDorbj.util.Trace;

/**
 * Pool of threads.
 * 
 * @autor Javier Fdz. Mejuto
 * @version 1.0
 */
class ThreadPool
    implements QueueReaderManager, ThreadStateListener, POAManagerConfListener
{

    private Vector m_pool = null;

    private POAManagerImpl m_poa_manager = null;

    private int m_used = 0; 

    private int m_active = 0;

    private boolean m_deactivated = false;

    /**
     * Constructor.
     * 
     * @param poaManager
     *            The POAManager which this thread pool belongs to.
     */
    public ThreadPool(POAManagerImpl poaManager)
    {
        m_pool = new Vector();
        m_poa_manager = poaManager;
    }

    /**
     * Test if another thread should be created.
     * 
     * @return Returns true if another thread has been started, false otherwise.
     */
    synchronized public boolean createNewReader()
    {
        if ((m_used == m_active)
            &&(m_used < m_poa_manager.m_conf.getMaxThreads())) {
            createThread();
            return true;
        }
        return false;
    }

    synchronized private void createThread()
    {
        ExecThread t = new ExecThread(m_poa_manager);
        m_pool.addElement(t);
        t.setThreadStateListener(this);
        t.setDaemon(false);
        t.start();
        m_used++;

        if (m_poa_manager.m_orb.m_trace != null) {
            String[] msg = 
            	{ "New thread created, ",
                   t.toString(),
                   ": ",
                   Integer.toString(m_used),
                   " threads are now actived (max ",
                   Integer.toString(m_poa_manager.m_conf.getMaxThreads()),
                   "	, min ",
                   Integer.toString(m_poa_manager.m_conf.getMinThreads()),
            	   ")" 
                 };
            m_poa_manager.m_orb.printTrace(Trace.DEBUG, msg);
        }
    }

    /**
     * Counts the number of active threads (increment).
     * 
     * @param t
     *            Thread that becomes active.    
     */
    synchronized public void setActive(Thread t)
    {
        m_active++;        
    }

    /**
     * Counts the number of active threads (decrement).
     * 
     * @param t
     *            Thread that becomes inactive.
     */
    synchronized public void setInactive(Thread t)
    {
        m_active--;
    }

    /**
     * Clean up when a thread dies.
     * 
     * @param t
     *            Thread that has died.
     */
    synchronized public void threadHasDied(Thread t)
    {
        m_pool.removeElement(t);
        m_used--;
        if (m_poa_manager.m_orb.m_trace != null) {
            String[] msg = 
            	{
            	 "Finalization of thread ",
            	 t.toString(),
            	 ": ",
            	 Integer.toString(m_used),
            	 " threads are now actived (max ",
            	 Integer.toString(m_poa_manager.m_conf.getMaxThreads()),
                 ", min ",
                 Integer.toString(m_poa_manager.m_conf.getMinThreads()),
                 ")"
                };
            m_poa_manager.m_orb.printTrace(Trace.DEBUG, msg);
        }
    }

    synchronized public void deactivation()
    {
        m_deactivated = true;
    }

    synchronized public boolean threadCanDie(Thread t)
    {
        if (m_deactivated)
            return true;
        else
            return (m_used > m_poa_manager.m_conf.getMinThreads());
    }

    synchronized public void minThreadsHasChanged()
    {
        while (m_used < m_poa_manager.m_conf.getMinThreads()) {
            createThread();
        }
    }

}