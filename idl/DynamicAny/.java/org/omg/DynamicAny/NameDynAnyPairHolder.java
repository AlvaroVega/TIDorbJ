//
// NameDynAnyPairHolder.java (holder)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny;

final public class NameDynAnyPairHolder
   implements org.omg.CORBA.portable.Streamable {

  public NameDynAnyPair value; 
  public NameDynAnyPairHolder() {
  }

  public NameDynAnyPairHolder(NameDynAnyPair initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.DynamicAny.NameDynAnyPairHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.DynamicAny.NameDynAnyPairHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.DynamicAny.NameDynAnyPairHelper.type();
  };

}
