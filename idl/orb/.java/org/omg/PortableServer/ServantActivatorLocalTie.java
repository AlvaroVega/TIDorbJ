//
// ServantActivatorLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class ServantActivatorLocalTie
 extends ServantActivatorLocalBase
 {

  private ServantActivatorOperations _delegate;
  public ServantActivatorLocalTie(ServantActivatorOperations delegate) {
    this._delegate = delegate;
  };

  public ServantActivatorOperations _delegate() {
    return this._delegate;
  };

  public org.omg.PortableServer.Servant incarnate(byte[] oid, org.omg.PortableServer.POA adapter)
    throws org.omg.PortableServer.ForwardRequest {
    return this._delegate.incarnate(
    oid, 
    adapter
    );
  };

  public void etherealize(byte[] oid, org.omg.PortableServer.POA adapter, org.omg.PortableServer.Servant serv, boolean cleanup_in_progress, boolean remaining_activations) {
    this._delegate.etherealize(
    oid, 
    adapter, 
    serv, 
    cleanup_in_progress, 
    remaining_activations
    );
  };



}
