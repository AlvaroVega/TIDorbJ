//
// RunTimePOA.java (skeleton)
//
// File generated: Thu May 19 07:31:40 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SendingContext;

abstract public class RunTimePOA
 extends org.omg.PortableServer.DynamicImplementation
 implements RunTimeOperations {

  public RunTime _this() {
    return RunTimeHelper.narrow(super._this_object());
  };

  public RunTime _this(org.omg.CORBA.ORB orb) {
    return RunTimeHelper.narrow(super._this_object(orb));
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/SendingContext/RunTime:1.0"
  };

  private static java.util.Dictionary _methods = new java.util.Hashtable();
  static {
  }

  public void invoke(org.omg.CORBA.ServerRequest _request) {
    java.lang.Object _method = _methods.get(_request.operation());
    if (_method == null) {
      throw new org.omg.CORBA.BAD_OPERATION(_request.operation());
    }
    int _method_id = ((java.lang.Integer)_method).intValue();
    switch(_method_id) {
    }
  }
}
