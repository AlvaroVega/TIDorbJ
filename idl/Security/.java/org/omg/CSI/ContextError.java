//
// ContextError.java (struct)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

public class ContextError
   implements org.omg.CORBA.portable.IDLEntity {

  public long client_context_id;
  public int major_status;
  public int minor_status;
  public byte[] error_token;

  public ContextError() {
  }

  public ContextError(long client_context_id, int major_status, int minor_status, byte[] error_token) {
    this.client_context_id = client_context_id;
    this.major_status = major_status;
    this.minor_status = minor_status;
    this.error_token = error_token;
  }

}
