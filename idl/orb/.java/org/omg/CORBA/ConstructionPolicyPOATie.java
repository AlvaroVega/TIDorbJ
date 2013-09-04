//
// ConstructionPolicyPOATie.java (tie)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class ConstructionPolicyPOATie
 extends ConstructionPolicyPOA
 implements ConstructionPolicyOperations {

  private ConstructionPolicyOperations _delegate;
  public ConstructionPolicyPOATie(ConstructionPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public ConstructionPolicyOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/ConstructionPolicy:1.0",
    "IDL:omg.org/CORBA/Policy:1.0"  };

  public void make_domain_manager(org.omg.CORBA.InterfaceDef object_type, boolean constr_policy) {
    this._delegate.make_domain_manager(
    object_type, 
    constr_policy
    );
  };

  public int policy_type() {
    return this._delegate.policy_type();
  }

  public org.omg.CORBA.Policy copy() {
    return this._delegate.copy(
    );
  };

  public void destroy() {
    this._delegate.destroy(
    );
  };



}
