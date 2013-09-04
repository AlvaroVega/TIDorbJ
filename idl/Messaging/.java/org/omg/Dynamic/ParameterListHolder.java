//
// ParameterListHolder.java (holder)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Dynamic;

final public class ParameterListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.Dynamic.Parameter[] value; 
  public ParameterListHolder() {
    value = new org.omg.Dynamic.Parameter[0];
  }

  public ParameterListHolder(org.omg.Dynamic.Parameter[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Dynamic.ParameterListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Dynamic.ParameterListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Dynamic.ParameterListHelper.type();
  };

}
