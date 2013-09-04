//
// PolicyManagerHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class PolicyManagerHolder
   implements org.omg.CORBA.portable.Streamable {

  public PolicyManager value; 
  public PolicyManagerHolder() {
  }

  public PolicyManagerHolder(PolicyManager initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.PolicyManagerHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.PolicyManagerHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.PolicyManagerHelper.type();
  };

}
