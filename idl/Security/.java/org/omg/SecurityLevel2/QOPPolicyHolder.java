//
// QOPPolicyHolder.java (holder)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

final public class QOPPolicyHolder
   implements org.omg.CORBA.portable.Streamable {

  public QOPPolicy value; 
  public QOPPolicyHolder() {
  }

  public QOPPolicyHolder(QOPPolicy initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.SecurityLevel2.QOPPolicyHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.SecurityLevel2.QOPPolicyHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.SecurityLevel2.QOPPolicyHelper.type();
  };

}
