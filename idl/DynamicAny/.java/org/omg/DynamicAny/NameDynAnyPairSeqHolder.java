//
// NameDynAnyPairSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny;

final public class NameDynAnyPairSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.DynamicAny.NameDynAnyPair[] value; 
  public NameDynAnyPairSeqHolder() {
    value = new org.omg.DynamicAny.NameDynAnyPair[0];
  }

  public NameDynAnyPairSeqHolder(org.omg.DynamicAny.NameDynAnyPair[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.DynamicAny.NameDynAnyPairSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.DynamicAny.NameDynAnyPairSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.DynamicAny.NameDynAnyPairSeqHelper.type();
  };

}
