//
// PriorityRangeHelper.java (helper)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

abstract public class PriorityRangeHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, PriorityRange value) {
    any.insert_Streamable(new PriorityRangeHolder(value));
  };

  public static PriorityRange extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof PriorityRangeHolder){
          return ((PriorityRangeHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("min", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_short), null);
      members[1] = new org.omg.CORBA.StructMember("max", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_short), null);
      _type = _orb().create_struct_tc(id(), "PriorityRange", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Messaging/PriorityRange:1.0";
  };

  public static PriorityRange read(org.omg.CORBA.portable.InputStream is) {
    PriorityRange result = new PriorityRange();
    result.min = is.read_short();
    result.max = is.read_short();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, PriorityRange val) {
    os.write_short(val.min);
    os.write_short(val.max);
  };

}
