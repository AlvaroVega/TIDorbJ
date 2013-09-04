//
// PolicyPOA.java (skeleton)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class PolicyPOA
 extends org.omg.PortableServer.DynamicImplementation
 implements PolicyOperations {

  public Policy _this() {
    return PolicyHelper.narrow(super._this_object());
  };

  public Policy _this(org.omg.CORBA.ORB orb) {
    return PolicyHelper.narrow(super._this_object(orb));
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/Policy:1.0"
  };

  private static java.util.Dictionary _methods = new java.util.Hashtable();
  static {
    _methods.put("_get_policy_type", new Integer(0));
    _methods.put("copy", new Integer(1));
    _methods.put("destroy", new Integer(2));
  }

  public void invoke(org.omg.CORBA.ServerRequest _request) {
    java.lang.Object _method = _methods.get(_request.operation());
    if (_method == null) {
      throw new org.omg.CORBA.BAD_OPERATION(_request.operation());
    }
    int _method_id = ((java.lang.Integer)_method).intValue();
    switch(_method_id) {
    case 0: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      int _result = this.policy_type();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_ulong(_result);
      _request.set_result(_resultAny);
      return;
    }
    case 1: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.Policy _result = this.copy();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.PolicyHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 2: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      this.destroy();
      return;
    }
    }
  }
}
