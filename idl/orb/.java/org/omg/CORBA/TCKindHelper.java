//
// TCKindHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class TCKindHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, TCKind value) {
    any.insert_Streamable(new TCKindHolder(value));
  };

  public static TCKind extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof TCKindHolder){
          return ((TCKindHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[34];
      members[0] = "tk_null";
      members[1] = "tk_void";
      members[2] = "tk_short";
      members[3] = "tk_long";
      members[4] = "tk_ushort";
      members[5] = "tk_ulong";
      members[6] = "tk_float";
      members[7] = "tk_double";
      members[8] = "tk_boolean";
      members[9] = "tk_char";
      members[10] = "tk_octet";
      members[11] = "tk_any";
      members[12] = "tk_TypeCode";
      members[13] = "tk_Principal";
      members[14] = "tk_objref";
      members[15] = "tk_struct";
      members[16] = "tk_union";
      members[17] = "tk_enum";
      members[18] = "tk_string";
      members[19] = "tk_sequence";
      members[20] = "tk_array";
      members[21] = "tk_alias";
      members[22] = "tk_except";
      members[23] = "tk_longlong";
      members[24] = "tk_ulonglong";
      members[25] = "tk_longdouble";
      members[26] = "tk_wchar";
      members[27] = "tk_wstring";
      members[28] = "tk_fixed";
      members[29] = "tk_value";
      members[30] = "tk_value_box";
      members[31] = "tk_native";
      members[32] = "tk_abstract_interface";
      members[33] = "tk_local_interface";
      _type = _orb().create_enum_tc(id(), "TCKind", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/TCKind:1.0";
  };

  public static TCKind read(org.omg.CORBA.portable.InputStream is) {
    return TCKind.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, TCKind val) {
    os.write_long(val.value());
  };

}
