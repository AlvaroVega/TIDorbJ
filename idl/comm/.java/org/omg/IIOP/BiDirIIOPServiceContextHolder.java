//
// BiDirIIOPServiceContextHolder.java (holder)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IIOP;

final public class BiDirIIOPServiceContextHolder
   implements org.omg.CORBA.portable.Streamable {

  public BiDirIIOPServiceContext value; 
  public BiDirIIOPServiceContextHolder() {
  }

  public BiDirIIOPServiceContextHolder(BiDirIIOPServiceContext initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.IIOP.BiDirIIOPServiceContextHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.IIOP.BiDirIIOPServiceContextHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.IIOP.BiDirIIOPServiceContextHelper.type();
  };

}
