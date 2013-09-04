//
// AuthenticationStatus.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class AuthenticationStatus
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SecAuthSuccess = 0;
  public static final AuthenticationStatus SecAuthSuccess = new AuthenticationStatus(_SecAuthSuccess);
  public static final int _SecAuthFailure = 1;
  public static final AuthenticationStatus SecAuthFailure = new AuthenticationStatus(_SecAuthFailure);
  public static final int _SecAuthContinue = 2;
  public static final AuthenticationStatus SecAuthContinue = new AuthenticationStatus(_SecAuthContinue);
  public static final int _SecAuthExpired = 3;
  public static final AuthenticationStatus SecAuthExpired = new AuthenticationStatus(_SecAuthExpired);

  public int value() { return _value; }
  public static AuthenticationStatus from_int(int value) {
    switch (value) {
      case 0: return SecAuthSuccess;
      case 1: return SecAuthFailure;
      case 2: return SecAuthContinue;
      case 3: return SecAuthExpired;
    };
    return null;
  };
  protected AuthenticationStatus (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
