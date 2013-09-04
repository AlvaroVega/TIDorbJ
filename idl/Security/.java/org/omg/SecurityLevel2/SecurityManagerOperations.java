//
// SecurityManager.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public interface SecurityManagerOperations {

  org.omg.Security.MechandOptions[] supported_mechanisms();

  org.omg.SecurityLevel2.Credentials[] own_credentials();

  org.omg.SecurityLevel2.RequiredRights required_rights_object();

  org.omg.SecurityLevel2.PrincipalAuthenticator principal_authenticator();

  org.omg.SecurityLevel2.AccessDecision access_decision();

  org.omg.SecurityLevel2.AuditDecision audit_decision();

  org.omg.SecurityLevel2.TargetCredentials get_target_credentials(org.omg.CORBA.Object obj_ref);

  void remove_own_credentials(org.omg.SecurityLevel2.Credentials creds);

  org.omg.CORBA.Policy get_security_policy(int policy_type);


}
