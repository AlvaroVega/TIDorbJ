//
// UnknownEncodingHelper.java (helper)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP.CodecFactoryPackage;

abstract public class UnknownEncodingHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, UnknownEncoding value) {
    any.insert_Streamable(new UnknownEncodingHolder(value));
  };

  public static UnknownEncoding extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof UnknownEncodingHolder){
          return ((UnknownEncodingHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[0];
      _type = _orb().create_exception_tc(id(), "UnknownEncoding", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/IOP/CodecFactory/UnknownEncoding:1.0";
  };

  public static UnknownEncoding read(org.omg.CORBA.portable.InputStream is) {
    if (! is.read_string().equals(id())) {
      throw new org.omg.CORBA.MARSHAL("Invalid repository id.");
    };
    UnknownEncoding result = new UnknownEncoding();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, UnknownEncoding val) {
    os.write_string(id());
  };

}
