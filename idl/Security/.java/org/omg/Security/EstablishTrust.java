//
// EstablishTrust.java (struct)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class EstablishTrust
   implements org.omg.CORBA.portable.IDLEntity {

  public boolean trust_in_client;
  public boolean trust_in_target;

  public EstablishTrust() {
  }

  public EstablishTrust(boolean trust_in_client, boolean trust_in_target) {
    this.trust_in_client = trust_in_client;
    this.trust_in_target = trust_in_target;
  }

}
