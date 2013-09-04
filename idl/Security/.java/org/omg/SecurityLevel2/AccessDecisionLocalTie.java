//
// AccessDecisionLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public class AccessDecisionLocalTie
 extends AccessDecisionLocalBase
 {

  private AccessDecisionOperations _delegate;
  public AccessDecisionLocalTie(AccessDecisionOperations delegate) {
    this._delegate = delegate;
  };

  public AccessDecisionOperations _delegate() {
    return this._delegate;
  };

  public boolean access_allowed(org.omg.SecurityLevel2.Credentials[] cred_list, org.omg.CORBA.Object target, java.lang.String operation_name, java.lang.String target_interface_name) {
    return this._delegate.access_allowed(
    cred_list, 
    target, 
    operation_name, 
    target_interface_name
    );
  };


}
