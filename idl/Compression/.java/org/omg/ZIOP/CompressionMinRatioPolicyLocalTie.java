//
// CompressionMinRatioPolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.ZIOP;

public class CompressionMinRatioPolicyLocalTie
 extends CompressionMinRatioPolicyLocalBase
 {

  private CompressionMinRatioPolicyOperations _delegate;
  public CompressionMinRatioPolicyLocalTie(CompressionMinRatioPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public CompressionMinRatioPolicyOperations _delegate() {
    return this._delegate;
  };

  public float ratio() {
    return this._delegate.ratio();
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
