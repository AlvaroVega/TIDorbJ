//
// MaxHopsPolicyHolder.java (holder)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

final public class MaxHopsPolicyHolder
   implements org.omg.CORBA.portable.Streamable {

  public MaxHopsPolicy value; 
  public MaxHopsPolicyHolder() {
  }

  public MaxHopsPolicyHolder(MaxHopsPolicy initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Messaging.MaxHopsPolicyHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Messaging.MaxHopsPolicyHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Messaging.MaxHopsPolicyHelper.type();
  };

}
