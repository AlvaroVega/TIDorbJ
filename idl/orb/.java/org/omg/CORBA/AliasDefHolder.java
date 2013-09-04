//
// AliasDefHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class AliasDefHolder
   implements org.omg.CORBA.portable.Streamable {

  public AliasDef value; 
  public AliasDefHolder() {
  }

  public AliasDefHolder(AliasDef initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.AliasDefHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.AliasDefHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.AliasDefHelper.type();
  };

}
