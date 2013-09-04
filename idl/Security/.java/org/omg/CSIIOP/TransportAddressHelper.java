//
// TransportAddressHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

abstract public class TransportAddressHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, TransportAddress value) {
    any.insert_Streamable(new TransportAddressHolder(value));
  };

  public static TransportAddress extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof TransportAddressHolder){
          return ((TransportAddressHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("host_name", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[1] = new org.omg.CORBA.StructMember("port", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      _type = _orb().create_struct_tc(id(), "TransportAddress", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSIIOP/TransportAddress:1.0";
  };

  public static TransportAddress read(org.omg.CORBA.portable.InputStream is) {
    TransportAddress result = new TransportAddress();
    result.host_name = is.read_string();
    result.port = is.read_ushort();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, TransportAddress val) {
    os.write_string(val.host_name);
    os.write_ushort(val.port);
  };

}
