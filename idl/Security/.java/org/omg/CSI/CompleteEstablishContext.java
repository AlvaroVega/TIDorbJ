//
// CompleteEstablishContext.java (struct)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

public class CompleteEstablishContext
   implements org.omg.CORBA.portable.IDLEntity {

  public long client_context_id;
  public boolean context_stateful;
  public byte[] final_context_token;

  public CompleteEstablishContext() {
  }

  public CompleteEstablishContext(long client_context_id, boolean context_stateful, byte[] final_context_token) {
    this.client_context_id = client_context_id;
    this.context_stateful = context_stateful;
    this.final_context_token = final_context_token;
  }

}
