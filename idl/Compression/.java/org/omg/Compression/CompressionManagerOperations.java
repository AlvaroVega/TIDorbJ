//
// CompressionManager.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

public interface CompressionManagerOperations {

  void register_factory(org.omg.Compression.CompressorFactory compressor_factory)
    throws org.omg.Compression.FactoryAlreadyRegistered;

  void unregister_factory(short compressor_id)
    throws org.omg.Compression.UnknownCompressorId;

  org.omg.Compression.CompressorFactory get_factory(short compressor_id)
    throws org.omg.Compression.UnknownCompressorId;

  org.omg.Compression.Compressor get_compressor(short compressor_id, short compression_level)
    throws org.omg.Compression.UnknownCompressorId;

  org.omg.Compression.CompressorFactory[] get_factories();


}
