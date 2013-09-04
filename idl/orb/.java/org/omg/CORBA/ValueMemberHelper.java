//
// ValueMemberHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class ValueMemberHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ValueMember value) {
    any.insert_Streamable(new ValueMemberHolder(value));
  };

  public static ValueMember extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ValueMemberHolder){
          return ((ValueMemberHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[7];
      members[0] = new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[1] = new org.omg.CORBA.StructMember("id", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[2] = new org.omg.CORBA.StructMember("defined_in", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[3] = new org.omg.CORBA.StructMember("version", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[4] = new org.omg.CORBA.StructMember("type", org.omg.CORBA.TypeCodeHelper.type(), null);
      members[5] = new org.omg.CORBA.StructMember("type_def", org.omg.CORBA.IDLTypeHelper.type(), null);
      members[6] = new org.omg.CORBA.StructMember("access", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_short), null);
      _type = _orb().create_struct_tc(id(), "ValueMember", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/ValueMember:1.0";
  };

  public static ValueMember read(org.omg.CORBA.portable.InputStream is) {
    ValueMember result = new ValueMember();
    result.name = is.read_string();
    result.id = is.read_string();
    result.defined_in = is.read_string();
    result.version = is.read_string();
    result.type = org.omg.CORBA.TypeCodeHelper.read(is);
    result.type_def = org.omg.CORBA.IDLTypeHelper.read(is);
    result.access = is.read_short();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ValueMember val) {
    os.write_string(val.name);
    os.write_string(val.id);
    os.write_string(val.defined_in);
    os.write_string(val.version);
    org.omg.CORBA.TypeCodeHelper.write(os,val.type);
    org.omg.CORBA.IDLTypeHelper.write(os,val.type_def);
    os.write_short(val.access);
  };

}
