//
// TransportAddress.java (struct)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

public class TransportAddress
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String host_name;
  public short port;

  public TransportAddress() {
    host_name = "";
  }

  public TransportAddress(java.lang.String host_name, short port) {
    this.host_name = host_name;
    this.port = port;
  }

}
