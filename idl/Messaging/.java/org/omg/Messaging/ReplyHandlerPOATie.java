//
// ReplyHandlerPOATie.java (tie)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

public class ReplyHandlerPOATie
 extends ReplyHandlerPOA
 implements ReplyHandlerOperations {

  private ReplyHandlerOperations _delegate;
  public ReplyHandlerPOATie(ReplyHandlerOperations delegate) {
    this._delegate = delegate;
  };

  public ReplyHandlerOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/ReplyHandler:1.0"  };


}
