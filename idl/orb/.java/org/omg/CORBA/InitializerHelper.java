//
// InitializerHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class InitializerHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, Initializer value) {
    any.insert_Streamable(new InitializerHolder(value));
  };

  public static Initializer extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof InitializerHolder){
          return ((InitializerHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("members", org.omg.CORBA.StructMemberSeqHelper.type(), null);
      members[1] = new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      _type = _orb().create_struct_tc(id(), "Initializer", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/Initializer:1.0";
  };

  public static Initializer read(org.omg.CORBA.portable.InputStream is) {
    Initializer result = new Initializer();
    result.members = org.omg.CORBA.StructMemberSeqHelper.read(is);
    result.name = is.read_string();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, Initializer val) {
    org.omg.CORBA.StructMemberSeqHelper.write(os,val.members);
    os.write_string(val.name);
  };

}
