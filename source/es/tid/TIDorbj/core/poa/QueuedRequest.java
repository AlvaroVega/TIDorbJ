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
* (C) Copyright 2004 Telef?nica Investigaci?n y Desarrollo
*     S.A.Unipersonal (Telef?nica I+D)
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


import es.tid.TIDorbj.core.comm.ResponseHandler;
import es.tid.TIDorbj.core.policy.PolicyContext;

/**
 * Super class for request that are enqueued for later execution.
 * 
 * @autor Javier Fdz. Mejuto
 * @version 1.0
 */
public abstract class QueuedRequest implements ResponseHandler
{
    //TODO: Should be removed after refactoring
    private POAKey m_poakey;

    private int m_current_child_poa_name_level;

    private boolean m_must_discard = false;

    private org.omg.CORBA.CompletionStatus m_status;

    private POAImpl m_current_poa;
    
    private PolicyContext m_policy_context;
    
    private long m_serial;
    private short m_priority;
    

    //TODO: added by jprojas... 
    private ResponseHandler responseHandler;

    public QueuedRequest(){
        m_current_child_poa_name_level = 0;
        m_poakey = null;
        m_status = org.omg.CORBA.CompletionStatus.COMPLETED_NO;
        m_current_poa = null;
        m_policy_context = null;
        m_serial = 0L;            
    }
    
    public void setPolicyContext(PolicyContext context)
    {
        m_policy_context = context;
    }
    
    public PolicyContext getPolicyContext()
    {
        return m_policy_context;
    }
    
    public void setPriority(short priority) {
        m_priority = priority;
    }
    
    public short getPriority () {
        return m_priority;
    }
    
    /**
     * @param serial the ORB request serial number
     */
    public void setSerial(long serial)
    {
        m_serial = serial;      
    }
    
    /**
     * @return serial the ORB request serial number
     */
    public long getSerial()
    {
        return m_serial;      
    }  

    
    public org.omg.CORBA.CompletionStatus getStatus() {
        return m_status;
    }

    public void setStatus(org.omg.CORBA.CompletionStatus value) {
        if (value != null) {
            m_status = value;
        }
    }

    public void setCurrentPOA( POAImpl currentPOA ){
        this.m_current_poa = currentPOA;
    }
    
    public POAImpl getCurrentPOA() {
        return m_current_poa;
    }

    //TODO: this method name... igh!
    public void nextChildPOA(POAImpl poa) {
        m_current_poa = poa;
        m_current_child_poa_name_level++;
    }

    public String getCurrentChildPOAName() {
        return m_poakey.getPOA(m_current_child_poa_name_level);
    }
    
    public void setPOAKey( POAKey key ){
        this.m_poakey = key;
    }

    public POAKey getPOAKey() {
        return m_poakey;
    }

    public OID getOID() {
        return m_poakey.getOID();
    }

    public boolean isFinalPOA() {
        return m_current_child_poa_name_level >= m_poakey.numberOfPOAs();
    }

    public void setMustDiscard( boolean value ) {
        m_must_discard = value;
    }

    public boolean getMustDiscard() {
        return m_must_discard;
    }

    public void destroy() {
    	//Empty implementation
    }

    

}