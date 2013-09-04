//
// ChannelBindingsHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class ChannelBindingsHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ChannelBindings value) {
    any.insert_Streamable(new ChannelBindingsHolder(value));
  };

  public static ChannelBindings extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ChannelBindingsHolder){
          return ((ChannelBindingsHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[5];
      members[0] = new org.omg.CORBA.StructMember("initiator_addrtype", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      members[1] = new org.omg.CORBA.StructMember("initiator_address", _orb().create_sequence_tc(0 , org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet)), null);
      members[2] = new org.omg.CORBA.StructMember("acceptor_addrtype", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      members[3] = new org.omg.CORBA.StructMember("acceptor_address", _orb().create_sequence_tc(0 , org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet)), null);
      members[4] = new org.omg.CORBA.StructMember("application_data", _orb().create_sequence_tc(0 , org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet)), null);
      _type = _orb().create_struct_tc(id(), "ChannelBindings", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/ChannelBindings:1.0";
  };

  public static ChannelBindings read(org.omg.CORBA.portable.InputStream is) {
    ChannelBindings result = new ChannelBindings();
    result.initiator_addrtype = is.read_ulong();
      int length2 = is.read_ulong();
      result.initiator_address = new byte[length2];
      for (int i0=0; i0<length2; i0++) {
        result.initiator_address[i0] = is.read_octet();
      }
    result.acceptor_addrtype = is.read_ulong();
      int length6 = is.read_ulong();
      result.acceptor_address = new byte[length6];
      for (int i0=0; i0<length6; i0++) {
        result.acceptor_address[i0] = is.read_octet();
      }
      int length8 = is.read_ulong();
      result.application_data = new byte[length8];
      for (int i0=0; i0<length8; i0++) {
        result.application_data[i0] = is.read_octet();
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ChannelBindings val) {
    os.write_ulong(val.initiator_addrtype);
      os.write_ulong(val.initiator_address.length);
      for (int i0=0; i0<val.initiator_address.length; i0++) {
        os.write_octet(val.initiator_address[i0]);
      }
    os.write_ulong(val.acceptor_addrtype);
      os.write_ulong(val.acceptor_address.length);
      for (int i0=0; i0<val.acceptor_address.length; i0++) {
        os.write_octet(val.acceptor_address[i0]);
      }
      os.write_ulong(val.application_data.length);
      for (int i0=0; i0<val.application_data.length; i0++) {
        os.write_octet(val.application_data[i0]);
      }
  };

}
