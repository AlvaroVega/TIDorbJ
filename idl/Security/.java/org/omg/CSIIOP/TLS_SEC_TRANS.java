//
// TLS_SEC_TRANS.java (struct)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

public class TLS_SEC_TRANS
   implements org.omg.CORBA.portable.IDLEntity {

  public short target_supports;
  public short target_requires;
  public org.omg.CSIIOP.TransportAddress[] addresses;

  public TLS_SEC_TRANS() {
  }

  public TLS_SEC_TRANS(short target_supports, short target_requires, org.omg.CSIIOP.TransportAddress[] addresses) {
    this.target_supports = target_supports;
    this.target_requires = target_requires;
    this.addresses = addresses;
  }

}
