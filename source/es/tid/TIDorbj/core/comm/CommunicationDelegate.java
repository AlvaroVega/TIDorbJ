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
package es.tid.TIDorbj.core.comm;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.InvalidPolicies;
import org.omg.CORBA.Object;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.portable.Delegate;

import es.tid.TIDorbj.core.ContextImpl;
import es.tid.TIDorbj.core.ContextListImpl;
import es.tid.TIDorbj.core.ExceptionListImpl;
import es.tid.TIDorbj.core.NVListImpl;
import es.tid.TIDorbj.core.RequestImpl;
import es.tid.TIDorbj.core.StreamRequestImpl;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.policy.PolicyContext;
import es.tid.TIDorbj.util.Trace;

/**
 * 
 * CommunicationDelegate is the base type for ORB's to be uncoupled of the 
 * underlying communications infraestructure from the 'client' side. 
 * It contains the base methods to allow sending requests to other ORB's. It 
 * keeps also the org.omg Delegate view, to allow regular corba Helper and 
 * Holder interaction. 
 * 
 * @author Juan Pablo Rojas.
 * 
 */
public abstract class CommunicationDelegate extends Delegate {

	protected TIDORB orb;
	protected IOR reference;
	protected CommunicationDelegate  forwardReference;
	protected PolicyContext policyContext;

	/**
	 *  
	 */
	public CommunicationDelegate() {
		super();
		this.orb = null;
		this.reference = null;
		this.policyContext = null;
	}

	public CommunicationDelegate( TIDORB orb ) {
		super();
		setORB( orb );
	}

	public void setORB(TIDORB orb) {
		if (orb != null) {
			this.orb = orb;
		} else {
			throw new IllegalArgumentException("Parameter 'orb' cannot be null");
		}
	}//setORB

	public TIDORB getORB() {
		return this.orb;
	}//getORB

	public void setReference(IOR ior) {
		if (ior != null) {
			this.reference = ior;
		} else {
			throw new IllegalArgumentException("Parameter 'ior' cannot be null");
		}
	}//setReference

    /* 
     * As IOR could be set in it's generic and not unmarshalled way, children de
     * legates should replace the generic implementation with a more specialized
     * version...  
     */
	public abstract IOR getReference();/* {
		return this.reference;
	}//getReference*/
	
	public synchronized void setPolicyContext( PolicyContext policyContext ){
		this.policyContext = policyContext;
	}//setPolicyContext
	
	public PolicyContext getPolicyContext(){
		synchronized( this ) {
			if ( this.policyContext == null ){
				this.policyContext = new PolicyContext(null);
			}
		}
		return this.policyContext;
	}//getPolicyContext

 public boolean is_a(org.omg.CORBA.Object self, String repositoryIdentifier) {
		if (repositoryIdentifier == null) {
			throw new BAD_PARAM("Null string reference", 0,
					CompletionStatus.COMPLETED_NO);
		}

		if (repositoryIdentifier.equals("IDL:omg.org/CORBA/Object:1.0")) {
			return true;
		}
		if (repositoryIdentifier.equals(this.reference.getTypeId())) {
			return true;
		}
		// search in known interface
		String[] interf_ids = ((org.omg.CORBA.portable.ObjectImpl) self)._ids();

		if (interf_ids != null) {
			for (int i = 0; i < interf_ids.length; i++) {
				if (repositoryIdentifier.equals(interf_ids)) {
					return true;
				}
			}
		}

		//ask to the remote object
		if (this.orb == null) {
			throw new BAD_OPERATION("ORB Singleton");
		}

		return this.invoke_is_a(self, repositoryIdentifier);
	}
	
	public boolean is_equivalent(org.omg.CORBA.Object self,
			org.omg.CORBA.Object other) {
		if (other == null) {
			throw new BAD_PARAM("Null reference");
		}
		// are they the same object?
		if (self == other) {
			return true;
		}
		Delegate delegate = 
			( (org.omg.CORBA.portable.ObjectImpl) other )._get_delegate();

		if (delegate instanceof CommunicationDelegate) {
			return this.reference.equivalent(
				((CommunicationDelegate) delegate).getReference()
			);
		} else {
			return toString().equals(
				delegate.orb(other).object_to_string(other)
			);
		}
	}

	public org.omg.CORBA.ORB orb( org.omg.CORBA.Object self ){
		return this.orb;
	}
	
