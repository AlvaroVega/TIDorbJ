//
// ObjectAlreadyActive.java (exception)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer.POAPackage;

final public class ObjectAlreadyActive
   extends org.omg.CORBA.UserException {


  public ObjectAlreadyActive() {
    super(ObjectAlreadyActiveHelper.id());
  }

  public ObjectAlreadyActive(String reason) {
    super(ObjectAlreadyActiveHelper.id()+" "+reason);

  }

}
