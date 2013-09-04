//
// DynAnySeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny;

final public class DynAnySeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.DynamicAny.DynAny[] value; 
  public DynAnySeqHolder() {
    value = new org.omg.DynamicAny.DynAny[0];
  }

  public DynAnySeqHolder(org.omg.DynamicAny.DynAny[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.DynamicAny.DynAnySeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.DynamicAny.DynAnySeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.DynamicAny.DynAnySeqHelper.type();
  };

}
