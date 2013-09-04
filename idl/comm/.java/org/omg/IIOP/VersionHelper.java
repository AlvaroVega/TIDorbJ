//
// VersionHelper.java (helper)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IIOP;

abstract public class VersionHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, Version value) {
    any.insert_Streamable(new VersionHolder(value));
  };

  public static Version extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof VersionHolder){
          return ((VersionHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("major", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet), null);
      members[1] = new org.omg.CORBA.StructMember("minor", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet), null);
      _type = _orb().create_struct_tc(id(), "Version", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/IIOP/Version:1.0";
  };

  public static Version read(org.omg.CORBA.portable.InputStream is) {
    Version result = new Version();
    result.major = is.read_octet();
    result.minor = is.read_octet();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, Version val) {
    os.write_octet(val.major);
    os.write_octet(val.minor);
  };

}
