//
// CompressionLowValuePolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.ZIOP;

public class CompressionLowValuePolicyLocalTie
 extends CompressionLowValuePolicyLocalBase
 {

  private CompressionLowValuePolicyOperations _delegate;
  public CompressionLowValuePolicyLocalTie(CompressionLowValuePolicyOperations delegate) {
    this._delegate = delegate;
  };

  public CompressionLowValuePolicyOperations _delegate() {
    return this._delegate;
  };

  public int low_value() {
    return this._delegate.low_value();
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
