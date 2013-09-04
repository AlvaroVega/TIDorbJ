//
// SSL.java (struct)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SSLIOP;

public class SSL
   implements org.omg.CORBA.portable.IDLEntity {

  public short target_supports;
  public short target_requires;
  public short port;

  public SSL() {
  }

  public SSL(short target_supports, short target_requires, short port) {
    this.target_supports = target_supports;
    this.target_requires = target_requires;
    this.port = port;
  }

}
