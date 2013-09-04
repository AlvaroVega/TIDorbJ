//
// ObjectIdHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

abstract public class ObjectIdHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, byte[] value) {
    any.insert_Streamable(new org.omg.CORBA.OctetSeqHolder(value));
  };

  public static byte[] extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof org.omg.CORBA.OctetSeqHolder){
          return ((org.omg.CORBA.OctetSeqHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.TypeCode original_type = org.omg.CORBA.OctetSeqHelper.type();
      _type = _orb().create_alias_tc(id(), "ObjectId", original_type);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/PortableServer/ObjectId:1.0";
  };

  public static byte[] read(org.omg.CORBA.portable.InputStream is) {
    byte[] result;
    result = org.omg.CORBA.OctetSeqHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, byte[] val) {
    org.omg.CORBA.OctetSeqHelper.write(os,val);
  };

}
