//
// ParameterModeHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class ParameterModeHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ParameterMode value) {
    any.insert_Streamable(new ParameterModeHolder(value));
  };

  public static ParameterMode extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ParameterModeHolder){
          return ((ParameterModeHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[3];
      members[0] = "PARAM_IN";
      members[1] = "PARAM_OUT";
      members[2] = "PARAM_INOUT";
      _type = _orb().create_enum_tc(id(), "ParameterMode", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/ParameterMode:1.0";
  };

  public static ParameterMode read(org.omg.CORBA.portable.InputStream is) {
    return ParameterMode.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ParameterMode val) {
    os.write_long(val.value());
  };

}
