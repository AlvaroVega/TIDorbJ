//
// IdentityExtensionHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

final public class IdentityExtensionHolder
   implements org.omg.CORBA.portable.Streamable {

  public byte[] value; 
  public IdentityExtensionHolder() {
    value = new byte[0];
  }

  public IdentityExtensionHolder(byte[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CSI.IdentityExtensionHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CSI.IdentityExtensionHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CSI.IdentityExtensionHelper.type();
  };

}
