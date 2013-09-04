//
// PolicyListHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class PolicyListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.Policy[] value; 
  public PolicyListHolder() {
    value = new org.omg.CORBA.Policy[0];
  }

  public PolicyListHolder(org.omg.CORBA.Policy[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.PolicyListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.PolicyListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.PolicyListHelper.type();
  };

}
