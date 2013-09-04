//
// CompressorHolder.java (holder)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

final public class CompressorHolder
   implements org.omg.CORBA.portable.Streamable {

  public Compressor value; 
  public CompressorHolder() {
  }

  public CompressorHolder(Compressor initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Compression.CompressorHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Compression.CompressorHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Compression.CompressorHelper.type();
  };

}
