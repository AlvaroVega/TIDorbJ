//
// NameValuePairSeqHelper.java (helper)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny;

abstract public class NameValuePairSeqHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, org.omg.DynamicAny.NameValuePair[] value) {
    any.insert_Streamable(new NameValuePairSeqHolder(value));
  };

  public static org.omg.DynamicAny.NameValuePair[] extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof NameValuePairSeqHolder){
          return ((NameValuePairSeqHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.TypeCode original_type = _orb().create_sequence_tc(0 , org.omg.DynamicAny.NameValuePairHelper.type());
      _type = _orb().create_alias_tc(id(), "NameValuePairSeq", original_type);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/DynamicAny/NameValuePairSeq:1.0";
  };

  public static org.omg.DynamicAny.NameValuePair[] read(org.omg.CORBA.portable.InputStream is) {
    org.omg.DynamicAny.NameValuePair[] result;
      int length0 = is.read_ulong();
      result = new org.omg.DynamicAny.NameValuePair[length0];
      for (int i0=0; i0<length0; i0++) {
        result[i0] = org.omg.DynamicAny.NameValuePairHelper.read(is);
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, org.omg.DynamicAny.NameValuePair[] val) {
      os.write_ulong(val.length);
      for (int i0=0; i0<val.length; i0++) {
        org.omg.DynamicAny.NameValuePairHelper.write(os,val[i0]);
      }
  };

}
