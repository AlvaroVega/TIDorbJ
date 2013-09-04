//
// CompressorIdLevelHelper.java (helper)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

abstract public class CompressorIdLevelHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, CompressorIdLevel value) {
    any.insert_Streamable(new CompressorIdLevelHolder(value));
  };

  public static CompressorIdLevel extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof CompressorIdLevelHolder){
          return ((CompressorIdLevelHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("compressor_id", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      members[1] = new org.omg.CORBA.StructMember("compression_level", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      _type = _orb().create_struct_tc(id(), "CompressorIdLevel", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Compression/CompressorIdLevel:1.0";
  };

  public static CompressorIdLevel read(org.omg.CORBA.portable.InputStream is) {
    CompressorIdLevel result = new CompressorIdLevel();
    result.compressor_id = is.read_ushort();
    result.compression_level = is.read_ushort();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, CompressorIdLevel val) {
    os.write_ushort(val.compressor_id);
    os.write_ushort(val.compression_level);
  };

}
