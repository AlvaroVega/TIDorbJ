//
// CurrentLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public class CurrentLocalTie
 extends CurrentLocalBase
 {

  private CurrentOperations _delegate;
  public CurrentLocalTie(CurrentOperations delegate) {
    this._delegate = delegate;
  };

  public CurrentOperations _delegate() {
    return this._delegate;
  };

  public org.omg.SecurityLevel2.ReceivedCredentials received_credentials() {
    return this._delegate.received_credentials();
  }

  public org.omg.Security.SecAttribute[] get_attributes(org.omg.Security.AttributeType[] attributes) {
    return this._delegate.get_attributes(
    attributes
    );
  };




}
