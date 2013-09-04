//
// ValueMemberHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class ValueMemberHolder
   implements org.omg.CORBA.portable.Streamable {

  public ValueMember value; 
  public ValueMemberHolder() {
  }

  public ValueMemberHolder(ValueMember initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.ValueMemberHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.ValueMemberHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.ValueMemberHelper.type();
  };

}
