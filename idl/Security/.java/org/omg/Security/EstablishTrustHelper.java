//
// EstablishTrustHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class EstablishTrustHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, EstablishTrust value) {
    any.insert_Streamable(new EstablishTrustHolder(value));
  };

  public static EstablishTrust extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof EstablishTrustHolder){
          return ((EstablishTrustHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("trust_in_client", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean), null);
      members[1] = new org.omg.CORBA.StructMember("trust_in_target", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean), null);
      _type = _orb().create_struct_tc(id(), "EstablishTrust", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/EstablishTrust:1.0";
  };

  public static EstablishTrust read(org.omg.CORBA.portable.InputStream is) {
    EstablishTrust result = new EstablishTrust();
    result.trust_in_client = is.read_boolean();
    result.trust_in_target = is.read_boolean();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, EstablishTrust val) {
    os.write_boolean(val.trust_in_client);
    os.write_boolean(val.trust_in_target);
  };

}
