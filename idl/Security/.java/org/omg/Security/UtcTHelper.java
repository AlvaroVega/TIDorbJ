//
// UtcTHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class UtcTHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, org.omg.TimeBase.UtcT value) {
    any.insert_Streamable(new org.omg.TimeBase.UtcTHolder(value));
  };

  public static org.omg.TimeBase.UtcT extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof org.omg.TimeBase.UtcTHolder){
          return ((org.omg.TimeBase.UtcTHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.TypeCode original_type = org.omg.TimeBase.UtcTHelper.type();
      _type = _orb().create_alias_tc(id(), "UtcT", original_type);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/UtcT:1.0";
  };

  public static org.omg.TimeBase.UtcT read(org.omg.CORBA.portable.InputStream is) {
    org.omg.TimeBase.UtcT result;
    result = org.omg.TimeBase.UtcTHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, org.omg.TimeBase.UtcT val) {
    org.omg.TimeBase.UtcTHelper.write(os,val);
  };

}
