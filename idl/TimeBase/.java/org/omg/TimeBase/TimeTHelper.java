//
// TimeTHelper.java (helper)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.TimeBase;

abstract public class TimeTHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, long value) {
    any.insert_Streamable(new org.omg.CORBA.LongHolder(value));
  };

  public static long extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof org.omg.CORBA.LongHolder){
          return ((org.omg.CORBA.LongHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.TypeCode original_type = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulonglong);
      _type = _orb().create_alias_tc(id(), "TimeT", original_type);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/TimeBase/TimeT:1.0";
  };

  public static long read(org.omg.CORBA.portable.InputStream is) {
    long result;
    result = is.read_ulonglong();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, long val) {
    os.write_ulonglong(val);
  };

}
