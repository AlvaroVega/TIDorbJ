//
// IdentityTokenHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

abstract public class IdentityTokenHelper {

  public static void insert(org.omg.CORBA.Any any, IdentityToken value) {
    any.insert_Streamable(new IdentityTokenHolder(value));
  };

  public static IdentityToken extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof IdentityTokenHolder){
          return ((IdentityTokenHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
     if (_type == null) {
       org.omg.CORBA.UnionMember[] members = new org.omg.CORBA.UnionMember[6];
       org.omg.CORBA.Any _any0 = _orb().create_any();
       _any0.insert_ulong((int) 0);
       members[0] = new org.omg.CORBA.UnionMember("absent", _any0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean), null);
       org.omg.CORBA.Any _any1 = _orb().create_any();
       _any1.insert_ulong((int) 1);
       members[1] = new org.omg.CORBA.UnionMember("anonymous", _any1, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean), null);
       org.omg.CORBA.Any _any2 = _orb().create_any();
       _any2.insert_ulong((int) 2);
       members[2] = new org.omg.CORBA.UnionMember("principal_name", _any2, org.omg.CSI.GSS_NT_ExportedNameHelper.type(), null);
       org.omg.CORBA.Any _any3 = _orb().create_any();
       _any3.insert_ulong((int) 4);
       members[3] = new org.omg.CORBA.UnionMember("certificate_chain", _any3, org.omg.CSI.X509CertificateChainHelper.type(), null);
       org.omg.CORBA.Any _any4 = _orb().create_any();
       _any4.insert_ulong((int) 8);
       members[4] = new org.omg.CORBA.UnionMember("dn", _any4, org.omg.CSI.X501DistinguishedNameHelper.type(), null);
       org.omg.CORBA.Any _any5 = _orb().create_any();
       _any5.insert_octet((byte)0);
       members[5] = new org.omg.CORBA.UnionMember("id", _any5, org.omg.CSI.IdentityExtensionHelper.type(), null);
       _type = _orb().create_union_tc(id(), "IdentityToken", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong), members);
     };
     return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSI/IdentityToken:1.0";
  };

  public static IdentityToken read(org.omg.CORBA.portable.InputStream is) {
    IdentityToken _union_result = new IdentityToken();
    int _disc_value = is.read_ulong();
    switch (_disc_value){
     case 0: {
       boolean _tmp = is.read_boolean();
       _union_result.absent(_tmp);
       break;
     }
     case 1: {
       boolean _tmp = is.read_boolean();
       _union_result.anonymous(_tmp);
       break;
     }
     case 2: {
       byte[] _tmp = org.omg.CSI.GSS_NT_ExportedNameHelper.read(is);
       _union_result.principal_name(_tmp);
       break;
     }
     case 4: {
       byte[] _tmp = org.omg.CSI.X509CertificateChainHelper.read(is);
       _union_result.certificate_chain(_tmp);
       break;
     }
     case 8: {
       byte[] _tmp = org.omg.CSI.X501DistinguishedNameHelper.read(is);
       _union_result.dn(_tmp);
       break;
     }
     default:  {
       byte[] _tmp = org.omg.CSI.IdentityExtensionHelper.read(is);
       _union_result.id(_tmp);
       break;
     }
    }
    return _union_result;

  }

  public static void write(org.omg.CORBA.portable.OutputStream os, IdentityToken _value) {
    os.write_ulong(_value.discriminator());
    if (_value._isDefault){
       byte[] id = _value.id();
       org.omg.CSI.IdentityExtensionHelper.write(os,id);
       return;
    }
    switch (_value.discriminator()){
     case 0: {
       boolean absent = _value.absent();
       os.write_boolean(absent);
       break;
     }
     case 1: {
       boolean anonymous = _value.anonymous();
       os.write_boolean(anonymous);
       break;
     }
     case 2: {
       byte[] principal_name = _value.principal_name();
       org.omg.CSI.GSS_NT_ExportedNameHelper.write(os,principal_name);
       break;
     }
     case 4: {
       byte[] certificate_chain = _value.certificate_chain();
       org.omg.CSI.X509CertificateChainHelper.write(os,certificate_chain);
       break;
     }
     case 8: {
       byte[] dn = _value.dn();
       org.omg.CSI.X501DistinguishedNameHelper.write(os,dn);
       break;
     }
    }

  }

}
