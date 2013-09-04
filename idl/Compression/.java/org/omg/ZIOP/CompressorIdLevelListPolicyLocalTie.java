//
// CompressorIdLevelListPolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.ZIOP;

public class CompressorIdLevelListPolicyLocalTie
 extends CompressorIdLevelListPolicyLocalBase
 {

  private CompressorIdLevelListPolicyOperations _delegate;
  public CompressorIdLevelListPolicyLocalTie(CompressorIdLevelListPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public CompressorIdLevelListPolicyOperations _delegate() {
    return this._delegate;
  };

  public org.omg.Compression.CompressorIdLevel[] compressor_ids() {
    return this._delegate.compressor_ids();
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
