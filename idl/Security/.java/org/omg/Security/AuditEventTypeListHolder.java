//
// AuditEventTypeListHolder.java (holder)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

final public class AuditEventTypeListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.Security.AuditEventType[] value; 
  public AuditEventTypeListHolder() {
    value = new org.omg.Security.AuditEventType[0];
  }

  public AuditEventTypeListHolder(org.omg.Security.AuditEventType[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Security.AuditEventTypeListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Security.AuditEventTypeListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Security.AuditEventTypeListHelper.type();
  };

}
