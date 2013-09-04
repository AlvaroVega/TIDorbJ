//
// RightHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class RightHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, Right value) {
    any.insert_Streamable(new RightHolder(value));
  };

  public static Right extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof RightHolder){
          return ((RightHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("rights_family", org.omg.Security.ExtensibleFamilyHelper.type(), null);
      members[1] = new org.omg.CORBA.StructMember("the_right", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      _type = _orb().create_struct_tc(id(), "Right", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/Right:1.0";
  };

  public static Right read(org.omg.CORBA.portable.InputStream is) {
    Right result = new Right();
    result.rights_family = org.omg.Security.ExtensibleFamilyHelper.read(is);
    result.the_right = is.read_string();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, Right val) {
    org.omg.Security.ExtensibleFamilyHelper.write(os,val.rights_family);
    os.write_string(val.the_right);
  };

}
