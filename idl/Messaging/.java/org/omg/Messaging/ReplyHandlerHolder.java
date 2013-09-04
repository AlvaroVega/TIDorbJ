//
// ReplyHandlerHolder.java (holder)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

final public class ReplyHandlerHolder
   implements org.omg.CORBA.portable.Streamable {

  public ReplyHandler value; 
  public ReplyHandlerHolder() {
  }

  public ReplyHandlerHolder(ReplyHandler initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Messaging.ReplyHandlerHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Messaging.ReplyHandlerHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Messaging.ReplyHandlerHelper.type();
  };

}
