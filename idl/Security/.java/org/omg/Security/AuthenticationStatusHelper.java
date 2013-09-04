//
// AuthenticationStatusHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class AuthenticationStatusHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, AuthenticationStatus value) {
    any.insert_Streamable(new AuthenticationStatusHolder(value));
  };

  public static AuthenticationStatus extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof AuthenticationStatusHolder){
          return ((AuthenticationStatusHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[4];
      members[0] = "SecAuthSuccess";
      members[1] = "SecAuthFailure";
      members[2] = "SecAuthContinue";
      members[3] = "SecAuthExpired";
      _type = _orb().create_enum_tc(id(), "AuthenticationStatus", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/AuthenticationStatus:1.0";
  };

  public static AuthenticationStatus read(org.omg.CORBA.portable.InputStream is) {
    return AuthenticationStatus.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, AuthenticationStatus val) {
    os.write_long(val.value());
  };

}
