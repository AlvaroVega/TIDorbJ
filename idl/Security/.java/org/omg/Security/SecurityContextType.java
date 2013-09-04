//
// SecurityContextType.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class SecurityContextType
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SecClientSecurityContext = 0;
  public static final SecurityContextType SecClientSecurityContext = new SecurityContextType(_SecClientSecurityContext);
  public static final int _SecServerSecurityContext = 1;
  public static final SecurityContextType SecServerSecurityContext = new SecurityContextType(_SecServerSecurityContext);

  public int value() { return _value; }
  public static SecurityContextType from_int(int value) {
    switch (value) {
      case 0: return SecClientSecurityContext;
      case 1: return SecServerSecurityContext;
    };
    return null;
  };
  protected SecurityContextType (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
