//
// InvocationCredentialsType.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class InvocationCredentialsType
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SecOwnCredentials = 0;
  public static final InvocationCredentialsType SecOwnCredentials = new InvocationCredentialsType(_SecOwnCredentials);
  public static final int _SecReceivedCredentials = 1;
  public static final InvocationCredentialsType SecReceivedCredentials = new InvocationCredentialsType(_SecReceivedCredentials);
  public static final int _SecTargetCredentials = 2;
  public static final InvocationCredentialsType SecTargetCredentials = new InvocationCredentialsType(_SecTargetCredentials);

  public int value() { return _value; }
  public static InvocationCredentialsType from_int(int value) {
    switch (value) {
      case 0: return SecOwnCredentials;
      case 1: return SecReceivedCredentials;
      case 2: return SecTargetCredentials;
    };
    return null;
  };
  protected InvocationCredentialsType (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
