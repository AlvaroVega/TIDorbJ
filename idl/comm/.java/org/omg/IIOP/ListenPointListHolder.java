//
// ListenPointListHolder.java (holder)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IIOP;

final public class ListenPointListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.IIOP.ListenPoint[] value; 
  public ListenPointListHolder() {
    value = new org.omg.IIOP.ListenPoint[0];
  }

  public ListenPointListHolder(org.omg.IIOP.ListenPoint[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.IIOP.ListenPointListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.IIOP.ListenPointListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.IIOP.ListenPointListHelper.type();
  };

}
