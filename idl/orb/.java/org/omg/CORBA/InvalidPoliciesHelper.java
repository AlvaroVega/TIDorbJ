//
// InvalidPoliciesHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class InvalidPoliciesHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, InvalidPolicies value) {
    any.insert_Streamable(new InvalidPoliciesHolder(value));
  };

  public static InvalidPolicies extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof InvalidPoliciesHolder){
          return ((InvalidPoliciesHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[1];
      members[0] = new org.omg.CORBA.StructMember("indices", _orb().create_sequence_tc(0 , org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort)), null);
      _type = _orb().create_exception_tc(id(), "InvalidPolicies", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/InvalidPolicies:1.0";
  };

  public static InvalidPolicies read(org.omg.CORBA.portable.InputStream is) {
    if (! is.read_string().equals(id())) {
      throw new org.omg.CORBA.MARSHAL("Invalid repository id.");
    };
    InvalidPolicies result = new InvalidPolicies();
      int length0 = is.read_ulong();
      result.indices = new short[length0];
      for (int i0=0; i0<length0; i0++) {
        result.indices[i0] = is.read_ushort();
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, InvalidPolicies val) {
    os.write_string(id());
      os.write_ulong(val.indices.length);
      for (int i0=0; i0<val.indices.length; i0++) {
        os.write_ushort(val.indices[i0]);
      }
  };

}
