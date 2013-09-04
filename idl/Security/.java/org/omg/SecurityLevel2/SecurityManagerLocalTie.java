//
// SecurityManagerLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public class SecurityManagerLocalTie
 extends SecurityManagerLocalBase
 {

  private SecurityManagerOperations _delegate;
  public SecurityManagerLocalTie(SecurityManagerOperations delegate) {
    this._delegate = delegate;
  };

  public SecurityManagerOperations _delegate() {
    return this._delegate;
  };

  public org.omg.Security.MechandOptions[] supported_mechanisms() {
    return this._delegate.supported_mechanisms();
  }

  public org.omg.SecurityLevel2.Credentials[] own_credentials() {
    return this._delegate.own_credentials();
  }

  public org.omg.SecurityLevel2.RequiredRights required_rights_object() {
    return this._delegate.required_rights_object();
  }

  public org.omg.SecurityLevel2.PrincipalAuthenticator principal_authenticator() {
    return this._delegate.principal_authenticator();
  }

  public org.omg.SecurityLevel2.AccessDecision access_decision() {
    return this._delegate.access_decision();
  }

  public org.omg.SecurityLevel2.AuditDecision audit_decision() {
    return this._delegate.audit_decision();
  }

  public org.omg.SecurityLevel2.TargetCredentials get_target_credentials(org.omg.CORBA.Object obj_ref) {
    return this._delegate.get_target_credentials(
    obj_ref
    );
  };

  public void remove_own_credentials(org.omg.SecurityLevel2.Credentials creds) {
    this._delegate.remove_own_credentials(
    creds
    );
  };

  public org.omg.CORBA.Policy get_security_policy(int policy_type) {
    return this._delegate.get_security_policy(
    policy_type
    );
  };


}
