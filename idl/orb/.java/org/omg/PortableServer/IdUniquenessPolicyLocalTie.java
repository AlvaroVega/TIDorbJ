//
// IdUniquenessPolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class IdUniquenessPolicyLocalTie
 extends IdUniquenessPolicyLocalBase
 {

  private IdUniquenessPolicyOperations _delegate;
  public IdUniquenessPolicyLocalTie(IdUniquenessPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public IdUniquenessPolicyOperations _delegate() {
    return this._delegate;
  };

  public org.omg.PortableServer.IdUniquenessPolicyValue value() {
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
