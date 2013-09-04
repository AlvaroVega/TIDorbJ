//
// AuditChannel.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public interface AuditChannelOperations {

  void audit_write(org.omg.Security.AuditEventType event_type, org.omg.SecurityLevel2.Credentials[] creds, org.omg.TimeBase.UtcT time, org.omg.Security.SelectorValue[] descriptors, org.omg.CORBA.Any event_specific_data);

  int audit_channel_id();


}
