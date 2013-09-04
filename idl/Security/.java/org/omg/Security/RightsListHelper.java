//
// RightsListHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class RightsListHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, org.omg.Security.Right[] value) {
    any.insert_Streamable(new RightsListHolder(value));
  };

  public static org.omg.Security.Right[] extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof RightsListHolder){
          return ((RightsListHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.TypeCode original_type = _orb().create_sequence_tc(0 , org.omg.Security.RightHelper.type());
      _type = _orb().create_alias_tc(id(), "RightsList", original_type);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/RightsList:1.0";
  };

  public static org.omg.Security.Right[] read(org.omg.CORBA.portable.InputStream is) {
    org.omg.Security.Right[] result;
      int length0 = is.read_ulong();
      result = new org.omg.Security.Right[length0];
      for (int i0=0; i0<length0; i0++) {
        result[i0] = org.omg.Security.RightHelper.read(is);
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, org.omg.Security.Right[] val) {
      os.write_ulong(val.length);
      for (int i0=0; i0<val.length; i0++) {
        org.omg.Security.RightHelper.write(os,val[i0]);
      }
  };

}
