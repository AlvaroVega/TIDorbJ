//
// PrimitiveKindHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class PrimitiveKindHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, PrimitiveKind value) {
    any.insert_Streamable(new PrimitiveKindHolder(value));
  };

  public static PrimitiveKind extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof PrimitiveKindHolder){
          return ((PrimitiveKindHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[22];
      members[0] = "pk_null";
      members[1] = "pk_void";
      members[2] = "pk_short";
      members[3] = "pk_long";
      members[4] = "pk_ushort";
      members[5] = "pk_ulong";
      members[6] = "pk_float";
      members[7] = "pk_double";
      members[8] = "pk_boolean";
      members[9] = "pk_char";
      members[10] = "pk_octet";
      members[11] = "pk_any";
      members[12] = "pk_TypeCode";
      members[13] = "pk_Principal";
      members[14] = "pk_string";
      members[15] = "pk_objref";
      members[16] = "pk_longlong";
      members[17] = "pk_ulonglong";
      members[18] = "pk_longdouble";
      members[19] = "pk_wchar";
      members[20] = "pk_wstring";
      members[21] = "pk_value_base";
      _type = _orb().create_enum_tc(id(), "PrimitiveKind", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/PrimitiveKind:1.0";
  };

  public static PrimitiveKind read(org.omg.CORBA.portable.InputStream is) {
    return PrimitiveKind.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, PrimitiveKind val) {
    os.write_long(val.value());
  };

}
