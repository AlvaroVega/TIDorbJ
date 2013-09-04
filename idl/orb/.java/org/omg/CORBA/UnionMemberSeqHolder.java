//
// UnionMemberSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class UnionMemberSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.UnionMember[] value; 
  public UnionMemberSeqHolder() {
    value = new org.omg.CORBA.UnionMember[0];
  }

  public UnionMemberSeqHolder(org.omg.CORBA.UnionMember[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.UnionMemberSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.UnionMemberSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.UnionMemberSeqHelper.type();
  };

}
