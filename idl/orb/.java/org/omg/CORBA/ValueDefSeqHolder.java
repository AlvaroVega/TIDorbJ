//
// ValueDefSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class ValueDefSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.ValueDef[] value; 
  public ValueDefSeqHolder() {
    value = new org.omg.CORBA.ValueDef[0];
  }

  public ValueDefSeqHolder(org.omg.CORBA.ValueDef[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.ValueDefSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.ValueDefSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.ValueDefSeqHelper.type();
  };

}
