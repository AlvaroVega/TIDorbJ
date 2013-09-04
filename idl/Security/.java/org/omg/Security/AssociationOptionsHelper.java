//
// AssociationOptionsHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class AssociationOptionsHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, short value) {
    any.insert_Streamable(new org.omg.CORBA.ShortHolder(value));
  };

  public static short extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof org.omg.CORBA.ShortHolder){
          return ((org.omg.CORBA.ShortHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.TypeCode original_type = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort);
      _type = _orb().create_alias_tc(id(), "AssociationOptions", original_type);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/AssociationOptions:1.0";
  };

  public static short read(org.omg.CORBA.portable.InputStream is) {
    short result;
    result = is.read_ushort();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, short val) {
    os.write_ushort(val);
  };

}
