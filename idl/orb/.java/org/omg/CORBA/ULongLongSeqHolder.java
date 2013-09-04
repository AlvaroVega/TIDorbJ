//
// ULongLongSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class ULongLongSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public long[] value; 
  public ULongLongSeqHolder() {
    value = new long[0];
  }

  public ULongLongSeqHolder(long[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.ULongLongSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.ULongLongSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.ULongLongSeqHelper.type();
  };

}
