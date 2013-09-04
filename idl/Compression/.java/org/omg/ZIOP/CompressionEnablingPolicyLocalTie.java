//
// CompressionEnablingPolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.ZIOP;

public class CompressionEnablingPolicyLocalTie
 extends CompressionEnablingPolicyLocalBase
 {

  private CompressionEnablingPolicyOperations _delegate;
  public CompressionEnablingPolicyLocalTie(CompressionEnablingPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public CompressionEnablingPolicyOperations _delegate() {
    return this._delegate;
  };

  public boolean compression_enabled() {
    return this._delegate.compression_enabled();
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
