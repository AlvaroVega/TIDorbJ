//
// ServiceContextHelper.java (helper)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

abstract public class ServiceContextHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ServiceContext value) {
    any.insert_Streamable(new ServiceContextHolder(value));
  };

  public static ServiceContext extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ServiceContextHolder){
          return ((ServiceContextHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("context_id", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      members[1] = new org.omg.CORBA.StructMember("context_data", _orb().create_sequence_tc(0 , org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet)), null);
      _type = _orb().create_struct_tc(id(), "ServiceContext", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/IOP/ServiceContext:1.0";
  };

  public static ServiceContext read(org.omg.CORBA.portable.InputStream is) {
    ServiceContext result = new ServiceContext();
    result.context_id = is.read_ulong();
      int length2 = is.read_ulong();
      result.context_data = new byte[length2];
      for (int i0=0; i0<length2; i0++) {
        result.context_data[i0] = is.read_octet();
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ServiceContext val) {
    os.write_ulong(val.context_id);
      os.write_ulong(val.context_data.length);
      for (int i0=0; i0<val.context_data.length; i0++) {
        os.write_octet(val.context_data[i0]);
      }
  };

}
