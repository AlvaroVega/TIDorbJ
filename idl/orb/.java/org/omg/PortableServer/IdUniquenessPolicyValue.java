//
// IdUniquenessPolicyValue.java (enum)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class IdUniquenessPolicyValue
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _UNIQUE_ID = 0;
  public static final IdUniquenessPolicyValue UNIQUE_ID = new IdUniquenessPolicyValue(_UNIQUE_ID);
  public static final int _MULTIPLE_ID = 1;
  public static final IdUniquenessPolicyValue MULTIPLE_ID = new IdUniquenessPolicyValue(_MULTIPLE_ID);

  public int value() { return _value; }
  public static IdUniquenessPolicyValue from_int(int value) {
    switch (value) {
      case 0: return UNIQUE_ID;
      case 1: return MULTIPLE_ID;
    };
    return null;
  };
  protected IdUniquenessPolicyValue (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
