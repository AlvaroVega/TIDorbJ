//
// CurrentPOATie.java (tie)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class CurrentPOATie
 extends CurrentPOA
 implements CurrentOperations {

  private CurrentOperations _delegate;
  public CurrentPOATie(CurrentOperations delegate) {
    this._delegate = delegate;
  };

  public CurrentOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/Current:1.0"  };


}
