//
// Current.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public interface CurrentOperations
   extends org.omg.CORBA.CurrentOperations {

  org.omg.PortableServer.POA get_POA()
    throws org.omg.PortableServer.CurrentPackage.NoContext;

  byte[] get_object_id()
    throws org.omg.PortableServer.CurrentPackage.NoContext;

  org.omg.CORBA.Object get_reference()
    throws org.omg.PortableServer.CurrentPackage.NoContext;

  org.omg.PortableServer.Servant get_servant()
    throws org.omg.PortableServer.CurrentPackage.NoContext;


}
