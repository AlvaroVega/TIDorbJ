//
// SAS_ContextSec.java (struct)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

public class SAS_ContextSec
   implements org.omg.CORBA.portable.IDLEntity {

  public short target_supports;
  public short target_requires;
  public org.omg.CSIIOP.ServiceConfiguration[] privilege_authorities;
  public byte[][] supported_naming_mechanisms;
  public int supported_identity_types;

  public SAS_ContextSec() {
  }

  public SAS_ContextSec(short target_supports, short target_requires, org.omg.CSIIOP.ServiceConfiguration[] privilege_authorities, byte[][] supported_naming_mechanisms, int supported_identity_types) {
    this.target_supports = target_supports;
    this.target_requires = target_requires;
    this.privilege_authorities = privilege_authorities;
    this.supported_naming_mechanisms = supported_naming_mechanisms;
    this.supported_identity_types = supported_identity_types;
  }

}
