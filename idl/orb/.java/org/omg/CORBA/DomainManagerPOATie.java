//
// DomainManagerPOATie.java (tie)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class DomainManagerPOATie
 extends DomainManagerPOA
 implements DomainManagerOperations {

  private DomainManagerOperations _delegate;
  public DomainManagerPOATie(DomainManagerOperations delegate) {
    this._delegate = delegate;
  };

  public DomainManagerOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/DomainManager:1.0"  };

  public org.omg.CORBA.Policy get_domain_policy(int policy_type) {
    return this._delegate.get_domain_policy(
    policy_type
    );
  };


}
