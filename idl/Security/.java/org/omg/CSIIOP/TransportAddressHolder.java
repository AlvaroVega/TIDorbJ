//
// TransportAddressHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

final public class TransportAddressHolder
   implements org.omg.CORBA.portable.Streamable {

  public TransportAddress value; 
  public TransportAddressHolder() {
  }

  public TransportAddressHolder(TransportAddress initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CSIIOP.TransportAddressHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CSIIOP.TransportAddressHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CSIIOP.TransportAddressHelper.type();
  };

}
