//
// SAS_ContextSecHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

final public class SAS_ContextSecHolder
   implements org.omg.CORBA.portable.Streamable {

  public SAS_ContextSec value; 
  public SAS_ContextSecHolder() {
  }

  public SAS_ContextSecHolder(SAS_ContextSec initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CSIIOP.SAS_ContextSecHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CSIIOP.SAS_ContextSecHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CSIIOP.SAS_ContextSecHelper.type();
  };

}
