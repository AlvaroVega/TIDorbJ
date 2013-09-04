//
// RequiresSupportsHolder.java (holder)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

final public class RequiresSupportsHolder
   implements org.omg.CORBA.portable.Streamable {

  public RequiresSupports value; 
  public RequiresSupportsHolder() {
  }

  public RequiresSupportsHolder(RequiresSupports initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Security.RequiresSupportsHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Security.RequiresSupportsHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Security.RequiresSupportsHelper.type();
  };

}
