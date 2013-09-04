//
// MechanismPolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public class MechanismPolicyLocalTie
 extends MechanismPolicyLocalBase
 {

  private MechanismPolicyOperations _delegate;
  public MechanismPolicyLocalTie(MechanismPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public MechanismPolicyOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] mechanisms() {
    return this._delegate.mechanisms();
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
