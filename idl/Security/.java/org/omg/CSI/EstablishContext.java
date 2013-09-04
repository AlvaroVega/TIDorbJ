//
// EstablishContext.java (struct)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

public class EstablishContext
   implements org.omg.CORBA.portable.IDLEntity {

  public long client_context_id;
  public org.omg.CSI.AuthorizationElement[] authorization_token;
  public org.omg.CSI.IdentityToken identity_token;
  public byte[] client_authentication_token;

  public EstablishContext() {
  }

  public EstablishContext(long client_context_id, org.omg.CSI.AuthorizationElement[] authorization_token, org.omg.CSI.IdentityToken identity_token, byte[] client_authentication_token) {
    this.client_context_id = client_context_id;
    this.authorization_token = authorization_token;
    this.identity_token = identity_token;
    this.client_authentication_token = client_authentication_token;
  }

}
