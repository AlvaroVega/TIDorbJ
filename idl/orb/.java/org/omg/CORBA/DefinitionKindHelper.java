//
// DefinitionKindHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class DefinitionKindHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, DefinitionKind value) {
    any.insert_Streamable(new DefinitionKindHolder(value));
  };

  public static DefinitionKind extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof DefinitionKindHolder){
          return ((DefinitionKindHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[24];
      members[0] = "dk_none";
      members[1] = "dk_all";
      members[2] = "dk_Attribute";
      members[3] = "dk_Constant";
      members[4] = "dk_Exception";
      members[5] = "dk_Interface";
      members[6] = "dk_Module";
      members[7] = "dk_Operation";
      members[8] = "dk_Typedef";
      members[9] = "dk_Alias";
      members[10] = "dk_Struct";
      members[11] = "dk_Union";
      members[12] = "dk_Enum";
      members[13] = "dk_Primitive";
      members[14] = "dk_String";
      members[15] = "dk_Sequence";
      members[16] = "dk_Array";
      members[17] = "dk_Repository";
      members[18] = "dk_Wstring";
      members[19] = "dk_Fixed";
      members[20] = "dk_Value";
      members[21] = "dk_ValueBox";
      members[22] = "dk_ValueMember";
      members[23] = "dk_Native";
      _type = _orb().create_enum_tc(id(), "DefinitionKind", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CORBA/DefinitionKind:1.0";
  };

  public static DefinitionKind read(org.omg.CORBA.portable.InputStream is) {
    return DefinitionKind.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, DefinitionKind val) {
    os.write_long(val.value());
  };

}
