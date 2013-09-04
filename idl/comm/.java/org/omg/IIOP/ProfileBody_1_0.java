//
// ProfileBody_1_0.java (struct)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IIOP;

public class ProfileBody_1_0
   implements org.omg.CORBA.portable.IDLEntity {

  public org.omg.IIOP.Version iiop_version;
  public java.lang.String host;
  public short port;
  public byte[] object_key;

  public ProfileBody_1_0() {
    host = "";
  }

  public ProfileBody_1_0(org.omg.IIOP.Version iiop_version, java.lang.String host, short port, byte[] object_key) {
    this.iiop_version = iiop_version;
    this.host = host;
    this.port = port;
    this.object_key = object_key;
  }

}
