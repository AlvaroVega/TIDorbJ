//
// _RequiredRightsStub.java (stub)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public class _RequiredRightsStub
 extends org.omg.CORBA.portable.ObjectImpl
 implements RequiredRights {

  public java.lang.String[] _ids() {
    return __ids;
  }

  private static java.lang.String[] __ids = {
    "IDL:omg.org/SecurityLevel2/RequiredRights:1.0"  };

  public void get_required_rights(org.omg.CORBA.Object obj, java.lang.String operation_name, java.lang.String interface_name, org.omg.Security.RightsListHolder rights, org.omg.Security.RightsCombinatorHolder rights_combinator) {
    org.omg.CORBA.Request _request = this._request("get_required_rights");
    org.omg.CORBA.Any $obj = _request.add_named_in_arg("obj");
    $obj.insert_Object(obj);
    org.omg.CORBA.Any $operation_name = _request.add_named_in_arg("operation_name");
    $operation_name.insert_string(operation_name);
    org.omg.CORBA.Any $interface_name = _request.add_named_in_arg("interface_name");
    $interface_name.insert_string(interface_name);
    org.omg.CORBA.Any $rights = _request.add_named_out_arg("rights");
    $rights.type(org.omg.Security.RightsListHelper.type());
    org.omg.CORBA.Any $rights_combinator = _request.add_named_out_arg("rights_combinator");
    $rights_combinator.type(org.omg.Security.RightsCombinatorHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    rights.value = org.omg.Security.RightsListHelper.extract($rights);
    rights_combinator.value = org.omg.Security.RightsCombinatorHelper.extract($rights_combinator);
  }

  public void set_required_rights(java.lang.String operation_name, java.lang.String interface_name, org.omg.Security.Right[] rights, org.omg.Security.RightsCombinator rights_combinator) {
    org.omg.CORBA.Request _request = this._request("set_required_rights");
    org.omg.CORBA.Any $operation_name = _request.add_named_in_arg("operation_name");
    $operation_name.insert_string(operation_name);
    org.omg.CORBA.Any $interface_name = _request.add_named_in_arg("interface_name");
    $interface_name.insert_string(interface_name);
    org.omg.CORBA.Any $rights = _request.add_named_in_arg("rights");
    org.omg.Security.RightsListHelper.insert($rights,rights);
    org.omg.CORBA.Any $rights_combinator = _request.add_named_in_arg("rights_combinator");
    org.omg.Security.RightsCombinatorHelper.insert($rights_combinator,rights_combinator);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }


}
