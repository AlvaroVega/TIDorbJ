//
// DataOutputStream.java (valuetype)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public interface DataOutputStream
   extends org.omg.CORBA.portable.ValueBase{

  public abstract void write_any(org.omg.CORBA.Any value);

  public abstract void write_boolean(boolean value);

  public abstract void write_char(char value);

  public abstract void write_wchar(char value);

  public abstract void write_octet(byte value);

  public abstract void write_short(short value);

  public abstract void write_ushort(short value);

  public abstract void write_long(int value);

  public abstract void write_ulong(int value);

  public abstract void write_longlong(long value);

  public abstract void write_ulonglong(long value);

  public abstract void write_float(float value);

  public abstract void write_double(double value);

  public abstract void write_string(java.lang.String value);

  public abstract void write_wstring(java.lang.String value);

  public abstract void write_Object(org.omg.CORBA.Object value);

  public abstract void write_Abstract(java.lang.Object value);

  public abstract void write_Value(java.io.Serializable value);

  public abstract void write_TypeCode(org.omg.CORBA.TypeCode value);

  public abstract void write_any_array(org.omg.CORBA.Any[] seq, int offset, int length);

  public abstract void write_boolean_array(boolean[] seq, int offset, int length);

  public abstract void write_char_array(char[] seq, int offset, int length);

  public abstract void write_wchar_array(char[] seq, int offset, int length);

  public abstract void write_octet_array(byte[] seq, int offset, int length);

  public abstract void write_short_array(short[] seq, int offset, int length);

  public abstract void write_ushort_array(short[] seq, int offset, int length);

  public abstract void write_long_array(int[] seq, int offset, int length);

  public abstract void write_ulong_array(int[] seq, int offset, int length);

  public abstract void write_ulonglong_array(long[] seq, int offset, int length);

  public abstract void write_longlong_array(long[] seq, int offset, int length);

  public abstract void write_float_array(float[] seq, int offset, int length);

  public abstract void write_double_array(double[] seq, int offset, int length);

  public abstract void write_fixed(org.omg.CORBA.Any fixed_value)
    throws org.omg.CORBA.BadFixedValue;

  public abstract void write_fixed_array(org.omg.CORBA.Any[] seq, int offset, int length)
    throws org.omg.CORBA.BadFixedValue;


}
