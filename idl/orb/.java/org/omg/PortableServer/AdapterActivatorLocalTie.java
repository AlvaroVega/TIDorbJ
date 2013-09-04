//
// AdapterActivatorLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class AdapterActivatorLocalTie
 extends AdapterActivatorLocalBase
 {

  private AdapterActivatorOperations _delegate;
  public AdapterActivatorLocalTie(AdapterActivatorOperations delegate) {
    this._delegate = delegate;
  };

  public AdapterActivatorOperations _delegate() {
    return this._delegate;
  };

  public boolean unknown_adapter(org.omg.PortableServer.POA parent, java.lang.String name) {
    return this._delegate.unknown_adapter(
    parent, 
    name
    );
  };


}
