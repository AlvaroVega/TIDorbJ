//
// FullInterfaceDescriptionHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA.InterfaceDefPackage;

abstract public class FullInterfaceDescriptionHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, FullInterfaceDescription value) {
    any.insert_Streamable(new FullInterfaceDescriptionHolder(value));
  };

  public static FullInterfaceDescription extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof FullInterfaceDescriptionHolder){
          return ((FullInterfaceDescriptionHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[9];
      members[0] = new org.omg.CORBA.StructMember("name", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[1] = new org.omg.CORBA.StructMember("id", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[2] = new org.omg.CORBA.StructMember("defined_in", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[3] = new org.omg.CORBA.StructMember("version", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[4] = new org.omg.CORBA.StructMember("operations", org.omg.CORBA.OpDescriptionSeqHelper.type(), null);
      members[5] = new org.omg.CORBA.StructMember("attributes", org.omg.CORBA.AttrDescriptionSeqHelper.type(), null);
      members[6] = new org.omg.CORBA.StructMember("base_interfaces", org.omg.CORBA.RepositoryIdSeqHelper.type(), null);
      members[7] = new org.omg.CORBA.StructMember("type", org.omg.CORBA.TypeCodeHelper.type(), null);
      members[8] = new org.omg.CORBA.StructMember("is_abstract", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean), null);
      _type = _orb().create_struct_tc(id(), "FullInterfaceDescription", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/InterfaceDef/FullInterfaceDescription:1.0";
  };

  public static FullInterfaceDescription read(org.omg.CORBA.portable.InputStream is) {
    FullInterfaceDescription result = new FullInterfaceDescription();
    result.name = is.read_string();
    result.id = is.read_string();
    result.defined_in = is.read_string();
    result.version = is.read_string();
    result.operations = org.omg.CORBA.OpDescriptionSeqHelper.read(is);
    result.attributes = org.omg.CORBA.AttrDescriptionSeqHelper.read(is);
    result.base_interfaces = org.omg.CORBA.RepositoryIdSeqHelper.read(is);
    result.type = org.omg.CORBA.TypeCodeHelper.read(is);
    result.is_abstract = is.read_boolean();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, FullInterfaceDescription val) {
    os.write_string(val.name);
    os.write_string(val.id);
    os.write_string(val.defined_in);
    os.write_string(val.version);
    org.omg.CORBA.OpDescriptionSeqHelper.write(os,val.operations);
    org.omg.CORBA.AttrDescriptionSeqHelper.write(os,val.attributes);
    org.omg.CORBA.RepositoryIdSeqHelper.write(os,val.base_interfaces);
    org.omg.CORBA.TypeCodeHelper.write(os,val.type);
    os.write_boolean(val.is_abstract);
  };

}
