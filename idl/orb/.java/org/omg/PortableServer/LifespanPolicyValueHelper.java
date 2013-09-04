//
// LifespanPolicyValueHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

abstract public class LifespanPolicyValueHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, LifespanPolicyValue value) {
    any.insert_Streamable(new LifespanPolicyValueHolder(value));
  };

  public static LifespanPolicyValue extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof LifespanPolicyValueHolder){
          return ((LifespanPolicyValueHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[2];
      members[0] = "TRANSIENT";
      members[1] = "PERSISTENT";
      _type = _orb().create_enum_tc(id(), "LifespanPolicyValue", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/PortableServer/LifespanPolicyValue:1.0";
  };

  public static LifespanPolicyValue read(org.omg.CORBA.portable.InputStream is) {
    return LifespanPolicyValue.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, LifespanPolicyValue val) {
    os.write_long(val.value());
  };

}
