//
// OptionsDirectionPairHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class OptionsDirectionPairHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, OptionsDirectionPair value) {
    any.insert_Streamable(new OptionsDirectionPairHolder(value));
  };

  public static OptionsDirectionPair extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof OptionsDirectionPairHolder){
          return ((OptionsDirectionPairHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("options", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      members[1] = new org.omg.CORBA.StructMember("direction", org.omg.Security.CommunicationDirectionHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "OptionsDirectionPair", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/OptionsDirectionPair:1.0";
  };

  public static OptionsDirectionPair read(org.omg.CORBA.portable.InputStream is) {
    OptionsDirectionPair result = new OptionsDirectionPair();
    result.options = is.read_ushort();
    result.direction = org.omg.Security.CommunicationDirectionHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, OptionsDirectionPair val) {
    os.write_ushort(val.options);
    org.omg.Security.CommunicationDirectionHelper.write(os,val.direction);
  };

}
