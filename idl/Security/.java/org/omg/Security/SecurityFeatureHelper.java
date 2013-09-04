//
// SecurityFeatureHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class SecurityFeatureHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, SecurityFeature value) {
    any.insert_Streamable(new SecurityFeatureHolder(value));
  };

  public static SecurityFeature extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof SecurityFeatureHolder){
          return ((SecurityFeatureHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[11];
      members[0] = "SecNoDelegation";
      members[1] = "SecSimpleDelegation";
      members[2] = "SecCompositeDelegation";
      members[3] = "SecNoProtection";
      members[4] = "SecIntegrity";
      members[5] = "SecConfidentiality";
      members[6] = "SecIntegrityAndConfidentiality";
      members[7] = "SecDetectReplay";
      members[8] = "SecDetectMisordering";
      members[9] = "SecEstablishTrustInTarget";
      members[10] = "SecEstablishTrustInClient";
      _type = _orb().create_enum_tc(id(), "SecurityFeature", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/SecurityFeature:1.0";
  };

  public static SecurityFeature read(org.omg.CORBA.portable.InputStream is) {
    return SecurityFeature.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, SecurityFeature val) {
    os.write_long(val.value());
  };

}
