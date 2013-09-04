//
// RequestEndTimePolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

public class RequestEndTimePolicyLocalTie
 extends RequestEndTimePolicyLocalBase
 {

  private RequestEndTimePolicyOperations _delegate;
  public RequestEndTimePolicyLocalTie(RequestEndTimePolicyOperations delegate) {
    this._delegate = delegate;
  };

  public RequestEndTimePolicyOperations _delegate() {
    return this._delegate;
  };

  public org.omg.TimeBase.UtcT end_time() {
    return this._delegate.end_time();
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
