//
// CompressedDataHelper.java (helper)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.ZIOP;

abstract public class CompressedDataHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, CompressedData value) {
    any.insert_Streamable(new CompressedDataHolder(value));
  };

  public static CompressedData extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof CompressedDataHolder){
          return ((CompressedDataHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[3];
      members[0] = new org.omg.CORBA.StructMember("compressorid", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ushort), null);
      members[1] = new org.omg.CORBA.StructMember("original_length", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), null);
      members[2] = new org.omg.CORBA.StructMember("data", org.omg.Compression.BufferHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "CompressedData", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/ZIOP/CompressedData:1.0";
  };

  public static CompressedData read(org.omg.CORBA.portable.InputStream is) {
    CompressedData result = new CompressedData();
    result.compressorid = is.read_ushort();
    result.original_length = is.read_ulong();
    result.data = org.omg.Compression.BufferHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, CompressedData val) {
    os.write_ushort(val.compressorid);
    os.write_ulong(val.original_length);
    org.omg.Compression.BufferHelper.write(os,val.data);
  };

}
