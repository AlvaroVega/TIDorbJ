//
// BindingIteratorPOA.java (skeleton)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming;

abstract public class BindingIteratorPOA
 extends org.omg.PortableServer.DynamicImplementation
 implements BindingIteratorOperations {

  public BindingIterator _this() {
    return BindingIteratorHelper.narrow(super._this_object());
  };

  public BindingIterator _this(org.omg.CORBA.ORB orb) {
    return BindingIteratorHelper.narrow(super._this_object(orb));
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CosNaming/BindingIterator:1.0"
  };

  private static java.util.Dictionary _methods = new java.util.Hashtable();
  static {
    _methods.put("next_one", new Integer(0));
    _methods.put("next_n", new Integer(1));
    _methods.put("destroy", new Integer(2));
  }

  public void invoke(org.omg.CORBA.ServerRequest _request) {
    java.lang.Object _method = _methods.get(_request.operation());
    if (_method == null) {
      throw new org.omg.CORBA.BAD_OPERATION(_request.operation());
    }
    int _method_id = ((java.lang.Integer)_method).intValue();
    switch(_method_id) {
    case 0: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $b = _orb().create_any();
      $b.type(org.omg.CosNaming.BindingHelper.type());
      _params.add_value("b", $b, org.omg.CORBA.ARG_OUT.value);
      _request.arguments(_params);
      org.omg.CosNaming.BindingHolder b = new org.omg.CosNaming.BindingHolder();
      boolean _result = this.next_one(b);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_boolean(_result);
      _request.set_result(_resultAny);
      org.omg.CosNaming.BindingHelper.insert($b,b.value);
      return;
    }
    case 1: {
      org.omg.CORBA.NVList _params = _orb().create_list(2);
      org.omg.CORBA.Any $how_many = _orb().create_any();
      $how_many.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong));
      _params.add_value("how_many", $how_many, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $bl = _orb().create_any();
      $bl.type(org.omg.CosNaming.BindingListHelper.type());
      _params.add_value("bl", $bl, org.omg.CORBA.ARG_OUT.value);
      _request.arguments(_params);
      int how_many;
      how_many = $how_many.extract_ulong();
      org.omg.CosNaming.BindingListHolder bl = new org.omg.CosNaming.BindingListHolder();
      boolean _result = this.next_n(how_many, bl);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_boolean(_result);
      _request.set_result(_resultAny);
      org.omg.CosNaming.BindingListHelper.insert($bl,bl.value);
      return;
    }
    case 2: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      this.destroy();
      return;
    }
    }
  }
}
