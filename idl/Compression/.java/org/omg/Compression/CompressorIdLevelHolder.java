//
// CompressorIdLevelHolder.java (holder)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

final public class CompressorIdLevelHolder
   implements org.omg.CORBA.portable.Streamable {

  public CompressorIdLevel value; 
  public CompressorIdLevelHolder() {
  }

  public CompressorIdLevelHolder(CompressorIdLevel initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Compression.CompressorIdLevelHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Compression.CompressorIdLevelHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Compression.CompressorIdLevelHelper.type();
  };

}
