//
// _ConstructionPolicyStub.java (stub)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class _ConstructionPolicyStub
 extends org.omg.CORBA.portable.ObjectImpl
 implements ConstructionPolicy {

  public java.lang.String[] _ids() {
    return __ids;
  }

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/ConstructionPolicy:1.0",
    "IDL:omg.org/CORBA/Policy:1.0"  };

  public void make_domain_manager(org.omg.CORBA.InterfaceDef object_type, boolean constr_policy) {
    org.omg.CORBA.Request _request = this._request("make_domain_manager");
    org.omg.CORBA.Any $object_type = _request.add_named_in_arg("object_type");
    org.omg.CORBA.InterfaceDefHelper.insert($object_type,object_type);
    org.omg.CORBA.Any $constr_policy = _request.add_named_in_arg("constr_policy");
    $constr_policy.insert_boolean(constr_policy);
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

  public int policy_type() {
    org.omg.CORBA.Request _request = this._request("_get_policy_type");
    _request.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong));
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    int _result;
    _result = _request.return_value().extract_ulong();
    return _result;  }

  public org.omg.CORBA.Policy copy() {
    org.omg.CORBA.Request _request = this._request("copy");
    _request.set_return_type(org.omg.CORBA.PolicyHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.Policy _result;
    _result = org.omg.CORBA.PolicyHelper.extract(_request.return_value());
    return _result;
  }

  public void destroy() {
    org.omg.CORBA.Request _request = this._request("destroy");
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
