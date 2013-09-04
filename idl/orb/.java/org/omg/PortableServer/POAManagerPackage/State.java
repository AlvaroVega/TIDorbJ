//
// State.java (enum)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer.POAManagerPackage;

public class State
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _HOLDING = 0;
  public static final State HOLDING = new State(_HOLDING);
  public static final int _ACTIVE = 1;
  public static final State ACTIVE = new State(_ACTIVE);
  public static final int _DISCARDING = 2;
  public static final State DISCARDING = new State(_DISCARDING);
  public static final int _INACTIVE = 3;
  public static final State INACTIVE = new State(_INACTIVE);

  public int value() { return _value; }
  public static State from_int(int value) {
    switch (value) {
      case 0: return HOLDING;
      case 1: return ACTIVE;
      case 2: return DISCARDING;
      case 3: return INACTIVE;
    };
    return null;
  };
  protected State (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
