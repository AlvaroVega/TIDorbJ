//
// OperationModeHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class OperationModeHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, OperationMode value) {
    any.insert_Streamable(new OperationModeHolder(value));
  };

  public static OperationMode extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof OperationModeHolder){
          return ((OperationModeHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[2];
      members[0] = "OP_NORMAL";
      members[1] = "OP_ONEWAY";
      _type = _orb().create_enum_tc(id(), "OperationMode", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/OperationMode:1.0";
  };

  public static OperationMode read(org.omg.CORBA.portable.InputStream is) {
    return OperationMode.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, OperationMode val) {
    os.write_long(val.value());
  };

}
