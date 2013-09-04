//
// CompleteEstablishContextHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

abstract public class CompleteEstablishContextHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, CompleteEstablishContext value) {
    any.insert_Streamable(new CompleteEstablishContextHolder(value));
  };

  public static CompleteEstablishContext extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof CompleteEstablishContextHolder){
          return ((CompleteEstablishContextHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[3];
      members[0] = new org.omg.CORBA.StructMember("client_context_id", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulonglong), null);
      members[1] = new org.omg.CORBA.StructMember("context_stateful", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean), null);
      members[2] = new org.omg.CORBA.StructMember("final_context_token", org.omg.CSI.GSSTokenHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "CompleteEstablishContext", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSI/CompleteEstablishContext:1.0";
  };

  public static CompleteEstablishContext read(org.omg.CORBA.portable.InputStream is) {
    CompleteEstablishContext result = new CompleteEstablishContext();
    result.client_context_id = is.read_ulonglong();
    result.context_stateful = is.read_boolean();
    result.final_context_token = org.omg.CSI.GSSTokenHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, CompleteEstablishContext val) {
    os.write_ulonglong(val.client_context_id);
    os.write_boolean(val.context_stateful);
    org.omg.CSI.GSSTokenHelper.write(os,val.final_context_token);
  };

}
