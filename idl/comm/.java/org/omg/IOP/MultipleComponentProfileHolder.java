//
// MultipleComponentProfileHolder.java (holder)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

final public class MultipleComponentProfileHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.IOP.TaggedComponent[] value; 
  public MultipleComponentProfileHolder() {
    value = new org.omg.IOP.TaggedComponent[0];
  }

  public MultipleComponentProfileHolder(org.omg.IOP.TaggedComponent[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.IOP.MultipleComponentProfileHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.IOP.MultipleComponentProfileHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.IOP.MultipleComponentProfileHelper.type();
  };

}
