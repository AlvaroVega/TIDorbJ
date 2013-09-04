//
// TaggedComponentHelper.java (helper)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

abstract public class TaggedComponentHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, TaggedComponent value) {
    any.insert_Streamable(new TaggedComponentHolder(value));
  };

  public static TaggedComponent extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof TaggedComponentHolder){
          return ((TaggedComponentHolder) holder).value;
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
      members[1] = new org.omg.CORBA.StructMember("component_data", _orb().create_sequence_tc(0 , org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet)), null);
      _type = _orb().create_struct_tc(id(), "TaggedComponent", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/IOP/TaggedComponent:1.0";
  };

  public static TaggedComponent read(org.omg.CORBA.portable.InputStream is) {
    TaggedComponent result = new TaggedComponent();
    result.tag = is.read_ulong();
      int length2 = is.read_ulong();
      result.component_data = new byte[length2];
      for (int i0=0; i0<length2; i0++) {
        result.component_data[i0] = is.read_octet();
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, TaggedComponent val) {
    os.write_ulong(val.tag);
      os.write_ulong(val.component_data.length);
      for (int i0=0; i0<val.component_data.length; i0++) {
        os.write_octet(val.component_data[i0]);
      }
  };

}
