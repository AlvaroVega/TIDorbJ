//
// ReplyPriorityPolicyPOATie.java (tie)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

public class ReplyPriorityPolicyPOATie
 extends ReplyPriorityPolicyPOA
 implements ReplyPriorityPolicyOperations {

  private ReplyPriorityPolicyOperations _delegate;
  public ReplyPriorityPolicyPOATie(ReplyPriorityPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public ReplyPriorityPolicyOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/Messaging/ReplyPriorityPolicy:1.0",
    "IDL:omg.org/CORBA/Policy:1.0"  };

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
