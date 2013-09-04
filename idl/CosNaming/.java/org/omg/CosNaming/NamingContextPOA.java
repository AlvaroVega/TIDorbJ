//
// NamingContextPOA.java (skeleton)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming;

abstract public class NamingContextPOA
 extends org.omg.PortableServer.DynamicImplementation
 implements NamingContextOperations {

  public NamingContext _this() {
    return NamingContextHelper.narrow(super._this_object());
  };

  public NamingContext _this(org.omg.CORBA.ORB orb) {
    return NamingContextHelper.narrow(super._this_object(orb));
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CosNaming/NamingContext:1.0"
  };

  private static java.util.Dictionary _methods = new java.util.Hashtable();
  static {
    _methods.put("bind", new Integer(0));
    _methods.put("rebind", new Integer(1));
    _methods.put("bind_context", new Integer(2));
    _methods.put("rebind_context", new Integer(3));
    _methods.put("resolve", new Integer(4));
    _methods.put("unbind", new Integer(5));
    _methods.put("new_context", new Integer(6));
    _methods.put("bind_new_context", new Integer(7));
    _methods.put("destroy", new Integer(8));
    _methods.put("list", new Integer(9));
  }

  public void invoke(org.omg.CORBA.ServerRequest _request) {
    java.lang.Object _method = _methods.get(_request.operation());
    if (_method == null) {
      throw new org.omg.CORBA.BAD_OPERATION(_request.operation());
    }
    int _method_id = ((java.lang.Integer)_method).intValue();
    switch(_method_id) {
    case 0: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(2);
      org.omg.CORBA.Any $n = _orb().create_any();
      $n.type(org.omg.CosNaming.NameHelper.type());
      _params.add_value("n", $n, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $obj = _orb().create_any();
      $obj.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_objref));
      _params.add_value("obj", $obj, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      org.omg.CosNaming.NameComponent[] n;
      n = org.omg.CosNaming.NameHelper.extract($n);
      org.omg.CORBA.Object obj;
      obj = $obj.extract_Object();
      this.bind(n, obj);
      } catch(org.omg.CosNaming.NamingContextPackage.NotFound _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.InvalidName _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.AlreadyBound _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 1: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(2);
      org.omg.CORBA.Any $n = _orb().create_any();
      $n.type(org.omg.CosNaming.NameHelper.type());
      _params.add_value("n", $n, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $obj = _orb().create_any();
      $obj.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_objref));
      _params.add_value("obj", $obj, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      org.omg.CosNaming.NameComponent[] n;
      n = org.omg.CosNaming.NameHelper.extract($n);
      org.omg.CORBA.Object obj;
      obj = $obj.extract_Object();
      this.rebind(n, obj);
      } catch(org.omg.CosNaming.NamingContextPackage.NotFound _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.InvalidName _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 2: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(2);
      org.omg.CORBA.Any $n = _orb().create_any();
      $n.type(org.omg.CosNaming.NameHelper.type());
      _params.add_value("n", $n, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $nc = _orb().create_any();
      $nc.type(org.omg.CosNaming.NamingContextHelper.type());
      _params.add_value("nc", $nc, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      org.omg.CosNaming.NameComponent[] n;
      n = org.omg.CosNaming.NameHelper.extract($n);
      org.omg.CosNaming.NamingContext nc;
      nc = org.omg.CosNaming.NamingContextHelper.extract($nc);
      this.bind_context(n, nc);
      } catch(org.omg.CosNaming.NamingContextPackage.NotFound _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.InvalidName _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.AlreadyBound _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 3: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(2);
      org.omg.CORBA.Any $n = _orb().create_any();
      $n.type(org.omg.CosNaming.NameHelper.type());
      _params.add_value("n", $n, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $nc = _orb().create_any();
      $nc.type(org.omg.CosNaming.NamingContextHelper.type());
      _params.add_value("nc", $nc, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      org.omg.CosNaming.NameComponent[] n;
      n = org.omg.CosNaming.NameHelper.extract($n);
      org.omg.CosNaming.NamingContext nc;
      nc = org.omg.CosNaming.NamingContextHelper.extract($nc);
      this.rebind_context(n, nc);
      } catch(org.omg.CosNaming.NamingContextPackage.NotFound _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.InvalidName _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 4: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $n = _orb().create_any();
      $n.type(org.omg.CosNaming.NameHelper.type());
      _params.add_value("n", $n, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      org.omg.CosNaming.NameComponent[] n;
      n = org.omg.CosNaming.NameHelper.extract($n);
      org.omg.CORBA.Object _result = this.resolve(n);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_Object(_result);
      _request.set_result(_resultAny);
      } catch(org.omg.CosNaming.NamingContextPackage.NotFound _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.InvalidName _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 5: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $n = _orb().create_any();
      $n.type(org.omg.CosNaming.NameHelper.type());
      _params.add_value("n", $n, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      org.omg.CosNaming.NameComponent[] n;
      n = org.omg.CosNaming.NameHelper.extract($n);
      this.unbind(n);
      } catch(org.omg.CosNaming.NamingContextPackage.NotFound _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.InvalidName _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 6: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CosNaming.NamingContext _result = this.new_context();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CosNaming.NamingContextHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 7: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $n = _orb().create_any();
      $n.type(org.omg.CosNaming.NameHelper.type());
      _params.add_value("n", $n, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      org.omg.CosNaming.NameComponent[] n;
      n = org.omg.CosNaming.NameHelper.extract($n);
      org.omg.CosNaming.NamingContext _result = this.bind_new_context(n);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CosNaming.NamingContextHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      } catch(org.omg.CosNaming.NamingContextPackage.NotFound _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.NotFoundHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.AlreadyBound _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.AlreadyBoundHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.CannotProceed _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.CannotProceedHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      } catch(org.omg.CosNaming.NamingContextPackage.InvalidName _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.InvalidNameHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 8: {
      try {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      this.destroy();
      } catch(org.omg.CosNaming.NamingContextPackage.NotEmpty _exception) {
        org.omg.CORBA.Any _exceptionAny = _orb().create_any();
        org.omg.CosNaming.NamingContextPackage.NotEmptyHelper.insert(_exceptionAny, _exception);
        _request.set_exception(_exceptionAny);
      }
      return;
    }
    case 9: {
      org.omg.CORBA.NVList _params = _orb().create_list(3);
      org.omg.CORBA.Any $how_many = _orb().create_any();
      $how_many.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong));
      _params.add_value("how_many", $how_many, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $bl = _orb().create_any();
      $bl.type(org.omg.CosNaming.BindingListHelper.type());
      _params.add_value("bl", $bl, org.omg.CORBA.ARG_OUT.value);
      org.omg.CORBA.Any $bi = _orb().create_any();
      $bi.type(org.omg.CosNaming.BindingIteratorHelper.type());
      _params.add_value("bi", $bi, org.omg.CORBA.ARG_OUT.value);
      _request.arguments(_params);
      int how_many;
      how_many = $how_many.extract_ulong();
      org.omg.CosNaming.BindingListHolder bl = new org.omg.CosNaming.BindingListHolder();
      org.omg.CosNaming.BindingIteratorHolder bi = new org.omg.CosNaming.BindingIteratorHolder();
      this.list(how_many, bl, bi);
      org.omg.CosNaming.BindingListHelper.insert($bl,bl.value);
      org.omg.CosNaming.BindingIteratorHelper.insert($bi,bi.value);
      return;
    }
    }
  }
}
