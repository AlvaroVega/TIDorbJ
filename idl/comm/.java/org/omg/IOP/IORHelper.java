//
// IORHelper.java (helper)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

abstract public class IORHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, IOR value) {
    any.insert_Streamable(new IORHolder(value));
  };

  public static IOR extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof IORHolder){
          return ((IORHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("type_id", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[1] = new org.omg.CORBA.StructMember("profiles", _orb().create_sequence_tc(0 , org.omg.IOP.TaggedProfileHelper.type()), null);
      _type = _orb().create_struct_tc(id(), "IOR", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/IOP/IOR:1.0";
  };

  public static IOR read(org.omg.CORBA.portable.InputStream is) {
    IOR result = new IOR();
    result.type_id = is.read_string();
      int length2 = is.read_ulong();
      result.profiles = new org.omg.IOP.TaggedProfile[length2];
      for (int i0=0; i0<length2; i0++) {
        result.profiles[i0] = org.omg.IOP.TaggedProfileHelper.read(is);
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, IOR val) {
    os.write_string(val.type_id);
      os.write_ulong(val.profiles.length);
      for (int i0=0; i0<val.profiles.length; i0++) {
        org.omg.IOP.TaggedProfileHelper.write(os,val.profiles[i0]);
      }
  };

}
