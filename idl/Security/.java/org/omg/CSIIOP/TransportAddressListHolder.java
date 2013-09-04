//
// TransportAddressListHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

final public class TransportAddressListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CSIIOP.TransportAddress[] value; 
  public TransportAddressListHolder() {
    value = new org.omg.CSIIOP.TransportAddress[0];
  }

  public TransportAddressListHolder(org.omg.CSIIOP.TransportAddress[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CSIIOP.TransportAddressListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CSIIOP.TransportAddressListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CSIIOP.TransportAddressListHelper.type();
  };

}
