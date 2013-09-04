//
// ServantRetentionPolicyValue.java (enum)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class ServantRetentionPolicyValue
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _RETAIN = 0;
  public static final ServantRetentionPolicyValue RETAIN = new ServantRetentionPolicyValue(_RETAIN);
  public static final int _NON_RETAIN = 1;
  public static final ServantRetentionPolicyValue NON_RETAIN = new ServantRetentionPolicyValue(_NON_RETAIN);

  public int value() { return _value; }
  public static ServantRetentionPolicyValue from_int(int value) {
    switch (value) {
      case 0: return RETAIN;
      case 1: return NON_RETAIN;
    };
    return null;
  };
  protected ServantRetentionPolicyValue (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
