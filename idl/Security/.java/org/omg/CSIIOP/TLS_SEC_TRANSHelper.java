//
// TLS_SEC_TRANSHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

abstract public class TLS_SEC_TRANSHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, TLS_SEC_TRANS value) {
    any.insert_Streamable(new TLS_SEC_TRANSHolder(value));
  };

  public static TLS_SEC_TRANS extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof TLS_SEC_TRANSHolder){
          return ((TLS_SEC_TRANSHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[3];
      members[0] = new org.omg.CORBA.StructMember("target_supports", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      members[1] = new org.omg.CORBA.StructMember("target_requires", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      members[2] = new org.omg.CORBA.StructMember("addresses", org.omg.CSIIOP.TransportAddressListHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "TLS_SEC_TRANS", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSIIOP/TLS_SEC_TRANS:1.0";
  };

  public static TLS_SEC_TRANS read(org.omg.CORBA.portable.InputStream is) {
    TLS_SEC_TRANS result = new TLS_SEC_TRANS();
    result.target_supports = is.read_ushort();
    result.target_requires = is.read_ushort();
    result.addresses = org.omg.CSIIOP.TransportAddressListHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, TLS_SEC_TRANS val) {
    os.write_ushort(val.target_supports);
    os.write_ushort(val.target_requires);
    org.omg.CSIIOP.TransportAddressListHelper.write(os,val.addresses);
  };

}
