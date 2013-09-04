//
// _ArrayDefStub.java (stub)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class _ArrayDefStub
 extends org.omg.CORBA.portable.ObjectImpl
 implements ArrayDef {

  public java.lang.String[] _ids() {
    return __ids;
  }

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/ArrayDef:1.0",
    "IDL:omg.org/CORBA/IDLType:1.0",
    "IDL:omg.org/CORBA/IRObject:1.0"  };

  public int length() {
    org.omg.CORBA.Request _request = this._request("_get_length");
    _request.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong));
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    int _result;
    _result = _request.return_value().extract_ulong();
    return _result;  }

  public void length(int value) {
    org.omg.CORBA.Request _request = this._request("_set_length");
    org.omg.CORBA.Any $value = _request.add_in_arg();
    $value.insert_ulong(value);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public org.omg.CORBA.TypeCode element_type() {
    org.omg.CORBA.Request _request = this._request("_get_element_type");
    _request.set_return_type(org.omg.CORBA.TypeCodeHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.TypeCode _result;
    _result = org.omg.CORBA.TypeCodeHelper.extract(_request.return_value());
    return _result;  }

  public org.omg.CORBA.IDLType element_type_def() {
    org.omg.CORBA.Request _request = this._request("_get_element_type_def");
    _request.set_return_type(org.omg.CORBA.IDLTypeHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.IDLType _result;
    _result = org.omg.CORBA.IDLTypeHelper.extract(_request.return_value());
    return _result;  }

  public void element_type_def(org.omg.CORBA.IDLType value) {
    org.omg.CORBA.Request _request = this._request("_set_element_type_def");
    org.omg.CORBA.Any $value = _request.add_in_arg();
    org.omg.CORBA.IDLTypeHelper.insert($value,value);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public org.omg.CORBA.TypeCode type() {
    org.omg.CORBA.Request _request = this._request("_get_type");
    _request.set_return_type(org.omg.CORBA.TypeCodeHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.TypeCode _result;
    _result = org.omg.CORBA.TypeCodeHelper.extract(_request.return_value());
    return _result;  }

  public org.omg.CORBA.DefinitionKind def_kind() {
    org.omg.CORBA.Request _request = this._request("_get_def_kind");
    _request.set_return_type(org.omg.CORBA.DefinitionKindHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.DefinitionKind _result;
    _result = org.omg.CORBA.DefinitionKindHelper.extract(_request.return_value());
    return _result;  }

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
