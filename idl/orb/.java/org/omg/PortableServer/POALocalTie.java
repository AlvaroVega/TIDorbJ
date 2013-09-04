//
// POALocalTie.java (tie)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class POALocalTie
 extends POALocalBase
 {

  private POAOperations _delegate;
  public POALocalTie(POAOperations delegate) {
    this._delegate = delegate;
  };

  public POAOperations _delegate() {
    return this._delegate;
  };

  public org.omg.PortableServer.POA create_POA(java.lang.String adapter_name, org.omg.PortableServer.POAManager a_POAManager, org.omg.CORBA.Policy[] policies)
    throws org.omg.PortableServer.POAPackage.AdapterAlreadyExists, org.omg.PortableServer.POAPackage.InvalidPolicy {
    return this._delegate.create_POA(
    adapter_name, 
    a_POAManager, 
    policies
    );
  };

  public org.omg.PortableServer.POA find_POA(java.lang.String adapter_name, boolean activate_it)
    throws org.omg.PortableServer.POAPackage.AdapterNonExistent {
    return this._delegate.find_POA(
    adapter_name, 
    activate_it
    );
  };

  public void destroy(boolean etherealize_objects, boolean wait_for_completion) {
    this._delegate.destroy(
    etherealize_objects, 
    wait_for_completion
    );
  };

  public org.omg.PortableServer.ThreadPolicy create_thread_policy(org.omg.PortableServer.ThreadPolicyValue value) {
    return this._delegate.create_thread_policy(
    value
    );
  };

  public org.omg.PortableServer.LifespanPolicy create_lifespan_policy(org.omg.PortableServer.LifespanPolicyValue value) {
    return this._delegate.create_lifespan_policy(
    value
    );
  };

  public org.omg.PortableServer.IdUniquenessPolicy create_id_uniqueness_policy(org.omg.PortableServer.IdUniquenessPolicyValue value) {
    return this._delegate.create_id_uniqueness_policy(
    value
    );
  };

  public org.omg.PortableServer.IdAssignmentPolicy create_id_assignment_policy(org.omg.PortableServer.IdAssignmentPolicyValue value) {
    return this._delegate.create_id_assignment_policy(
    value
    );
  };

  public org.omg.PortableServer.ImplicitActivationPolicy create_implicit_activation_policy(org.omg.PortableServer.ImplicitActivationPolicyValue value) {
    return this._delegate.create_implicit_activation_policy(
    value
    );
  };

  public org.omg.PortableServer.ServantRetentionPolicy create_servant_retention_policy(org.omg.PortableServer.ServantRetentionPolicyValue value) {
    return this._delegate.create_servant_retention_policy(
    value
    );
  };

  public org.omg.PortableServer.RequestProcessingPolicy create_request_processing_policy(org.omg.PortableServer.RequestProcessingPolicyValue value) {
    return this._delegate.create_request_processing_policy(
    value
    );
  };

  public java.lang.String the_name() {
    return this._delegate.the_name();
  }

  public org.omg.PortableServer.POA the_parent() {
    return this._delegate.the_parent();
  }

  public org.omg.PortableServer.POA[] the_children() {
    return this._delegate.the_children();
  }

  public org.omg.PortableServer.POAManager the_POAManager() {
    return this._delegate.the_POAManager();
  }

  public org.omg.PortableServer.AdapterActivator the_activator() {
    return this._delegate.the_activator();
  }

  public void the_activator(org.omg.PortableServer.AdapterActivator value) {
    this._delegate.the_activator(value);
  }

  public org.omg.PortableServer.ServantManager get_servant_manager()
    throws org.omg.PortableServer.POAPackage.WrongPolicy {
    return this._delegate.get_servant_manager(
    );
  };

  public void set_servant_manager(org.omg.PortableServer.ServantManager imgr)
    throws org.omg.PortableServer.POAPackage.WrongPolicy {
    this._delegate.set_servant_manager(
    imgr
    );
  };

  public org.omg.PortableServer.Servant get_servant()
    throws org.omg.PortableServer.POAPackage.NoServant, org.omg.PortableServer.POAPackage.WrongPolicy {
    return this._delegate.get_servant(
    );
  };

  public void set_servant(org.omg.PortableServer.Servant p_servant)
    throws org.omg.PortableServer.POAPackage.WrongPolicy {
    this._delegate.set_servant(
    p_servant
    );
  };

  public byte[] activate_object(org.omg.PortableServer.Servant p_servant)
    throws org.omg.PortableServer.POAPackage.ServantAlreadyActive, org.omg.PortableServer.POAPackage.WrongPolicy {
    return this._delegate.activate_object(
    p_servant
    );
  };

  public void activate_object_with_id(byte[] id, org.omg.PortableServer.Servant p_servant)
    throws org.omg.PortableServer.POAPackage.ServantAlreadyActive, org.omg.PortableServer.POAPackage.ObjectAlreadyActive, org.omg.PortableServer.POAPackage.WrongPolicy {
    this._delegate.activate_object_with_id(
    id, 
    p_servant
    );
  };

  public void deactivate_object(byte[] oid)
    throws org.omg.PortableServer.POAPackage.ObjectNotActive, org.omg.PortableServer.POAPackage.WrongPolicy {
    this._delegate.deactivate_object(
    oid
    );
  };

  public org.omg.CORBA.Object create_reference(java.lang.String intf)
    throws org.omg.PortableServer.POAPackage.WrongPolicy {
    return this._delegate.create_reference(
    intf
    );
  };

  public org.omg.CORBA.Object create_reference_with_id(byte[] oid, java.lang.String intf) {
    return this._delegate.create_reference_with_id(
    oid, 
    intf
    );
  };

  public byte[] servant_to_id(org.omg.PortableServer.Servant p_servant)
    throws org.omg.PortableServer.POAPackage.ServantNotActive, org.omg.PortableServer.POAPackage.WrongPolicy {
    return this._delegate.servant_to_id(
    p_servant
    );
  };

  public org.omg.CORBA.Object servant_to_reference(org.omg.PortableServer.Servant p_servant)
    throws org.omg.PortableServer.POAPackage.ServantNotActive, org.omg.PortableServer.POAPackage.WrongPolicy {
    return this._delegate.servant_to_reference(
    p_servant
    );
  };

  public org.omg.PortableServer.Servant reference_to_servant(org.omg.CORBA.Object reference)
    throws org.omg.PortableServer.POAPackage.ObjectNotActive, org.omg.PortableServer.POAPackage.WrongAdapter, org.omg.PortableServer.POAPackage.WrongPolicy {
    return this._delegate.reference_to_servant(
    reference
    );
  };

  public byte[] reference_to_id(org.omg.CORBA.Object reference)
    throws org.omg.PortableServer.POAPackage.WrongAdapter, org.omg.PortableServer.POAPackage.WrongPolicy {
    return this._delegate.reference_to_id(
    reference
    );
  };

  public org.omg.PortableServer.Servant id_to_servant(byte[] oid)
    throws org.omg.PortableServer.POAPackage.ObjectNotActive, org.omg.PortableServer.POAPackage.WrongPolicy {
    return this._delegate.id_to_servant(
    oid
    );
  };

  public org.omg.CORBA.Object id_to_reference(byte[] oid)
    throws org.omg.PortableServer.POAPackage.ObjectNotActive, org.omg.PortableServer.POAPackage.WrongPolicy {
    return this._delegate.id_to_reference(
    oid
    );
  };

  public byte[] id() {
    return this._delegate.id();
  }


}
