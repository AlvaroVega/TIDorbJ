//
// PrincipalAuthenticator.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public interface PrincipalAuthenticatorOperations {

  int[] get_supported_authen_methods(java.lang.String mechanism);

  org.omg.Security.AuthenticationStatus authenticate(int method, java.lang.String mechanism, java.lang.String security_name, org.omg.CORBA.Any auth_data, org.omg.Security.SecAttribute[] privileges, org.omg.SecurityLevel2.CredentialsHolder creds, org.omg.CORBA.AnyHolder continuation_data, org.omg.CORBA.AnyHolder auth_specific_data);

  org.omg.Security.AuthenticationStatus continue_authentication(org.omg.CORBA.Any response_data, org.omg.SecurityLevel2.Credentials creds, org.omg.CORBA.AnyHolder continuation_data, org.omg.CORBA.AnyHolder auth_specific_data);


}
