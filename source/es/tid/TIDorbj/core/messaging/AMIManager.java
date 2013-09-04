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
	
			
	/**
	 * Implementation of AMIManager.
	 * 
	 * @autor Irenka Redondo
	 * @version 1.0
	 */
	public class AMIManager 
	{
	
	    // Relationships
	    protected es.tid.TIDorbj.core.TIDORB m_orb = null;
	
	    protected AMIManagerConf m_conf;
	
	    private AMILockList m_ami_lock_list;
	
	    private AMIThreadPool m_pool;
	    
	    protected java.lang.Object m_state_mutex;
	
	    protected String m_manager_name;      
	
	    static es.tid.TIDorbj.core.util.Counter st_serial =  new es.tid.TIDorbj.core.util.Counter();
	
	    protected short m_queue_order;
	    
	
	    /**
	     * Constructor.
	     * 
	     * @param orb
	     *            The ORB.
	     */
	    public AMIManager(es.tid.TIDorbj.core.TIDORB orb)
	    {
	        m_orb = orb;
	        m_manager_name = "AMIManager " + st_serial.inc();

	        m_conf = new AMIManagerConf(orb.m_conf.ami_min_threads,
	                                    orb.m_conf.ami_max_threads,
	                                    orb.m_conf.ami_max_queued_handled_requests,
	                                    orb.m_conf.ami_starving_time);

	        m_pool = new AMIThreadPool(this);
	        m_conf.setListener(m_pool);
	        m_ami_lock_list = new AMILockList(m_pool, this);
	        //m_pool.minThreadsHasChanged();
	        
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
	     * Sets the maximun number of request queued in the AMIManager
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
	     * @return maximun number of request queued in the AMIManager
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
	    public AMIManagerConf getConf()
	    {
	        return m_conf;
	    }

	    /**
	     * @return The request queue of this AMIManager.
	     */
	    public AMILockList getAMILockList()
	    {
	        return m_ami_lock_list;
	    }
	
	    /**
	     * @return The thread pool of this POAManager.
	     */
	    public AMIThreadPool getThreadPool()
	    {
	        return m_pool;
	    }   
	
	    /* ************ ObjectImpl ************* */
	
	    public String toString()
	    {
	        return m_manager_name;
	    }
	}
