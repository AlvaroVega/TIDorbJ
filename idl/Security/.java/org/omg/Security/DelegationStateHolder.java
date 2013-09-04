//
// DelegationStateHolder.java (holder)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

final public class DelegationStateHolder
   implements org.omg.CORBA.portable.Streamable {

  public DelegationState value; 
  public DelegationStateHolder() {
  }

  public DelegationStateHolder(DelegationState initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Security.DelegationStateHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Security.DelegationStateHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Security.DelegationStateHelper.type();
  };

}
