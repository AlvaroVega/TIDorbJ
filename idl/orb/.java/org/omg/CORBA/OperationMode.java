//
// OperationMode.java (enum)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class OperationMode
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _OP_NORMAL = 0;
  public static final OperationMode OP_NORMAL = new OperationMode(_OP_NORMAL);
  public static final int _OP_ONEWAY = 1;
  public static final OperationMode OP_ONEWAY = new OperationMode(_OP_ONEWAY);

  public int value() { return _value; }
  public static OperationMode from_int(int value) {
    switch (value) {
      case 0: return OP_NORMAL;
      case 1: return OP_ONEWAY;
    };
    return null;
  };
  protected OperationMode (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
