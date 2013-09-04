//
// SecurityContextStateHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class SecurityContextStateHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, SecurityContextState value) {
    any.insert_Streamable(new SecurityContextStateHolder(value));
  };

  public static SecurityContextState extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof SecurityContextStateHolder){
          return ((SecurityContextStateHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[7];
      members[0] = "SecContextInitialized";
      members[1] = "SecContextContinued";
      members[2] = "SecContextClientEstablished";
      members[3] = "SecContextEstablished";
      members[4] = "SecContextEstablishExpired";
      members[5] = "SecContextExpired";
      members[6] = "SecContextInvalid";
      _type = _orb().create_enum_tc(id(), "SecurityContextState", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/SecurityContextState:1.0";
  };

  public static SecurityContextState read(org.omg.CORBA.portable.InputStream is) {
    return SecurityContextState.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, SecurityContextState val) {
    os.write_long(val.value());
  };

}
