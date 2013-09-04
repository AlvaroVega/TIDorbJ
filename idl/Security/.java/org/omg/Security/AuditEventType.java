//
// AuditEventType.java (struct)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class AuditEventType
   implements org.omg.CORBA.portable.IDLEntity {

  public org.omg.Security.ExtensibleFamily event_family;
  public short event_type;

  public AuditEventType() {
  }

  public AuditEventType(org.omg.Security.ExtensibleFamily event_family, short event_type) {
    this.event_family = event_family;
    this.event_type = event_type;
  }

}
