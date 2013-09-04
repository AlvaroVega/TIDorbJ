//
// NameDynAnyPairHelper.java (helper)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny;

abstract public class NameDynAnyPairHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, NameDynAnyPair value) {
    any.insert_Streamable(new NameDynAnyPairHolder(value));
  };

  public static NameDynAnyPair extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof NameDynAnyPairHolder){
          return ((NameDynAnyPairHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("id", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      members[1] = new org.omg.CORBA.StructMember("value", org.omg.DynamicAny.DynAnyHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "NameDynAnyPair", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/DynamicAny/NameDynAnyPair:1.0";
  };

  public static NameDynAnyPair read(org.omg.CORBA.portable.InputStream is) {
    NameDynAnyPair result = new NameDynAnyPair();
    result.id = is.read_string();
    result.value = org.omg.DynamicAny.DynAnyHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, NameDynAnyPair val) {
    os.write_string(val.id);
    org.omg.DynamicAny.DynAnyHelper.write(os,val.value);
  };

}
