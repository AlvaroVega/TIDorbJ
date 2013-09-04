//
// ServiceInformationHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class ServiceInformationHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ServiceInformation value) {
    any.insert_Streamable(new ServiceInformationHolder(value));
  };

  public static ServiceInformation extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ServiceInformationHolder){
          return ((ServiceInformationHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("service_options", _orb().create_sequence_tc(0 , org.omg.CORBA.ServiceOptionHelper.type()), null);
      members[1] = new org.omg.CORBA.StructMember("service_details", _orb().create_sequence_tc(0 , org.omg.CORBA.ServiceDetailHelper.type()), null);
      _type = _orb().create_struct_tc(id(), "ServiceInformation", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/ServiceInformation:1.0";
  };

  public static ServiceInformation read(org.omg.CORBA.portable.InputStream is) {
    ServiceInformation result = new ServiceInformation();
      int length0 = is.read_ulong();
      result.service_options = new int[length0];
      for (int i0=0; i0<length0; i0++) {
        result.service_options[i0] = org.omg.CORBA.ServiceOptionHelper.read(is);
      }
      int length2 = is.read_ulong();
      result.service_details = new org.omg.CORBA.ServiceDetail[length2];
      for (int i0=0; i0<length2; i0++) {
        result.service_details[i0] = org.omg.CORBA.ServiceDetailHelper.read(is);
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ServiceInformation val) {
      os.write_ulong(val.service_options.length);
      for (int i0=0; i0<val.service_options.length; i0++) {
        org.omg.CORBA.ServiceOptionHelper.write(os,val.service_options[i0]);
      }
      os.write_ulong(val.service_details.length);
      for (int i0=0; i0<val.service_details.length; i0++) {
        org.omg.CORBA.ServiceDetailHelper.write(os,val.service_details[i0]);
      }
  };

}
