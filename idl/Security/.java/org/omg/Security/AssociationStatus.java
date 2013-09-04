//
// AssociationStatus.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class AssociationStatus
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SecAssocSuccess = 0;
  public static final AssociationStatus SecAssocSuccess = new AssociationStatus(_SecAssocSuccess);
  public static final int _SecAssocFailure = 1;
  public static final AssociationStatus SecAssocFailure = new AssociationStatus(_SecAssocFailure);
  public static final int _SecAssocContinue = 2;
  public static final AssociationStatus SecAssocContinue = new AssociationStatus(_SecAssocContinue);

  public int value() { return _value; }
  public static AssociationStatus from_int(int value) {
    switch (value) {
      case 0: return SecAssocSuccess;
      case 1: return SecAssocFailure;
      case 2: return SecAssocContinue;
    };
    return null;
  };
  protected AssociationStatus (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
