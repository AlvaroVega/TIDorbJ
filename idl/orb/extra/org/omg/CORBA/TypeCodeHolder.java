//
// TypeCodeHolder.java (holder)
//
// File generated: Mon Sep 25 11:22:55 GMT+02:00 2000
//   by TidORB Idl2Java 1.0
//

package org.omg.CORBA;

public class TypeCodeHolder
   implements org.omg.CORBA.portable.Streamable {

  public TypeCode value; 
  public TypeCodeHolder() {} 
  public TypeCodeHolder(TypeCode initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = is.read_TypeCode();
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    os.write_TypeCode(value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.ORB.init().get_primitive_tc((org.omg.CORBA.TCKind.tk_TypeCode));
  };

}
