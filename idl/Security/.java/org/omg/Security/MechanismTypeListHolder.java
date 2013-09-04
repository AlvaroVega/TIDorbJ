//
// MechanismTypeListHolder.java (holder)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

final public class MechanismTypeListHolder
   implements org.omg.CORBA.portable.Streamable {

  public java.lang.String[] value; 
  public MechanismTypeListHolder() {
    value = new java.lang.String[0];
  }

  public MechanismTypeListHolder(java.lang.String[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Security.MechanismTypeListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Security.MechanismTypeListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Security.MechanismTypeListHelper.type();
  };

}
