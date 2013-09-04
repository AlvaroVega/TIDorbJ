//
// _DomainManagerStub.java (stub)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class _DomainManagerStub
 extends org.omg.CORBA.portable.ObjectImpl
 implements DomainManager {

  public java.lang.String[] _ids() {
    return __ids;
  }

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/DomainManager:1.0"  };

  public org.omg.CORBA.Policy get_domain_policy(int policy_type) {
    org.omg.CORBA.Request _request = this._request("get_domain_policy");
    _request.set_return_type(org.omg.CORBA.PolicyHelper.type());
    org.omg.CORBA.Any $policy_type = _request.add_named_in_arg("policy_type");
    $policy_type.insert_ulong(policy_type);
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


}
