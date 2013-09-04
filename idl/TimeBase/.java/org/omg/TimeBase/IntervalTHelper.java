//
// IntervalTHelper.java (helper)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.TimeBase;

abstract public class IntervalTHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, IntervalT value) {
    any.insert_Streamable(new IntervalTHolder(value));
  };

  public static IntervalT extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof IntervalTHolder){
          return ((IntervalTHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("lower_bound", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulonglong), null);
      members[1] = new org.omg.CORBA.StructMember("upper_bound", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulonglong), null);
      _type = _orb().create_struct_tc(id(), "IntervalT", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/TimeBase/IntervalT:1.0";
  };

  public static IntervalT read(org.omg.CORBA.portable.InputStream is) {
    IntervalT result = new IntervalT();
    result.lower_bound = is.read_ulonglong();
    result.upper_bound = is.read_ulonglong();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, IntervalT val) {
    os.write_ulonglong(val.lower_bound);
    os.write_ulonglong(val.upper_bound);
  };

}
