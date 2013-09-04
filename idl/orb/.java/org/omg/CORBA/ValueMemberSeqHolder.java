//
// ValueMemberSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class ValueMemberSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.ValueMember[] value; 
  public ValueMemberSeqHolder() {
    value = new org.omg.CORBA.ValueMember[0];
  }

  public ValueMemberSeqHolder(org.omg.CORBA.ValueMember[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.ValueMemberSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.ValueMemberSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.ValueMemberSeqHelper.type();
  };

}
