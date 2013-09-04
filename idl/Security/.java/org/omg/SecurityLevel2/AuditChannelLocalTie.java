//
// AuditChannelLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public class AuditChannelLocalTie
 extends AuditChannelLocalBase
 {

  private AuditChannelOperations _delegate;
  public AuditChannelLocalTie(AuditChannelOperations delegate) {
    this._delegate = delegate;
  };

  public AuditChannelOperations _delegate() {
    return this._delegate;
  };

  public void audit_write(org.omg.Security.AuditEventType event_type, org.omg.SecurityLevel2.Credentials[] creds, org.omg.TimeBase.UtcT time, org.omg.Security.SelectorValue[] descriptors, org.omg.CORBA.Any event_specific_data) {
    this._delegate.audit_write(
    event_type, 
    creds, 
    time, 
    descriptors, 
    event_specific_data
    );
  };

  public int audit_channel_id() {
    return this._delegate.audit_channel_id();
  }


}
