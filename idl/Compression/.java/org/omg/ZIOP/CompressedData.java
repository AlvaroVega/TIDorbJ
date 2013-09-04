//
// CompressedData.java (struct)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.ZIOP;

public class CompressedData
   implements org.omg.CORBA.portable.IDLEntity {

  public short compressorid;
  public int original_length;
  public byte[] data;

  public CompressedData() {
  }

  public CompressedData(short compressorid, int original_length, byte[] data) {
    this.compressorid = compressorid;
    this.original_length = original_length;
    this.data = data;
  }

}
