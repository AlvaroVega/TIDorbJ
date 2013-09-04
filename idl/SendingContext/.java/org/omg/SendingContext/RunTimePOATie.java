//
// RunTimePOATie.java (tie)
//
// File generated: Thu May 19 07:31:40 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SendingContext;

public class RunTimePOATie
 extends RunTimePOA
 implements RunTimeOperations {

  private RunTimeOperations _delegate;
  public RunTimePOATie(RunTimeOperations delegate) {
    this._delegate = delegate;
  };

  public RunTimeOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/SendingContext/RunTime:1.0"  };


}
