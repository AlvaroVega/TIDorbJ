//
// RequestStartTimePolicyHolder.java (holder)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

final public class RequestStartTimePolicyHolder
   implements org.omg.CORBA.portable.Streamable {

  public RequestStartTimePolicy value; 
  public RequestStartTimePolicyHolder() {
  }

  public RequestStartTimePolicyHolder(RequestStartTimePolicy initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Messaging.RequestStartTimePolicyHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Messaging.RequestStartTimePolicyHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Messaging.RequestStartTimePolicyHelper.type();
  };

}
