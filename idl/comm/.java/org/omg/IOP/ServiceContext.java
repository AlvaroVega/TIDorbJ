//
// ServiceContext.java (struct)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

public class ServiceContext
   implements org.omg.CORBA.portable.IDLEntity {

  public int context_id;
  public byte[] context_data;

  public ServiceContext() {
  }

  public ServiceContext(int context_id, byte[] context_data) {
    this.context_id = context_id;
    this.context_data = context_data;
  }

}
