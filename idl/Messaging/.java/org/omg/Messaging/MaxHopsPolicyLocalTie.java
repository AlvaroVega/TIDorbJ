//
// MaxHopsPolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

public class MaxHopsPolicyLocalTie
 extends MaxHopsPolicyLocalBase
 {

  private MaxHopsPolicyOperations _delegate;
  public MaxHopsPolicyLocalTie(MaxHopsPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public MaxHopsPolicyOperations _delegate() {
    return this._delegate;
  };

  public short max_hops() {
    return this._delegate.max_hops();
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
