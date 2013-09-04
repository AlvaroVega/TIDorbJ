//
// EstablishContextHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

abstract public class EstablishContextHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, EstablishContext value) {
    any.insert_Streamable(new EstablishContextHolder(value));
  };

  public static EstablishContext extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof EstablishContextHolder){
          return ((EstablishContextHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[4];
      members[0] = new org.omg.CORBA.StructMember("client_context_id", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulonglong), null);
      members[1] = new org.omg.CORBA.StructMember("authorization_token", org.omg.CSI.AuthorizationTokenHelper.type(), null);
      members[2] = new org.omg.CORBA.StructMember("identity_token", org.omg.CSI.IdentityTokenHelper.type(), null);
      members[3] = new org.omg.CORBA.StructMember("client_authentication_token", org.omg.CSI.GSSTokenHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "EstablishContext", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSI/EstablishContext:1.0";
  };

  public static EstablishContext read(org.omg.CORBA.portable.InputStream is) {
    EstablishContext result = new EstablishContext();
    result.client_context_id = is.read_ulonglong();
    result.authorization_token = org.omg.CSI.AuthorizationTokenHelper.read(is);
    result.identity_token = org.omg.CSI.IdentityTokenHelper.read(is);
    result.client_authentication_token = org.omg.CSI.GSSTokenHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, EstablishContext val) {
    os.write_ulonglong(val.client_context_id);
    org.omg.CSI.AuthorizationTokenHelper.write(os,val.authorization_token);
    org.omg.CSI.IdentityTokenHelper.write(os,val.identity_token);
    org.omg.CSI.GSSTokenHelper.write(os,val.client_authentication_token);
  };

}
