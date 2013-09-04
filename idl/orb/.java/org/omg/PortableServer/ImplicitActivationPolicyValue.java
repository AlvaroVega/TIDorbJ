//
// ImplicitActivationPolicyValue.java (enum)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class ImplicitActivationPolicyValue
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _IMPLICIT_ACTIVATION = 0;
  public static final ImplicitActivationPolicyValue IMPLICIT_ACTIVATION = new ImplicitActivationPolicyValue(_IMPLICIT_ACTIVATION);
  public static final int _NO_IMPLICIT_ACTIVATION = 1;
  public static final ImplicitActivationPolicyValue NO_IMPLICIT_ACTIVATION = new ImplicitActivationPolicyValue(_NO_IMPLICIT_ACTIVATION);

  public int value() { return _value; }
  public static ImplicitActivationPolicyValue from_int(int value) {
    switch (value) {
      case 0: return IMPLICIT_ACTIVATION;
      case 1: return NO_IMPLICIT_ACTIVATION;
    };
    return null;
  };
  protected ImplicitActivationPolicyValue (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
