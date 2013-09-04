//
// DoubleSeqHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class DoubleSeqHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, double[] value) {
    any.insert_Streamable(new DoubleSeqHolder(value));
  };

  public static double[] extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof DoubleSeqHolder){
          return ((DoubleSeqHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.TypeCode original_type = _orb().create_sequence_tc(0 , org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_double));
      _type = _orb().create_alias_tc(id(), "DoubleSeq", original_type);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/DoubleSeq:1.0";
  };

  public static double[] read(org.omg.CORBA.portable.InputStream is) {
    double[] result;
      int length0 = is.read_ulong();
      result = new double[length0];
      for (int i0=0; i0<length0; i0++) {
        result[i0] = is.read_double();
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, double[] val) {
      os.write_ulong(val.length);
      for (int i0=0; i0<val.length; i0++) {
        os.write_double(val[i0]);
      }
  };

}
