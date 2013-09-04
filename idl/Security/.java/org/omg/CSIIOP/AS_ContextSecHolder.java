//
// AS_ContextSecHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

final public class AS_ContextSecHolder
   implements org.omg.CORBA.portable.Streamable {

  public AS_ContextSec value; 
  public AS_ContextSecHolder() {
  }

  public AS_ContextSecHolder(AS_ContextSec initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CSIIOP.AS_ContextSecHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CSIIOP.AS_ContextSecHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CSIIOP.AS_ContextSecHelper.type();
  };

}
