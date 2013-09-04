//
// DataInputStream.java (valuetype)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public interface DataInputStream
   extends org.omg.CORBA.portable.ValueBase{

  public abstract org.omg.CORBA.Any read_any();

  public abstract boolean read_boolean();

  public abstract char read_char();

  public abstract char read_wchar();

  public abstract byte read_octet();

  public abstract short read_short();

  public abstract short read_ushort();

  public abstract int read_long();

  public abstract int read_ulong();

  public abstract long read_longlong();

  public abstract long read_ulonglong();

  public abstract float read_float();

  public abstract double read_double();

  public abstract java.lang.String read_string();

  public abstract java.lang.String read_wstring();

  public abstract org.omg.CORBA.Object read_Object();

  public abstract java.lang.Object read_Abstract();

  public abstract java.io.Serializable read_Value();

  public abstract org.omg.CORBA.TypeCode read_TypeCode();

  public abstract void read_any_array(org.omg.CORBA.AnySeqHolder seq, int offset, int length);

  public abstract void read_boolean_array(org.omg.CORBA.BooleanSeqHolder seq, int offset, int length);

  public abstract void read_char_array(org.omg.CORBA.CharSeqHolder seq, int offset, int length);

  public abstract void read_wchar_array(org.omg.CORBA.WCharSeqHolder seq, int offset, int length);

  public abstract void read_octet_array(org.omg.CORBA.OctetSeqHolder seq, int offset, int length);

  public abstract void read_short_array(org.omg.CORBA.ShortSeqHolder seq, int offset, int length);

  public abstract void read_ushort_array(org.omg.CORBA.UShortSeqHolder seq, int offset, int length);

  public abstract void read_long_array(org.omg.CORBA.LongSeqHolder seq, int offset, int length);

  public abstract void read_ulong_array(org.omg.CORBA.ULongSeqHolder seq, int offset, int length);

  public abstract void read_ulonglong_array(org.omg.CORBA.ULongLongSeqHolder seq, int offset, int length);

  public abstract void read_longlong_array(org.omg.CORBA.LongLongSeqHolder seq, int offset, int length);

  public abstract void read_float_array(org.omg.CORBA.FloatSeqHolder seq, int offset, int length);

  public abstract void read_double_array(org.omg.CORBA.DoubleSeqHolder seq, int offset, int length);

  public abstract org.omg.CORBA.Any read_fixed(short digits, short scale)
    throws org.omg.CORBA.BadFixedValue;

  public abstract void read_fixed_array(org.omg.CORBA.AnySeqHolder seq, int offset, int length, short digits, short scale)
    throws org.omg.CORBA.BadFixedValue;


}
