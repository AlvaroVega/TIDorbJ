/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 13 $
* Date: $Date: 2006-02-09 10:58:57 +0100 (Thu, 09 Feb 2006) $
* Last modified by: $Author: avega $
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

import org.omg.Messaging.ORDER_ANY;
import org.omg.Messaging.ORDER_DEADLINE;
import org.omg.Messaging.ORDER_PRIORITY;
import org.omg.Messaging.ORDER_TEMPORAL;
import org.omg.Messaging.QueueOrderPolicy;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAManagerPackage.State;

/**
 * Implementation of POAManager.
 * 
 * @autor Javier Fdz. Mejuto
 * @version 1.0
 */
public class POAManagerImpl extends org.omg.PortableServer.POAManagerLocalBase
    implements es.tid.PortableServer.POAManager
{

    private boolean destroyed;

    // Relationships
    protected es.tid.TIDorbj.core.TIDORB m_orb = null;

    protected POAManagerConf m_conf;

    private Vector m_poas;

    private RequestQueue m_request_queue;

    private ThreadPool m_pool;
       

    // Members
    private State m_state;

    protected java.lang.Object m_state_mutex;

    protected String m_manager_name;      

    static es.tid.TIDorbj.core.util.Counter st_serial =
        new es.tid.TIDorbj.core.util.Counter();

    protected short m_queue_order;
    
    // waitForCompletion
    private CompletionWaiter m_completion;

    /**
     * Constructor.
     * 
     * @param orb
     *            The ORB.
     */
    public POAManagerImpl(es.tid.TIDorbj.core.TIDORB orb)
    {
        m_orb = orb;
        synchronized (orb.m_POAManagers) {
            orb.m_POAManagers.addElement(this);
        }
        m_manager_name = "POAManager " + st_serial.inc();
        m_state = State.HOLDING;
        m_state_mutex = new Boolean(true);
        m_conf = new POAManagerConf(orb.m_conf.poa_min_threads,
                                    orb.m_conf.poa_max_threads,
                                    orb.m_conf.poa_max_queued_requests,
                                    orb.m_conf.poa_starving_time);
        m_completion = new CompletionWaiter(orb);
        m_poas = new Vector();
        m_pool = new ThreadPool(this);
        m_conf.setListener(m_pool);
        m_request_queue = new RequestQueue(this, m_pool, new TemporalRequestComparator());
        m_pool.minThreadsHasChanged();
        m_queue_order = ORDER_TEMPORAL.value;
        
    }

    /**
     * Sets the mininum number of execution threads
     */
    public void set_min_threads(int min_threads)
    {
        m_conf.setMinThreads(min_threads);
    }

    /**
     * Sets the maximun number of execution threads
     */
    public void set_max_threads(int max_threads)
    {
        m_conf.setMaxThreads(max_threads);
    }

    /**
     * Sets the maximun number of request queued in the POAManager
     */
    public void set_max_queued_requests(int max_queued_requests)
    {
        m_conf.setMaxQueuedRequests(max_queued_requests);
    }

    /**
     * Sets the maximun time an execution thread is inactive.
     */
    public void set_starving_time(int millisecs)
    {
        m_conf.setStarvingTime(millisecs);
    }

    /**
     * @return maximun number of request queued in the POAManager
     */
    public int get_max_queued_requests()
    {
        return m_conf.getMaxQueuedRequests();
    }

    /**
     * @return the maximun number of execution threads
     */
    public int get_max_threads()
    {
        return m_conf.getMaxThreads();
    }

    /**
     * @return the mininum number of execution threads
     */
    public int get_min_threads()
    {
        return m_conf.getMinThreads();
    }

    /**
     * @return the maximun time an execution thread is inactive.
     */
    public int get_starving_time()
    {
        return m_conf.getStarvingTime();
    }

    /**
     * @return The associated POAManagerConf object.
     */
    protected POAManagerConf getConf()
    {
        return m_conf;
    }

    /**
     * Adds a POA to this POAManager.
     * 
     * @param poa
     *            The POA.
     */
    protected void addPOA(org.omg.PortableServer.POA poa)
    {
        m_poas.addElement(poa);
    }

    /**
     * @return The request queue of this POAManager.
     */
    protected RequestQueue getRequestQueue()
    {
        return m_request_queue;
    }

    /**
     * @return The thread pool of this POAManager.
     */
    protected ThreadPool getThreadPool()
    {
        return m_pool;
    }
    
    protected void setQueueOrderPolicy(QueueOrderPolicy policy) {
        
        short order = policy.allowed_orders();
        
        set_queue_order(order);
       
        
    }
    
    public void set_queue_order(short order) {
        if(((order ^ ORDER_PRIORITY.value) != 0) 
        		&& (m_queue_order != ORDER_PRIORITY.value)){
            m_request_queue.setComparator(new PriorityRequestComparator());
            m_queue_order = ORDER_PRIORITY.value;
        } else if (((order ^ ORDER_DEADLINE.value) != 0)
            && (m_queue_order != ORDER_DEADLINE.value)){
            m_request_queue.setComparator(new DeadlineRequestComparator());
            m_queue_order = ORDER_DEADLINE.value;
        } else if ((m_queue_order != ORDER_TEMPORAL.value)
            || (m_queue_order != ORDER_ANY.value)) { 
            // default comparator will be used            
            m_request_queue.setComparator(new TemporalRequestComparator());
        }
    }
    
    public short get_queue_order()
    {
        return m_queue_order;
       
    }


    /* ************ State changes ************* */

    /**
     * This operation changes the POA manager to ACTIVE.
     * 
     * @exception org.omg.PortableServer.POAManagerPackage.AdapterInactive
     *                If POA manager state is INACTIVE.
     */
    public void activate()
        throws org.omg.PortableServer.POAManagerPackage.AdapterInactive
    {
        // State change -> ACTIVE
        synchronized (m_state_mutex) {
            if (m_state == State.INACTIVE) {
                throw new AdapterInactive();
            }
            if (m_state != State.ACTIVE) {
                m_completion.stopWaiting();
                m_state = State.ACTIVE;
                m_state_mutex.notifyAll();
            }
        }
    }

    /**
     * This operation changes the POA manager to HOLDING.
     * 
     * @param wait_for_completion
     *            Wait-for-completion flag.
     * @exception org.omg.PortableServer.POAManagerPackage.AdapterInactive
     *                If POA manager state is INACTIVE.
     */
    public void hold_requests(boolean wait_for_completion)
        throws org.omg.PortableServer.POAManagerPackage.AdapterInactive
    {
        // State change -> HOLDING
        synchronized (m_state_mutex) {
            if (m_state == State.INACTIVE) {
                throw new AdapterInactive();
            }
            if (m_state != State.HOLDING) {
                m_completion.stopWaiting();
                m_state = State.HOLDING;
                m_state_mutex.notifyAll();
            }
        }
        // Wait for completion, if necessary
        if (wait_for_completion && m_completion.conditionToWait()) {
            m_completion.waitForCompletion(); // synchronized
        }
    }

    /**
     * This operation changes the POA manager to DISCARDING.
     * 
     * @param wait_for_completion
     *            Wait-for-completion flag.
     * @exception org.omg.PortableServer.POAManagerPackage.AdapterInactive
     *                If POA manager state is INACTIVE.
     */
    public void discard_requests(boolean wait_for_completion)
        throws org.omg.PortableServer.POAManagerPackage.AdapterInactive
    {
        // State change -> DISCARDING
        synchronized (m_state_mutex) {
            if (m_state == State.INACTIVE) {
                throw new AdapterInactive();
            }
            if (m_state != State.DISCARDING) {
                m_completion.stopWaiting();
                m_state = State.DISCARDING;
                m_state_mutex.notifyAll();
            }
        }
        // Wait for completion, if necessary
        if (wait_for_completion && m_completion.conditionToWait()) {
            m_completion.waitForCompletion(); // synchronized
        }
    }

    /**
     * This operation changes the POA manager to DEACTIVATE.
     * 
     * @param etherealize_object
     *            If it is true, then all objects must be etherealized.
     * @param wait_for_completion
     *            Wait-for-completion flag.
     * @exception org.omg.PortableServer.POAManagerPackage.AdapterInactive
     *                If POA manager state is INACTIVE.
     */
    public void deactivate(boolean etherealize_objects,
                           boolean wait_for_completion)
        throws org.omg.PortableServer.POAManagerPackage.AdapterInactive
    {

        // State change -> INACTIVE
        synchronized (m_state_mutex) {
            if (m_state == State.INACTIVE) {
                throw new AdapterInactive();
            }

            // reset the last completion waiters
            m_completion.stopWaiting();

            m_pool.deactivation();

            m_request_queue.deactivation();

            m_state = State.INACTIVE;
            synchronized (m_orb.m_POAManagers) {
                for (int i = 0; i < m_orb.m_POAManagers.size(); i++) {
                    if (m_orb.m_POAManagers.elementAt(i) == this)
                        m_orb.m_POAManagers.removeElementAt(i);
                }
            }
        }

        // Wait for completion, if necessary
        if (wait_for_completion && m_completion.conditionToWait()) {
            m_completion.waitForCompletion(); //synchronized
            if (etherealize_objects) {
                // Etherealize objects (blocking)
                etherealizeAllPOAs();
            }
        } else if (etherealize_objects) {
            // Etherealize in background
            EtherealizerThread t = new EtherealizerThread(this);
            t.start();
        }
    }

    /**
     * @return Return the state of the POA manager.
     */
    public org.omg.PortableServer.POAManagerPackage.State get_state()
    {
        return m_state;
    }

    /* ************ TIDorb methods - POA Location ************* */

    /**
     * Puts a new request in this POA manager.
     * 
     * @param request
     *            The request.
     */
    public void put(QueuedRequest request)
    {
        m_request_queue.add(request);
    }

    /**
     * Removes a POA from this POA manager.
     * 
     * @param poa
     *            The POA being removed.
     */
    protected void removePOA(org.omg.PortableServer.POA poa)
    {
        m_poas.removeElement(poa);
    }

    /**
     * Finds a POA in this POA manager.
     * 
     * @param poaName
     *            The POA name.
     */
    protected org.omg.PortableServer.POA findPOA(String poaName)
    {
        for (int i = 0; i < m_poas.size(); i++) {
            org.omg.PortableServer.POA poa = (org.omg.PortableServer.POA)
            	m_poas.elementAt(i);
            if (poa.the_name().equals(poaName)) {
                return poa;
            }
        }
        return null;
    }

    /**
     * Begins the execution of a request.
     */
    protected void beginRequest()
    {
        m_completion.beginRequest();
    }

    /**
     * Ends the execution of a request.
     */
    protected void endRequest()
    {
        m_completion.endRequest();
    }

    /**
     * Etherealizes all POAs (due to POA manager deactivation).
     */
    protected void etherealizeAllPOAs()
    {
        for (int i = 0; i < m_poas.size(); i++) {
            POAImpl poa = (POAImpl) m_poas.elementAt(i);
            poa.etherealizeAllObjects();
        }
    }

    /**
     * Class to etherealize all POAs in background.
     */
    public class EtherealizerThread extends Thread
    {

        POAManagerImpl poaManager;

        public EtherealizerThread(POAManagerImpl poaManager)
        {
            this.poaManager = poaManager;
        }

        public void run()
        {
            poaManager.etherealizeAllPOAs();
        }
    }

    /* ************ ObjectImpl ************* */

    public String toString()
    {
        return m_manager_name;
    }
}