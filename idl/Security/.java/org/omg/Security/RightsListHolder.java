//
// RightsListHolder.java (holder)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

final public class RightsListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.Security.Right[] value; 
  public RightsListHolder() {
    value = new org.omg.Security.Right[0];
  }

  public RightsListHolder(org.omg.Security.Right[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Security.RightsListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Security.RightsListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Security.RightsListHelper.type();
  };

}
