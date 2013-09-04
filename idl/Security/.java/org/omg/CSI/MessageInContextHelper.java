//
// MessageInContextHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

abstract public class MessageInContextHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, MessageInContext value) {
    any.insert_Streamable(new MessageInContextHolder(value));
  };

  public static MessageInContext extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof MessageInContextHolder){
          return ((MessageInContextHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("client_context_id", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulonglong), null);
      members[1] = new org.omg.CORBA.StructMember("discard_context", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean), null);
      _type = _orb().create_struct_tc(id(), "MessageInContext", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSI/MessageInContext:1.0";
  };

  public static MessageInContext read(org.omg.CORBA.portable.InputStream is) {
    MessageInContext result = new MessageInContext();
    result.client_context_id = is.read_ulonglong();
    result.discard_context = is.read_boolean();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, MessageInContext val) {
    os.write_ulonglong(val.client_context_id);
    os.write_boolean(val.discard_context);
  };

}
