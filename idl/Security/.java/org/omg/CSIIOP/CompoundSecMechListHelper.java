//
// CompoundSecMechListHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

abstract public class CompoundSecMechListHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, CompoundSecMechList value) {
    any.insert_Streamable(new CompoundSecMechListHolder(value));
  };

  public static CompoundSecMechList extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof CompoundSecMechListHolder){
          return ((CompoundSecMechListHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("stateful", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean), null);
      members[1] = new org.omg.CORBA.StructMember("mechanism_list", org.omg.CSIIOP.CompoundSecMechanismsHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "CompoundSecMechList", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSIIOP/CompoundSecMechList:1.0";
  };

  public static CompoundSecMechList read(org.omg.CORBA.portable.InputStream is) {
    CompoundSecMechList result = new CompoundSecMechList();
    result.stateful = is.read_boolean();
    result.mechanism_list = org.omg.CSIIOP.CompoundSecMechanismsHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, CompoundSecMechList val) {
    os.write_boolean(val.stateful);
    org.omg.CSIIOP.CompoundSecMechanismsHelper.write(os,val.mechanism_list);
  };

}
