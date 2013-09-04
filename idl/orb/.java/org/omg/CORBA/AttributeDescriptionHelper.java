//
// AttributeDescriptionHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class AttributeDescriptionHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, AttributeDescription value) {
    any.insert_Streamable(new AttributeDescriptionHolder(value));
  };

  public static AttributeDescription extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof AttributeDescriptionHolder){
          return ((AttributeDescriptionHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[6];
      members[0] = new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[1] = new org.omg.CORBA.StructMember("id", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[2] = new org.omg.CORBA.StructMember("defined_in", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[3] = new org.omg.CORBA.StructMember("version", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[4] = new org.omg.CORBA.StructMember("type", org.omg.CORBA.TypeCodeHelper.type(), null);
      members[5] = new org.omg.CORBA.StructMember("mode", org.omg.CORBA.AttributeModeHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "AttributeDescription", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/AttributeDescription:1.0";
  };

  public static AttributeDescription read(org.omg.CORBA.portable.InputStream is) {
    AttributeDescription result = new AttributeDescription();
    result.name = is.read_string();
    result.id = is.read_string();
    result.defined_in = is.read_string();
    result.version = is.read_string();
    result.type = org.omg.CORBA.TypeCodeHelper.read(is);
    result.mode = org.omg.CORBA.AttributeModeHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, AttributeDescription val) {
    os.write_string(val.name);
    os.write_string(val.id);
    os.write_string(val.defined_in);
    os.write_string(val.version);
    org.omg.CORBA.TypeCodeHelper.write(os,val.type);
    org.omg.CORBA.AttributeModeHelper.write(os,val.mode);
  };

}
