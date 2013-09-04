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
package es.tid.TIDorbj.core.poa;

import java.util.Vector;

import org.omg.BiDirPolicy.BOTH;
import org.omg.BiDirPolicy.BidirectionalPolicy;
import org.omg.BiDirPolicy.NORMAL;
import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.PolicyError;
import org.omg.Messaging.QueueOrderPolicy;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POAPackage.*;
import org.omg.PortableServer.ServantLocatorPackage.CookieHolder;

import es.tid.TIDorbj.core.comm.CommunicationDelegate;
import es.tid.TIDorbj.core.comm.CommunicationException;
import es.tid.TIDorbj.core.comm.iiop.IIOPCommunicationLayer;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.iop.TaggedComponent;
import es.tid.TIDorbj.util.Trace;
import es.tid.TIDorbj.core.messaging.PoliciesComponent;
import es.tid.TIDorbj.core.poa.policies.*;
import es.tid.TIDorbj.core.policy.PolicyContext;


/**
 * Implementation of RTPortableServer::POA
 * 
 * @autor Javier Fdz. Mejuto
 * @autor Juan A. Caceres
 * @version 2.0
 */
public class POAImpl extends org.omg.PortableServer.POALocalBase
{

    /**
     * If <code>true</code> the Object has been destroyed and it will throw a
     * <code>org.omg.CORBA.OBJECT_NOT_EXIST<code> exception.
     * Many Objects has the destroy method, this method will change this attribute.
     */

    protected boolean m_destroyed;

    // attributes for all POAs
    public es.tid.TIDorbj.core.TIDORB m_orb = null;

    private String m_name = null;

    private String m_poa_string_name = null;

    private long m_poa_id = 0;

    private byte[] m_id = null;

    private POAKey m_key_seed = null;

    private Vector m_children = null;

    private org.omg.PortableServer.POA m_parent = null;

    private String[] m_path = null;

    private POAManagerImpl m_poa_manager = null;

    private CompletionWaiter m_completion = null;

    private boolean m_destroying = false;

    private boolean m_etherealize = false;

    // attributes depending on policies
    private org.omg.PortableServer.AdapterActivator m_adapter_activator = null;

    private org.omg.PortableServer.ServantManager m_servant_manager = null;

    private org.omg.PortableServer.Servant m_default_servant = null;

    private ActiveObjectMap m_active_object_map = null;

    // OID if SystemPolicy
    private SystemOID m_current_OID;

    // policies
    private IdAssignmentPolicy m_id_assignment_policy = null;

    private IdUniquenessPolicy m_id_uniqueness_policy = null;

    private ImplicitActivationPolicy m_implicit_activation_policy = null;

    private LifespanPolicy m_lifespan_policy = null;

    private RequestProcessingPolicy m_request_processing_policy = null;

    private ServantRetentionPolicy m_servant_retention_policy = null;

    private ThreadPolicy m_thread_policy = null;
           
    private PoliciesComponent referencePolicies = null;

    /**
     * Constructor.
     * 
     * @param orb
     *            The ORB.
     * @param adapter_name
     *            The POA name.
     * @param a_POAManager
     *            The POA manager which this POA belongs to.
     * @param policies
     *            The policies to be used in the POA.
     * @param parent
     *            The parent POA of this POA.
     * @exception org.omg.PortableServer.POAPackage.InvalidPolicy
     *                When policies are invalid.
     */
    public POAImpl(es.tid.TIDorbj.core.TIDORB orb,
                   java.lang.String adapter_name,
                   org.omg.PortableServer.POAManager a_POAManager,
                   org.omg.CORBA.Policy[] policies,
                   org.omg.PortableServer.POA parent)
        throws org.omg.PortableServer.POAPackage.InvalidPolicy
    {
        int length;
        this.m_orb = orb;
        this.m_name = adapter_name;
        this.m_poa_id = System.currentTimeMillis();
        this.m_children = new Vector();
        this.m_poa_manager = (POAManagerImpl) a_POAManager;
        m_poa_manager.addPOA(this);
        this.m_parent = parent;
        if (parent == null)
            this.m_path = new String[0];
        else {
            length = (((POAImpl) parent).getPath()).length;
            this.m_path = new String[length + 1];
            System.arraycopy(((POAImpl) parent).getPath(), 0, this.m_path, 0,
                             length);
            this.m_path[length] = adapter_name;
        }
        this.m_completion = new CompletionWaiter(this.m_orb);
        
        this.referencePolicies = new PoliciesComponent(new PolicyContext(null));

        // Set policies
        if (policies != null) {
            for (int i = 0; i < policies.length; i++) {
                if (policies[i] == null)
                    throw new BAD_PARAM("Null policy reference");

                switch (policies[i].policy_type())
                {
                    case ID_ASSIGNMENT_POLICY_ID.value:
                        if (m_id_assignment_policy != null) {
                            throw new InvalidPolicy();
                        }
                        m_id_assignment_policy = 
                            (IdAssignmentPolicy) policies[i];
                    break;
                    case ID_UNIQUENESS_POLICY_ID.value:
                        if (m_id_uniqueness_policy != null) {
                            throw new InvalidPolicy();
                        }
                        m_id_uniqueness_policy = 
                            (IdUniquenessPolicy) policies[i];
                    break;
                    case IMPLICIT_ACTIVATION_POLICY_ID.value:
                        if (m_implicit_activation_policy != null) {
                            throw new InvalidPolicy();
                        }
                        m_implicit_activation_policy =
                            (ImplicitActivationPolicy) policies[i];
                    break;
                    case org.omg.PortableServer.LIFESPAN_POLICY_ID.value:
                        if (m_lifespan_policy != null) {
                            throw new InvalidPolicy();
                        }
                        m_lifespan_policy = (LifespanPolicy) policies[i];
                    break;
                    case REQUEST_PROCESSING_POLICY_ID.value:
                        if (m_request_processing_policy != null) {
                            throw new InvalidPolicy();
                        }
                        m_request_processing_policy =
                            (RequestProcessingPolicy) policies[i];
                    break;
                    case SERVANT_RETENTION_POLICY_ID.value:
                        if (m_servant_retention_policy != null) {
                            throw new InvalidPolicy();
                        }
                        m_servant_retention_policy = 
                            (ServantRetentionPolicy) policies[i];
                    break;
                    case THREAD_POLICY_ID.value:
                        if (m_thread_policy != null) {
                            throw new InvalidPolicy();
                        }
                        m_thread_policy = (ThreadPolicy) policies[i];
                    break;
                    
                    default:                        
                        try {
                            referencePolicies.getPolicies().setPolicy(policies[i]);
                        }
                        catch (PolicyError e) {
                            throw new InvalidPolicy();
                        }          
                }
            }
        }
        // Set default policies
        if (m_id_assignment_policy == null) {
            m_id_assignment_policy = 
                create_id_assignment_policy(IdAssignmentPolicyValue.SYSTEM_ID);
        }

        if (m_id_uniqueness_policy == null) {
            m_id_uniqueness_policy = 
                create_id_uniqueness_policy(IdUniquenessPolicyValue.UNIQUE_ID);
        }

        if (m_implicit_activation_policy == null) {
            m_implicit_activation_policy = 
                create_implicit_activation_policy(
                  ImplicitActivationPolicyValue.NO_IMPLICIT_ACTIVATION);
        }

        if (m_lifespan_policy == null) {
            m_lifespan_policy = 
                create_lifespan_policy(LifespanPolicyValue.TRANSIENT);
        }

        if (m_request_processing_policy == null) {
            m_request_processing_policy =
                create_request_processing_policy(
                  RequestProcessingPolicyValue.USE_ACTIVE_OBJECT_MAP_ONLY);
        }

        if (m_servant_retention_policy == null) {
            m_servant_retention_policy = 
                create_servant_retention_policy(
                  ServantRetentionPolicyValue.RETAIN);
        }
        if (m_thread_policy == null) {
            m_thread_policy = 
                create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL);
        }

