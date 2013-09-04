//
// _NativeDefStub.java (stub)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class _NativeDefStub
 extends org.omg.CORBA.portable.ObjectImpl
 implements NativeDef {

  public java.lang.String[] _ids() {
    return __ids;
  }

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/NativeDef:1.0",
    "IDL:omg.org/CORBA/TypedefDef:1.0",
    "IDL:omg.org/CORBA/Contained:1.0",
    "IDL:omg.org/CORBA/IRObject:1.0",
    "IDL:omg.org/CORBA/IDLType:1.0"  };

  public java.lang.String id() {
    org.omg.CORBA.Request _request = this._request("_get_id");
    _request.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    java.lang.String _result;
    _result = _request.return_value().extract_string();
    return _result;  }

  public void id(java.lang.String value) {
    org.omg.CORBA.Request _request = this._request("_set_id");
    org.omg.CORBA.Any $value = _request.add_in_arg();
    $value.insert_string(value);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public java.lang.String name() {
    org.omg.CORBA.Request _request = this._request("_get_name");
    _request.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    java.lang.String _result;
    _result = _request.return_value().extract_string();
    return _result;  }

  public void name(java.lang.String value) {
    org.omg.CORBA.Request _request = this._request("_set_name");
    org.omg.CORBA.Any $value = _request.add_in_arg();
    $value.insert_string(value);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public java.lang.String version() {
    org.omg.CORBA.Request _request = this._request("_get_version");
    _request.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    java.lang.String _result;
    _result = _request.return_value().extract_string();
    return _result;  }

  public void version(java.lang.String value) {
    org.omg.CORBA.Request _request = this._request("_set_version");
    org.omg.CORBA.Any $value = _request.add_in_arg();
    $value.insert_string(value);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public org.omg.CORBA.Container defined_in() {
    org.omg.CORBA.Request _request = this._request("_get_defined_in");
    _request.set_return_type(org.omg.CORBA.ContainerHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.Container _result;
    _result = org.omg.CORBA.ContainerHelper.extract(_request.return_value());
    return _result;  }

  public java.lang.String absolute_name() {
    org.omg.CORBA.Request _request = this._request("_get_absolute_name");
    _request.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    java.lang.String _result;
    _result = _request.return_value().extract_string();
    return _result;  }

  public org.omg.CORBA.Repository containing_repository() {
    org.omg.CORBA.Request _request = this._request("_get_containing_repository");
    _request.set_return_type(org.omg.CORBA.RepositoryHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.Repository _result;
    _result = org.omg.CORBA.RepositoryHelper.extract(_request.return_value());
    return _result;  }

  public org.omg.CORBA.ContainedPackage.Description describe() {
    org.omg.CORBA.Request _request = this._request("describe");
    _request.set_return_type(org.omg.CORBA.ContainedPackage.DescriptionHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.ContainedPackage.Description _result;
    _result = org.omg.CORBA.ContainedPackage.DescriptionHelper.extract(_request.return_value());
    return _result;
  }

  public void move(org.omg.CORBA.Container new_container, java.lang.String new_name, java.lang.String new_version) {
    org.omg.CORBA.Request _request = this._request("move");
    org.omg.CORBA.Any $new_container = _request.add_named_in_arg("new_container");
    org.omg.CORBA.ContainerHelper.insert($new_container,new_container);
    org.omg.CORBA.Any $new_name = _request.add_named_in_arg("new_name");
    $new_name.insert_string(new_name);
    org.omg.CORBA.Any $new_version = _request.add_named_in_arg("new_version");
    $new_version.insert_string(new_version);
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




}
