//
// ObjectAlreadyActiveHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer.POAPackage;

final public class ObjectAlreadyActiveHolder
   implements org.omg.CORBA.portable.Streamable {

  public ObjectAlreadyActive value; 
  public ObjectAlreadyActiveHolder() {
  }

  public ObjectAlreadyActiveHolder(ObjectAlreadyActive initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.PortableServer.POAPackage.ObjectAlreadyActiveHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.PortableServer.POAPackage.ObjectAlreadyActiveHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.PortableServer.POAPackage.ObjectAlreadyActiveHelper.type();
  };

}
