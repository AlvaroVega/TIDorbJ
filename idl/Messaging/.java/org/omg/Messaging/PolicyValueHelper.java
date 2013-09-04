//
// PolicyValueHelper.java (helper)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

abstract public class PolicyValueHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, PolicyValue value) {
    any.insert_Streamable(new PolicyValueHolder(value));
  };

  public static PolicyValue extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof PolicyValueHolder){
          return ((PolicyValueHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("ptype", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      members[1] = new org.omg.CORBA.StructMember("pvalue", _orb().create_sequence_tc(0 , org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet)), null);
      _type = _orb().create_struct_tc(id(), "PolicyValue", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/PolicyValue:1.0";
  };

  public static PolicyValue read(org.omg.CORBA.portable.InputStream is) {
    PolicyValue result = new PolicyValue();
    result.ptype = is.read_ulong();
      int length2 = is.read_ulong();
      result.pvalue = new byte[length2];
      for (int i0=0; i0<length2; i0++) {
        result.pvalue[i0] = is.read_octet();
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, PolicyValue val) {
    os.write_ulong(val.ptype);
      os.write_ulong(val.pvalue.length);
      for (int i0=0; i0<val.pvalue.length; i0++) {
        os.write_octet(val.pvalue[i0]);
      }
  };

}
