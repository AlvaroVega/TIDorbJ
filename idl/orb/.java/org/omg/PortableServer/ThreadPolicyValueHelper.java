//
// ThreadPolicyValueHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

abstract public class ThreadPolicyValueHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ThreadPolicyValue value) {
    any.insert_Streamable(new ThreadPolicyValueHolder(value));
  };

  public static ThreadPolicyValue extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ThreadPolicyValueHolder){
          return ((ThreadPolicyValueHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[3];
      members[0] = "ORB_CTRL_MODEL";
      members[1] = "SINGLE_THREAD_MODEL";
      members[2] = "MAIN_THREAD_MODEL";
      _type = _orb().create_enum_tc(id(), "ThreadPolicyValue", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/PortableServer/ThreadPolicyValue:1.0";
  };

  public static ThreadPolicyValue read(org.omg.CORBA.portable.InputStream is) {
    return ThreadPolicyValue.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ThreadPolicyValue val) {
    os.write_long(val.value());
  };

}
