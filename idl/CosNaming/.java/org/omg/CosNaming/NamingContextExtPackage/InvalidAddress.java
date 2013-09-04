//
// InvalidAddress.java (exception)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming.NamingContextExtPackage;

final public class InvalidAddress
   extends org.omg.CORBA.UserException {


  public InvalidAddress() {
    super(InvalidAddressHelper.id());
  }

  public InvalidAddress(String reason) {
    super(InvalidAddressHelper.id()+" "+reason);

  }

}
