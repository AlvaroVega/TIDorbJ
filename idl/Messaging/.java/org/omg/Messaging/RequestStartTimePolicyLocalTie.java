//
// RequestStartTimePolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

public class RequestStartTimePolicyLocalTie
 extends RequestStartTimePolicyLocalBase
 {

  private RequestStartTimePolicyOperations _delegate;
  public RequestStartTimePolicyLocalTie(RequestStartTimePolicyOperations delegate) {
    this._delegate = delegate;
  };

  public RequestStartTimePolicyOperations _delegate() {
    return this._delegate;
  };

  public org.omg.TimeBase.UtcT start_time() {
    return this._delegate.start_time();
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
