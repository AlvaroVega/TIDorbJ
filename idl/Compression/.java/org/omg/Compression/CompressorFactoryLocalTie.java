//
// CompressorFactoryLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

public class CompressorFactoryLocalTie
 extends CompressorFactoryLocalBase
 {

  private CompressorFactoryOperations _delegate;
  public CompressorFactoryLocalTie(CompressorFactoryOperations delegate) {
    this._delegate = delegate;
  };

  public CompressorFactoryOperations _delegate() {
    return this._delegate;
  };

  public short compressor_id() {
    return this._delegate.compressor_id();
  }

  public org.omg.Compression.Compressor get_compressor(short compression_level) {
    return this._delegate.get_compressor(
    compression_level
    );
  };


}
