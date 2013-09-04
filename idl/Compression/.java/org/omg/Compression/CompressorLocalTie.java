//
// CompressorLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

public class CompressorLocalTie
 extends CompressorLocalBase
 {

  private CompressorOperations _delegate;
  public CompressorLocalTie(CompressorOperations delegate) {
    this._delegate = delegate;
  };

  public CompressorOperations _delegate() {
    return this._delegate;
  };

  public void compress(byte[] source, org.omg.CORBA.OctetSeqHolder target)
    throws org.omg.Compression.CompressionException {
    this._delegate.compress(
    source, 
    target
    );
  };

  public void decompress(byte[] source, org.omg.CORBA.OctetSeqHolder target)
    throws org.omg.Compression.CompressionException {
    this._delegate.decompress(
    source, 
    target
    );
  };

  public org.omg.Compression.CompressorFactory compressor_factory() {
    return this._delegate.compressor_factory();
  }

  public short compression_level() {
    return this._delegate.compression_level();
  }

  public long compressed_bytes() {
    return this._delegate.compressed_bytes();
  }

  public long uncompressed_bytes() {
    return this._delegate.uncompressed_bytes();
  }

  public float compression_ratio() {
    return this._delegate.compression_ratio();
  }


}
