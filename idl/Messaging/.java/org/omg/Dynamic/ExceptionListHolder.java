//
// ExceptionListHolder.java (holder)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Dynamic;

final public class ExceptionListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.TypeCode[] value; 
  public ExceptionListHolder() {
    value = new org.omg.CORBA.TypeCode[0];
  }

  public ExceptionListHolder(org.omg.CORBA.TypeCode[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Dynamic.ExceptionListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Dynamic.ExceptionListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Dynamic.ExceptionListHelper.type();
  };

}
