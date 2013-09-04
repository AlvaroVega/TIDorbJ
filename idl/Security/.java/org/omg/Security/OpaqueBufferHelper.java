//
// OpaqueBufferHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class OpaqueBufferHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, OpaqueBuffer value) {
    any.insert_Streamable(new OpaqueBufferHolder(value));
  };

  public static OpaqueBuffer extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof OpaqueBufferHolder){
          return ((OpaqueBufferHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[3];
      members[0] = new org.omg.CORBA.StructMember("buffer", org.omg.Security.OpaqueHelper.type(), null);
      members[1] = new org.omg.CORBA.StructMember("startpos", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      members[2] = new org.omg.CORBA.StructMember("endpos", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      _type = _orb().create_struct_tc(id(), "OpaqueBuffer", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/OpaqueBuffer:1.0";
  };

  public static OpaqueBuffer read(org.omg.CORBA.portable.InputStream is) {
    OpaqueBuffer result = new OpaqueBuffer();
    result.buffer = org.omg.Security.OpaqueHelper.read(is);
    result.startpos = is.read_ulong();
    result.endpos = is.read_ulong();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, OpaqueBuffer val) {
    org.omg.Security.OpaqueHelper.write(os,val.buffer);
    os.write_ulong(val.startpos);
    os.write_ulong(val.endpos);
  };

}
