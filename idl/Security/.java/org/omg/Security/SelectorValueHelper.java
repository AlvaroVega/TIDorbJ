//
// SelectorValueHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class SelectorValueHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, SelectorValue value) {
    any.insert_Streamable(new SelectorValueHolder(value));
  };

  public static SelectorValue extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof SelectorValueHolder){
          return ((SelectorValueHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("selector", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      members[1] = new org.omg.CORBA.StructMember("value", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_any), null);
      _type = _orb().create_struct_tc(id(), "SelectorValue", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/SelectorValue:1.0";
  };

  public static SelectorValue read(org.omg.CORBA.portable.InputStream is) {
    SelectorValue result = new SelectorValue();
    result.selector = is.read_ulong();
    result.value = is.read_any();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, SelectorValue val) {
    os.write_ulong(val.selector);
    os.write_any(val.value);
  };

}
