//
// FactoryAlreadyRegistered.java (exception)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

final public class FactoryAlreadyRegistered
   extends org.omg.CORBA.UserException {


  public FactoryAlreadyRegistered() {
    super(FactoryAlreadyRegisteredHelper.id());
  }

  public FactoryAlreadyRegistered(String reason) {
    super(FactoryAlreadyRegisteredHelper.id()+" "+reason);

  }

}
