//
// ForwardRequestHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

abstract public class ForwardRequestHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ForwardRequest value) {
    any.insert_Streamable(new ForwardRequestHolder(value));
  };

  public static ForwardRequest extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ForwardRequestHolder){
          return ((ForwardRequestHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[1];
      members[0] = new org.omg.CORBA.StructMember("forward_reference", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_objref), null);
      _type = _orb().create_exception_tc(id(), "ForwardRequest", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/PortableServer/ForwardRequest:1.0";
  };

  public static ForwardRequest read(org.omg.CORBA.portable.InputStream is) {
    if (! is.read_string().equals(id())) {
      throw new org.omg.CORBA.MARSHAL("Invalid repository id.");
    };
    ForwardRequest result = new ForwardRequest();
    result.forward_reference = is.read_Object();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ForwardRequest val) {
    os.write_string(id());
    os.write_Object(val.forward_reference);
  };

}
