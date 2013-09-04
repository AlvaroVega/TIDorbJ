//
// SAS_ContextSecHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

abstract public class SAS_ContextSecHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, SAS_ContextSec value) {
    any.insert_Streamable(new SAS_ContextSecHolder(value));
  };

  public static SAS_ContextSec extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof SAS_ContextSecHolder){
          return ((SAS_ContextSecHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[5];
      members[0] = new org.omg.CORBA.StructMember("target_supports", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      members[1] = new org.omg.CORBA.StructMember("target_requires", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      members[2] = new org.omg.CORBA.StructMember("privilege_authorities", org.omg.CSIIOP.ServiceConfigurationListHelper.type(), null);
      members[3] = new org.omg.CORBA.StructMember("supported_naming_mechanisms", org.omg.CSI.OIDListHelper.type(), null);
      members[4] = new org.omg.CORBA.StructMember("supported_identity_types", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      _type = _orb().create_struct_tc(id(), "SAS_ContextSec", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSIIOP/SAS_ContextSec:1.0";
  };

  public static SAS_ContextSec read(org.omg.CORBA.portable.InputStream is) {
    SAS_ContextSec result = new SAS_ContextSec();
    result.target_supports = is.read_ushort();
    result.target_requires = is.read_ushort();
    result.privilege_authorities = org.omg.CSIIOP.ServiceConfigurationListHelper.read(is);
    result.supported_naming_mechanisms = org.omg.CSI.OIDListHelper.read(is);
    result.supported_identity_types = is.read_ulong();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, SAS_ContextSec val) {
    os.write_ushort(val.target_supports);
    os.write_ushort(val.target_requires);
    org.omg.CSIIOP.ServiceConfigurationListHelper.write(os,val.privilege_authorities);
    org.omg.CSI.OIDListHelper.write(os,val.supported_naming_mechanisms);
    os.write_ulong(val.supported_identity_types);
  };

}
