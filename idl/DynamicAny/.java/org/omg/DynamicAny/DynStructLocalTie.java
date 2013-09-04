//
// DynStructLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny;

public class DynStructLocalTie
 extends DynStructLocalBase
 {

  private DynStructOperations _delegate;
  public DynStructLocalTie(DynStructOperations delegate) {
    this._delegate = delegate;
  };

  public DynStructOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String current_member_name()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.current_member_name(
    );
  };

  public org.omg.CORBA.TCKind current_member_kind()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.current_member_kind(
    );
  };

  public org.omg.DynamicAny.NameValuePair[] get_members() {
    return this._delegate.get_members(
    );
  };

  public void set_members(org.omg.DynamicAny.NameValuePair[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.set_members(
    value
    );
  };

  public org.omg.DynamicAny.NameDynAnyPair[] get_members_as_dyn_any() {
    return this._delegate.get_members_as_dyn_any(
    );
  };

  public void set_members_as_dyn_any(org.omg.DynamicAny.NameDynAnyPair[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.set_members_as_dyn_any(
    value
    );
  };

  public org.omg.CORBA.TypeCode type() {
    return this._delegate.type(
    );
  };

  public void assign(org.omg.DynamicAny.DynAny dyn_any)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch {
    this._delegate.assign(
    dyn_any
    );
  };

  public void from_any(org.omg.CORBA.Any value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.from_any(
    value
    );
  };

  public org.omg.CORBA.Any to_any() {
    return this._delegate.to_any(
    );
  };

  public boolean equal(org.omg.DynamicAny.DynAny dyn_any) {
    return this._delegate.equal(
    dyn_any
    );
  };

  public void destroy() {
    this._delegate.destroy(
    );
  };

  public org.omg.DynamicAny.DynAny copy() {
    return this._delegate.copy(
    );
  };

  public void insert_boolean(boolean value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_boolean(
    value
    );
  };

  public void insert_octet(byte value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_octet(
    value
    );
  };

  public void insert_char(char value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_char(
    value
    );
  };

  public void insert_short(short value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_short(
    value
    );
  };

  public void insert_ushort(short value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_ushort(
    value
    );
  };

  public void insert_long(int value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_long(
    value
    );
  };

  public void insert_ulong(int value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_ulong(
    value
    );
  };

  public void insert_float(float value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_float(
    value
    );
  };

  public void insert_double(double value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_double(
    value
    );
  };

  public void insert_string(java.lang.String value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_string(
    value
    );
  };

  public void insert_reference(org.omg.CORBA.Object value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_reference(
    value
    );
  };

  public void insert_typecode(org.omg.CORBA.TypeCode value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_typecode(
    value
    );
  };

  public void insert_longlong(long value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_longlong(
    value
    );
  };

  public void insert_ulonglong(long value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_ulonglong(
    value
    );
  };

  public void insert_wchar(char value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_wchar(
    value
    );
  };

  public void insert_wstring(java.lang.String value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_wstring(
    value
    );
  };

  public void insert_any(org.omg.CORBA.Any value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_any(
    value
    );
  };

  public void insert_dyn_any(org.omg.DynamicAny.DynAny value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_dyn_any(
    value
    );
  };

  public void insert_val(java.io.Serializable value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_val(
    value
    );
  };

  public boolean get_boolean()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_boolean(
    );
  };

  public byte get_octet()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_octet(
    );
  };

  public char get_char()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_char(
    );
  };

  public short get_short()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_short(
    );
  };

  public short get_ushort()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_ushort(
    );
  };

  public int get_long()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_long(
    );
  };

  public int get_ulong()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_ulong(
    );
  };

  public float get_float()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_float(
    );
  };

  public double get_double()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_double(
    );
  };

  public java.lang.String get_string()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_string(
    );
  };

  public org.omg.CORBA.Object get_reference()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_reference(
    );
  };

  public org.omg.CORBA.TypeCode get_typecode()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_typecode(
    );
  };

  public long get_longlong()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_longlong(
    );
  };

  public long get_ulonglong()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_ulonglong(
    );
  };

  public char get_wchar()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_wchar(
    );
  };

  public java.lang.String get_wstring()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_wstring(
    );
  };

  public org.omg.CORBA.Any get_any()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_any(
    );
  };

  public org.omg.DynamicAny.DynAny get_dyn_any()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_dyn_any(
    );
  };

  public java.io.Serializable get_val()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_val(
    );
  };

  public boolean seek(int index) {
    return this._delegate.seek(
    index
    );
  };

  public void rewind() {
    this._delegate.rewind(
    );
  };

  public boolean next() {
    return this._delegate.next(
    );
  };

  public int component_count() {
    return this._delegate.component_count(
    );
  };

  public org.omg.DynamicAny.DynAny current_component()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch {
    return this._delegate.current_component(
    );
  };

  public void insert_abstract(java.lang.Object value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_abstract(
    value
    );
  };

  public java.lang.Object get_abstract()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_abstract(
    );
  };

  public void insert_boolean_seq(boolean[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_boolean_seq(
    value
    );
  };

  public void insert_octet_seq(byte[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_octet_seq(
    value
    );
  };

  public void insert_char_seq(char[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_char_seq(
    value
    );
  };

  public void insert_short_seq(short[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_short_seq(
    value
    );
  };

  public void insert_ushort_seq(short[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_ushort_seq(
    value
    );
  };

  public void insert_long_seq(int[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_long_seq(
    value
    );
  };

  public void insert_ulong_seq(int[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_ulong_seq(
    value
    );
  };

  public void insert_float_seq(float[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_float_seq(
    value
    );
  };

  public void insert_double_seq(double[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_double_seq(
    value
    );
  };

  public void insert_longlong_seq(long[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_longlong_seq(
    value
    );
  };

  public void insert_ulonglong_seq(long[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_ulonglong_seq(
    value
    );
  };

  public void insert_wchar_seq(char[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    this._delegate.insert_wchar_seq(
    value
    );
  };

  public boolean[] get_boolean_seq()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_boolean_seq(
    );
  };

  public byte[] get_octet_seq()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_octet_seq(
    );
  };

  public char[] get_char_seq()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_char_seq(
    );
  };

  public short[] get_short_seq()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_short_seq(
    );
  };

  public short[] get_ushort_seq()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_ushort_seq(
    );
  };

  public int[] get_long_seq()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_long_seq(
    );
  };

  public int[] get_ulong_seq()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_ulong_seq(
    );
  };

  public float[] get_float_seq()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_float_seq(
    );
  };

  public double[] get_double_seq()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_double_seq(
    );
  };

  public long[] get_longlong_seq()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_longlong_seq(
    );
  };

  public long[] get_ulonglong_seq()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_ulonglong_seq(
    );
  };

  public char[] get_wchar_seq()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue {
    return this._delegate.get_wchar_seq(
    );
  };



}
