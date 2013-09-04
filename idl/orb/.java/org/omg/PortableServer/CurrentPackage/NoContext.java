//
// NoContext.java (exception)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer.CurrentPackage;

final public class NoContext
   extends org.omg.CORBA.UserException {


  public NoContext() {
    super(NoContextHelper.id());
  }

  public NoContext(String reason) {
    super(NoContextHelper.id()+" "+reason);

  }

}
