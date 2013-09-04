//
// ChannelBindings.java (struct)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class ChannelBindings
   implements org.omg.CORBA.portable.IDLEntity {

  public int initiator_addrtype;
  public byte[] initiator_address;
  public int acceptor_addrtype;
  public byte[] acceptor_address;
  public byte[] application_data;

  public ChannelBindings() {
  }

  public ChannelBindings(int initiator_addrtype, byte[] initiator_address, int acceptor_addrtype, byte[] acceptor_address, byte[] application_data) {
    this.initiator_addrtype = initiator_addrtype;
    this.initiator_address = initiator_address;
    this.acceptor_addrtype = acceptor_addrtype;
    this.acceptor_address = acceptor_address;
    this.application_data = application_data;
  }

}
