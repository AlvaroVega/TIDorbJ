//
// _CodeBaseStub.java (stub)
//
// File generated: Thu May 19 07:31:40 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SendingContext;

public class _CodeBaseStub
 extends org.omg.CORBA.portable.ObjectImpl
 implements CodeBase {

  public java.lang.String[] _ids() {
    return __ids;
  }

  private static java.lang.String[] __ids = {
    "IDL:omg.org/SendingContext/CodeBase:1.0",
    "IDL:omg.org/SendingContext/RunTime:1.0"  };

  public org.omg.CORBA.Repository get_ir() {
    org.omg.CORBA.Request _request = this._request("get_ir");
    _request.set_return_type(org.omg.CORBA.RepositoryHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.Repository _result;
    _result = org.omg.CORBA.RepositoryHelper.extract(_request.return_value());
    return _result;
  }

  public java.lang.String implementation(java.lang.String x) {
    org.omg.CORBA.Request _request = this._request("implementation");
    _request.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
    org.omg.CORBA.Any $x = _request.add_named_in_arg("x");
    $x.insert_string(x);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    java.lang.String _result;
    _result = _request.return_value().extract_string();
    return _result;
  }

  public java.lang.String[] implementations(java.lang.String[] x) {
    org.omg.CORBA.Request _request = this._request("implementations");
    _request.set_return_type(org.omg.SendingContext.CodeBasePackage.URLSeqHelper.type());
    org.omg.CORBA.Any $x = _request.add_named_in_arg("x");
    org.omg.CORBA.RepositoryIdSeqHelper.insert($x,x);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    java.lang.String[] _result;
    _result = org.omg.SendingContext.CodeBasePackage.URLSeqHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.ValueDefPackage.FullValueDescription meta(java.lang.String x) {
    org.omg.CORBA.Request _request = this._request("meta");
    _request.set_return_type(org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper.type());
    org.omg.CORBA.Any $x = _request.add_named_in_arg("x");
    $x.insert_string(x);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.ValueDefPackage.FullValueDescription _result;
    _result = org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.ValueDefPackage.FullValueDescription[] metas(java.lang.String[] x) {
    org.omg.CORBA.Request _request = this._request("metas");
    _request.set_return_type(org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper.type());
    org.omg.CORBA.Any $x = _request.add_named_in_arg("x");
    org.omg.CORBA.RepositoryIdSeqHelper.insert($x,x);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.ValueDefPackage.FullValueDescription[] _result;
    _result = org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper.extract(_request.return_value());
    return _result;
  }

  public java.lang.String[] bases(java.lang.String x) {
    org.omg.CORBA.Request _request = this._request("bases");
    _request.set_return_type(org.omg.CORBA.RepositoryIdSeqHelper.type());
    org.omg.CORBA.Any $x = _request.add_named_in_arg("x");
    $x.insert_string(x);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    java.lang.String[] _result;
    _result = org.omg.CORBA.RepositoryIdSeqHelper.extract(_request.return_value());
    return _result;
  }



}
