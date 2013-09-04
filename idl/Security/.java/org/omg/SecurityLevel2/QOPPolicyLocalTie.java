//
// QOPPolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public class QOPPolicyLocalTie
 extends QOPPolicyLocalBase
 {

  private QOPPolicyOperations _delegate;
  public QOPPolicyLocalTie(QOPPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public QOPPolicyOperations _delegate() {
    return this._delegate;
  };

  public org.omg.Security.QOP qop() {
    return this._delegate.qop();
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
