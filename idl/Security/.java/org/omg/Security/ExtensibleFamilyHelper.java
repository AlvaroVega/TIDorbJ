//
// ExtensibleFamilyHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class ExtensibleFamilyHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ExtensibleFamily value) {
    any.insert_Streamable(new ExtensibleFamilyHolder(value));
  };

  public static ExtensibleFamily extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ExtensibleFamilyHolder){
          return ((ExtensibleFamilyHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("family_definer", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      members[1] = new org.omg.CORBA.StructMember("family", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      _type = _orb().create_struct_tc(id(), "ExtensibleFamily", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/ExtensibleFamily:1.0";
  };

  public static ExtensibleFamily read(org.omg.CORBA.portable.InputStream is) {
    ExtensibleFamily result = new ExtensibleFamily();
    result.family_definer = is.read_ushort();
    result.family = is.read_ushort();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ExtensibleFamily val) {
    os.write_ushort(val.family_definer);
    os.write_ushort(val.family);
  };

}
