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

import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.Object;
import org.omg.PortableServer.CurrentLocalBase;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.CurrentPackage.NoContext;

import es.tid.TIDorbj.core.TIDORB;

/**
 * TIDORB implementation of the Current CORBA Object. The current object
 * accesses to the CurrentInfo stored in the ExecThread, the thread assotiated
 * to the servant execution, returning at each moment the current poa and object
 * id (if exists).
 * 
 * @see CurrentInfo
 * @see ExecThread
 * 
 * @author Juan A. C&aacute;ceres
 */

public class CurrentImpl extends CurrentLocalBase
{

    private TIDORB m_orb;

    private boolean destroyed;

    public CurrentImpl(TIDORB orb)
    {
        m_orb = orb;
    }

    public synchronized void destroy()
    {
        m_orb = null;
        destroyed = true;
    }

    public synchronized org.omg.PortableServer.POA get_POA()
        throws org.omg.PortableServer.CurrentPackage.NoContext
    {
        if (destroyed)
            throw new OBJECT_NOT_EXIST();

        return getCurrentInfo().m_current_poa;
    }

    public synchronized byte[] get_object_id()
        throws org.omg.PortableServer.CurrentPackage.NoContext
    {
        if (destroyed)
            throw new OBJECT_NOT_EXIST();

        return getCurrentInfo().m_current_oid.toByteArray();
    }

    public org.omg.CORBA.Object getReference()
        throws org.omg.PortableServer.CurrentPackage.NoContext
    {
        if (destroyed)
            throw new OBJECT_NOT_EXIST();

        return getCurrentInfo().m_servant._this_object();
    }

    public synchronized org.omg.PortableServer.Servant getServant()
        throws org.omg.PortableServer.CurrentPackage.NoContext
    {
        if (destroyed)
            throw new OBJECT_NOT_EXIST();

        return getCurrentInfo().m_servant;
    }

    /**
     * Looks in the current thread for the <code>CurrentInfo</code> that
     * determines the current poa and object id needed by the
     * <code>Current</code> object.
     * 
     * @return the <code>CurrentInfo</code> object if exits.
     * 
     * @exception org.omg.PortableServer.CurrentPackage.NoContext
     *                if the current thread is not an instance of
     *                <code>ExecThread</code> (it is the orb main thread) or
     *                it is and it is executing a local request, and there is
     *                out of a POA-dispatched operation.
     */

    protected CurrentInfo getCurrentInfo()
        throws org.omg.PortableServer.CurrentPackage.NoContext
    {
        Thread th = Thread.currentThread();

        if (th instanceof ExecThread) { // the current is in a servant
                                        // execthread

            ExecThread exec = (ExecThread) th;

            CurrentInfo info = exec.getCurrentInfo();

            if (info != null)

                return info;
        }

        // error executing a local request or we are in the orb main thread

        throw new org.omg.PortableServer.CurrentPackage.NoContext();
    }

    public boolean inContext()
    {
        Thread th = Thread.currentThread();

        if (th instanceof ExecThread) { // the current is in a servant
                                        // execthread

            ExecThread exec = (ExecThread) th;

            CurrentInfo info = exec.getCurrentInfo();

            return (info != null);
        }

        return false;
    }

	/* (non-Javadoc)
	 * @see org.omg.PortableServer.CurrentOperations#get_reference()
	 */
	public Object get_reference() throws NoContext {
		return this.getReference();
	}

	/* (non-Javadoc)
	 * @see org.omg.PortableServer.CurrentOperations#get_servant()
	 */
	public Servant get_servant() throws NoContext {
		return this.getCurrentInfo().m_servant;
	}

}