//
// InvalidPolicy.java (exception)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer.POAPackage;

final public class InvalidPolicy
   extends org.omg.CORBA.UserException {

  public short index;

  public InvalidPolicy() {
    super(InvalidPolicyHelper.id());
  }

  public InvalidPolicy(short _index) {
    super(InvalidPolicyHelper.id());

    this.index = _index;
  }

  public InvalidPolicy(String reason, short _index) {
    super(InvalidPolicyHelper.id()+" "+reason);

    this.index = _index;
  }

}
