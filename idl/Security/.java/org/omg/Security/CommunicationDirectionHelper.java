//
// CommunicationDirectionHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class CommunicationDirectionHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, CommunicationDirection value) {
    any.insert_Streamable(new CommunicationDirectionHolder(value));
  };

  public static CommunicationDirection extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof CommunicationDirectionHolder){
          return ((CommunicationDirectionHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[3];
      members[0] = "SecDirectionBoth";
      members[1] = "SecDirectionRequest";
      members[2] = "SecDirectionReply";
      _type = _orb().create_enum_tc(id(), "CommunicationDirection", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/CommunicationDirection:1.0";
  };

  public static CommunicationDirection read(org.omg.CORBA.portable.InputStream is) {
    return CommunicationDirection.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, CommunicationDirection val) {
    os.write_long(val.value());
  };

}
