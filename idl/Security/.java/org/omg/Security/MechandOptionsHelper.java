//
// MechandOptionsHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class MechandOptionsHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, MechandOptions value) {
    any.insert_Streamable(new MechandOptionsHolder(value));
  };

  public static MechandOptions extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof MechandOptionsHolder){
          return ((MechandOptionsHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("mechanism_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[1] = new org.omg.CORBA.StructMember("options_supported", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      _type = _orb().create_struct_tc(id(), "MechandOptions", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/MechandOptions:1.0";
  };

  public static MechandOptions read(org.omg.CORBA.portable.InputStream is) {
    MechandOptions result = new MechandOptions();
    result.mechanism_type = is.read_string();
    result.options_supported = is.read_ushort();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, MechandOptions val) {
    os.write_string(val.mechanism_type);
    os.write_ushort(val.options_supported);
  };

}
