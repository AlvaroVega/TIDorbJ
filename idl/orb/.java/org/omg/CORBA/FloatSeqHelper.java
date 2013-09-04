//
// FloatSeqHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class FloatSeqHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, float[] value) {
    any.insert_Streamable(new FloatSeqHolder(value));
  };

  public static float[] extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof FloatSeqHolder){
          return ((FloatSeqHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.TypeCode original_type = _orb().create_sequence_tc(0 , org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_float));
      _type = _orb().create_alias_tc(id(), "FloatSeq", original_type);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/FloatSeq:1.0";
  };

  public static float[] read(org.omg.CORBA.portable.InputStream is) {
    float[] result;
      int length0 = is.read_ulong();
      result = new float[length0];
      for (int i0=0; i0<length0; i0++) {
        result[i0] = is.read_float();
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, float[] val) {
      os.write_ulong(val.length);
      for (int i0=0; i0<val.length; i0++) {
        os.write_float(val[i0]);
      }
  };

}
