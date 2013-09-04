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
package es.tid.TIDorbj.core.messaging;

import java.util.LinkedList;
import java.util.List;

import es.tid.TIDorbj.core.comm.giop.RequestId;
import es.tid.TIDorbj.core.poa.POAManagerImpl;
import es.tid.TIDorbj.core.poa.QueueReaderManager;
import es.tid.TIDorbj.core.poa.QueuedRequest;

public class AMILockList
{

    java.util.Hashtable m_table;
    
    LinkedList m_ready_list;
    
    private QueueReaderManager m_queue_read_manager = null;

    private AMIManager m_ami_manager;


    /**
     * Constructor.
     * 
     * @param reader
     *            Object that manages the creation of new readers for this
     *            queue.
     */
    public AMILockList(QueueReaderManager reader, AMIManager ami_manager)
    {
        m_table = new java.util.Hashtable();
        m_ready_list = new LinkedList();
        m_queue_read_manager = reader;
        m_ami_manager = ami_manager;
    }

    synchronized public void put(AMILock lock)
    {
        m_table.put(lock.requestId(), lock);
    }

    synchronized private void putReady(AMILock lock) {
    	m_ready_list.add(lock);
        if (!m_queue_read_manager.createNewReader()) {
            notify();
        }
    }
    
    synchronized public AMILock get(RequestId request_id)
    {
        return (AMILock) m_table.get(request_id);
    }

    // Gets and removes de first element of the list
    synchronized public AMILock getFirstReady() {
    	if (m_ready_list.size()==0) {
            try {
                wait(m_ami_manager.get_starving_time());
            } catch (InterruptedException ie) {}
            	if (m_ready_list.size()==0) {
            		return null;
            }
    	}
    	return (AMILock) m_ready_list.removeFirst();
    }
        
    synchronized public void remove(RequestId request_id)
    {
        m_table.remove(request_id);
    }

    synchronized public int size()
    {
        return m_table.size();
    }

    synchronized public int sizeReady()
    {
        return m_ready_list.size();
    }

    synchronized public AMILockEnumeration elements()
    {
        return new AMILockEnumeration(m_table.elements());
    }
    
    synchronized public void setCompleted (RequestId reqId) {
    	AMILock lockCompleted = (AMILock) m_table.get(reqId);
    	remove(reqId);
    	lockCompleted.setCompleted();
    	putReady(lockCompleted);
    }

}
