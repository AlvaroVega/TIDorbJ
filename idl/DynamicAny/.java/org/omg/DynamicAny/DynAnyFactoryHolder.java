//
// DynAnyFactoryHolder.java (holder)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny;

final public class DynAnyFactoryHolder
   implements org.omg.CORBA.portable.Streamable {

  public DynAnyFactory value; 
  public DynAnyFactoryHolder() {
  }

  public DynAnyFactoryHolder(DynAnyFactory initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.DynamicAny.DynAnyFactoryHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.DynamicAny.DynAnyFactoryHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.DynamicAny.DynAnyFactoryHelper.type();
  };

}
