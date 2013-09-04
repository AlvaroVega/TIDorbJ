//
// POAListHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

final public class POAListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.PortableServer.POA[] value; 
  public POAListHolder() {
    value = new org.omg.PortableServer.POA[0];
  }

  public POAListHolder(org.omg.PortableServer.POA[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.PortableServer.POAListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.PortableServer.POAListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.PortableServer.POAListHelper.type();
  };

}
