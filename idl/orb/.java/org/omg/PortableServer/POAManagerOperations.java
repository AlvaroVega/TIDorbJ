//
// POAManager.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public interface POAManagerOperations {

  void activate()
    throws org.omg.PortableServer.POAManagerPackage.AdapterInactive;

  void hold_requests(boolean wait_for_completion)
    throws org.omg.PortableServer.POAManagerPackage.AdapterInactive;

  void discard_requests(boolean wait_for_completion)
    throws org.omg.PortableServer.POAManagerPackage.AdapterInactive;

  void deactivate(boolean etherealize_objects, boolean wait_for_completion)
    throws org.omg.PortableServer.POAManagerPackage.AdapterInactive;

  org.omg.PortableServer.POAManagerPackage.State get_state();


}
