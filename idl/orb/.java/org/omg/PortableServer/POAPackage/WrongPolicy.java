//
// WrongPolicy.java (exception)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer.POAPackage;

final public class WrongPolicy
   extends org.omg.CORBA.UserException {


  public WrongPolicy() {
    super(WrongPolicyHelper.id());
  }

  public WrongPolicy(String reason) {
    super(WrongPolicyHelper.id()+" "+reason);

  }

}
