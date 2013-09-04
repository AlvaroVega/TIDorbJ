//
// DelegationDirective.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class DelegationDirective
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _Delegate = 0;
  public static final DelegationDirective Delegate = new DelegationDirective(_Delegate);
  public static final int _NoDelegate = 1;
  public static final DelegationDirective NoDelegate = new DelegationDirective(_NoDelegate);

  public int value() { return _value; }
  public static DelegationDirective from_int(int value) {
    switch (value) {
      case 0: return Delegate;
      case 1: return NoDelegate;
    };
    return null;
  };
  protected DelegationDirective (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
