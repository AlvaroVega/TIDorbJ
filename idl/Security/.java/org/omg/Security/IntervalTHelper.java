//
// IntervalTHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class IntervalTHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, org.omg.TimeBase.IntervalT value) {
    any.insert_Streamable(new org.omg.TimeBase.IntervalTHolder(value));
  };

  public static org.omg.TimeBase.IntervalT extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof org.omg.TimeBase.IntervalTHolder){
          return ((org.omg.TimeBase.IntervalTHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.TypeCode original_type = org.omg.TimeBase.IntervalTHelper.type();
      _type = _orb().create_alias_tc(id(), "IntervalT", original_type);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/IntervalT:1.0";
  };

  public static org.omg.TimeBase.IntervalT read(org.omg.CORBA.portable.InputStream is) {
    org.omg.TimeBase.IntervalT result;
    result = org.omg.TimeBase.IntervalTHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, org.omg.TimeBase.IntervalT val) {
    org.omg.TimeBase.IntervalTHelper.write(os,val);
  };

}
