//
// TypeCodeHelper.java (helper)
//
// File generated: Wed Feb 21 16:59:33 CET 2001
//   by TIDorbJ idl2Java 1.0
//

package org.omg.CORBA;

abstract public class TypeCodeHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, TypeCode value) {
    any.insert_TypeCode(value);
  };

  public static TypeCode extract(org.omg.CORBA.Any any) {
    return any.extract_TypeCode();
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      _type = _orb().get_primitive_tc(TCKind.tk_TypeCode);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/TypeCode:1.0";
  };

  public static TypeCode read(org.omg.CORBA.portable.InputStream is) {
    return is.read_TypeCode();
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, TypeCode val) {
    os.write_TypeCode(val);
  };

}
