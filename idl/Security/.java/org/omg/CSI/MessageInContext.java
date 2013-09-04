//
// MessageInContext.java (struct)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

public class MessageInContext
   implements org.omg.CORBA.portable.IDLEntity {

  public long client_context_id;
  public boolean discard_context;

  public MessageInContext() {
  }

  public MessageInContext(long client_context_id, boolean discard_context) {
    this.client_context_id = client_context_id;
    this.discard_context = discard_context;
  }

}
