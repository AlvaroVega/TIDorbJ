//
// EstablishTrustPolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public class EstablishTrustPolicyLocalTie
 extends EstablishTrustPolicyLocalBase
 {

  private EstablishTrustPolicyOperations _delegate;
  public EstablishTrustPolicyLocalTie(EstablishTrustPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public EstablishTrustPolicyOperations _delegate() {
    return this._delegate;
  };

  public org.omg.Security.EstablishTrust trust() {
    return this._delegate.trust();
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
