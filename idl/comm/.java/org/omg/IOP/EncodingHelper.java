//
// EncodingHelper.java (helper)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

abstract public class EncodingHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, Encoding value) {
    any.insert_Streamable(new EncodingHolder(value));
  };

  public static Encoding extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof EncodingHolder){
          return ((EncodingHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[3];
      members[0] = new org.omg.CORBA.StructMember("format", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_short), null);
      members[1] = new org.omg.CORBA.StructMember("major_version", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet), null);
      members[2] = new org.omg.CORBA.StructMember("minor_version", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet), null);
      _type = _orb().create_struct_tc(id(), "Encoding", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/IOP/Encoding:1.0";
  };

  public static Encoding read(org.omg.CORBA.portable.InputStream is) {
    Encoding result = new Encoding();
    result.format = is.read_short();
    result.major_version = is.read_octet();
    result.minor_version = is.read_octet();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, Encoding val) {
    os.write_short(val.format);
    os.write_octet(val.major_version);
    os.write_octet(val.minor_version);
  };

}
