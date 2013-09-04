//
// ContextErrorHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

abstract public class ContextErrorHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ContextError value) {
    any.insert_Streamable(new ContextErrorHolder(value));
  };

  public static ContextError extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ContextErrorHolder){
          return ((ContextErrorHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[4];
      members[0] = new org.omg.CORBA.StructMember("client_context_id", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulonglong), null);
      members[1] = new org.omg.CORBA.StructMember("major_status", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long), null);
      members[2] = new org.omg.CORBA.StructMember("minor_status", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long), null);
      members[3] = new org.omg.CORBA.StructMember("error_token", org.omg.CSI.GSSTokenHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "ContextError", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSI/ContextError:1.0";
  };

  public static ContextError read(org.omg.CORBA.portable.InputStream is) {
    ContextError result = new ContextError();
    result.client_context_id = is.read_ulonglong();
    result.major_status = is.read_long();
    result.minor_status = is.read_long();
    result.error_token = org.omg.CSI.GSSTokenHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ContextError val) {
    os.write_ulonglong(val.client_context_id);
    os.write_long(val.major_status);
    os.write_long(val.minor_status);
    org.omg.CSI.GSSTokenHelper.write(os,val.error_token);
  };

}
