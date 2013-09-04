//
// PrincipalAuthenticatorLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public class PrincipalAuthenticatorLocalTie
 extends PrincipalAuthenticatorLocalBase
 {

  private PrincipalAuthenticatorOperations _delegate;
  public PrincipalAuthenticatorLocalTie(PrincipalAuthenticatorOperations delegate) {
    this._delegate = delegate;
  };

  public PrincipalAuthenticatorOperations _delegate() {
    return this._delegate;
  };

  public int[] get_supported_authen_methods(java.lang.String mechanism) {
    return this._delegate.get_supported_authen_methods(
    mechanism
    );
  };

  public org.omg.Security.AuthenticationStatus authenticate(int method, java.lang.String mechanism, java.lang.String security_name, org.omg.CORBA.Any auth_data, org.omg.Security.SecAttribute[] privileges, org.omg.SecurityLevel2.CredentialsHolder creds, org.omg.CORBA.AnyHolder continuation_data, org.omg.CORBA.AnyHolder auth_specific_data) {
    return this._delegate.authenticate(
    method, 
    mechanism, 
    security_name, 
    auth_data, 
    privileges, 
    creds, 
    continuation_data, 
    auth_specific_data
    );
  };

  public org.omg.Security.AuthenticationStatus continue_authentication(org.omg.CORBA.Any response_data, org.omg.SecurityLevel2.Credentials creds, org.omg.CORBA.AnyHolder continuation_data, org.omg.CORBA.AnyHolder auth_specific_data) {
    return this._delegate.continue_authentication(
    response_data, 
    creds, 
    continuation_data, 
    auth_specific_data
    );
  };


}
