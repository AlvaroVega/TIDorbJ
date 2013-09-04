//
// BadFixedValueHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class BadFixedValueHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, BadFixedValue value) {
    any.insert_Streamable(new BadFixedValueHolder(value));
  };

  public static BadFixedValue extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof BadFixedValueHolder){
          return ((BadFixedValueHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[1];
      members[0] = new org.omg.CORBA.StructMember("offset", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      _type = _orb().create_exception_tc(id(), "BadFixedValue", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/BadFixedValue:1.0";
  };

  public static BadFixedValue read(org.omg.CORBA.portable.InputStream is) {
    if (! is.read_string().equals(id())) {
      throw new org.omg.CORBA.MARSHAL("Invalid repository id.");
    };
    BadFixedValue result = new BadFixedValue();
    result.offset = is.read_ulong();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, BadFixedValue val) {
    os.write_string(id());
    os.write_ulong(val.offset);
  };

}
