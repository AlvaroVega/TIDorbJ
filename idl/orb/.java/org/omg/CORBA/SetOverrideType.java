//
// SetOverrideType.java (enum)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class SetOverrideType
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SET_OVERRIDE = 0;
  public static final SetOverrideType SET_OVERRIDE = new SetOverrideType(_SET_OVERRIDE);
  public static final int _ADD_OVERRIDE = 1;
  public static final SetOverrideType ADD_OVERRIDE = new SetOverrideType(_ADD_OVERRIDE);

  public int value() { return _value; }
  public static SetOverrideType from_int(int value) {
    switch (value) {
      case 0: return SET_OVERRIDE;
      case 1: return ADD_OVERRIDE;
    };
    return null;
  };
  protected SetOverrideType (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
