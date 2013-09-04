//
// CompressorIdLevel.java (struct)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

public class CompressorIdLevel
   implements org.omg.CORBA.portable.IDLEntity {

  public short compressor_id;
  public short compression_level;

  public CompressorIdLevel() {
  }

  public CompressorIdLevel(short compressor_id, short compression_level) {
    this.compressor_id = compressor_id;
    this.compression_level = compression_level;
  }

}
