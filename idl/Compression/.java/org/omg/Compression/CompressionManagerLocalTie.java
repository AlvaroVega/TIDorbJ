//
// CompressionManagerLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

public class CompressionManagerLocalTie
 extends CompressionManagerLocalBase
 {

  private CompressionManagerOperations _delegate;
  public CompressionManagerLocalTie(CompressionManagerOperations delegate) {
    this._delegate = delegate;
  };

  public CompressionManagerOperations _delegate() {
    return this._delegate;
  };

  public void register_factory(org.omg.Compression.CompressorFactory compressor_factory)
    throws org.omg.Compression.FactoryAlreadyRegistered {
    this._delegate.register_factory(
    compressor_factory
    );
  };

  public void unregister_factory(short compressor_id)
    throws org.omg.Compression.UnknownCompressorId {
    this._delegate.unregister_factory(
    compressor_id
    );
  };

  public org.omg.Compression.CompressorFactory get_factory(short compressor_id)
    throws org.omg.Compression.UnknownCompressorId {
    return this._delegate.get_factory(
    compressor_id
    );
  };

  public org.omg.Compression.Compressor get_compressor(short compressor_id, short compression_level)
    throws org.omg.Compression.UnknownCompressorId {
    return this._delegate.get_compressor(
    compressor_id, 
    compression_level
    );
  };

  public org.omg.Compression.CompressorFactory[] get_factories() {
    return this._delegate.get_factories(
    );
  };


}
