//
// DefinitionKind.java (enum)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class DefinitionKind
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _dk_none = 0;
  public static final DefinitionKind dk_none = new DefinitionKind(_dk_none);
  public static final int _dk_all = 1;
  public static final DefinitionKind dk_all = new DefinitionKind(_dk_all);
  public static final int _dk_Attribute = 2;
  public static final DefinitionKind dk_Attribute = new DefinitionKind(_dk_Attribute);
  public static final int _dk_Constant = 3;
  public static final DefinitionKind dk_Constant = new DefinitionKind(_dk_Constant);
  public static final int _dk_Exception = 4;
  public static final DefinitionKind dk_Exception = new DefinitionKind(_dk_Exception);
  public static final int _dk_Interface = 5;
  public static final DefinitionKind dk_Interface = new DefinitionKind(_dk_Interface);
  public static final int _dk_Module = 6;
  public static final DefinitionKind dk_Module = new DefinitionKind(_dk_Module);
  public static final int _dk_Operation = 7;
  public static final DefinitionKind dk_Operation = new DefinitionKind(_dk_Operation);
  public static final int _dk_Typedef = 8;
  public static final DefinitionKind dk_Typedef = new DefinitionKind(_dk_Typedef);
  public static final int _dk_Alias = 9;
  public static final DefinitionKind dk_Alias = new DefinitionKind(_dk_Alias);
  public static final int _dk_Struct = 10;
  public static final DefinitionKind dk_Struct = new DefinitionKind(_dk_Struct);
  public static final int _dk_Union = 11;
  public static final DefinitionKind dk_Union = new DefinitionKind(_dk_Union);
  public static final int _dk_Enum = 12;
  public static final DefinitionKind dk_Enum = new DefinitionKind(_dk_Enum);
  public static final int _dk_Primitive = 13;
  public static final DefinitionKind dk_Primitive = new DefinitionKind(_dk_Primitive);
  public static final int _dk_String = 14;
  public static final DefinitionKind dk_String = new DefinitionKind(_dk_String);
  public static final int _dk_Sequence = 15;
  public static final DefinitionKind dk_Sequence = new DefinitionKind(_dk_Sequence);
  public static final int _dk_Array = 16;
  public static final DefinitionKind dk_Array = new DefinitionKind(_dk_Array);
  public static final int _dk_Repository = 17;
  public static final DefinitionKind dk_Repository = new DefinitionKind(_dk_Repository);
  public static final int _dk_Wstring = 18;
  public static final DefinitionKind dk_Wstring = new DefinitionKind(_dk_Wstring);
  public static final int _dk_Fixed = 19;
  public static final DefinitionKind dk_Fixed = new DefinitionKind(_dk_Fixed);
  public static final int _dk_Value = 20;
  public static final DefinitionKind dk_Value = new DefinitionKind(_dk_Value);
  public static final int _dk_ValueBox = 21;
  public static final DefinitionKind dk_ValueBox = new DefinitionKind(_dk_ValueBox);
  public static final int _dk_ValueMember = 22;
  public static final DefinitionKind dk_ValueMember = new DefinitionKind(_dk_ValueMember);
  public static final int _dk_Native = 23;
  public static final DefinitionKind dk_Native = new DefinitionKind(_dk_Native);

  public int value() { return _value; }
  public static DefinitionKind from_int(int value) {
    switch (value) {
      case 0: return dk_none;
      case 1: return dk_all;
      case 2: return dk_Attribute;
      case 3: return dk_Constant;
      case 4: return dk_Exception;
      case 5: return dk_Interface;
      case 6: return dk_Module;
      case 7: return dk_Operation;
      case 8: return dk_Typedef;
      case 9: return dk_Alias;
      case 10: return dk_Struct;
      case 11: return dk_Union;
      case 12: return dk_Enum;
      case 13: return dk_Primitive;
      case 14: return dk_String;
      case 15: return dk_Sequence;
      case 16: return dk_Array;
      case 17: return dk_Repository;
      case 18: return dk_Wstring;
      case 19: return dk_Fixed;
      case 20: return dk_Value;
      case 21: return dk_ValueBox;
      case 22: return dk_ValueMember;
      case 23: return dk_Native;
    };
    return null;
  };
  protected DefinitionKind (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
