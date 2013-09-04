//
// CodecFactoryHolder.java (holder)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

final public class CodecFactoryHolder
   implements org.omg.CORBA.portable.Streamable {

  public CodecFactory value; 
  public CodecFactoryHolder() {
  }

  public CodecFactoryHolder(CodecFactory initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.IOP.CodecFactoryHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.IOP.CodecFactoryHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.IOP.CodecFactoryHelper.type();
  };

}
