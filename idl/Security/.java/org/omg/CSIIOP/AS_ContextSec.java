//
// AS_ContextSec.java (struct)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

public class AS_ContextSec
   implements org.omg.CORBA.portable.IDLEntity {

  public short target_supports;
  public short target_requires;
  public byte[] client_authentication_mech;
  public byte[] target_name;

  public AS_ContextSec() {
  }

  public AS_ContextSec(short target_supports, short target_requires, byte[] client_authentication_mech, byte[] target_name) {
    this.target_supports = target_supports;
    this.target_requires = target_requires;
    this.client_authentication_mech = client_authentication_mech;
    this.target_name = target_name;
  }

}
