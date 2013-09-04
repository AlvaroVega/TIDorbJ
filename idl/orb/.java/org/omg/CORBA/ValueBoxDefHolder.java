//
// ValueBoxDefHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class ValueBoxDefHolder
   implements org.omg.CORBA.portable.Streamable {

  public ValueBoxDef value; 
  public ValueBoxDefHolder() {
  }

  public ValueBoxDefHolder(ValueBoxDef initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.ValueBoxDefHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.ValueBoxDefHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.ValueBoxDefHelper.type();
  };

}
