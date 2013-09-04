//
// CompressionEnablingPolicyValueHelper.java (helper)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.ZIOP;

abstract public class CompressionEnablingPolicyValueHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, boolean value) {
    any.insert_Streamable(new org.omg.CORBA.BooleanHolder(value));
  };

  public static boolean extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof org.omg.CORBA.BooleanHolder){
          return ((org.omg.CORBA.BooleanHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.TypeCode original_type = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean);
      _type = _orb().create_alias_tc(id(), "CompressionEnablingPolicyValue", original_type);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/ZIOP/CompressionEnablingPolicyValue:1.0";
  };

  public static boolean read(org.omg.CORBA.portable.InputStream is) {
    boolean result;
    result = is.read_boolean();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, boolean val) {
    os.write_boolean(val);
  };

}
