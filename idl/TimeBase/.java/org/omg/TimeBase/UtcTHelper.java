//
// UtcTHelper.java (helper)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.TimeBase;

abstract public class UtcTHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, UtcT value) {
    any.insert_Streamable(new UtcTHolder(value));
  };

  public static UtcT extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof UtcTHolder){
          return ((UtcTHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[4];
      members[0] = new org.omg.CORBA.StructMember("time", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulonglong), null);
      members[1] = new org.omg.CORBA.StructMember("inacclo", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      members[2] = new org.omg.CORBA.StructMember("inacchi", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      members[3] = new org.omg.CORBA.StructMember("tdf", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_short), null);
      _type = _orb().create_struct_tc(id(), "UtcT", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/TimeBase/UtcT:1.0";
  };

  public static UtcT read(org.omg.CORBA.portable.InputStream is) {
    UtcT result = new UtcT();
    result.time = is.read_ulonglong();
    result.inacclo = is.read_ulong();
    result.inacchi = is.read_ushort();
    result.tdf = is.read_short();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, UtcT val) {
    os.write_ulonglong(val.time);
    os.write_ulong(val.inacclo);
    os.write_ushort(val.inacchi);
    os.write_short(val.tdf);
  };

}
