//
// ReceivedCredentials.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public interface ReceivedCredentialsOperations
   extends org.omg.SecurityLevel2.CredentialsOperations {

  org.omg.SecurityLevel2.Credentials accepting_credentials();

  short association_options_used();

  org.omg.Security.DelegationState delegation_state();

  org.omg.Security.DelegationMode delegation_mode();


}
