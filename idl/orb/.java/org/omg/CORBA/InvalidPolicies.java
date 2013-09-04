//
// InvalidPolicies.java (exception)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class InvalidPolicies
   extends org.omg.CORBA.UserException {

  public short[] indices;

  public InvalidPolicies() {
    super(InvalidPoliciesHelper.id());
  }

  public InvalidPolicies(short[] _indices) {
    super(InvalidPoliciesHelper.id());

    this.indices = _indices;
  }

  public InvalidPolicies(String reason, short[] _indices) {
    super(InvalidPoliciesHelper.id()+" "+reason);

    this.indices = _indices;
  }

}
