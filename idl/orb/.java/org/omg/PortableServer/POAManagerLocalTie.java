//
// POAManagerLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class POAManagerLocalTie
 extends POAManagerLocalBase
 {

  private POAManagerOperations _delegate;
  public POAManagerLocalTie(POAManagerOperations delegate) {
    this._delegate = delegate;
  };

  public POAManagerOperations _delegate() {
    return this._delegate;
  };

  public void activate()
    throws org.omg.PortableServer.POAManagerPackage.AdapterInactive {
    this._delegate.activate(
    );
  };

  public void hold_requests(boolean wait_for_completion)
    throws org.omg.PortableServer.POAManagerPackage.AdapterInactive {
    this._delegate.hold_requests(
    wait_for_completion
    );
  };

  public void discard_requests(boolean wait_for_completion)
    throws org.omg.PortableServer.POAManagerPackage.AdapterInactive {
    this._delegate.discard_requests(
    wait_for_completion
    );
  };

  public void deactivate(boolean etherealize_objects, boolean wait_for_completion)
    throws org.omg.PortableServer.POAManagerPackage.AdapterInactive {
    this._delegate.deactivate(
    etherealize_objects, 
    wait_for_completion
    );
  };

  public org.omg.PortableServer.POAManagerPackage.State get_state() {
    return this._delegate.get_state(
    );
  };


}
