//
// SECIOP_SEC_TRANSHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

final public class SECIOP_SEC_TRANSHolder
   implements org.omg.CORBA.portable.Streamable {

  public SECIOP_SEC_TRANS value; 
  public SECIOP_SEC_TRANSHolder() {
  }

  public SECIOP_SEC_TRANSHolder(SECIOP_SEC_TRANS initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CSIIOP.SECIOP_SEC_TRANSHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CSIIOP.SECIOP_SEC_TRANSHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CSIIOP.SECIOP_SEC_TRANSHelper.type();
  };

}
