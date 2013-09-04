//
// ParameterHolder.java (holder)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Dynamic;

final public class ParameterHolder
   implements org.omg.CORBA.portable.Streamable {

  public Parameter value; 
  public ParameterHolder() {
  }

  public ParameterHolder(Parameter initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Dynamic.ParameterHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Dynamic.ParameterHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Dynamic.ParameterHelper.type();
  };

}
