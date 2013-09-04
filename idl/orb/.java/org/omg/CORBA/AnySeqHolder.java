//
// AnySeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class AnySeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.Any[] value; 
  public AnySeqHolder() {
    value = new org.omg.CORBA.Any[0];
  }

  public AnySeqHolder(org.omg.CORBA.Any[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.AnySeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.AnySeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.AnySeqHelper.type();
  };

}
