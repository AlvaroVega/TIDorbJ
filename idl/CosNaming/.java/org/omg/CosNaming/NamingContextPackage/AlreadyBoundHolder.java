//
// AlreadyBoundHolder.java (holder)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming.NamingContextPackage;

final public class AlreadyBoundHolder
   implements org.omg.CORBA.portable.Streamable {

  public AlreadyBound value; 
  public AlreadyBoundHolder() {
  }

  public AlreadyBoundHolder(AlreadyBound initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.type();
  };

}
