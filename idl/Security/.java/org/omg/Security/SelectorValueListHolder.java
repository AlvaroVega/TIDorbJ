//
// SelectorValueListHolder.java (holder)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

final public class SelectorValueListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.Security.SelectorValue[] value; 
  public SelectorValueListHolder() {
    value = new org.omg.Security.SelectorValue[0];
  }

  public SelectorValueListHolder(org.omg.Security.SelectorValue[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Security.SelectorValueListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Security.SelectorValueListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Security.SelectorValueListHelper.type();
  };

}
