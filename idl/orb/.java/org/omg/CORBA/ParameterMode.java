//
// ParameterMode.java (enum)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class ParameterMode
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _PARAM_IN = 0;
  public static final ParameterMode PARAM_IN = new ParameterMode(_PARAM_IN);
  public static final int _PARAM_OUT = 1;
  public static final ParameterMode PARAM_OUT = new ParameterMode(_PARAM_OUT);
  public static final int _PARAM_INOUT = 2;
  public static final ParameterMode PARAM_INOUT = new ParameterMode(_PARAM_INOUT);

  public int value() { return _value; }
  public static ParameterMode from_int(int value) {
    switch (value) {
      case 0: return PARAM_IN;
      case 1: return PARAM_OUT;
      case 2: return PARAM_INOUT;
    };
    return null;
  };
  protected ParameterMode (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
