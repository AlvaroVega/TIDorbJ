//
// AuthorizationElementHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

abstract public class AuthorizationElementHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, AuthorizationElement value) {
    any.insert_Streamable(new AuthorizationElementHolder(value));
  };

  public static AuthorizationElement extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof AuthorizationElementHolder){
          return ((AuthorizationElementHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("the_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      members[1] = new org.omg.CORBA.StructMember("the_element", org.omg.CSI.AuthorizationElementContentsHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "AuthorizationElement", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSI/AuthorizationElement:1.0";
  };

  public static AuthorizationElement read(org.omg.CORBA.portable.InputStream is) {
    AuthorizationElement result = new AuthorizationElement();
    result.the_type = is.read_ulong();
    result.the_element = org.omg.CSI.AuthorizationElementContentsHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, AuthorizationElement val) {
    os.write_ulong(val.the_type);
    org.omg.CSI.AuthorizationElementContentsHelper.write(os,val.the_element);
  };

}
