//
// Compressor.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

public interface CompressorOperations {

  void compress(byte[] source, org.omg.CORBA.OctetSeqHolder target)
    throws org.omg.Compression.CompressionException;

  void decompress(byte[] source, org.omg.CORBA.OctetSeqHolder target)
    throws org.omg.Compression.CompressionException;

  org.omg.Compression.CompressorFactory compressor_factory();

  short compression_level();

  long compressed_bytes();

  long uncompressed_bytes();

  float compression_ratio();


}
