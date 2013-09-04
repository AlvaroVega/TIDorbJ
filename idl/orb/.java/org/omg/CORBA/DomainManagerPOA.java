//
// DomainManagerPOA.java (skeleton)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class DomainManagerPOA
 extends org.omg.PortableServer.DynamicImplementation
 implements DomainManagerOperations {

  public DomainManager _this() {
    return DomainManagerHelper.narrow(super._this_object());
  };

  public DomainManager _this(org.omg.CORBA.ORB orb) {
    return DomainManagerHelper.narrow(super._this_object(orb));
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/DomainManager:1.0"
  };

  private static java.util.Dictionary _methods = new java.util.Hashtable();
  static {
    _methods.put("get_domain_policy", new Integer(0));
  }

  public void invoke(org.omg.CORBA.ServerRequest _request) {
    java.lang.Object _method = _methods.get(_request.operation());
    if (_method == null) {
      throw new org.omg.CORBA.BAD_OPERATION(_request.operation());
    }
    int _method_id = ((java.lang.Integer)_method).intValue();
    switch(_method_id) {
    case 0: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $policy_type = _orb().create_any();
      $policy_type.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong));
      _params.add_value("policy_type", $policy_type, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      int policy_type;
      policy_type = $policy_type.extract_ulong();
      org.omg.CORBA.Policy _result = this.get_domain_policy(policy_type);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.PolicyHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    }
  }
}
