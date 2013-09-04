//
// ExceptionDefSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class ExceptionDefSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.ExceptionDef[] value; 
  public ExceptionDefSeqHolder() {
    value = new org.omg.CORBA.ExceptionDef[0];
  }

  public ExceptionDefSeqHolder(org.omg.CORBA.ExceptionDef[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.ExceptionDefSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.ExceptionDefSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.ExceptionDefSeqHelper.type();
  };

}
