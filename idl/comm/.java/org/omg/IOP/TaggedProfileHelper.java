//
// TaggedProfileHelper.java (helper)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

abstract public class TaggedProfileHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, TaggedProfile value) {
    any.insert_Streamable(new TaggedProfileHolder(value));
  };

  public static TaggedProfile extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof TaggedProfileHolder){
          return ((TaggedProfileHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("tag", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      members[1] = new org.omg.CORBA.StructMember("profile_data", _orb().create_sequence_tc(0 , org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet)), null);
      _type = _orb().create_struct_tc(id(), "TaggedProfile", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/IOP/TaggedProfile:1.0";
  };

  public static TaggedProfile read(org.omg.CORBA.portable.InputStream is) {
    TaggedProfile result = new TaggedProfile();
    result.tag = is.read_ulong();
      int length2 = is.read_ulong();
      result.profile_data = new byte[length2];
      for (int i0=0; i0<length2; i0++) {
        result.profile_data[i0] = is.read_octet();
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, TaggedProfile val) {
    os.write_ulong(val.tag);
      os.write_ulong(val.profile_data.length);
      for (int i0=0; i0<val.profile_data.length; i0++) {
        os.write_octet(val.profile_data[i0]);
      }
  };

}
