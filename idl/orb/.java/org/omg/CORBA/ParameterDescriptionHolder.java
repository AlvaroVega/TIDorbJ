//
// ParameterDescriptionHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class ParameterDescriptionHolder
   implements org.omg.CORBA.portable.Streamable {

  public ParameterDescription value; 
  public ParameterDescriptionHolder() {
  }

  public ParameterDescriptionHolder(ParameterDescription initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.ParameterDescriptionHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.ParameterDescriptionHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.ParameterDescriptionHelper.type();
  };

}
