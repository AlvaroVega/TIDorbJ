//
// BindingType.java (enum)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming;

public class BindingType
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _nobject = 0;
  public static final BindingType nobject = new BindingType(_nobject);
  public static final int _ncontext = 1;
  public static final BindingType ncontext = new BindingType(_ncontext);

  public int value() { return _value; }
  public static BindingType from_int(int value) {
    switch (value) {
      case 0: return nobject;
      case 1: return ncontext;
    };
    return null;
  };
  protected BindingType (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
