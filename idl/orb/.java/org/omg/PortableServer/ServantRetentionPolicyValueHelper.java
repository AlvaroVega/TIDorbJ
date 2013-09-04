//
// ServantRetentionPolicyValueHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

abstract public class ServantRetentionPolicyValueHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, ServantRetentionPolicyValue value) {
    any.insert_Streamable(new ServantRetentionPolicyValueHolder(value));
  };

  public static ServantRetentionPolicyValue extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof ServantRetentionPolicyValueHolder){
          return ((ServantRetentionPolicyValueHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[2];
      members[0] = "RETAIN";
      members[1] = "NON_RETAIN";
      _type = _orb().create_enum_tc(id(), "ServantRetentionPolicyValue", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/PortableServer/ServantRetentionPolicyValue:1.0";
  };

  public static ServantRetentionPolicyValue read(org.omg.CORBA.portable.InputStream is) {
    return ServantRetentionPolicyValue.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, ServantRetentionPolicyValue val) {
    os.write_long(val.value());
  };

}
