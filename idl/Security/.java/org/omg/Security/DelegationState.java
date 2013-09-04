//
// DelegationState.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class DelegationState
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SecInitiator = 0;
  public static final DelegationState SecInitiator = new DelegationState(_SecInitiator);
  public static final int _SecDelegate = 1;
  public static final DelegationState SecDelegate = new DelegationState(_SecDelegate);

  public int value() { return _value; }
  public static DelegationState from_int(int value) {
    switch (value) {
      case 0: return SecInitiator;
      case 1: return SecDelegate;
    };
    return null;
  };
  protected DelegationState (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
