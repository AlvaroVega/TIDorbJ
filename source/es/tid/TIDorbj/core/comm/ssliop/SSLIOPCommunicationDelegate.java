/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 391 $
* Date: $Date: 2009-05-25 16:58:01 +0200 (Mon, 25 May 2009) $
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
package es.tid.TIDorbj.core.comm.ssliop;

import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;

import es.tid.TIDorbj.core.ObjectImpl;
import es.tid.TIDorbj.core.RequestImpl;
import es.tid.TIDorbj.core.StreamRequestImpl;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.comm.CommunicationDelegate;
import es.tid.TIDorbj.core.comm.CommunicationException;
import es.tid.TIDorbj.core.comm.ForwardRequest;
import es.tid.TIDorbj.core.comm.iiop.IIOPIOR;
import es.tid.TIDorbj.core.comm.iiop.IIOPProfile;
import es.tid.TIDorbj.core.comm.iiop.CommunicationLayer;

import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.messaging.PoliciesComponent;
import es.tid.TIDorbj.core.policy.PolicyContext;

/**
 * @author avega
 *
 */
public class SSLIOPCommunicationDelegate extends CommunicationDelegate {

    private IIOPIOR                  ssliopReference;
    private SSLIOPCommunicationLayer communicationLayer;
	
    /**
     * 
     */
    public SSLIOPCommunicationDelegate( SSLIOPCommunicationLayer communicationLayer ) {
        super();
        if ( communicationLayer != null ){
            super.setORB( communicationLayer.getORB() );
            this.communicationLayer = communicationLayer;
            this.forwardReference = null;
        } else {
            throw new IllegalArgumentException( 
				"Parameter 'commLayer' cannot be null" 
                                );
        }
    }
	
