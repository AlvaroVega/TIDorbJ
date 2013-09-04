//
// StructMemberHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class StructMemberHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, StructMember value) {
    any.insert_Streamable(new StructMemberHolder(value));
  };

  public static StructMember extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof StructMemberHolder){
          return ((StructMemberHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[3];
      members[0] = new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[1] = new org.omg.CORBA.StructMember("type", org.omg.CORBA.TypeCodeHelper.type(), null);
      members[2] = new org.omg.CORBA.StructMember("type_def", org.omg.CORBA.IDLTypeHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "StructMember", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/StructMember:1.0";
  };

  public static StructMember read(org.omg.CORBA.portable.InputStream is) {
    StructMember result = new StructMember();
    result.name = is.read_string();
    result.type = org.omg.CORBA.TypeCodeHelper.read(is);
    result.type_def = org.omg.CORBA.IDLTypeHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, StructMember val) {
    os.write_string(val.name);
    org.omg.CORBA.TypeCodeHelper.write(os,val.type);
    org.omg.CORBA.IDLTypeHelper.write(os,val.type_def);
  };

}
