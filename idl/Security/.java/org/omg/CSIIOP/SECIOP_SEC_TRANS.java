//
// SECIOP_SEC_TRANS.java (struct)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

public class SECIOP_SEC_TRANS
   implements org.omg.CORBA.portable.IDLEntity {

  public short target_supports;
  public short target_requires;
  public byte[] mech_oid;
  public byte[] target_name;
  public org.omg.CSIIOP.TransportAddress[] addresses;

  public SECIOP_SEC_TRANS() {
  }

  public SECIOP_SEC_TRANS(short target_supports, short target_requires, byte[] mech_oid, byte[] target_name, org.omg.CSIIOP.TransportAddress[] addresses) {
    this.target_supports = target_supports;
    this.target_requires = target_requires;
    this.mech_oid = mech_oid;
    this.target_name = target_name;
    this.addresses = addresses;
  }

}
