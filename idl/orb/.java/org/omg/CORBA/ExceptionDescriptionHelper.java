//
// ExceptionDescriptionHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class ExceptionDescriptionHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ExceptionDescription value) {
    any.insert_Streamable(new ExceptionDescriptionHolder(value));
  };

  public static ExceptionDescription extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ExceptionDescriptionHolder){
          return ((ExceptionDescriptionHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[5];
      members[0] = new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[1] = new org.omg.CORBA.StructMember("id", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[2] = new org.omg.CORBA.StructMember("defined_in", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[3] = new org.omg.CORBA.StructMember("version", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[4] = new org.omg.CORBA.StructMember("type", org.omg.CORBA.TypeCodeHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "ExceptionDescription", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/ExceptionDescription:1.0";
  };

  public static ExceptionDescription read(org.omg.CORBA.portable.InputStream is) {
    ExceptionDescription result = new ExceptionDescription();
    result.name = is.read_string();
    result.id = is.read_string();
    result.defined_in = is.read_string();
    result.version = is.read_string();
    result.type = org.omg.CORBA.TypeCodeHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ExceptionDescription val) {
    os.write_string(val.name);
    os.write_string(val.id);
    os.write_string(val.defined_in);
    os.write_string(val.version);
    org.omg.CORBA.TypeCodeHelper.write(os,val.type);
  };

}