	public int hash(org.omg.CORBA.Object self, int maximum) {
		int code = this.reference.hashCode();
		if (code > maximum) {
			code %= maximum;
		}
		return code;
	}//hash
	
	
    public org.omg.CORBA.Request request( org.omg.CORBA.Object self,
										  String operation ) {
		if (operation == null) {
			throw new BAD_PARAM(
				"Null operation string", 0, CompletionStatus.COMPLETED_NO
			);
		}

		if ( this.orb == null) {
			throw new BAD_OPERATION(
				"ORB Singleton", 0, CompletionStatus.COMPLETED_NO
			);
		}
		//TODO: RequestImpl should be common to all delegates, rename it?
		return new RequestImpl(
				self, 
				new ContextImpl( this.orb, "" ), 
				operation,
				new NVListImpl( this.orb ), 
				null, 
				new ExceptionListImpl(),
				new ContextListImpl()
		);
	}//request
	
    public org.omg.CORBA.Request create_request( 
    	org.omg.CORBA.Object self,
		org.omg.CORBA.Context ctx, 
		String operation,
		org.omg.CORBA.NVList arg_list, 
		org.omg.CORBA.NamedValue result ) {
		
    	if ( this.orb == null) {
			throw new BAD_OPERATION(
				"ORB Singleton", 0, CompletionStatus.COMPLETED_NO
			);
		}//configurationCheck

		if (       self == null || 
			        ctx == null || 
			  operation == null || 
			   arg_list == null    ) {

			throw new BAD_PARAM(
				"Null reference.", 0, CompletionStatus.COMPLETED_NO
			);
		}//parametersCheck

		return new RequestImpl(
			self, 
			ctx, 
			operation, 
			arg_list, 
			result,
			new ExceptionListImpl(), 
			new ContextListImpl()
		);
	}//createRequest

	public org.omg.CORBA.Request create_request( org.omg.CORBA.Object self,
		org.omg.CORBA.Context ctx, 
		String operation,
		org.omg.CORBA.NVList arg_list, 
		org.omg.CORBA.NamedValue result,
		org.omg.CORBA.ExceptionList exclist,
		org.omg.CORBA.ContextList ctxlist ) {
		
		if ( this.orb == null ) {
			throw new BAD_OPERATION(
				"ORB Singleton: Can not create request", 0, CompletionStatus.COMPLETED_NO
			);
		}

		if (       self == null || 
			        ctx == null || 
			  operation == null || 
			   arg_list == null || 
			    exclist == null || 
				ctxlist == null    ) {

			throw new BAD_PARAM(
				"Null reference", 0, CompletionStatus.COMPLETED_NO );
		}

		return new RequestImpl(
			self, 
			ctx, 
			operation, 
			arg_list, 
			result, 
			exclist,
			ctxlist
		);
	}//createRequest
	
	
	public org.omg.CORBA.Policy get_policy(
		org.omg.CORBA.Object self,
		int policy_type ) {

		PolicyContext request_context;
		request_context = createRequestPolicyContext();

		return request_context.getPolicy( policy_type );
	}//get_policy
	
	public org.omg.CORBA.Object set_policy_override(
		org.omg.CORBA.Object self,
        org.omg.CORBA.Policy[] policies,
        org.omg.CORBA.SetOverrideType set_add ) {
		
	    org.omg.CORBA.portable.ObjectImpl copy;
	    copy = ( org.omg.CORBA.portable.ObjectImpl ) self._duplicate();
	
	    //TODO: Note that the delegate stored in object reference must be a 
	    //CommunicationsDelegate instance!
	    CommunicationDelegate delegate = ( CommunicationDelegate )copy._get_delegate();
	
	    try {
	        delegate.getPolicyContext().setPolicies(policies, set_add);
	    } catch (InvalidPolicies iv) {}
	    return copy;
	
	}//set_policy_override
	
	/*
	 * UNIMPLEMENTED API
	 */
    public org.omg.CORBA.DomainManager[] _get_domain_managers(
    		org.omg.CORBA.Object self ) {

    	throw new org.omg.CORBA.NO_IMPLEMENT();
	}//_get_domain_managers
	
	
	/*
	 * DEPRECATED API
	 */
	
