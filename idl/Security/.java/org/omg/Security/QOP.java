//
// QOP.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class QOP
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SecQOPNoProtection = 0;
  public static final QOP SecQOPNoProtection = new QOP(_SecQOPNoProtection);
  public static final int _SecQOPIntegrity = 1;
  public static final QOP SecQOPIntegrity = new QOP(_SecQOPIntegrity);
  public static final int _SecQOPConfidentiality = 2;
  public static final QOP SecQOPConfidentiality = new QOP(_SecQOPConfidentiality);
  public static final int _SecQOPIntegrityAndConfidentiality = 3;
  public static final QOP SecQOPIntegrityAndConfidentiality = new QOP(_SecQOPIntegrityAndConfidentiality);

  public int value() { return _value; }
  public static QOP from_int(int value) {
    switch (value) {
      case 0: return SecQOPNoProtection;
      case 1: return SecQOPIntegrity;
      case 2: return SecQOPConfidentiality;
      case 3: return SecQOPIntegrityAndConfidentiality;
    };
    return null;
  };
  protected QOP (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
