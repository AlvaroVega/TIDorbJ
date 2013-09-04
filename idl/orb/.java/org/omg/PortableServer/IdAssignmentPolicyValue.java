//
// IdAssignmentPolicyValue.java (enum)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class IdAssignmentPolicyValue
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _USER_ID = 0;
  public static final IdAssignmentPolicyValue USER_ID = new IdAssignmentPolicyValue(_USER_ID);
  public static final int _SYSTEM_ID = 1;
  public static final IdAssignmentPolicyValue SYSTEM_ID = new IdAssignmentPolicyValue(_SYSTEM_ID);

  public int value() { return _value; }
  public static IdAssignmentPolicyValue from_int(int value) {
    switch (value) {
      case 0: return USER_ID;
      case 1: return SYSTEM_ID;
    };
    return null;
  };
  protected IdAssignmentPolicyValue (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