    public void setReference(IOR ior) {	
        IIOPIOR iiopIOR = null;
        if(! (ior instanceof IIOPIOR) ) {
            iiopIOR = new IIOPIOR(ior);
            
        } else {
            iiopIOR = (IIOPIOR) ior;
        }
        
        super.setReference(iiopIOR);
        
        IIOPProfile profile = iiopIOR.profileIIOP();
        
        PoliciesComponent policiesComponent  = profile.getPoliciesComponent();
        
        if(policiesComponent != null) {	        
            this.setPolicyContext(policiesComponent.getPolicies());	                  
        }
        
        
        
    }//setReference
    
    
    public synchronized IIOPIOR getIIOPReference(){
        if ( this.ssliopReference == null ){
            this.ssliopReference = new IIOPIOR();
            this.ssliopReference.assign( super.reference );
        }
        return this.ssliopReference;
    }
    
    
    public IOR getReference(){
        return getIIOPReference();
    }
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationDelegate#isLocal(org.omg.CORBA.Object)
     */
    public boolean isLocal( org.omg.CORBA.Object self) {
		
        if ( super.getORB() == null) {
            throw new BAD_OPERATION("ORB Singleton", 0, 
                                    CompletionStatus.COMPLETED_NO );
        }
        
        try {
            if ( this.forwardReference == null) {
                return this.communicationLayer.isLocal( this.getIIOPReference() );        	
            } else {
                return this.forwardReference.is_local(null);	            
            }
        } catch ( CommunicationException ce ) {
            return false;
        }
    }//isLocal
    

    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationDelegate#non_existent(org.omg.CORBA.Object)
     */
    public boolean non_existent(org.omg.CORBA.Object self){
    	TIDORB orb = super.getORB();
        if ( orb == null) {
            throw new BAD_OPERATION("ORB Singleton");
        }

        PolicyContext request_policy_context = createRequestPolicyContext();

        CommunicationLayer comm_layer = this.communicationLayer.getCommLayer();

        try {

            if ( this.forwardReference == null) {
                return !comm_layer.objectExists( 
                    this.getIIOPReference(),
                    request_policy_context);
            } else { // there is forwards
                boolean non_existent = true;
                try {
                    non_existent = this.forwardReference.non_existent(null);
                }
                catch (org.omg.CORBA.COMM_FAILURE cf) {
                    non_existent = true;
                }
                catch (org.omg.CORBA.OBJECT_NOT_EXIST one) {
                    non_existent = false;
                }

                if (non_existent) {
                    this.forwardReference = null;
                }

                return non_existent;
            }
        } catch (ForwardRequest fe) {
            
            this.setForward(fe);
            
        }
        
        return non_existent(self);
    }//non_existent


    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationDelegate#duplicate(org.omg.CORBA.Object)
     */
    public Object duplicate(Object self) {
        SSLIOPCommunicationDelegate delegate; 
        delegate = new SSLIOPCommunicationDelegate( this.communicationLayer );
        delegate.setReference( this.getReference() );
        
        delegate.forwardReference = this.forwardReference;
        
        //getPolicyContext never returns null.
        delegate.setPolicyContext( super.getPolicyContext().duplicate() );

        ObjectImpl obj = new ObjectImpl();

        obj._set_delegate(delegate);

        return obj;
    }//duplicate


    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationDelegate#release(org.omg.CORBA.Object)
     */
    public void release(org.omg.CORBA.Object self) {
        //Empty treatment
    }//release


    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationDelegate#release_reply(org.omg.CORBA.Object, org.omg.CORBA.portable.InputStream)
     */
    public void release_reply(org.omg.CORBA.Object object,
                              org.omg.CORBA.portable.InputStream inputStream ) {
        //Empty treatment
    }//release_reply


    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationDelegate#prepareRequest(es.tid.TIDorbj.core.StreamRequestImpl)
     */
    public void prepareRequest( StreamRequestImpl request ) {
        this.communicationLayer.getCommLayer().prepareRequest( request );
    }//prepareRequest


    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationDelegate#onewayRequest(es.tid.TIDorbj.core.RequestImpl)
     */
    public void onewayRequest(RequestImpl request) {
        if ( this.forwardReference != null ) {
            this.forwardReference.onewayRequest(request);
        } else {		    
            this.communicationLayer.getCommLayer().onewayRequest( request, this.getIIOPReference() );
        }
    }//oneWayRequest


    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationDelegate#invoke(es.tid.TIDorbj.core.RequestImpl)
     */
    public void invoke(RequestImpl request) {
        try {
            if ( this.forwardReference == null ) {
                this.communicationLayer.getCommLayer().request(
                	request, 
                        (IIOPIOR)this.getReference()
                        );
            } else { 
                // there is forwards
                this.forwardReference.invoke(request);
				
            }
        } catch (org.omg.CORBA.COMM_FAILURE cf) {
            this.forwardReference = null;
            throw cf;
        } catch (org.omg.CORBA.OBJECT_NOT_EXIST one) {
            this.forwardReference = null;
            throw one;
        } catch (ForwardRequest fe) {
            
           this.setForward(fe);
           
           request.setPolicyContext(this.forwardReference.createRequestPolicyContext());
           
           invoke(request);
            
        }//forwarded
    }//invoke


    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationDelegate#invoke(org.omg.CORBA.Object, org.omg.CORBA.portable.OutputStream)
     */
    public InputStream invoke(org.omg.CORBA.Object object, 
                              OutputStream stream ) 
        throws ApplicationException, RemarshalException 
    {
        
        if (is_local(object)) {
            throw new BAD_OPERATION("Request is only valid for remote objects");
        }

        if (stream instanceof es.tid.TIDorbj.core.cdr.CDROutputStream) {

            es.tid.TIDorbj.core.cdr.CDROutputStream out = 
                (es.tid.TIDorbj.core.cdr.CDROutputStream) stream;
            try {
                if ( this.forwardReference == null) {
                	return this.communicationLayer.getCommLayer().request(
						(IIOPIOR)this.getReference(),
                                                out,
                                                createRequestPolicyContext() 
                                                );
                } else {
                    return this.forwardReference.invoke(object, stream);                	
                }

            }
            catch (es.tid.TIDorbj.core.comm.ForwardRequest fr) {
                this.setForward(fr);
                throw new org.omg.CORBA.portable.RemarshalException();
            }
        } else {
            throw new org.omg.CORBA.BAD_OPERATION("Not a TIDorbJ's Stream");
        }
    }//invoke stream
    
    // AMI callback operations
    
    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.comm.CommunicationDelegate#asyncRequest(es.tid.TIDorbj.core.RequestImpl)
     */
    public void asyncRequest(RequestImpl request, Object ami_handler ) {
        try {
            request.set_ami_handler(ami_handler);
            if ( this.forwardReference == null ) {
                this.communicationLayer.getCommLayer().asyncRequest(
                	request, (IIOPIOR)this.getReference()
                        );
            } else { 
                // there is forwards
                this.forwardReference.asyncRequest(request, ami_handler);
                
            }
        } catch (org.omg.CORBA.COMM_FAILURE cf) {
            this.forwardReference = null;
            throw cf;
        } catch (org.omg.CORBA.OBJECT_NOT_EXIST one) {
            this.forwardReference = null;
            throw one;
        } catch (ForwardRequest fe) {
            
           this.setForward(fe);
           
           request.setPolicyContext(this.forwardReference.createRequestPolicyContext());
           
           asyncRequest(request, ami_handler);
            
        }//forwarded
    }//asyncRequest
	
	
	
	

}//SSLIOPCommunicationDelegate
