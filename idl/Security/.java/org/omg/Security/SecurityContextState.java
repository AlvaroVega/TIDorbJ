//
// SecurityContextState.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class SecurityContextState
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SecContextInitialized = 0;
  public static final SecurityContextState SecContextInitialized = new SecurityContextState(_SecContextInitialized);
  public static final int _SecContextContinued = 1;
  public static final SecurityContextState SecContextContinued = new SecurityContextState(_SecContextContinued);
  public static final int _SecContextClientEstablished = 2;
  public static final SecurityContextState SecContextClientEstablished = new SecurityContextState(_SecContextClientEstablished);
  public static final int _SecContextEstablished = 3;
  public static final SecurityContextState SecContextEstablished = new SecurityContextState(_SecContextEstablished);
  public static final int _SecContextEstablishExpired = 4;
  public static final SecurityContextState SecContextEstablishExpired = new SecurityContextState(_SecContextEstablishExpired);
  public static final int _SecContextExpired = 5;
  public static final SecurityContextState SecContextExpired = new SecurityContextState(_SecContextExpired);
  public static final int _SecContextInvalid = 6;
  public static final SecurityContextState SecContextInvalid = new SecurityContextState(_SecContextInvalid);

  public int value() { return _value; }
  public static SecurityContextState from_int(int value) {
    switch (value) {
      case 0: return SecContextInitialized;
      case 1: return SecContextContinued;
      case 2: return SecContextClientEstablished;
      case 3: return SecContextEstablished;
      case 4: return SecContextEstablishExpired;
      case 5: return SecContextExpired;
      case 6: return SecContextInvalid;
    };
    return null;
  };
  protected SecurityContextState (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
