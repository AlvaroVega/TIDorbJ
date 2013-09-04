//
// CodeBasePOA.java (skeleton)
//
// File generated: Thu May 19 07:31:40 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SendingContext;

abstract public class CodeBasePOA
 extends org.omg.PortableServer.DynamicImplementation
 implements CodeBaseOperations {

  public CodeBase _this() {
    return CodeBaseHelper.narrow(super._this_object());
  };

  public CodeBase _this(org.omg.CORBA.ORB orb) {
    return CodeBaseHelper.narrow(super._this_object(orb));
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/SendingContext/CodeBase:1.0",
    "IDL:omg.org/SendingContext/RunTime:1.0"
  };

  private static java.util.Dictionary _methods = new java.util.Hashtable();
  static {
    _methods.put("get_ir", new Integer(0));
    _methods.put("implementation", new Integer(1));
    _methods.put("implementations", new Integer(2));
    _methods.put("meta", new Integer(3));
    _methods.put("metas", new Integer(4));
    _methods.put("bases", new Integer(5));
  }

  public void invoke(org.omg.CORBA.ServerRequest _request) {
    java.lang.Object _method = _methods.get(_request.operation());
    if (_method == null) {
      throw new org.omg.CORBA.BAD_OPERATION(_request.operation());
    }
    int _method_id = ((java.lang.Integer)_method).intValue();
    switch(_method_id) {
    case 0: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.Repository _result = this.get_ir();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.RepositoryHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 1: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $x = _orb().create_any();
      $x.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("x", $x, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String x;
      x = $x.extract_string();
      java.lang.String _result = this.implementation(x);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_string(_result);
      _request.set_result(_resultAny);
      return;
    }
    case 2: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $x = _orb().create_any();
      $x.type(org.omg.CORBA.RepositoryIdSeqHelper.type());
      _params.add_value("x", $x, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String[] x;
      x = org.omg.CORBA.RepositoryIdSeqHelper.extract($x);
      java.lang.String[] _result = this.implementations(x);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.SendingContext.CodeBasePackage.URLSeqHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 3: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $x = _orb().create_any();
      $x.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("x", $x, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String x;
      x = $x.extract_string();
      org.omg.CORBA.ValueDefPackage.FullValueDescription _result = this.meta(x);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 4: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $x = _orb().create_any();
      $x.type(org.omg.CORBA.RepositoryIdSeqHelper.type());
      _params.add_value("x", $x, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String[] x;
      x = org.omg.CORBA.RepositoryIdSeqHelper.extract($x);
      org.omg.CORBA.ValueDefPackage.FullValueDescription[] _result = this.metas(x);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 5: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $x = _orb().create_any();
      $x.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("x", $x, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String x;
      x = $x.extract_string();
      java.lang.String[] _result = this.bases(x);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.RepositoryIdSeqHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    }
  }
}
