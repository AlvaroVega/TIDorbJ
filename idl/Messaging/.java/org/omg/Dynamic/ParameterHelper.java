//
// ParameterHelper.java (helper)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Dynamic;

abstract public class ParameterHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, Parameter value) {
    any.insert_Streamable(new ParameterHolder(value));
  };

  public static Parameter extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ParameterHolder){
          return ((ParameterHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("argument", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_any), null);
      members[1] = new org.omg.CORBA.StructMember("mode", org.omg.CORBA.ParameterModeHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "Parameter", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Dynamic/Parameter:1.0";
  };

  public static Parameter read(org.omg.CORBA.portable.InputStream is) {
    Parameter result = new Parameter();
    result.argument = is.read_any();
    result.mode = org.omg.CORBA.ParameterModeHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, Parameter val) {
    os.write_any(val.argument);
    org.omg.CORBA.ParameterModeHelper.write(os,val.mode);
  };

}
