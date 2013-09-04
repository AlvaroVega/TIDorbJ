//
// AttributeMode.java (enum)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class AttributeMode
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _ATTR_NORMAL = 0;
  public static final AttributeMode ATTR_NORMAL = new AttributeMode(_ATTR_NORMAL);
  public static final int _ATTR_READONLY = 1;
  public static final AttributeMode ATTR_READONLY = new AttributeMode(_ATTR_READONLY);

  public int value() { return _value; }
  public static AttributeMode from_int(int value) {
    switch (value) {
      case 0: return ATTR_NORMAL;
      case 1: return ATTR_READONLY;
    };
    return null;
  };
  protected AttributeMode (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
