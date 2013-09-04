//
// CodeBaseHolder.java (holder)
//
// File generated: Thu May 19 07:31:40 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SendingContext;

final public class CodeBaseHolder
   implements org.omg.CORBA.portable.Streamable {

  public CodeBase value; 
  public CodeBaseHolder() {
  }

  public CodeBaseHolder(CodeBase initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.SendingContext.CodeBaseHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.SendingContext.CodeBaseHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.SendingContext.CodeBaseHelper.type();
  };

}
