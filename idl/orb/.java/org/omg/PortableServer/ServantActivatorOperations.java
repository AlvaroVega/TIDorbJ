//
// ServantActivator.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public interface ServantActivatorOperations
   extends org.omg.PortableServer.ServantManagerOperations {

  org.omg.PortableServer.Servant incarnate(byte[] oid, org.omg.PortableServer.POA adapter)
    throws org.omg.PortableServer.ForwardRequest;

  void etherealize(byte[] oid, org.omg.PortableServer.POA adapter, org.omg.PortableServer.Servant serv, boolean cleanup_in_progress, boolean remaining_activations);


}
