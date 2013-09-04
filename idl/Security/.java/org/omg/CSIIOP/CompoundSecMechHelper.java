//
// CompoundSecMechHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

abstract public class CompoundSecMechHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, CompoundSecMech value) {
    any.insert_Streamable(new CompoundSecMechHolder(value));
  };

  public static CompoundSecMech extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof CompoundSecMechHolder){
          return ((CompoundSecMechHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[4];
      members[0] = new org.omg.CORBA.StructMember("target_requires", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      members[1] = new org.omg.CORBA.StructMember("transport_mech", org.omg.IOP.TaggedComponentHelper.type(), null);
      members[2] = new org.omg.CORBA.StructMember("as_context_mech", org.omg.CSIIOP.AS_ContextSecHelper.type(), null);
      members[3] = new org.omg.CORBA.StructMember("sas_context_mech", org.omg.CSIIOP.SAS_ContextSecHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "CompoundSecMech", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSIIOP/CompoundSecMech:1.0";
  };

  public static CompoundSecMech read(org.omg.CORBA.portable.InputStream is) {
    CompoundSecMech result = new CompoundSecMech();
    result.target_requires = is.read_ushort();
    result.transport_mech = org.omg.IOP.TaggedComponentHelper.read(is);
    result.as_context_mech = org.omg.CSIIOP.AS_ContextSecHelper.read(is);
    result.sas_context_mech = org.omg.CSIIOP.SAS_ContextSecHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, CompoundSecMech val) {
    os.write_ushort(val.target_requires);
    org.omg.IOP.TaggedComponentHelper.write(os,val.transport_mech);
    org.omg.CSIIOP.AS_ContextSecHelper.write(os,val.as_context_mech);
    org.omg.CSIIOP.SAS_ContextSecHelper.write(os,val.sas_context_mech);
  };

}
