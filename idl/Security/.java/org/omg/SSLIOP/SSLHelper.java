//
// SSLHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SSLIOP;

abstract public class SSLHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, SSL value) {
    any.insert_Streamable(new SSLHolder(value));
  };

  public static SSL extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof SSLHolder){
          return ((SSLHolder) holder).value;
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
      members[2] = new org.omg.CORBA.StructMember("port", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      _type = _orb().create_struct_tc(id(), "SSL", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/SSLIOP/SSL:1.0";
  };

  public static SSL read(org.omg.CORBA.portable.InputStream is) {
    SSL result = new SSL();
    result.target_supports = is.read_ushort();
    result.target_requires = is.read_ushort();
    result.port = is.read_ushort();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, SSL val) {
    os.write_ushort(val.target_supports);
    os.write_ushort(val.target_requires);
    os.write_ushort(val.port);
  };

}
