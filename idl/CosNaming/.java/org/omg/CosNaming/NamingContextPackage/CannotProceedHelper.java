//
// CannotProceedHelper.java (helper)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming.NamingContextPackage;

abstract public class CannotProceedHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, CannotProceed value) {
    any.insert_Streamable(new CannotProceedHolder(value));
  };

  public static CannotProceed extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof CannotProceedHolder){
          return ((CannotProceedHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("cxt", org.omg.CosNaming.NamingContextHelper.type(), null);
      members[1] = new org.omg.CORBA.StructMember("rest_of_name", org.omg.CosNaming.NameHelper.type(), null);
      _type = _orb().create_exception_tc(id(), "CannotProceed", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CosNaming/NamingContext/CannotProceed:1.0";
  };

  public static CannotProceed read(org.omg.CORBA.portable.InputStream is) {
    if (! is.read_string().equals(id())) {
      throw new org.omg.CORBA.MARSHAL("Invalid repository id.");
    };
    CannotProceed result = new CannotProceed();
    result.cxt = org.omg.CosNaming.NamingContextHelper.read(is);
    result.rest_of_name = org.omg.CosNaming.NameHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, CannotProceed val) {
    os.write_string(id());
    org.omg.CosNaming.NamingContextHelper.write(os,val.cxt);
    org.omg.CosNaming.NameHelper.write(os,val.rest_of_name);
  };

}
