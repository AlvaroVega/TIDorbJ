//
// SecAttributeHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class SecAttributeHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, SecAttribute value) {
    any.insert_Streamable(new SecAttributeHolder(value));
  };

  public static SecAttribute extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof SecAttributeHolder){
          return ((SecAttributeHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[3];
      members[0] = new org.omg.CORBA.StructMember("attribute_type", org.omg.Security.AttributeTypeHelper.type(), null);
      members[1] = new org.omg.CORBA.StructMember("defining_authority", org.omg.Security.OIDHelper.type(), null);
      members[2] = new org.omg.CORBA.StructMember("value", org.omg.Security.OpaqueHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "SecAttribute", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/SecAttribute:1.0";
  };

  public static SecAttribute read(org.omg.CORBA.portable.InputStream is) {
    SecAttribute result = new SecAttribute();
    result.attribute_type = org.omg.Security.AttributeTypeHelper.read(is);
    result.defining_authority = org.omg.Security.OIDHelper.read(is);
    result.value = org.omg.Security.OpaqueHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, SecAttribute val) {
    org.omg.Security.AttributeTypeHelper.write(os,val.attribute_type);
    org.omg.Security.OIDHelper.write(os,val.defining_authority);
    org.omg.Security.OpaqueHelper.write(os,val.value);
  };

}