        if (m_servant_retention_policy.value() 
            == ServantRetentionPolicyValue.RETAIN) {

            m_active_object_map = new ActiveObjectMap();
        }

        if (m_id_assignment_policy.value() == IdAssignmentPolicyValue.SYSTEM_ID)
            m_current_OID = new SystemOID(m_poa_id, 0);

        // Wrong Policies
        if ((m_implicit_activation_policy.value() 
            == ImplicitActivationPolicyValue.IMPLICIT_ACTIVATION)
            && ((m_id_assignment_policy.value()
                == IdAssignmentPolicyValue.USER_ID) 
                || (m_servant_retention_policy.value() 
                    == ServantRetentionPolicyValue.NON_RETAIN))) {
            throw new InvalidPolicy();
        }
        if ((m_servant_retention_policy.value()
            == ServantRetentionPolicyValue.NON_RETAIN)
            && (m_request_processing_policy.value()
                == RequestProcessingPolicyValue.USE_ACTIVE_OBJECT_MAP_ONLY)) {
            throw new InvalidPolicy();
        }

        // Generate id
        m_id = createKey(null).toString().getBytes();
    }

    /**
     * Create a POA as a children of this POA.
     * 
     * @param adapter_name
     *            The POA name of the new POA.
     * @param a_POAManager
     *            The POA manager which the new POA belongs to.
     * @param policies
     *            The policies to be used in the new POA.
     * @exception org.omg.PortableServer.POAPackage.AdapterAlreadyExists
     *                When this POA has alreay a children named adapter_name.
     * @exception org.omg.PortableServer.POAPackage.InvalidPolicy
     *                When policies are invalid.
     */
    synchronized public org.omg.PortableServer.POA
    	create_POA(java.lang.String adapter_name,
    	           org.omg.PortableServer.POAManager a_POAManager,
    	           org.omg.CORBA.Policy[] policies)
        throws org.omg.PortableServer.POAPackage.AdapterAlreadyExists,
        org.omg.PortableServer.POAPackage.InvalidPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if ((adapter_name == null))
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (findChildren(adapter_name) != null) {
            throw new org.omg.PortableServer.POAPackage.AdapterAlreadyExists();
        }
        POAManagerImpl POAmgr = null;
        if (a_POAManager == null) {
            POAmgr = new POAManagerImpl(m_orb);
        } else {
            if(! (a_POAManager instanceof POAManagerImpl)) {
                throw new BAD_PARAM("Invalid POAManager: it is not from TIDORB");
            }
            
            POAmgr = (POAManagerImpl) a_POAManager;
            
            if(POAmgr.m_orb != m_orb) {
                throw new BAD_PARAM("Invalid POAManager: it is not from the same instance of TIDORB");
            }
                
        }
        POAImpl newPOA =
            new POAImpl(m_orb, adapter_name, POAmgr, policies, this);

        if (m_destroying) {
            newPOA.destroy(false, false);
        } else {
            m_children.addElement(newPOA);
        }
        
        if(a_POAManager == null) { // recently created
	        PolicyContext context = newPOA.getPolicyContext();
	        
	        QueueOrderPolicy queuePolicy = context.getQueueOrderPolicy();
	        
	        if(queuePolicy != null) {
	            POAmgr.setQueueOrderPolicy(queuePolicy);  
	        }
        }

        return newPOA;
    }

    /**
     * @return
     */
    public PolicyContext getPolicyContext()
    {        
        return this.referencePolicies.getPolicies();
    }

    /**
     * Find a POA among this POA's children.
     * 
     * @param adapter_name
     *            The POA name.
     * @param activate_it
     *            If it is true find_POA() will try to activate the POA in case
     *            it can't find it.
     * @exception org.omg.PortableServer.POAPackage.AdapterNonExistent
     *                When this POA has not a children named adapter_name.
     */
    public org.omg.PortableServer.POA find_POA(java.lang.String adapter_name,
                                               boolean activate_it)
        throws org.omg.PortableServer.POAPackage.AdapterNonExistent
    {
        synchronized (this) {
            if (m_destroyed)
                throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);
        }

        if (adapter_name == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        org.omg.PortableServer.POA poa = findChildren(adapter_name);

        if (poa == null) {
            if ((activate_it) && (m_adapter_activator != null)) {
                try {
                    m_adapter_activator.unknown_adapter(this, adapter_name);
                }
                catch (Exception e) {
                    throw new org.omg.CORBA.OBJ_ADAPTER(e.getMessage());
                }
                poa = findChildren(adapter_name);
                // see if _activator has created the POA
                if (poa == null) {
                    // AdapterActivator failed!
                    throw new AdapterNonExistent();
                }

                if (m_destroying)
                    poa.destroy(false, false);

                return poa;

            } else {
                // AdapterActivator does not exist!
                throw new AdapterNonExistent();
            }
        } else
            return poa;
    }

    /**
     * Destroy the POA.
     * 
     * @param etherealize_objects
     *            If true, it must etherealize all objects.
     * @param wait_for_completion
     *            Wait-for-completion flag.
     */
    public void destroy(boolean etherealize_objects, 
                        boolean wait_for_completion)
    {
        synchronized (this) {
            if (!m_destroying) {
                for (int i = 0; i < m_children.size(); i++) {
                    org.omg.PortableServer.POA poa=(org.omg.PortableServer.POA)
                    m_children.elementAt(i);
                    poa.destroy(etherealize_objects, wait_for_completion);
                }
                m_etherealize = etherealize_objects;
            }
            m_destroying = true;
        }

        if (wait_for_completion) {
            // Wait until there are no executing requests
            if (m_completion.conditionToWait()) {
                m_completion.waitForCompletion();
            } else {
                throw new BAD_INV_ORDER("Can't waitForCompletion!");
            }

            synchronized (this) {

                if (!m_destroyed) {
                    // POA has not been destroyed yet -> destroy it now
                    trueDestroy();
                }
            }
        } else {
            synchronized (this) {
                boolean PoaNotInUse = m_completion.getActiveRequests() <= 0;
                if (PoaNotInUse) {
                    trueDestroy();
                }
            }

        }
    }

    /* ************ Policy creation ************* */

    public org.omg.PortableServer.ThreadPolicy 
    	create_thread_policy(org.omg.PortableServer.ThreadPolicyValue value)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (value == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new es.tid.TIDorbj.core.poa.policies.ThreadPolicyImpl(value);
    }

    public org.omg.PortableServer.LifespanPolicy 
    	create_lifespan_policy(org.omg.PortableServer.LifespanPolicyValue value)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (value == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new es.tid.TIDorbj.core.poa.policies.LifespanPolicyImpl(value);
    }

    public org.omg.PortableServer.IdUniquenessPolicy
    	create_id_uniqueness_policy
    		(org.omg.PortableServer.IdUniquenessPolicyValue value)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (value == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new 
        	es.tid.TIDorbj.core.poa.policies.IdUniquenessPolicyImpl(value);
    }

    public org.omg.PortableServer.IdAssignmentPolicy
    	create_id_assignment_policy(
    	   org.omg.PortableServer.IdAssignmentPolicyValue value)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (value == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new 
        	es.tid.TIDorbj.core.poa.policies.IdAssignmentPolicyImpl(value);
    }

    public org.omg.PortableServer.ImplicitActivationPolicy
    	create_implicit_activation_policy(
    	    org.omg.PortableServer.ImplicitActivationPolicyValue value)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (value == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new 
          es.tid.TIDorbj.core.poa.policies.ImplicitActivationPolicyImpl(value);
    }

    public org.omg.PortableServer.ServantRetentionPolicy 
      create_servant_retention_policy(
         org.omg.PortableServer.ServantRetentionPolicyValue value)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (value == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new 
          es.tid.TIDorbj.core.poa.policies.ServantRetentionPolicyImpl(value);
    }

    public org.omg.PortableServer.RequestProcessingPolicy 
      create_request_processing_policy(
         org.omg.PortableServer.RequestProcessingPolicyValue value)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (value == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new 
          es.tid.TIDorbj.core.poa.policies.RequestProcessingPolicyImpl(value);
    }

    /* ************ Accessors ************* */

    public long getId()
    {
        return m_poa_id;
    }

    public byte[] id()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        return m_id;
    }

    public java.lang.String the_name()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        return m_name;
    }

    public org.omg.PortableServer.POA the_parent()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        return m_parent;
    }

    public String[] getPath()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        return m_path;
    }

    synchronized public org.omg.PortableServer.POA[] the_children()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        org.omg.PortableServer.POA[] childrenArray =
            new org.omg.PortableServer.POA[m_children.size()];
        m_children.copyInto(childrenArray);
        return childrenArray;
    }

    public org.omg.PortableServer.POAManager the_POAManager()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        return m_poa_manager;
    }

    synchronized public org.omg.PortableServer.AdapterActivator the_activator()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        return m_adapter_activator;
    }

    synchronized public void 
    	the_activator(org.omg.PortableServer.AdapterActivator the_activator)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (the_activator == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        m_adapter_activator = the_activator;
    }

    synchronized public org.omg.PortableServer.ServantManager
    	get_servant_manager()
        throws org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (m_request_processing_policy.value()
            != RequestProcessingPolicyValue.USE_SERVANT_MANAGER) {
            throw new org.omg.PortableServer.POAPackage.WrongPolicy();
        }

        return m_servant_manager;
    }

    synchronized public void 
    	set_servant_manager(org.omg.PortableServer.ServantManager imgr)
        throws org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (imgr == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_request_processing_policy.value()
            != RequestProcessingPolicyValue.USE_SERVANT_MANAGER) {
            throw new org.omg.PortableServer.POAPackage.WrongPolicy();
        }
        if (m_servant_retention_policy.value()
            == ServantRetentionPolicyValue.RETAIN) {
            if (!(imgr instanceof org.omg.PortableServer.ServantActivator)) {
                throw new org.omg.CORBA.OBJ_ADAPTER();
            }
        }
        if (m_servant_retention_policy.value()
            == ServantRetentionPolicyValue.NON_RETAIN) {
            if (!(imgr instanceof org.omg.PortableServer.ServantLocator)) {
                throw new org.omg.CORBA.OBJ_ADAPTER();
            }
        }
        if (m_servant_manager != null) {
            throw new org.omg.CORBA.BAD_INV_ORDER();
        }
        m_servant_manager = imgr;
    }

    synchronized public org.omg.PortableServer.Servant get_servant()
        throws org.omg.PortableServer.POAPackage.NoServant,
        org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (m_request_processing_policy.value() 
            != RequestProcessingPolicyValue.USE_DEFAULT_SERVANT) {
            throw new org.omg.PortableServer.POAPackage.WrongPolicy();
        }
        if (m_default_servant == null) {
            throw new org.omg.PortableServer.POAPackage.NoServant();
        }
        return m_default_servant;
    }

    synchronized public void 
    	set_servant(org.omg.PortableServer.Servant p_servant)
        throws org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (p_servant == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_request_processing_policy.value()
            != RequestProcessingPolicyValue.USE_DEFAULT_SERVANT) {
            throw new org.omg.PortableServer.POAPackage.WrongPolicy();
        }
        m_default_servant = p_servant;
        // Servant delegate creation (oid == null ?)
        ServantDelegate delegate = new ServantDelegate(this, null);
        m_default_servant._set_delegate(delegate);
    }

    /* ************ Activation ************* */

    /**
     * Activate an object in this POA.
     * 
     * @param p_servant
     *            Servant.
     * @return Returns an ORB generated object Id.
     * @exception org.omg.PortableServer.POAPackage.ServantAlreadyActive
     *                If p_servant is already active in this POA.
     * @exception org.omg.PortableServer.POAPackage.WrongPolicy
     *                If POA policies do not allow this operation.
     */
    synchronized public byte[]
        activate_object(org.omg.PortableServer.Servant p_servant)
        throws org.omg.PortableServer.POAPackage.ServantAlreadyActive,
        org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (p_servant == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_id_assignment_policy.value()
            != IdAssignmentPolicyValue.SYSTEM_ID) {
            throw new org.omg.PortableServer.POAPackage.WrongPolicy();
        }
        OID id = nextOID();
        try {
            activate_object_with_id(id, p_servant);
        }
        catch (org.omg.PortableServer.POAPackage.ObjectAlreadyActive e) {
            // this should never happen
            throw new
              INTERNAL("ObjectAlreadyActive thrown with SYSTEM_ID policy!!");
        }
        return id.toByteArray();
    }

    /**
     * Activate an object in this POA.
     * 
     * @param id
     *            User created object Id.
     * @param p_servant
     *            Servant.
     * @exception org.omg.PortableServer.POAPackage.ObjectAlreadyActive
     *                If id is already active in this POA.
     * @exception org.omg.PortableServer.POAPackage.ServantAlreadyActive
     *                If p_servant is already active in this POA.
     * @exception org.omg.PortableServer.POAPackage.WrongPolicy
     *                If POA policies do not allow this operation.
     */
    synchronized public void
        activate_object_with_id(byte[] id,
                                org.omg.PortableServer.Servant p_servant)
        throws org.omg.PortableServer.POAPackage.ServantAlreadyActive,
        org.omg.PortableServer.POAPackage.ObjectAlreadyActive,
        org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if ((id == null) || (p_servant == null))
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (id.length == 0)
            throw new BAD_PARAM("Invalid id length: 0", 0,
                                CompletionStatus.COMPLETED_NO);

        activate_object_with_id(new OID(id), p_servant);

    }

    /**
     * Activate an object in this POA.
     * 
     * @param id
     *            User created object Id.
     * @param p_servant
     *            Servant.
     * @exception org.omg.PortableServer.POAPackage.ObjectAlreadyActive
     *                If id is already active in this POA.
     * @exception org.omg.PortableServer.POAPackage.ServantAlreadyActive
     *                If p_servant is already active in this POA.
     * @exception org.omg.PortableServer.POAPackage.WrongPolicy
     *                If POA policies do not allow this operation.
     */

    protected void 
      activate_object_with_id(OID oid,
                              org.omg.PortableServer.Servant p_servant)
        throws org.omg.PortableServer.POAPackage.ServantAlreadyActive,
        org.omg.PortableServer.POAPackage.ObjectAlreadyActive,
        org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (m_servant_retention_policy.value() 
            != ServantRetentionPolicyValue.RETAIN) {
            throw new org.omg.PortableServer.POAPackage.WrongPolicy();
        }
        boolean uniqueId =
            m_id_uniqueness_policy.value() == IdUniquenessPolicyValue.UNIQUE_ID;
        // See if there a destruction process running

        synchronized (m_active_object_map) {
            java.lang.Object semaphore = m_active_object_map.isDestroying(oid);
            if (semaphore != null) {
                synchronized (semaphore) {
                    try {
                        semaphore.wait();
                    }
                    catch (Exception e) {}
                }
            }
        }
        synchronized (this) {
            m_active_object_map.put(oid, p_servant, uniqueId);
            // Servant delegate creation
            ServantDelegate delegate = new ServantDelegate(this,
                                                           oid.toByteArray());
            p_servant._set_delegate(delegate);
        }
    }

    /**
     * Deactivate an object in this POA.
     * 
     * @param oid
     *            The object Id.
     * @exception org.omg.PortableServer.POAPackage.ObjectNotActive
     *                If id is not active in this POA.
     * @exception org.omg.PortableServer.POAPackage.WrongPolicy
     *                If POA policies do not allow this operation.
     */
    synchronized public void deactivate_object(byte[] oid)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive,
        org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (oid == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (oid.length == 0)
            throw new BAD_PARAM("Invalid oid length: 0", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_servant_retention_policy.value() 
            != ServantRetentionPolicyValue.RETAIN) {
            throw new org.omg.PortableServer.POAPackage.WrongPolicy();
        }

        OID tid_oid = new OID(oid);

        synchronized (m_active_object_map) {
            if (m_active_object_map.isDestroying(tid_oid) == null) {
                m_active_object_map.destroy(tid_oid);
                tryToRemoveObject(tid_oid);
            }
        }
    }

    /* ************ Id conversion ************* */

    /**
     * Create an object reference for a repository Id (using a new object Id).
     * 
     * @param intf
     *            The repository Id
     * @exception org.omg.PortableServer.POAPackage.WrongPolicy
     *                If POA policies do not allow this operation.
     */
    synchronized public org.omg.CORBA.Object
    	create_reference(java.lang.String intf)
        throws org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (intf == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (intf.length() == 0)
            throw new BAD_PARAM("Invalid id length: 0", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_id_assignment_policy.value()
            != IdAssignmentPolicyValue.SYSTEM_ID) {
            throw new org.omg.PortableServer.POAPackage.WrongPolicy();
        }
        OID newOid = nextOID();
        return createReferenceWithIdAux(newOid, intf);
    }

    /**
     * Create an object reference for a repository Id using a user defined
     * object Id.
     * 
     * @param oid
     *            The object Id
     * @param intf
     *            The repository Id
     */
    public org.omg.CORBA.Object create_reference_with_id(byte[] oid,
                                                         java.lang.String intf)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if ((oid == null) || (intf == null))
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (oid.length == 0)
            throw new BAD_PARAM("Invalid oid length: 0", 0,
                                CompletionStatus.COMPLETED_NO);

        if (intf.length() == 0)
            throw new BAD_PARAM("Invalid intf length: 0", 0,
                                CompletionStatus.COMPLETED_NO);

        return create_reference_with_id(new OID(oid), intf);
    }

    /**
     * Create an object reference for a repository Id using a user defined
     * object Id.
     * 
     * @param oid
     *            The object Id
     * @param intf
     *            The repository Id
     * @exception org.omg.PortableServer.POAPackage.WrongPolicy
     *                If POA policies do not allow this operation.
     */

    synchronized protected org.omg.CORBA.Object
    	create_reference_with_id(OID oid,
    	                         java.lang.String intf)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (m_id_assignment_policy.value() 
            == IdAssignmentPolicyValue.SYSTEM_ID) {

            SystemOID s_oid = SystemOID.fromOID(oid);

            if (s_oid == null)
                throw new BAD_PARAM("Invalid System OID", 0,
                                    CompletionStatus.COMPLETED_NO);

            if (m_lifespan_policy.value() == LifespanPolicyValue.TRANSIENT) {
                if ((s_oid.getPOAId() != m_poa_id)
                    || (currentOID().getSerial() < s_oid.getSerial())) {
                    throw new org.omg.CORBA.BAD_PARAM();
                }
            } else { // PERSISTENT
                if ((s_oid.getPOAId() == m_poa_id)
                    && (currentOID().getSerial() < s_oid.getSerial())) {
                    throw new org.omg.CORBA.BAD_PARAM();
                }
            }

            return createReferenceWithIdAux(s_oid, intf);
        }

        return createReferenceWithIdAux(oid, intf);
    }

    /**
     * Creates a new ObjectKey with the given oid.
     * <p>
     * If the Lifespan policy is TRANSIENT, the poa_id is set to remember that
     * the key is only valid in the current poa context.
     */

    protected POAKey createKey(OID oid)
    {
        if (m_key_seed == null) {
            if (m_lifespan_policy.value() == LifespanPolicyValue.TRANSIENT)
                m_key_seed = new POAKey(this, m_poa_id, null);
            else
                m_key_seed = new POAKey(this, 0, null);
        }

        POAKey key = null;

        try {
            key = (POAKey) m_key_seed.clone();
        }
        catch (CloneNotSupportedException cnse) {
            throw new org.omg.CORBA.INTERNAL();
        }

        key.setOID(oid);
        return key;
    }

    /**
     * Create an object reference for a repository Id using a user defined
     * object Id.
     * 
     * @param oid
     *            The object Id
     * @param intf
     *            The repository Id
     */
    protected org.omg.CORBA.Object createReferenceWithIdAux( OID oid, java.lang.String intf)
    {
        POAKey poakey = createKey(oid);
        IOR ior; 
        
        TaggedComponent[] poaComponents = null;
        
        if(referencePolicies.getPolicies().getSize() > 0) {
            poaComponents =  new TaggedComponent[1];
            poaComponents[0] = referencePolicies;
        }
        
        try {
            
            ior = m_orb.getCommunicationManager()
                      .getLayerById( IIOPCommunicationLayer.ID )
                          .createIOR(intf, poakey, poaComponents);            
            
        } catch ( CommunicationException ce ) {
            throw new INTERNAL( 
                "Unable to create ior:" + ce.getMessage(), 
                0, 
                CompletionStatus.COMPLETED_NO 
            );
        }
                
        return es.tid.TIDorbj.core.ObjectImpl.fromIOR(m_orb, ior);
    }

    /**
     * Get the object Id of a given servant.
     * 
     * @param p_servant
     *            The servant.
     * @exception org.omg.PortableServer.POAPackage.ServantNotActive
     *                If p_servant is not active yet.
     * @exception org.omg.PortableServer.POAPackage.WrongPolicy
     *                If POA policies do not allow this operation.
     */
    synchronized public byte[] 
        servant_to_id(org.omg.PortableServer.Servant p_servant)
        throws org.omg.PortableServer.POAPackage.ServantNotActive,
        org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (p_servant == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        boolean hasRetain = (m_servant_retention_policy.value() 
            == ServantRetentionPolicyValue.RETAIN);
        
        boolean hasUniqueId = (m_id_uniqueness_policy.value() 
            == IdUniquenessPolicyValue.UNIQUE_ID);
        
        boolean hasImplicitActivation =(m_implicit_activation_policy.value()
                == ImplicitActivationPolicyValue.IMPLICIT_ACTIVATION);
        
        boolean hasDefaultServant = (m_request_processing_policy.value()
            	== RequestProcessingPolicyValue.USE_DEFAULT_SERVANT);

        if (!(hasDefaultServant 
            || (hasRetain && (hasUniqueId || hasImplicitActivation))))
            throw new org.omg.PortableServer.POAPackage.WrongPolicy();

        if (hasRetain) {
            // 1st behaviour
            if (hasUniqueId) {
                OID oid = m_active_object_map.get(p_servant);
                if (oid != null) {
                    return oid.toByteArray();
                }
            }
            // 2nd behaviour
            if (hasImplicitActivation) {
                OID oid = m_active_object_map.get(p_servant);
                if ((oid == null) || (!hasUniqueId)) {
                    OID newOid = nextOID();
                    try {
                        activate_object_with_id(newOid, p_servant);
                        return newOid.toByteArray();
                    }
                    catch (ObjectAlreadyActive e) {
                        throw new org.omg.CORBA.INTERNAL();
                        // this should never happen
                    }
                    catch (ServantAlreadyActive e) {
                        throw new org.omg.CORBA.INTERNAL();
                        // this should never happen
                    }
                }
            }
        }
        // 3rd behaviour
        if (hasDefaultServant && (m_default_servant == p_servant)) {
            try {
                org.omg.PortableServer.Servant servant = 
                    m_orb.initPOACurrent().getServant();
                if (servant == m_default_servant) {
                    // operation invoked on the default servant
                    return m_orb.initPOACurrent().get_object_id();
                }
            }
            catch (Exception e) {
                throw new org.omg.PortableServer.POAPackage.ServantNotActive();
            }
        }

        // 4th behaviour
        throw new org.omg.PortableServer.POAPackage.ServantNotActive();
    }

    /**
     * Get the object reference of a given servant.
     * 
     * @param p_servant
     *            The servant.
     * @exception org.omg.PortableServer.POAPackage.ServantNotActive
     *                If p_servant is not active yet.
     * @exception org.omg.PortableServer.POAPackage.WrongPolicy
     *                If POA policies do not allow this operation.
     */
    synchronized public org.omg.CORBA.Object
    	servant_to_reference(org.omg.PortableServer.Servant p_servant)
        throws org.omg.PortableServer.POAPackage.ServantNotActive,
        org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (p_servant == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        boolean hasRetain = (m_servant_retention_policy.value()
            == ServantRetentionPolicyValue.RETAIN);
        boolean hasUniqueId = (m_id_uniqueness_policy.value()
            == IdUniquenessPolicyValue.UNIQUE_ID);
        boolean hasImplicitActivation = (m_implicit_activation_policy.value()
            == ImplicitActivationPolicyValue.IMPLICIT_ACTIVATION);

        if (!(hasRetain && (hasUniqueId || hasImplicitActivation))) {
            throw new org.omg.PortableServer.POAPackage.WrongPolicy();
        }

        // 1st behaviour
        if (hasUniqueId) {
            OID oid = m_active_object_map.get(p_servant);
            if (oid != null) {
                return create_reference_with_id(oid.toByteArray(),
                                                getRepositoryId(p_servant));
            }
        }

        // 2nd behaviour
        if (hasImplicitActivation) {
            OID oid = m_active_object_map.get(p_servant);
            if ((oid == null) || (!hasUniqueId)) {
                OID newOid = nextOID();
                try {
                    activate_object_with_id(newOid, p_servant);
                    return create_reference_with_id(newOid,
                                                    getRepositoryId(p_servant));
                }
                catch (ObjectAlreadyActive e) {
                    throw new org.omg.CORBA.INTERNAL();
                    // this should never happen
                }
                catch (ServantAlreadyActive e) {
                    throw new org.omg.CORBA.INTERNAL();
                    // this should never happen
                }
            }
        }
        // 3rd behaviour
        try {
            org.omg.PortableServer.Servant servant = m_orb.initPOACurrent()
                                                          .getServant();

            if (servant == m_default_servant) {
                // operation invoked on the default servant
                byte[] currentOID = m_orb.initPOACurrent().get_object_id();
                return create_reference_with_id(currentOID,
                                                getRepositoryId(servant));
            }
        }
        catch (Exception e) {
            throw new org.omg.PortableServer.POAPackage.ServantNotActive();
        }

        // 4th behaviour
        throw new org.omg.PortableServer.POAPackage.ServantNotActive();
    }

    /**
     * Get the servant of a given object reference.
     * 
     * @param reference
     *            The object reference.
     * @exception org.omg.PortableServer.POAPackage.ObjectNotActive
     *                If object is not active yet.
     * @exception org.omg.PortableServer.POAPackage.WrongAdapter
     *                If the object reference does not belong to this POA.
     * @exception org.omg.PortableServer.POAPackage.WrongPolicy
     *                If POA policies do not allow this operation.
     */
    synchronized public org.omg.PortableServer.Servant
    reference_to_servant(org.omg.CORBA.Object reference)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive,
        org.omg.PortableServer.POAPackage.WrongAdapter,
        org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (reference == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        byte[] oid = reference_to_id(reference);
        return id_to_servant(oid);
    }

    /**
     * Get the object Id of a given object reference.
     * 
     * @param reference
     *            The object reference.
     * @exception org.omg.PortableServer.POAPackage.WrongAdapter
     *                If the object reference does not belong to this POA.
     * @exception org.omg.PortableServer.POAPackage.WrongPolicy
     *                If POA policies do not allow this operation.
     */
    synchronized public byte[] reference_to_id(org.omg.CORBA.Object reference)
        throws org.omg.PortableServer.POAPackage.WrongAdapter,
        org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (reference == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        try {
            org.omg.CORBA.portable.ObjectImpl obj = 
                (org.omg.CORBA.portable.ObjectImpl) reference;
            CommunicationDelegate delegate = 
                (CommunicationDelegate) obj
                                                                                              ._get_delegate();
            IOR ior = delegate.getReference();

            POAKey poaKey = POAKey.createKey( ior.getObjectKey().getMarshaledKey() );
            POAKey poaKey2 = createKey(null);
            if (!poaKey2.samePOA(poaKey)) {
                throw new org.omg.PortableServer.POAPackage.WrongAdapter();
            }
            return poaKey.getOID().toByteArray();
        }
        catch (Exception e) {
            throw new org.omg.PortableServer.POAPackage.WrongAdapter();
        }
    }

    /**
     * Get the servant of a given object Id.
     * 
     * @param oid
     *            The object Id.
     * @exception org.omg.PortableServer.POAPackage.ObjectNotActive
     *                If object is not active yet.
     * @exception org.omg.PortableServer.POAPackage.WrongPolicy
     *                If POA policies do not allow this operation.
     */
    public org.omg.PortableServer.Servant id_to_servant(byte[] oid)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive,
        org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (oid == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (oid.length == 0)
            throw new BAD_PARAM("Invalid oid length: 0", 0,
                                CompletionStatus.COMPLETED_NO);

        return id_to_servant(new OID(oid));
    }

    /**
     * Get the servant of a given object Id.
     * 
     * @param oid
     *            The object Id.
     * @exception org.omg.PortableServer.POAPackage.ObjectNotActive
     *                If object is not active yet.
     * @exception org.omg.PortableServer.POAPackage.WrongPolicy
     *                If POA policies do not allow this operation.
     */
    synchronized protected org.omg.PortableServer.Servant id_to_servant(OID oid)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive,
        org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (( m_servant_retention_policy.value()  != ServantRetentionPolicyValue.RETAIN) && 
        	( m_request_processing_policy.value() != RequestProcessingPolicyValue.USE_DEFAULT_SERVANT)) {
            throw new org.omg.PortableServer.POAPackage.WrongPolicy();
        }
        if ((m_servant_retention_policy.value()
            == ServantRetentionPolicyValue.RETAIN)
            && (m_active_object_map.isActive(oid))) {
            return m_active_object_map.get(oid);
        }
        if ((m_request_processing_policy.value()
            == RequestProcessingPolicyValue.USE_DEFAULT_SERVANT)
            && (m_default_servant != null)) {
            // Default servant invocation (oid == current request oid ??)
            ServantDelegate srv_delegate = 
                (ServantDelegate) m_default_servant._get_delegate();
            srv_delegate.setObjectId(oid.toByteArray());
            return m_default_servant;
        }
        throw new org.omg.PortableServer.POAPackage.ObjectNotActive();
    }

    /**
     * Get the object reference of a given object Id.
     * 
     * @param oid
     *            The object Id.
     * @exception org.omg.PortableServer.POAPackage.ObjectNotActive
     *                If object is not active yet.
     * @exception org.omg.PortableServer.POAPackage.WrongPolicy
     *                If POA policies do not allow this operation.
     */
    synchronized public org.omg.CORBA.Object id_to_reference(byte[] oid)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive,
        org.omg.PortableServer.POAPackage.WrongPolicy
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (oid == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (oid.length == 0)
            throw new BAD_PARAM("Invalid oid length: 0", 0,
                                CompletionStatus.COMPLETED_NO);

        OID tid_oid = new OID(oid);

        if (m_servant_retention_policy.value() 
            != ServantRetentionPolicyValue.RETAIN) {
            throw new org.omg.PortableServer.POAPackage.WrongPolicy();
        }
        if (!m_active_object_map.isActive(tid_oid)) {
            throw new org.omg.PortableServer.POAPackage.ObjectNotActive();
        }
        org.omg.PortableServer.Servant servant =
            m_active_object_map.get(tid_oid);
        return create_reference_with_id(tid_oid, getRepositoryId(servant));
    }

    
   
    
    /* ************ TIDorb methods ************* */

    /**
     * Finds the servant for an object Id. Executes all needed activations,
     * according to the policies of this POA.
     * 
     * @param oid
     *            The object Id.
     * @param operation
     *            The name of the operation invoked (if any).
     * @param cookieHolder
     *            The CookieHolder (only if ServantLocator is needed).
     * @param servantLocatorUsed
     *            Returns true if ServantLocator has been used.
     * @return The servant.
     * @exception org.omg.PortableServer.POAPackage.ObjectNotActive
     *                If it tries to use a inactive object.
     * @exception org.omg.PortableServer.POAPackage.ObjectAlreadyActive
     *                If it tries to activate an active object.
     * @exception org.omg.PortableServer.ForwardRequest
     *                It can be thrown by incarnate().
     */
    synchronized protected org.omg.PortableServer.Servant
    	find_servant(OID oid,
    	             String operation,
    	             CookieHolder cookieHolder,
    	             org.omg.CORBA.BooleanHolder servantLocatorUsed)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive,
        org.omg.PortableServer.POAPackage.ObjectAlreadyActive,
        org.omg.PortableServer.ForwardRequest
    {

        servantLocatorUsed.value = false;
        org.omg.PortableServer.Servant servant = null;

        try {

            if ((m_servant_retention_policy.value()
                == ServantRetentionPolicyValue.RETAIN)) {
                // RETAIN && USE_ACTIVE_OBJECT_MAP_ONLY
                if (m_request_processing_policy.value()
                    == RequestProcessingPolicyValue.USE_ACTIVE_OBJECT_MAP_ONLY){
                    servant = id_to_servant(oid);
                    return servant;
                }

                // RETAIN && USE_DEFAULT_SERVANT
                if (m_request_processing_policy.value()
                    == RequestProcessingPolicyValue.USE_DEFAULT_SERVANT) {
                    servant = id_to_servant(oid);
                    return servant;
                }

                // RETAIN && USE_SERVANT_MANAGER
                if (m_request_processing_policy.value()
                    == RequestProcessingPolicyValue.USE_SERVANT_MANAGER) {
                    if (m_servant_manager == null) {
                        throw new org.omg.CORBA.OBJ_ADAPTER();
                    }

                    try {
                        // try to get id from the Active Object Map
                        servant = id_to_servant(oid);
                    }
                    catch (ObjectNotActive e){
                        // try to use Servant Activator (incarnate)
                        org.omg.PortableServer.ServantActivator activator = 
                            (org.omg.PortableServer.ServantActivator)
                            m_servant_manager;
                        servant = activator.incarnate(oid.toByteArray(), this);
                        if (servant == null) {
                            throw new org.omg.CORBA.OBJ_ADAPTER();
                        }
                        try {
                            // try to activate servant created by Servant
                            // Activator
                            activate_object_with_id(oid, servant);
                        }
                        catch (ServantAlreadyActive e2) {
                            throw new org.omg.CORBA.OBJ_ADAPTER();
                        }
                    }

                    return servant;
                }
            } else { // NON_REATAIN

                // NON_RETAIN && USE_DEFAULT_SERVANT
                if (m_request_processing_policy.value()
                    == RequestProcessingPolicyValue.USE_DEFAULT_SERVANT) {
                    if (m_default_servant == null) {
                        throw new org.omg.CORBA.OBJ_ADAPTER();
                    }
                    // return Default Servant
                    servant = m_default_servant;
                    // Default servant invocation (oid == current request oid
                    // ??)
                    ServantDelegate srv_delegate = 
                        (ServantDelegate) servant._get_delegate();
                    
                    srv_delegate.setObjectId(oid.toByteArray());

                    return servant;
                }
                // NON_RETAIN && USE_SERVANT_MANAGER
                if (m_request_processing_policy.value()
                    == RequestProcessingPolicyValue.USE_SERVANT_MANAGER) {
                    if (m_servant_manager == null) {
                        throw new org.omg.CORBA.OBJ_ADAPTER();
                    }
                    // try to use Servant Locator (preinvoke)
                    org.omg.PortableServer.ServantLocator locator = 
                        (org.omg.PortableServer.ServantLocator)
                        m_servant_manager;

                    servant = locator.preinvoke(oid.toByteArray(), this,
                                                operation, cookieHolder);

                    servantLocatorUsed.value = true;
                    // Servant delegate creation (oid == current request oid ??)

                    ServantDelegate delegate = 
                        new ServantDelegate(this,oid.toByteArray());

                    servant._set_delegate(delegate);

                    return servant;
                }
            }

        }
        catch (org.omg.PortableServer.POAPackage.WrongPolicy dummy) {
            if (m_orb.m_trace != null) {
                m_orb.printTrace(Trace.DEEP_DEBUG,
                                 "Unexpected WrongPolicy exception", dummy);
            }

            throw new INTERNAL("Unexpected WrongPolicy exception");
        }

        return servant;
    }

    /**
     * Call postinvoke. This method is only used if there was a preinvoke before
     * servant invocation.
     * 
     * @param oid
     *            The object Id.
     * @param operation
     *            The name of the operation invoked.
     * @param cookieHolder
     *            The CookieHolder
     * @param servant
     *            The servant.
     * @return The servant.
     */
    synchronized protected void 
    	callPostinvoke(byte[] oid,
    	               String operation,
    	               CookieHolder cookieHolder,
    	               org.omg.PortableServer.Servant servant)
    {
        if ((m_servant_retention_policy.value() 
            == ServantRetentionPolicyValue.NON_RETAIN)
            && (m_request_processing_policy.value()
                == RequestProcessingPolicyValue.USE_SERVANT_MANAGER)) {
            if (m_servant_manager == null) {
                throw new org.omg.CORBA.OBJ_ADAPTER();
            }
            org.omg.PortableServer.ServantLocator locator = 
                (org.omg.PortableServer.ServantLocator) m_servant_manager;
            if (isSingleThread()) {
                synchronized (locator) {
                    locator.postinvoke(oid, this, operation,
                                       cookieHolder.value, servant);
                }
            } else {
                locator.postinvoke(oid, 
                                   this, 
                                   operation,
                                   cookieHolder.value,
                                   servant);
            }
        }
    }

    /**
     * @return True if POA's ThreadPolicy is SINGLE_THREAD.
     */
    synchronized protected boolean isSingleThread()
    {
        return (m_thread_policy.value()
                == ThreadPolicyValue.SINGLE_THREAD_MODEL);
    }

    /**
     * @return True if POA's BidirectionalPolicy is BOTH.
     */
    synchronized public boolean isBidirectional()
    {
        BidirectionalPolicy bidirectional_policy = 
            referencePolicies.getPolicies().getBidirectionalPolicy();
        
        if(bidirectional_policy == null) {
            return false;
        } else {
            return (bidirectional_policy.value() == BOTH.value);
        }
    }

    /**
     * @return True if this POA has an AdapterActivator.
     */
    synchronized protected boolean hasAdapterActivator()
    {
        return m_adapter_activator != null;
    }

    /**
     * Adds a new user for this oid. This means that there is one more active
     * request executing some request with this object Id.
     * 
     * @param oid
     *            The object Id.
     * @exception org.omg.PortableServer.POAPackage.ObjectNotActive
     *                If object is not active yet.
     */
    synchronized protected void addUser(OID oid)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive
    {

        if (m_servant_retention_policy.value()
            == ServantRetentionPolicyValue.RETAIN) {

            if (m_active_object_map.isActive(oid)) {
                m_active_object_map.addUser(oid);
            }
        }

        m_completion.beginRequest();
        m_poa_manager.beginRequest();
    }

    /**
     * Removes a user for this oid.
     * 
     * @param oid
     *            The object Id.
     * @exception org.omg.PortableServer.POAPackage.ObjectNotActive
     *                If object is not active yet.
     * @see addUser.
     */
    protected void removeUser(OID oid)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive
    {
        // Decrease # of users of OID
        m_completion.endRequest();
        synchronized (this) {
            m_poa_manager.endRequest();
            if (m_destroying) {
                boolean PoaNotInUse = m_completion.getActiveRequests() <= 0;
                if (PoaNotInUse) {
                    trueDestroy();
                }
            } else {
                tryToRemoveObject(oid);
            }
        }
    }

    /**
     * Etherealizes all objects in this POA manager.
     */
    synchronized protected void etherealizeAllObjects()
    {
        if (m_servant_retention_policy.value()
            == ServantRetentionPolicyValue.RETAIN) {
            java.util.Enumeration oids = m_active_object_map.getOIDs();
            if ((m_request_processing_policy.value() 
                == RequestProcessingPolicyValue.USE_SERVANT_MANAGER)
                && (m_servant_manager != null)) {
                // call etherealize
                org.omg.PortableServer.ServantActivator activator =
                    (org.omg.PortableServer.ServantActivator) m_servant_manager;
                while (oids.hasMoreElements()) {
                    OID oid = (OID) oids.nextElement();
                    try {
                        org.omg.PortableServer.Servant servant = 
                            m_active_object_map.remove(oid);
                        if (isSingleThread()) {
                            synchronized (activator) {
                                activator.etherealize(
                                    oid.toByteArray(),
                                    this,
                                    servant,
                                    false,
                                    m_active_object_map.contains(servant));
                            }
                        } else {
                            activator.etherealize(
                                oid.toByteArray(),
                                this,
                                servant,
                                false,
                                m_active_object_map.contains(servant));
                        }
                    }
                    catch (Exception e) {
                        // should never happen
                        e.printStackTrace();
                    }
                }
            }
        } else {
            //nothing to do
        }
    }

    /**
     * Creates the root POA. This method should only be called by
     * es.tid.TIDorbj.core.TIDorb.resolve_initial_references()
     * 
     * @param orb
     *            The ORB.
     * @see es.tid.TIDorbj.core.TIDORB#resolve_initial_references
     */
    static public POAImpl createRootPOA(es.tid.TIDorbj.core.TIDORB orb)
    {
        POAImpl rootPOA = null;
        org.omg.CORBA.Policy[] policies = new org.omg.CORBA.Policy[8];
        policies[0] =
            new IdAssignmentPolicyImpl(IdAssignmentPolicyValue.SYSTEM_ID);
        policies[1] = 
            new IdUniquenessPolicyImpl(IdUniquenessPolicyValue.UNIQUE_ID);
        policies[2] = 
            new ImplicitActivationPolicyImpl(
                ImplicitActivationPolicyValue.IMPLICIT_ACTIVATION);
        policies[3] = 
            new LifespanPolicyImpl(LifespanPolicyValue.TRANSIENT);
        policies[4] = 
            new RequestProcessingPolicyImpl(
                RequestProcessingPolicyValue.USE_ACTIVE_OBJECT_MAP_ONLY);
        policies[5] = 
            new ServantRetentionPolicyImpl(ServantRetentionPolicyValue.RETAIN);
        policies[6] = new ThreadPolicyImpl(ThreadPolicyValue.ORB_CTRL_MODEL);
        policies[7] = 
            new es.tid.TIDorbj.core.BidirectionalPolicyImpl(NORMAL.value);
        try {
            POAManagerImpl mgr = new POAManagerImpl(orb);
            rootPOA = new POAImpl(orb, "rootPOA", mgr, policies, null);
        }
        catch (org.omg.PortableServer.POAPackage.InvalidPolicy e) {
            // this can never happen
        }
        return rootPOA;
    }

    /* ************ Private methods ************* */

    /**
     * Removes an object if there are no executing request using it.
     * 
     * @param oid
     *            The object Id.
     */
    private void tryToRemoveObject(OID oid)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive
    {
        if (m_servant_retention_policy.value() 
            == ServantRetentionPolicyValue.RETAIN) {
            if (m_active_object_map.isActive(oid)) {
                boolean OidNotInUse = m_active_object_map.removeUser(oid);
                if (OidNotInUse) {
                    trueRemoveObject(oid);
                }
            }
        }
    }

    /**
     * Removes an object.
     * 
     * @param oid
     *            The object Id.
     */
    private void trueRemoveObject(OID oid)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive
    {
        // Remove OID from ActiveObjectMap
        // /////////////////////////////////////////////////////////////
        org.omg.PortableServer.Servant servant = 
            m_active_object_map.remove(oid);
        if (m_orb.m_trace != null) {
            m_orb.printTrace(Trace.DEEP_DEBUG, "Removing object: "
                                               + oid.toString());
        }

        // Try to etherealize, if necessary
        if ((m_request_processing_policy.value() 
            == RequestProcessingPolicyValue.USE_SERVANT_MANAGER)
            && (m_servant_manager != null)) {
            org.omg.PortableServer.ServantActivator activator = 
                (org.omg.PortableServer.ServantActivator) m_servant_manager;
            if (isSingleThread()) {
                synchronized (activator) {
                   activator.etherealize(oid.toByteArray(),
                                         this,
                                         servant,
                                         true,
                                         m_active_object_map.contains(servant));
                }
            } else {
                activator.etherealize(oid.toByteArray(),
                                      this, servant,
                                      true,
                                      m_active_object_map.contains(servant));
            }
        }
        // Continue with waiting activations of oid (if any)
        synchronized (servant) {
            servant.notifyAll();
        }
    }

    public void destroy()
    {
        throw new BAD_OPERATION();
    }

    /**
     * Destroys the POA manager.
     */
    synchronized public void trueDestroy()
    {
        m_destroyed = true;

        if (m_etherealize) {
            // Etherealize all objects
            etherealizeAllObjects();
        } else {
            // init activeObjectMap (perhaps this is not necessary)
            m_active_object_map = new ActiveObjectMap();
        }
        // remove POA from parent
        if (m_parent != null) {
            ((POAImpl) m_parent).m_children.removeElement(this);
        }
        m_poa_manager.removePOA(this);
        m_poa_manager = null;
    }

    /**
     * Find a POA among the POA's children.
     * 
     * @param poa_name
     *            The POA name.
     */
    private org.omg.PortableServer.POA findChildren(String poa_name)
    {
        org.omg.PortableServer.POA poa = null;
        for (int i = 0; i < m_children.size(); i++) {
            poa = (org.omg.PortableServer.POA) m_children.elementAt(i);
            if (poa_name.equals(poa.the_name())) {
                return poa;
            }
        }
        return null;
    }

    /**
     * Returns the repository Id of a servant.
     * 
     * @param servant
     *            The servant.
     * @return The repository Id.
     */
    private String getRepositoryId(org.omg.PortableServer.Servant servant)
    {
        return servant._all_interfaces(this, servant._object_id())[0];
    }

    /**
     * @return The current System Object Id.
     */
    public SystemOID currentOID()
    {
        return m_current_OID;
    }

    /**
     * Generates the next oid.
     * 
     * @return The next Object Id.
     */
    public OID nextOID()
    {
        SystemOID aux = m_current_OID;
        m_current_OID = m_current_OID.next();
        return aux;
    }
      
    

    /* ************ ObjectImpl ************* */

    public String toString()
    {
        if (m_poa_string_name == null) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("POA ");
            POAKey key = createKey(null);
            buffer.append(key.getPOAPath());
            m_poa_string_name = buffer.toString();
        }

        return m_name;
    }

    

    
}
