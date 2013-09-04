//
// ServantManagerLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class ServantManagerLocalTie
 extends ServantManagerLocalBase
 {

  private ServantManagerOperations _delegate;
  public ServantManagerLocalTie(ServantManagerOperations delegate) {
    this._delegate = delegate;
  };

  public ServantManagerOperations _delegate() {
    return this._delegate;
  };


}
