//
// RequiresSupports.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class RequiresSupports
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SecRequires = 0;
  public static final RequiresSupports SecRequires = new RequiresSupports(_SecRequires);
  public static final int _SecSupports = 1;
  public static final RequiresSupports SecSupports = new RequiresSupports(_SecSupports);

  public int value() { return _value; }
  public static RequiresSupports from_int(int value) {
    switch (value) {
      case 0: return SecRequires;
      case 1: return SecSupports;
    };
    return null;
  };
  protected RequiresSupports (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
