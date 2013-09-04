//
// NameComponentHolder.java (holder)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming;

final public class NameComponentHolder
   implements org.omg.CORBA.portable.Streamable {

  public NameComponent value; 
  public NameComponentHolder() {
  }

  public NameComponentHolder(NameComponent initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CosNaming.NameComponentHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CosNaming.NameComponentHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CosNaming.NameComponentHelper.type();
  };

}
