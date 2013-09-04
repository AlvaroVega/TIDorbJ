//
// RequestProcessingPolicyValueHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

abstract public class RequestProcessingPolicyValueHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, RequestProcessingPolicyValue value) {
    any.insert_Streamable(new RequestProcessingPolicyValueHolder(value));
  };

  public static RequestProcessingPolicyValue extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof RequestProcessingPolicyValueHolder){
          return ((RequestProcessingPolicyValueHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[3];
      members[0] = "USE_ACTIVE_OBJECT_MAP_ONLY";
      members[1] = "USE_DEFAULT_SERVANT";
      members[2] = "USE_SERVANT_MANAGER";
      _type = _orb().create_enum_tc(id(), "RequestProcessingPolicyValue", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/PortableServer/RequestProcessingPolicyValue:1.0";
  };

  public static RequestProcessingPolicyValue read(org.omg.CORBA.portable.InputStream is) {
    return RequestProcessingPolicyValue.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, RequestProcessingPolicyValue val) {
    os.write_long(val.value());
  };

}
