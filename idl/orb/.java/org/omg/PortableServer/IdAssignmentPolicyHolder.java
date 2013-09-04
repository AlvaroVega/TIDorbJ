//
// IdAssignmentPolicyHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

final public class IdAssignmentPolicyHolder
   implements org.omg.CORBA.portable.Streamable {

  public IdAssignmentPolicy value; 
  public IdAssignmentPolicyHolder() {
  }

  public IdAssignmentPolicyHolder(IdAssignmentPolicy initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.PortableServer.IdAssignmentPolicyHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.PortableServer.IdAssignmentPolicyHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.PortableServer.IdAssignmentPolicyHelper.type();
  };

}
