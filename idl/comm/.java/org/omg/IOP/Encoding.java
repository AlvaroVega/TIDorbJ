//
// Encoding.java (struct)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

public class Encoding
   implements org.omg.CORBA.portable.IDLEntity {

  public short format;
  public byte major_version;
  public byte minor_version;

  public Encoding() {
  }

  public Encoding(short format, byte major_version, byte minor_version) {
    this.format = format;
    this.major_version = major_version;
    this.minor_version = minor_version;
  }

}
