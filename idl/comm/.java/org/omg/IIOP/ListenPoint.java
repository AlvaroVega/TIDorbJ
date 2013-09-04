//
// ListenPoint.java (struct)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IIOP;

public class ListenPoint
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String host;
  public short port;

  public ListenPoint() {
    host = "";
  }

  public ListenPoint(java.lang.String host, short port) {
    this.host = host;
    this.port = port;
  }

}
