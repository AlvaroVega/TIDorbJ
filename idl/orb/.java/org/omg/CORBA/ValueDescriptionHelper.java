//
// ValueDescriptionHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class ValueDescriptionHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ValueDescription value) {
    any.insert_Streamable(new ValueDescriptionHolder(value));
  };

  public static ValueDescription extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ValueDescriptionHolder){
          return ((ValueDescriptionHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[10];
      members[0] = new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[1] = new org.omg.CORBA.StructMember("id", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[2] = new org.omg.CORBA.StructMember("is_abstract", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean), null);
      members[3] = new org.omg.CORBA.StructMember("is_custom", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean), null);
      members[4] = new org.omg.CORBA.StructMember("defined_in", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[5] = new org.omg.CORBA.StructMember("version", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[6] = new org.omg.CORBA.StructMember("supported_interfaces", org.omg.CORBA.RepositoryIdSeqHelper.type(), null);
      members[7] = new org.omg.CORBA.StructMember("abstract_base_values", org.omg.CORBA.RepositoryIdSeqHelper.type(), null);
      members[8] = new org.omg.CORBA.StructMember("is_truncatable", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean), null);
      members[9] = new org.omg.CORBA.StructMember("base_value", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      _type = _orb().create_struct_tc(id(), "ValueDescription", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/ValueDescription:1.0";
  };

  public static ValueDescription read(org.omg.CORBA.portable.InputStream is) {
    ValueDescription result = new ValueDescription();
    result.name = is.read_string();
    result.id = is.read_string();
    result.is_abstract = is.read_boolean();
    result.is_custom = is.read_boolean();
    result.defined_in = is.read_string();
    result.version = is.read_string();
    result.supported_interfaces = org.omg.CORBA.RepositoryIdSeqHelper.read(is);
    result.abstract_base_values = org.omg.CORBA.RepositoryIdSeqHelper.read(is);
    result.is_truncatable = is.read_boolean();
    result.base_value = is.read_string();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ValueDescription val) {
    os.write_string(val.name);
    os.write_string(val.id);
    os.write_boolean(val.is_abstract);
    os.write_boolean(val.is_custom);
    os.write_string(val.defined_in);
    os.write_string(val.version);
    org.omg.CORBA.RepositoryIdSeqHelper.write(os,val.supported_interfaces);
    org.omg.CORBA.RepositoryIdSeqHelper.write(os,val.abstract_base_values);
    os.write_boolean(val.is_truncatable);
    os.write_string(val.base_value);
  };

}
