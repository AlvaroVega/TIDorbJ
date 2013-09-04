//
// TypeMismatchHolder.java (holder)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny.DynAnyPackage;

final public class TypeMismatchHolder
   implements org.omg.CORBA.portable.Streamable {

  public TypeMismatch value; 
  public TypeMismatchHolder() {
  }

  public TypeMismatchHolder(TypeMismatch initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.DynamicAny.DynAnyPackage.TypeMismatchHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.DynamicAny.DynAnyPackage.TypeMismatchHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.DynamicAny.DynAnyPackage.TypeMismatchHelper.type();
  };

}
