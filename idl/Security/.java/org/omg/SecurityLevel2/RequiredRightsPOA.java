//
// RequiredRightsPOA.java (skeleton)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

abstract public class RequiredRightsPOA
 extends org.omg.PortableServer.DynamicImplementation
 implements RequiredRightsOperations {

  public RequiredRights _this() {
    return RequiredRightsHelper.narrow(super._this_object());
  };

  public RequiredRights _this(org.omg.CORBA.ORB orb) {
    return RequiredRightsHelper.narrow(super._this_object(orb));
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/SecurityLevel2/RequiredRights:1.0"
  };

  private static java.util.Dictionary _methods = new java.util.Hashtable();
  static {
    _methods.put("get_required_rights", new Integer(0));
    _methods.put("set_required_rights", new Integer(1));
  }

  public void invoke(org.omg.CORBA.ServerRequest _request) {
    java.lang.Object _method = _methods.get(_request.operation());
    if (_method == null) {
      throw new org.omg.CORBA.BAD_OPERATION(_request.operation());
    }
    int _method_id = ((java.lang.Integer)_method).intValue();
    switch(_method_id) {
    case 0: {
      org.omg.CORBA.NVList _params = _orb().create_list(5);
      org.omg.CORBA.Any $obj = _orb().create_any();
      $obj.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_objref));
      _params.add_value("obj", $obj, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $operation_name = _orb().create_any();
      $operation_name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("operation_name", $operation_name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $interface_name = _orb().create_any();
      $interface_name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("interface_name", $interface_name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $rights = _orb().create_any();
      $rights.type(org.omg.Security.RightsListHelper.type());
      _params.add_value("rights", $rights, org.omg.CORBA.ARG_OUT.value);
      org.omg.CORBA.Any $rights_combinator = _orb().create_any();
      $rights_combinator.type(org.omg.Security.RightsCombinatorHelper.type());
      _params.add_value("rights_combinator", $rights_combinator, org.omg.CORBA.ARG_OUT.value);
      _request.arguments(_params);
      org.omg.CORBA.Object obj;
      obj = $obj.extract_Object();
      java.lang.String operation_name;
      operation_name = $operation_name.extract_string();
      java.lang.String interface_name;
      interface_name = $interface_name.extract_string();
      org.omg.Security.RightsListHolder rights = new org.omg.Security.RightsListHolder();
      org.omg.Security.RightsCombinatorHolder rights_combinator = new org.omg.Security.RightsCombinatorHolder();
      this.get_required_rights(obj, operation_name, interface_name, rights, rights_combinator);
      org.omg.Security.RightsListHelper.insert($rights,rights.value);
      org.omg.Security.RightsCombinatorHelper.insert($rights_combinator,rights_combinator.value);
      return;
    }
    case 1: {
      org.omg.CORBA.NVList _params = _orb().create_list(4);
      org.omg.CORBA.Any $operation_name = _orb().create_any();
      $operation_name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("operation_name", $operation_name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $interface_name = _orb().create_any();
      $interface_name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("interface_name", $interface_name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $rights = _orb().create_any();
      $rights.type(org.omg.Security.RightsListHelper.type());
      _params.add_value("rights", $rights, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $rights_combinator = _orb().create_any();
      $rights_combinator.type(org.omg.Security.RightsCombinatorHelper.type());
      _params.add_value("rights_combinator", $rights_combinator, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String operation_name;
      operation_name = $operation_name.extract_string();
      java.lang.String interface_name;
      interface_name = $interface_name.extract_string();
      org.omg.Security.Right[] rights;
      rights = org.omg.Security.RightsListHelper.extract($rights);
      org.omg.Security.RightsCombinator rights_combinator;
      rights_combinator = org.omg.Security.RightsCombinatorHelper.extract($rights_combinator);
      this.set_required_rights(operation_name, interface_name, rights, rights_combinator);
      return;
    }
    }
  }
}
