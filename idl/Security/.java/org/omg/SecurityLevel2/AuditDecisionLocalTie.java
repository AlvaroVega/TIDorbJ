//
// AuditDecisionLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public class AuditDecisionLocalTie
 extends AuditDecisionLocalBase
 {

  private AuditDecisionOperations _delegate;
  public AuditDecisionLocalTie(AuditDecisionOperations delegate) {
    this._delegate = delegate;
  };

  public AuditDecisionOperations _delegate() {
    return this._delegate;
  };

  public boolean audit_needed(org.omg.Security.AuditEventType event_type, org.omg.Security.SelectorValue[] value_list) {
    return this._delegate.audit_needed(
    event_type, 
    value_list
    );
  };

  public org.omg.SecurityLevel2.AuditChannel audit_channel() {
    return this._delegate.audit_channel();
  }


}
