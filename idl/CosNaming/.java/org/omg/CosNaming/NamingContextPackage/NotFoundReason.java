//
// NotFoundReason.java (enum)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming.NamingContextPackage;

public class NotFoundReason
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _missing_node = 0;
  public static final NotFoundReason missing_node = new NotFoundReason(_missing_node);
  public static final int _not_context = 1;
  public static final NotFoundReason not_context = new NotFoundReason(_not_context);
  public static final int _not_object = 2;
  public static final NotFoundReason not_object = new NotFoundReason(_not_object);

  public int value() { return _value; }
  public static NotFoundReason from_int(int value) {
    switch (value) {
      case 0: return missing_node;
      case 1: return not_context;
      case 2: return not_object;
    };
    return null;
  };
  protected NotFoundReason (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
