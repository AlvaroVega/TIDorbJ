//
// ProfileBody_1_0Helper.java (helper)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IIOP;

abstract public class ProfileBody_1_0Helper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ProfileBody_1_0 value) {
    any.insert_Streamable(new ProfileBody_1_0Holder(value));
  };

  public static ProfileBody_1_0 extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ProfileBody_1_0Holder){
          return ((ProfileBody_1_0Holder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[4];
      members[0] = new org.omg.CORBA.StructMember("iiop_version", org.omg.IIOP.VersionHelper.type(), null);
      members[1] = new org.omg.CORBA.StructMember("host", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[2] = new org.omg.CORBA.StructMember("port", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      members[3] = new org.omg.CORBA.StructMember("object_key", _orb().create_sequence_tc(0 , org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet)), null);
      _type = _orb().create_struct_tc(id(), "ProfileBody_1_0", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/IIOP/ProfileBody_1_0:1.0";
  };

  public static ProfileBody_1_0 read(org.omg.CORBA.portable.InputStream is) {
    ProfileBody_1_0 result = new ProfileBody_1_0();
    result.iiop_version = org.omg.IIOP.VersionHelper.read(is);
    result.host = is.read_string();
    result.port = is.read_ushort();
      int length6 = is.read_ulong();
      result.object_key = new byte[length6];
      for (int i0=0; i0<length6; i0++) {
        result.object_key[i0] = is.read_octet();
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ProfileBody_1_0 val) {
    org.omg.IIOP.VersionHelper.write(os,val.iiop_version);
    os.write_string(val.host);
    os.write_ushort(val.port);
      os.write_ulong(val.object_key.length);
      for (int i0=0; i0<val.object_key.length; i0++) {
        os.write_octet(val.object_key[i0]);
      }
  };

}
