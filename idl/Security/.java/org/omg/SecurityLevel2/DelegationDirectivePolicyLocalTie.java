//
// DelegationDirectivePolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public class DelegationDirectivePolicyLocalTie
 extends DelegationDirectivePolicyLocalBase
 {

  private DelegationDirectivePolicyOperations _delegate;
  public DelegationDirectivePolicyLocalTie(DelegationDirectivePolicyOperations delegate) {
    this._delegate = delegate;
  };

  public DelegationDirectivePolicyOperations _delegate() {
    return this._delegate;
  };

  public org.omg.Security.DelegationDirective delegation_directive() {
    return this._delegate.delegation_directive();
  }

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
