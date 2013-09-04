//
// ParameterDescriptionHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class ParameterDescriptionHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ParameterDescription value) {
    any.insert_Streamable(new ParameterDescriptionHolder(value));
  };

  public static ParameterDescription extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ParameterDescriptionHolder){
          return ((ParameterDescriptionHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[4];
      members[0] = new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[1] = new org.omg.CORBA.StructMember("type", org.omg.CORBA.TypeCodeHelper.type(), null);
      members[2] = new org.omg.CORBA.StructMember("type_def", org.omg.CORBA.IDLTypeHelper.type(), null);
      members[3] = new org.omg.CORBA.StructMember("mode", org.omg.CORBA.ParameterModeHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "ParameterDescription", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/ParameterDescription:1.0";
  };

  public static ParameterDescription read(org.omg.CORBA.portable.InputStream is) {
    ParameterDescription result = new ParameterDescription();
    result.name = is.read_string();
    result.type = org.omg.CORBA.TypeCodeHelper.read(is);
    result.type_def = org.omg.CORBA.IDLTypeHelper.read(is);
    result.mode = org.omg.CORBA.ParameterModeHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ParameterDescription val) {
    os.write_string(val.name);
    org.omg.CORBA.TypeCodeHelper.write(os,val.type);
    org.omg.CORBA.IDLTypeHelper.write(os,val.type_def);
    org.omg.CORBA.ParameterModeHelper.write(os,val.mode);
  };

}
