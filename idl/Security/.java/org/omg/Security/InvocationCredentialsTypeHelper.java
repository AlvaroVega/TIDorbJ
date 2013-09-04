//
// InvocationCredentialsTypeHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class InvocationCredentialsTypeHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, InvocationCredentialsType value) {
    any.insert_Streamable(new InvocationCredentialsTypeHolder(value));
  };

  public static InvocationCredentialsType extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof InvocationCredentialsTypeHolder){
          return ((InvocationCredentialsTypeHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[3];
      members[0] = "SecOwnCredentials";
      members[1] = "SecReceivedCredentials";
      members[2] = "SecTargetCredentials";
      _type = _orb().create_enum_tc(id(), "InvocationCredentialsType", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/InvocationCredentialsType:1.0";
  };

  public static InvocationCredentialsType read(org.omg.CORBA.portable.InputStream is) {
    return InvocationCredentialsType.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, InvocationCredentialsType val) {
    os.write_long(val.value());
  };

}
