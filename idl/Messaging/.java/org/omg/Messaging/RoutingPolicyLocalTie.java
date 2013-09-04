//
// RoutingPolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

public class RoutingPolicyLocalTie
 extends RoutingPolicyLocalBase
 {

  private RoutingPolicyOperations _delegate;
  public RoutingPolicyLocalTie(RoutingPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public RoutingPolicyOperations _delegate() {
    return this._delegate;
  };

  public org.omg.Messaging.RoutingTypeRange routing_range() {
    return this._delegate.routing_range();
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
