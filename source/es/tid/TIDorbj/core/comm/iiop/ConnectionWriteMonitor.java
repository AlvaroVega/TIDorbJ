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

public class ConnectionWriteMonitor
{
	final static int FREE = 0;
	
	final static int WRITTING = 1;
	
	final static int ERROR = 2;
	
	int m_status;
	
	int m_timeout;
	
	COMM_FAILURE m_exception;
	
	public ConnectionWriteMonitor(int timeout)
	{
		m_timeout = timeout;
		m_status = FREE;
		m_exception = null;
	}
	
	public synchronized void initWrite()
		throws WriteTimeout
	{						  		
		long t0 = new java.util.Date().getTime();//msegs
		long t1;
		long dif = 0;
		
		while ( m_status==WRITTING && dif < m_timeout){
			try {
				wait(m_timeout);
			}
			catch (java.lang.InterruptedException ie) {}
			
			t1 = new java.util.Date().getTime();
			dif = t1-t0;
		}
		
		if (m_status == WRITTING) {
			m_status = ERROR;
			m_exception = new COMM_FAILURE("Socket write timeout");
			throw new WriteTimeout();
		}
		
		if (m_status == ERROR)
			throw m_exception;
		
		if (m_status == FREE)
			m_status = WRITTING;		
	}
	
	public synchronized void endWrite()
	{
		m_status = FREE;	        	       
		notify();	       	        		       	
	}
	
	public COMM_FAILURE getException()
	{
		return m_exception;
	}
	
	public synchronized void setException(COMM_FAILURE cf)
	{
		m_exception = cf;
		notifyAll();
	}
	
}
