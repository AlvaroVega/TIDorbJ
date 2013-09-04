//
// AuthorizationTokenHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

final public class AuthorizationTokenHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CSI.AuthorizationElement[] value; 
  public AuthorizationTokenHolder() {
    value = new org.omg.CSI.AuthorizationElement[0];
  }

  public AuthorizationTokenHolder(org.omg.CSI.AuthorizationElement[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CSI.AuthorizationTokenHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CSI.AuthorizationTokenHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CSI.AuthorizationTokenHelper.type();
  };

}
