//
// DelegationDirectiveHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class DelegationDirectiveHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, DelegationDirective value) {
    any.insert_Streamable(new DelegationDirectiveHolder(value));
  };

  public static DelegationDirective extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof DelegationDirectiveHolder){
          return ((DelegationDirectiveHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[2];
      members[0] = "Delegate";
      members[1] = "NoDelegate";
      _type = _orb().create_enum_tc(id(), "DelegationDirective", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/DelegationDirective:1.0";
  };

  public static DelegationDirective read(org.omg.CORBA.portable.InputStream is) {
    return DelegationDirective.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, DelegationDirective val) {
    os.write_long(val.value());
  };

}
