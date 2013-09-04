//
// InterfaceDefHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class InterfaceDefHolder
   implements org.omg.CORBA.portable.Streamable {

  public InterfaceDef value; 
  public InterfaceDefHolder() {
  }

  public InterfaceDefHolder(InterfaceDef initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.InterfaceDefHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.InterfaceDefHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.InterfaceDefHelper.type();
  };

}
