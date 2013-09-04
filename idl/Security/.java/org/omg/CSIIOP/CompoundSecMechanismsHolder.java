//
// CompoundSecMechanismsHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

final public class CompoundSecMechanismsHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CSIIOP.CompoundSecMech[] value; 
  public CompoundSecMechanismsHolder() {
    value = new org.omg.CSIIOP.CompoundSecMech[0];
  }

  public CompoundSecMechanismsHolder(org.omg.CSIIOP.CompoundSecMech[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CSIIOP.CompoundSecMechanismsHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CSIIOP.CompoundSecMechanismsHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CSIIOP.CompoundSecMechanismsHelper.type();
  };

}
