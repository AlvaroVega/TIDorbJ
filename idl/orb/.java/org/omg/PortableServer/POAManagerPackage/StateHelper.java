//
// StateHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer.POAManagerPackage;

abstract public class StateHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, State value) {
    any.insert_Streamable(new StateHolder(value));
  };

  public static State extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof StateHolder){
          return ((StateHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[4];
      members[0] = "HOLDING";
      members[1] = "ACTIVE";
      members[2] = "DISCARDING";
      members[3] = "INACTIVE";
      _type = _orb().create_enum_tc(id(), "State", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/PortableServer/POAManager/State:1.0";
  };

  public static State read(org.omg.CORBA.portable.InputStream is) {
    return State.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, State val) {
    os.write_long(val.value());
  };

}
