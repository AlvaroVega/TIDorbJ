//
// LifespanPolicyValue.java (enum)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class LifespanPolicyValue
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _TRANSIENT = 0;
  public static final LifespanPolicyValue TRANSIENT = new LifespanPolicyValue(_TRANSIENT);
  public static final int _PERSISTENT = 1;
  public static final LifespanPolicyValue PERSISTENT = new LifespanPolicyValue(_PERSISTENT);

  public int value() { return _value; }
  public static LifespanPolicyValue from_int(int value) {
    switch (value) {
      case 0: return TRANSIENT;
      case 1: return PERSISTENT;
    };
    return null;
  };
  protected LifespanPolicyValue (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
