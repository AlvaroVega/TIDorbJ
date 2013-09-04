//
// AuditCombinator.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class AuditCombinator
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _SecAllSelectors = 0;
  public static final AuditCombinator SecAllSelectors = new AuditCombinator(_SecAllSelectors);
  public static final int _SecAnySelector = 1;
  public static final AuditCombinator SecAnySelector = new AuditCombinator(_SecAnySelector);

  public int value() { return _value; }
  public static AuditCombinator from_int(int value) {
    switch (value) {
      case 0: return SecAllSelectors;
      case 1: return SecAnySelector;
    };
    return null;
  };
  protected AuditCombinator (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
