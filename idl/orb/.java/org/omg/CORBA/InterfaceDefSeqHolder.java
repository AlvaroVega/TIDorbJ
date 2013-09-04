//
// InterfaceDefSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class InterfaceDefSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.InterfaceDef[] value; 
  public InterfaceDefSeqHolder() {
    value = new org.omg.CORBA.InterfaceDef[0];
  }

  public InterfaceDefSeqHolder(org.omg.CORBA.InterfaceDef[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.InterfaceDefSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.InterfaceDefSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.InterfaceDefSeqHelper.type();
  };

}
