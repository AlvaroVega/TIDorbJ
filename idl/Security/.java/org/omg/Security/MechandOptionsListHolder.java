//
// MechandOptionsListHolder.java (holder)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

final public class MechandOptionsListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.Security.MechandOptions[] value; 
  public MechandOptionsListHolder() {
    value = new org.omg.Security.MechandOptions[0];
  }

  public MechandOptionsListHolder(org.omg.Security.MechandOptions[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Security.MechandOptionsListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Security.MechandOptionsListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Security.MechandOptionsListHelper.type();
  };

}
