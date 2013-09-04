//
// NotEmpty.java (exception)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming.NamingContextPackage;

final public class NotEmpty
   extends org.omg.CORBA.UserException {


  public NotEmpty() {
    super(NotEmptyHelper.id());
  }

  public NotEmpty(String reason) {
    super(NotEmptyHelper.id()+" "+reason);

  }

}
