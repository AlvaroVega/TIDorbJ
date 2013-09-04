//
// AuditDecision.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public interface AuditDecisionOperations {

  boolean audit_needed(org.omg.Security.AuditEventType event_type, org.omg.Security.SelectorValue[] value_list);

  org.omg.SecurityLevel2.AuditChannel audit_channel();


}
