//
// RequestPriorityPolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

public class RequestPriorityPolicyLocalTie
 extends RequestPriorityPolicyLocalBase
 {

  private RequestPriorityPolicyOperations _delegate;
  public RequestPriorityPolicyLocalTie(RequestPriorityPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public RequestPriorityPolicyOperations _delegate() {
    return this._delegate;
  };

  public org.omg.Messaging.PriorityRange priority_range() {
    return this._delegate.priority_range();
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
