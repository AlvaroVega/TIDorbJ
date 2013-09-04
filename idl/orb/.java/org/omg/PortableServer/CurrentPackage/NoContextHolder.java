//
// NoContextHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer.CurrentPackage;

final public class NoContextHolder
   implements org.omg.CORBA.portable.Streamable {

  public NoContext value; 
  public NoContextHolder() {
  }

  public NoContextHolder(NoContext initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.PortableServer.CurrentPackage.NoContextHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.PortableServer.CurrentPackage.NoContextHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.PortableServer.CurrentPackage.NoContextHelper.type();
  };

}
