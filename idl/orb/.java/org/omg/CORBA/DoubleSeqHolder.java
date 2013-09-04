//
// DoubleSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class DoubleSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public double[] value; 
  public DoubleSeqHolder() {
    value = new double[0];
  }

  public DoubleSeqHolder(double[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.DoubleSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.DoubleSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.DoubleSeqHelper.type();
  };

}
