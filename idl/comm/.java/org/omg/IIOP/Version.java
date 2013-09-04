//
// Version.java (struct)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IIOP;

public class Version
   implements org.omg.CORBA.portable.IDLEntity {

  public byte major;
  public byte minor;

  public Version() {
  }

  public Version(byte major, byte minor) {
    this.major = major;
    this.minor = minor;
  }

}
