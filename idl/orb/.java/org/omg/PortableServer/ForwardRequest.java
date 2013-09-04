//
// ForwardRequest.java (exception)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

final public class ForwardRequest
   extends org.omg.CORBA.UserException {

  public org.omg.CORBA.Object forward_reference;

  public ForwardRequest() {
    super(ForwardRequestHelper.id());
  }

  public ForwardRequest(org.omg.CORBA.Object _forward_reference) {
    super(ForwardRequestHelper.id());

    this.forward_reference = _forward_reference;
  }

  public ForwardRequest(String reason, org.omg.CORBA.Object _forward_reference) {
    super(ForwardRequestHelper.id()+" "+reason);

    this.forward_reference = _forward_reference;
  }

}
