//
// AttributeTypeHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class AttributeTypeHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, AttributeType value) {
    any.insert_Streamable(new AttributeTypeHolder(value));
  };

  public static AttributeType extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof AttributeTypeHolder){
          return ((AttributeTypeHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("attribute_family", org.omg.Security.ExtensibleFamilyHelper.type(), null);
      members[1] = new org.omg.CORBA.StructMember("attribute_type", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      _type = _orb().create_struct_tc(id(), "AttributeType", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/AttributeType:1.0";
  };

  public static AttributeType read(org.omg.CORBA.portable.InputStream is) {
    AttributeType result = new AttributeType();
    result.attribute_family = org.omg.Security.ExtensibleFamilyHelper.read(is);
    result.attribute_type = is.read_ulong();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, AttributeType val) {
    org.omg.Security.ExtensibleFamilyHelper.write(os,val.attribute_family);
    os.write_ulong(val.attribute_type);
  };

}
