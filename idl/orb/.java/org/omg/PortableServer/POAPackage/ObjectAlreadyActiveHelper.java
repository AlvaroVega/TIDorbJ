//
// ObjectAlreadyActiveHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer.POAPackage;

abstract public class ObjectAlreadyActiveHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ObjectAlreadyActive value) {
    any.insert_Streamable(new ObjectAlreadyActiveHolder(value));
  };

  public static ObjectAlreadyActive extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ObjectAlreadyActiveHolder){
          return ((ObjectAlreadyActiveHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[0];
      _type = _orb().create_exception_tc(id(), "ObjectAlreadyActive", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/PortableServer/POA/ObjectAlreadyActive:1.0";
  };

  public static ObjectAlreadyActive read(org.omg.CORBA.portable.InputStream is) {
    if (! is.read_string().equals(id())) {
      throw new org.omg.CORBA.MARSHAL("Invalid repository id.");
    };
    ObjectAlreadyActive result = new ObjectAlreadyActive();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ObjectAlreadyActive val) {
    os.write_string(id());
  };

}
