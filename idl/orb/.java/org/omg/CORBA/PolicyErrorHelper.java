//
// PolicyErrorHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class PolicyErrorHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, PolicyError value) {
    any.insert_Streamable(new PolicyErrorHolder(value));
  };

  public static PolicyError extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof PolicyErrorHolder){
          return ((PolicyErrorHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[1];
      members[0] = new org.omg.CORBA.StructMember("reason", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_short), null);
      _type = _orb().create_exception_tc(id(), "PolicyError", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/PolicyError:1.0";
  };

  public static PolicyError read(org.omg.CORBA.portable.InputStream is) {
    if (! is.read_string().equals(id())) {
      throw new org.omg.CORBA.MARSHAL("Invalid repository id.");
    };
    PolicyError result = new PolicyError();
    result.reason = is.read_short();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, PolicyError val) {
    os.write_string(id());
    os.write_short(val.reason);
  };

}
