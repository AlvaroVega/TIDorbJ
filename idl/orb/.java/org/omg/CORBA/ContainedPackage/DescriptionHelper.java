//
// DescriptionHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA.ContainedPackage;

abstract public class DescriptionHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, Description value) {
    any.insert_Streamable(new DescriptionHolder(value));
  };

  public static Description extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof DescriptionHolder){
          return ((DescriptionHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("kind", org.omg.CORBA.DefinitionKindHelper.type(), null);
      members[1] = new org.omg.CORBA.StructMember("value", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_any), null);
      _type = _orb().create_struct_tc(id(), "Description", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/Contained/Description:1.0";
  };

  public static Description read(org.omg.CORBA.portable.InputStream is) {
    Description result = new Description();
    result.kind = org.omg.CORBA.DefinitionKindHelper.read(is);
    result.value = is.read_any();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, Description val) {
    org.omg.CORBA.DefinitionKindHelper.write(os,val.kind);
    os.write_any(val.value);
  };

}
