//
// ServiceConfigurationHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

abstract public class ServiceConfigurationHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ServiceConfiguration value) {
    any.insert_Streamable(new ServiceConfigurationHolder(value));
  };

  public static ServiceConfiguration extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ServiceConfigurationHolder){
          return ((ServiceConfigurationHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("syntax", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      members[1] = new org.omg.CORBA.StructMember("name", org.omg.CSIIOP.ServiceSpecificNameHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "ServiceConfiguration", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSIIOP/ServiceConfiguration:1.0";
  };

  public static ServiceConfiguration read(org.omg.CORBA.portable.InputStream is) {
    ServiceConfiguration result = new ServiceConfiguration();
    result.syntax = is.read_ulong();
    result.name = org.omg.CSIIOP.ServiceSpecificNameHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ServiceConfiguration val) {
    os.write_ulong(val.syntax);
    org.omg.CSIIOP.ServiceSpecificNameHelper.write(os,val.name);
  };

}
