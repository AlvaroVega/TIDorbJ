//
// ConstructionPolicyPOA.java (skeleton)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class ConstructionPolicyPOA
 extends org.omg.PortableServer.DynamicImplementation
 implements ConstructionPolicyOperations {

  public ConstructionPolicy _this() {
    return ConstructionPolicyHelper.narrow(super._this_object());
  };

  public ConstructionPolicy _this(org.omg.CORBA.ORB orb) {
    return ConstructionPolicyHelper.narrow(super._this_object(orb));
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/ConstructionPolicy:1.0",
    "IDL:omg.org/CORBA/Policy:1.0"
  };

  private static java.util.Dictionary _methods = new java.util.Hashtable();
  static {
    _methods.put("make_domain_manager", new Integer(0));
    _methods.put("_get_policy_type", new Integer(1));
    _methods.put("copy", new Integer(2));
    _methods.put("destroy", new Integer(3));
  }

  public void invoke(org.omg.CORBA.ServerRequest _request) {
    java.lang.Object _method = _methods.get(_request.operation());
    if (_method == null) {
      throw new org.omg.CORBA.BAD_OPERATION(_request.operation());
    }
    int _method_id = ((java.lang.Integer)_method).intValue();
    switch(_method_id) {
    case 0: {
      org.omg.CORBA.NVList _params = _orb().create_list(2);
      org.omg.CORBA.Any $object_type = _orb().create_any();
      $object_type.type(org.omg.CORBA.InterfaceDefHelper.type());
      _params.add_value("object_type", $object_type, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $constr_policy = _orb().create_any();
      $constr_policy.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean));
      _params.add_value("constr_policy", $constr_policy, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      org.omg.CORBA.InterfaceDef object_type;
      object_type = org.omg.CORBA.InterfaceDefHelper.extract($object_type);
      boolean constr_policy;
      constr_policy = $constr_policy.extract_boolean();
      this.make_domain_manager(object_type, constr_policy);
      return;
    }
    case 1: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      int _result = this.policy_type();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_ulong(_result);
      _request.set_result(_resultAny);
      return;
    }
    case 2: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.Policy _result = this.copy();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.PolicyHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 3: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      this.destroy();
      return;
    }
    }
  }
}
