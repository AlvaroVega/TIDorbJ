//
// PolicyValueSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

final public class PolicyValueSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.Messaging.PolicyValue[] value; 
  public PolicyValueSeqHolder() {
    value = new org.omg.Messaging.PolicyValue[0];
  }

  public PolicyValueSeqHolder(org.omg.Messaging.PolicyValue[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Messaging.PolicyValueSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Messaging.PolicyValueSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Messaging.PolicyValueSeqHelper.type();
  };

}
