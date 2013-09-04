//
// BidirectionalPolicyPOATie.java (tie)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.BiDirPolicy;

public class BidirectionalPolicyPOATie
 extends BidirectionalPolicyPOA
 implements BidirectionalPolicyOperations {

  private BidirectionalPolicyOperations _delegate;
  public BidirectionalPolicyPOATie(BidirectionalPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public BidirectionalPolicyOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/BiDirPolicy/BidirectionalPolicy:1.0",
    "IDL:omg.org/CORBA/Policy:1.0"  };

  public short value() {
    return this._delegate.value();
  }

  public int policy_type() {
    return this._delegate.policy_type();
  }

  public org.omg.CORBA.Policy copy() {
    return this._delegate.copy(
    );
  };

  public void destroy() {
    this._delegate.destroy(
    );
  };



}
