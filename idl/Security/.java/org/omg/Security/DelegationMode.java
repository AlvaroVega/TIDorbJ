//
// DelegationMode.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class DelegationMode
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SecDelModeNoDelegation = 0;
  public static final DelegationMode SecDelModeNoDelegation = new DelegationMode(_SecDelModeNoDelegation);
  public static final int _SecDelModeSimpleDelegation = 1;
  public static final DelegationMode SecDelModeSimpleDelegation = new DelegationMode(_SecDelModeSimpleDelegation);
  public static final int _SecDelModeCompositeDelegation = 2;
  public static final DelegationMode SecDelModeCompositeDelegation = new DelegationMode(_SecDelModeCompositeDelegation);

  public int value() { return _value; }
  public static DelegationMode from_int(int value) {
    switch (value) {
      case 0: return SecDelModeNoDelegation;
      case 1: return SecDelModeSimpleDelegation;
      case 2: return SecDelModeCompositeDelegation;
    };
    return null;
  };
  protected DelegationMode (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
