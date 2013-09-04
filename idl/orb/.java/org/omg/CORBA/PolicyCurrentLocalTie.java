//
// PolicyCurrentLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class PolicyCurrentLocalTie
 extends PolicyCurrentLocalBase
 {

  private PolicyCurrentOperations _delegate;
  public PolicyCurrentLocalTie(PolicyCurrentOperations delegate) {
    this._delegate = delegate;
  };

  public PolicyCurrentOperations _delegate() {
    return this._delegate;
  };

  public org.omg.CORBA.Policy[] get_policy_overrides(int[] ts) {
    return this._delegate.get_policy_overrides(
    ts
    );
  };

  public void set_policy_overrides(org.omg.CORBA.Policy[] policies, org.omg.CORBA.SetOverrideType set_add)
    throws org.omg.CORBA.InvalidPolicies {
    this._delegate.set_policy_overrides(
    policies, 
    set_add
    );
  };




}
