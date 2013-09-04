//
// PolicyErrorHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class PolicyErrorHolder
   implements org.omg.CORBA.portable.Streamable {

  public PolicyError value; 
  public PolicyErrorHolder() {
  }

  public PolicyErrorHolder(PolicyError initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.PolicyErrorHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.PolicyErrorHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.PolicyErrorHelper.type();
  };

}
