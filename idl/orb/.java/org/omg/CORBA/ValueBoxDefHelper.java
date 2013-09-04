//
// ValueBoxDefHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class ValueBoxDefHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      _type = _orb().create_interface_tc(id(), "ValueBoxDef");
    }
    return _type;
  }

  public static String id() {
    return "IDL:omg.org/CORBA/ValueBoxDef:1.0";
  };

  public static void insert(org.omg.CORBA.Any any, ValueBoxDef value) {
    any.insert_Object((org.omg.CORBA.Object)value, type());
  };

  public static ValueBoxDef extract(org.omg.CORBA.Any any) {
    org.omg.CORBA.Object obj = any.extract_Object();
    ValueBoxDef value = narrow(obj);
    return value;
  };

  public static ValueBoxDef read(org.omg.CORBA.portable.InputStream is) {
    return narrow(is.read_Object(), true); 
  }

  public static void write(org.omg.CORBA.portable.OutputStream os, ValueBoxDef val) {
    if (!(os instanceof org.omg.CORBA_2_3.portable.OutputStream)) {;
      throw new org.omg.CORBA.BAD_PARAM();
    };
    if (val != null && !(val instanceof org.omg.CORBA.portable.ObjectImpl)) {;
      throw new org.omg.CORBA.BAD_PARAM();
    };
    os.write_Object((org.omg.CORBA.Object)val);
  }

  public static ValueBoxDef narrow(org.omg.CORBA.Object obj) {
    return narrow(obj, false);
  }

  public static ValueBoxDef unchecked_narrow(org.omg.CORBA.Object obj) {
    return narrow(obj, true);
  }

  private static ValueBoxDef narrow(org.omg.CORBA.Object obj, boolean is_a) {
    if (obj == null) {
      return null;
    }
    if (obj instanceof ValueBoxDef) {
      return (ValueBoxDef)obj;
    }
    if (is_a || obj._is_a(id())) {
      _ValueBoxDefStub result = (_ValueBoxDefStub)new _ValueBoxDefStub();
      ((org.omg.CORBA.portable.ObjectImpl) result)._set_delegate
        (((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate());
      return (ValueBoxDef)result;
    }
    throw new org.omg.CORBA.BAD_PARAM();
  }

}
