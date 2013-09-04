//
// RightsCombinator.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class RightsCombinator
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SecAllRights = 0;
  public static final RightsCombinator SecAllRights = new RightsCombinator(_SecAllRights);
  public static final int _SecAnyRight = 1;
  public static final RightsCombinator SecAnyRight = new RightsCombinator(_SecAnyRight);

  public int value() { return _value; }
  public static RightsCombinator from_int(int value) {
    switch (value) {
      case 0: return SecAllRights;
      case 1: return SecAnyRight;
    };
    return null;
  };
  protected RightsCombinator (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
