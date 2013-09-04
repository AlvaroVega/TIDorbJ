//
// AuditEventTypeHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class AuditEventTypeHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, AuditEventType value) {
    any.insert_Streamable(new AuditEventTypeHolder(value));
  };

  public static AuditEventType extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof AuditEventTypeHolder){
          return ((AuditEventTypeHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("event_family", org.omg.Security.ExtensibleFamilyHelper.type(), null);
      members[1] = new org.omg.CORBA.StructMember("event_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      _type = _orb().create_struct_tc(id(), "AuditEventType", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/AuditEventType:1.0";
  };

  public static AuditEventType read(org.omg.CORBA.portable.InputStream is) {
    AuditEventType result = new AuditEventType();
    result.event_family = org.omg.Security.ExtensibleFamilyHelper.read(is);
    result.event_type = is.read_ushort();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, AuditEventType val) {
    org.omg.Security.ExtensibleFamilyHelper.write(os,val.event_family);
    os.write_ushort(val.event_type);
  };

}
