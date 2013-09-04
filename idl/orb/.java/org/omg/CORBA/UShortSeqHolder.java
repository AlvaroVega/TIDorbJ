//
// UShortSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class UShortSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public short[] value; 
  public UShortSeqHolder() {
    value = new short[0];
  }

  public UShortSeqHolder(short[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.UShortSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.UShortSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.UShortSeqHelper.type();
  };

}
