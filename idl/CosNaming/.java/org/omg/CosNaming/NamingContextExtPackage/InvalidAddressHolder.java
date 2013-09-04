//
// InvalidAddressHolder.java (holder)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming.NamingContextExtPackage;

final public class InvalidAddressHolder
   implements org.omg.CORBA.portable.Streamable {

  public InvalidAddress value; 
  public InvalidAddressHolder() {
  }

  public InvalidAddressHolder(InvalidAddress initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper.type();
  };

}
