//
// InvalidName.java (exception)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming.NamingContextPackage;

final public class InvalidName
   extends org.omg.CORBA.UserException {


  public InvalidName() {
    super(InvalidNameHelper.id());
  }

  public InvalidName(String reason) {
    super(InvalidNameHelper.id()+" "+reason);

  }

}
