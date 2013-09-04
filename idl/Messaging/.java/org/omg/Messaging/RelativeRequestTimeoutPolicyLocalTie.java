//
// RelativeRequestTimeoutPolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

public class RelativeRequestTimeoutPolicyLocalTie
 extends RelativeRequestTimeoutPolicyLocalBase
 {

  private RelativeRequestTimeoutPolicyOperations _delegate;
  public RelativeRequestTimeoutPolicyLocalTie(RelativeRequestTimeoutPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public RelativeRequestTimeoutPolicyOperations _delegate() {
    return this._delegate;
  };

  public long relative_expiry() {
    return this._delegate.relative_expiry();
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
