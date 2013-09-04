//
// ReceivedCredentialsHolder.java (holder)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

final public class ReceivedCredentialsHolder
   implements org.omg.CORBA.portable.Streamable {

  public ReceivedCredentials value; 
  public ReceivedCredentialsHolder() {
  }

  public ReceivedCredentialsHolder(ReceivedCredentials initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.SecurityLevel2.ReceivedCredentialsHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.SecurityLevel2.ReceivedCredentialsHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.SecurityLevel2.ReceivedCredentialsHelper.type();
  };

}
