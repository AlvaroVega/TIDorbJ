//
// SecurityContextTypeHolder.java (holder)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

final public class SecurityContextTypeHolder
   implements org.omg.CORBA.portable.Streamable {

  public SecurityContextType value; 
  public SecurityContextTypeHolder() {
  }

  public SecurityContextTypeHolder(SecurityContextType initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Security.SecurityContextTypeHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Security.SecurityContextTypeHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Security.SecurityContextTypeHelper.type();
  };

}
