//
// AS_ContextSecHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

abstract public class AS_ContextSecHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, AS_ContextSec value) {
    any.insert_Streamable(new AS_ContextSecHolder(value));
  };

  public static AS_ContextSec extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof AS_ContextSecHolder){
          return ((AS_ContextSecHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[4];
      members[0] = new org.omg.CORBA.StructMember("target_supports", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      members[1] = new org.omg.CORBA.StructMember("target_requires", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      members[2] = new org.omg.CORBA.StructMember("client_authentication_mech", org.omg.CSI.OIDHelper.type(), null);
      members[3] = new org.omg.CORBA.StructMember("target_name", org.omg.CSI.GSS_NT_ExportedNameHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "AS_ContextSec", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSIIOP/AS_ContextSec:1.0";
  };

  public static AS_ContextSec read(org.omg.CORBA.portable.InputStream is) {
    AS_ContextSec result = new AS_ContextSec();
    result.target_supports = is.read_ushort();
    result.target_requires = is.read_ushort();
    result.client_authentication_mech = org.omg.CSI.OIDHelper.read(is);
    result.target_name = org.omg.CSI.GSS_NT_ExportedNameHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, AS_ContextSec val) {
    os.write_ushort(val.target_supports);
    os.write_ushort(val.target_requires);
    org.omg.CSI.OIDHelper.write(os,val.client_authentication_mech);
    org.omg.CSI.GSS_NT_ExportedNameHelper.write(os,val.target_name);
  };

}
