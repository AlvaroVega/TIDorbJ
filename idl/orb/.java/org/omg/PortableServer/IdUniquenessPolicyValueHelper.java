//
// IdUniquenessPolicyValueHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

abstract public class IdUniquenessPolicyValueHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, IdUniquenessPolicyValue value) {
    any.insert_Streamable(new IdUniquenessPolicyValueHolder(value));
  };

  public static IdUniquenessPolicyValue extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof IdUniquenessPolicyValueHolder){
          return ((IdUniquenessPolicyValueHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[2];
      members[0] = "UNIQUE_ID";
      members[1] = "MULTIPLE_ID";
      _type = _orb().create_enum_tc(id(), "IdUniquenessPolicyValue", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/PortableServer/IdUniquenessPolicyValue:1.0";
  };

  public static IdUniquenessPolicyValue read(org.omg.CORBA.portable.InputStream is) {
    return IdUniquenessPolicyValue.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, IdUniquenessPolicyValue val) {
    os.write_long(val.value());
  };

}
