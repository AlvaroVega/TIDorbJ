//
// _NamingContextExtStub.java (stub)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming;

public class _NamingContextExtStub
 extends org.omg.CORBA.portable.ObjectImpl
 implements NamingContextExt {

  public java.lang.String[] _ids() {
    return __ids;
  }

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CosNaming/NamingContextExt:1.0",
    "IDL:omg.org/CosNaming/NamingContext:1.0"  };

  public java.lang.String to_string(org.omg.CosNaming.NameComponent[] n)
    throws org.omg.CosNaming.NamingContextPackage.InvalidName {
    org.omg.CORBA.Request _request = this._request("to_string");
    _request.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
    org.omg.CORBA.Any $n = _request.add_named_in_arg("n");
    org.omg.CosNaming.NameHelper.insert($n,n);
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    java.lang.String _result;
    _result = _request.return_value().extract_string();
    return _result;
  }

  public org.omg.CosNaming.NameComponent[] to_name(java.lang.String sn)
    throws org.omg.CosNaming.NamingContextPackage.InvalidName {
    org.omg.CORBA.Request _request = this._request("to_name");
    _request.set_return_type(org.omg.CosNaming.NameHelper.type());
    org.omg.CORBA.Any $sn = _request.add_named_in_arg("sn");
    $sn.insert_string(sn);
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CosNaming.NameComponent[] _result;
    _result = org.omg.CosNaming.NameHelper.extract(_request.return_value());
    return _result;
  }

  public java.lang.String to_url(java.lang.String addr, java.lang.String sn)
    throws org.omg.CosNaming.NamingContextExtPackage.InvalidAddress, org.omg.CosNaming.NamingContextPackage.InvalidName {
    org.omg.CORBA.Request _request = this._request("to_url");
    _request.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
    org.omg.CORBA.Any $addr = _request.add_named_in_arg("addr");
    $addr.insert_string(addr);
    org.omg.CORBA.Any $sn = _request.add_named_in_arg("sn");
    $sn.insert_string(sn);
    _request.exceptions().add(org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper.type())) {
          throw org.omg.CosNaming.NamingContextExtPackage.InvalidAddressHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    java.lang.String _result;
    _result = _request.return_value().extract_string();
    return _result;
  }

  public org.omg.CORBA.Object resolve_str(java.lang.String sn)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
    org.omg.CORBA.Request _request = this._request("resolve_str");
    _request.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_objref));
    org.omg.CORBA.Any $sn = _request.add_named_in_arg("sn");
    $sn.insert_string(sn);
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.Object _result;
    _result = _request.return_value().extract_Object();
    return _result;
  }

  public void bind(org.omg.CosNaming.NameComponent[] n, org.omg.CORBA.Object obj)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, org.omg.CosNaming.NamingContextPackage.AlreadyBound {
    org.omg.CORBA.Request _request = this._request("bind");
    org.omg.CORBA.Any $n = _request.add_named_in_arg("n");
    org.omg.CosNaming.NameHelper.insert($n,n);
    org.omg.CORBA.Any $obj = _request.add_named_in_arg("obj");
    $obj.insert_Object(obj);
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public void rebind(org.omg.CosNaming.NameComponent[] n, org.omg.CORBA.Object obj)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
    org.omg.CORBA.Request _request = this._request("rebind");
    org.omg.CORBA.Any $n = _request.add_named_in_arg("n");
    org.omg.CosNaming.NameHelper.insert($n,n);
    org.omg.CORBA.Any $obj = _request.add_named_in_arg("obj");
    $obj.insert_Object(obj);
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public void bind_context(org.omg.CosNaming.NameComponent[] n, org.omg.CosNaming.NamingContext nc)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, org.omg.CosNaming.NamingContextPackage.AlreadyBound {
    org.omg.CORBA.Request _request = this._request("bind_context");
    org.omg.CORBA.Any $n = _request.add_named_in_arg("n");
    org.omg.CosNaming.NameHelper.insert($n,n);
    org.omg.CORBA.Any $nc = _request.add_named_in_arg("nc");
    org.omg.CosNaming.NamingContextHelper.insert($nc,nc);
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public void rebind_context(org.omg.CosNaming.NameComponent[] n, org.omg.CosNaming.NamingContext nc)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
    org.omg.CORBA.Request _request = this._request("rebind_context");
    org.omg.CORBA.Any $n = _request.add_named_in_arg("n");
    org.omg.CosNaming.NameHelper.insert($n,n);
    org.omg.CORBA.Any $nc = _request.add_named_in_arg("nc");
    org.omg.CosNaming.NamingContextHelper.insert($nc,nc);
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public org.omg.CORBA.Object resolve(org.omg.CosNaming.NameComponent[] n)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
    org.omg.CORBA.Request _request = this._request("resolve");
    _request.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_objref));
    org.omg.CORBA.Any $n = _request.add_named_in_arg("n");
    org.omg.CosNaming.NameHelper.insert($n,n);
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.Object _result;
    _result = _request.return_value().extract_Object();
    return _result;
  }

  public void unbind(org.omg.CosNaming.NameComponent[] n)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
    org.omg.CORBA.Request _request = this._request("unbind");
    org.omg.CORBA.Any $n = _request.add_named_in_arg("n");
    org.omg.CosNaming.NameHelper.insert($n,n);
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public org.omg.CosNaming.NamingContext new_context() {
    org.omg.CORBA.Request _request = this._request("new_context");
    _request.set_return_type(org.omg.CosNaming.NamingContextHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CosNaming.NamingContext _result;
    _result = org.omg.CosNaming.NamingContextHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CosNaming.NamingContext bind_new_context(org.omg.CosNaming.NameComponent[] n)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.AlreadyBound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
    org.omg.CORBA.Request _request = this._request("bind_new_context");
    _request.set_return_type(org.omg.CosNaming.NamingContextHelper.type());
    org.omg.CORBA.Any $n = _request.add_named_in_arg("n");
    org.omg.CosNaming.NameHelper.insert($n,n);
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type());
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.NotFoundHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.NotFoundHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.extract(_userException.except);
        }
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CosNaming.NamingContext _result;
    _result = org.omg.CosNaming.NamingContextHelper.extract(_request.return_value());
    return _result;
  }

  public void destroy()
    throws org.omg.CosNaming.NamingContextPackage.NotEmpty {
    org.omg.CORBA.Request _request = this._request("destroy");
    _request.exceptions().add(org.omg.CosNaming.NamingContextPackage.NotEmptyHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
        if (_userException.except.type().equal(org.omg.CosNaming.NamingContextPackage.NotEmptyHelper.type())) {
          throw org.omg.CosNaming.NamingContextPackage.NotEmptyHelper.extract(_userException.except);
        }
        throw new org.omg.CORBA.UNKNOWN();
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }

  public void list(int how_many, org.omg.CosNaming.BindingListHolder bl, org.omg.CosNaming.BindingIteratorHolder bi) {
    org.omg.CORBA.Request _request = this._request("list");
    org.omg.CORBA.Any $how_many = _request.add_named_in_arg("how_many");
    $how_many.insert_ulong(how_many);
    org.omg.CORBA.Any $bl = _request.add_named_out_arg("bl");
    $bl.type(org.omg.CosNaming.BindingListHelper.type());
    org.omg.CORBA.Any $bi = _request.add_named_out_arg("bi");
    $bi.type(org.omg.CosNaming.BindingIteratorHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    bl.value = org.omg.CosNaming.BindingListHelper.extract($bl);
    bi.value = org.omg.CosNaming.BindingIteratorHelper.extract($bi);
  }



}
