//
// CompressionExceptionHelper.java (helper)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

abstract public class CompressionExceptionHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, CompressionException value) {
    any.insert_Streamable(new CompressionExceptionHolder(value));
  };

  public static CompressionException extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof CompressionExceptionHolder){
          return ((CompressionExceptionHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("reason", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long), null);
      members[1] = new org.omg.CORBA.StructMember("description", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      _type = _orb().create_exception_tc(id(), "CompressionException", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Compression/CompressionException:1.0";
  };

  public static CompressionException read(org.omg.CORBA.portable.InputStream is) {
    if (! is.read_string().equals(id())) {
      throw new org.omg.CORBA.MARSHAL("Invalid repository id.");
    };
    CompressionException result = new CompressionException();
    result.reason = is.read_long();
    result.description = is.read_string();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, CompressionException val) {
    os.write_string(id());
    os.write_long(val.reason);
    os.write_string(val.description);
  };

}
