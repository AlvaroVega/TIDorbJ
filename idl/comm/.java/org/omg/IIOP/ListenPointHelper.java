//
// ListenPointHelper.java (helper)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IIOP;

abstract public class ListenPointHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ListenPoint value) {
    any.insert_Streamable(new ListenPointHolder(value));
  };

  public static ListenPoint extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ListenPointHolder){
          return ((ListenPointHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("host", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[1] = new org.omg.CORBA.StructMember("port", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      _type = _orb().create_struct_tc(id(), "ListenPoint", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/IIOP/ListenPoint:1.0";
  };

  public static ListenPoint read(org.omg.CORBA.portable.InputStream is) {
    ListenPoint result = new ListenPoint();
    result.host = is.read_string();
    result.port = is.read_ushort();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ListenPoint val) {
    os.write_string(val.host);
    os.write_ushort(val.port);
  };

}