	/**
     * @deprecated Deprecated by CORBA 2.3
     */
    public org.omg.CORBA.InterfaceDef get_interface(org.omg.CORBA.Object self) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * @deprecated Deprecated by CORBA 2.3
     */
    public org.omg.CORBA.Object get_interface_def(org.omg.CORBA.Object self) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }
	
    
    /*
     * Utility methods used by TIDOrbJ
     */
    
    public void setForward(ForwardRequest fe)
	{
	    try {
            this.forwardReference = 
                orb.getCommunicationManager().createDelegate(fe.forward_reference);
        }
        catch (CommunicationException e) {
           throw new INV_OBJREF("Cannot create delegate for: " 
                                + fe.forward_reference.toString());
        }
        
        orb.printTrace( 
        	Trace.DEBUG, new String[]{ 
        	"DelegateImpl::non_exist(): ",
            this.getReference().toString(), 
			" forwarded to: ",
            this.forwardReference.toString() 
        	}
        );
	}
    
    /**
     * Creates the PolicyContext for a request using reference + thread + ORB
     * PolicyManagers
     */
    public  PolicyContext createRequestPolicyContext()
	{
        // generates effective Request context
        
        PolicyContext context = getPolicyContext();
        context = context.duplicate();
        Thread currentThread = Thread.currentThread();
        
        // thread_context has as father ORB Context
        PolicyContext thread_context = 
            orb.getPolicyContextManager().getThreadContext(currentThread);
        
        context.setFatherContext(thread_context);
	    return context;
	}
    
    /*
     * must use CommunicationManager to retrieve a valid delegate 
     * from provided ior.
     * @deprecated since 6.0.1 
     */
    public CommunicationDelegate fromString( TIDORB orb, String ior ) {
    	throw new UnsupportedOperationException();
    }
    
    public String toString(){
    	if ( this.reference != null ) {
    		return this.reference.toString();
    	} else {
    		throw new IllegalStateException( "No reference configured!" );
    	}
    }
    
    protected boolean invoke_is_a( org.omg.CORBA.Object self, String id ){
        // Create the request:
        org.omg.CORBA.Request request = request(self, "_is_a");

        // insert the parameter and return type
        Any inputAny = request.add_named_in_arg( "repositoryIdentifier" );
        inputAny.insert_string(id);
        request.set_return_type( this.orb.get_primitive_tc(TCKind.tk_boolean) );

        // Perform the invocation:
        request.invoke();

        java.lang.Exception exception;
        if ( ( exception = request.env().exception() ) != null ) {
            throw (org.omg.CORBA.SystemException) exception;
        }

        // Extrae el resultado y los argumentos de salida:
        return request.return_value().extract_boolean();

    }//invoke_is_a
    
    public org.omg.CORBA.portable.OutputStream request(
    	org.omg.CORBA.Object object, 
		String operation,
		boolean responseExpected ){
    	
        if ( this.is_local( object ) ) {
            throw new BAD_OPERATION( 
            	"Request is only valid for remote objects" 
            );
        }
    	
        org.omg.CORBA.portable.ObjectImpl target;
        target = (org.omg.CORBA.portable.ObjectImpl) object;

        StreamRequestImpl request = new StreamRequestImpl(
       		this.orb,  
			target,     
			operation, 
			responseExpected
		);

        this.prepareRequest(request);

        return request.getOutputStream();
    	
    }
    
    public String get_codebase( org.omg.CORBA.Object self ){
    	return null;
    }
    
	/*
	 * To be implemented by concrete implementations 
	 */
	public abstract boolean isLocal( org.omg.CORBA.Object self );
	
	public abstract boolean non_existent( org.omg.CORBA.Object self );

	public abstract org.omg.CORBA.Object duplicate( org.omg.CORBA.Object self );
	
	public abstract void release( org.omg.CORBA.Object self );
	
	public abstract void release_reply( 
		org.omg.CORBA.Object self, 
		org.omg.CORBA.portable.InputStream inputStream 
	);
	
	//TODO: actually this operation is performed by the 'old' commLayer itself
	public abstract void prepareRequest( StreamRequestImpl request );
	
	public abstract void onewayRequest( RequestImpl request );
	
    public abstract void invoke( RequestImpl request );

    // AMI callback operations
	public abstract void asyncRequest( RequestImpl request, Object ami_handler );

	public abstract org.omg.CORBA.portable.InputStream invoke(
    	org.omg.CORBA.Object object,
		org.omg.CORBA.portable.OutputStream stream ) 
    throws 
		org.omg.CORBA.portable.ApplicationException,
		org.omg.CORBA.portable.RemarshalException ;
    	
    
 
	
}
