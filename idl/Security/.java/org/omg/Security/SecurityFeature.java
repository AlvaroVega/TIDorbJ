//
// SecurityFeature.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class SecurityFeature
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SecNoDelegation = 0;
  public static final SecurityFeature SecNoDelegation = new SecurityFeature(_SecNoDelegation);
  public static final int _SecSimpleDelegation = 1;
  public static final SecurityFeature SecSimpleDelegation = new SecurityFeature(_SecSimpleDelegation);
  public static final int _SecCompositeDelegation = 2;
  public static final SecurityFeature SecCompositeDelegation = new SecurityFeature(_SecCompositeDelegation);
  public static final int _SecNoProtection = 3;
  public static final SecurityFeature SecNoProtection = new SecurityFeature(_SecNoProtection);
  public static final int _SecIntegrity = 4;
  public static final SecurityFeature SecIntegrity = new SecurityFeature(_SecIntegrity);
  public static final int _SecConfidentiality = 5;
  public static final SecurityFeature SecConfidentiality = new SecurityFeature(_SecConfidentiality);
  public static final int _SecIntegrityAndConfidentiality = 6;
  public static final SecurityFeature SecIntegrityAndConfidentiality = new SecurityFeature(_SecIntegrityAndConfidentiality);
  public static final int _SecDetectReplay = 7;
  public static final SecurityFeature SecDetectReplay = new SecurityFeature(_SecDetectReplay);
  public static final int _SecDetectMisordering = 8;
  public static final SecurityFeature SecDetectMisordering = new SecurityFeature(_SecDetectMisordering);
  public static final int _SecEstablishTrustInTarget = 9;
  public static final SecurityFeature SecEstablishTrustInTarget = new SecurityFeature(_SecEstablishTrustInTarget);
  public static final int _SecEstablishTrustInClient = 10;
  public static final SecurityFeature SecEstablishTrustInClient = new SecurityFeature(_SecEstablishTrustInClient);

  public int value() { return _value; }
  public static SecurityFeature from_int(int value) {
    switch (value) {
      case 0: return SecNoDelegation;
      case 1: return SecSimpleDelegation;
      case 2: return SecCompositeDelegation;
      case 3: return SecNoProtection;
      case 4: return SecIntegrity;
      case 5: return SecConfidentiality;
      case 6: return SecIntegrityAndConfidentiality;
      case 7: return SecDetectReplay;
      case 8: return SecDetectMisordering;
      case 9: return SecEstablishTrustInTarget;
      case 10: return SecEstablishTrustInClient;
    };
    return null;
  };
  protected SecurityFeature (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
