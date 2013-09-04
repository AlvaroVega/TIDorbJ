//
// AdapterInactive.java (exception)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer.POAManagerPackage;

final public class AdapterInactive
   extends org.omg.CORBA.UserException {


  public AdapterInactive() {
    super(AdapterInactiveHelper.id());
  }

  public AdapterInactive(String reason) {
    super(AdapterInactiveHelper.id()+" "+reason);

  }

}
