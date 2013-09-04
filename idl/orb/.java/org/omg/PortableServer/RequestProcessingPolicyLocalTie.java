//
// RequestProcessingPolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class RequestProcessingPolicyLocalTie
 extends RequestProcessingPolicyLocalBase
 {

  private RequestProcessingPolicyOperations _delegate;
  public RequestProcessingPolicyLocalTie(RequestProcessingPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public RequestProcessingPolicyOperations _delegate() {
    return this._delegate;
  };

  public org.omg.PortableServer.RequestProcessingPolicyValue value() {
    return this._delegate.value();
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
