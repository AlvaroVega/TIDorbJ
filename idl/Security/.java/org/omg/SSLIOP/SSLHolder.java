//
// SSLHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SSLIOP;

final public class SSLHolder
   implements org.omg.CORBA.portable.Streamable {

  public SSL value; 
  public SSLHolder() {
  }

  public SSLHolder(SSL initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.SSLIOP.SSLHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.SSLIOP.SSLHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.SSLIOP.SSLHelper.type();
  };

}
