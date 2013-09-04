//
// AttributeDefPOA.java (skeleton)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class AttributeDefPOA
 extends org.omg.PortableServer.DynamicImplementation
 implements AttributeDefOperations {

  public AttributeDef _this() {
    return AttributeDefHelper.narrow(super._this_object());
  };

  public AttributeDef _this(org.omg.CORBA.ORB orb) {
    return AttributeDefHelper.narrow(super._this_object(orb));
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/AttributeDef:1.0",
    "IDL:omg.org/CORBA/Contained:1.0",
    "IDL:omg.org/CORBA/IRObject:1.0"
  };

  private static java.util.Dictionary _methods = new java.util.Hashtable();
  static {
    _methods.put("_get_type", new Integer(0));
    _methods.put("_get_type_def", new Integer(1));
    _methods.put("_set_type_def", new Integer(2));
    _methods.put("_get_mode", new Integer(3));
    _methods.put("_set_mode", new Integer(4));
    _methods.put("_get_id", new Integer(5));
    _methods.put("_set_id", new Integer(6));
    _methods.put("_get_name", new Integer(7));
    _methods.put("_set_name", new Integer(8));
    _methods.put("_get_version", new Integer(9));
    _methods.put("_set_version", new Integer(10));
    _methods.put("_get_defined_in", new Integer(11));
    _methods.put("_get_absolute_name", new Integer(12));
    _methods.put("_get_containing_repository", new Integer(13));
    _methods.put("describe", new Integer(14));
    _methods.put("move", new Integer(15));
    _methods.put("_get_def_kind", new Integer(16));
    _methods.put("destroy", new Integer(17));
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
      org.omg.CORBA.TypeCode _result = this.type();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.TypeCodeHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 1: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.IDLType _result = this.type_def();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.IDLTypeHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 2: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $value = _orb().create_any();
      $value.type(org.omg.CORBA.IDLTypeHelper.type());
      _params.add_value("value", $value, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      org.omg.CORBA.IDLType value;
      value = org.omg.CORBA.IDLTypeHelper.extract($value);
      this.type_def(value);
      return;
    }
    case 3: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.AttributeMode _result = this.mode();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.AttributeModeHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 4: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $value = _orb().create_any();
      $value.type(org.omg.CORBA.AttributeModeHelper.type());
      _params.add_value("value", $value, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      org.omg.CORBA.AttributeMode value;
      value = org.omg.CORBA.AttributeModeHelper.extract($value);
      this.mode(value);
      return;
    }
    case 5: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      java.lang.String _result = this.id();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_string(_result);
      _request.set_result(_resultAny);
      return;
    }
    case 6: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $value = _orb().create_any();
      $value.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("value", $value, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String value;
      value = $value.extract_string();
      this.id(value);
      return;
    }
    case 7: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      java.lang.String _result = this.name();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_string(_result);
      _request.set_result(_resultAny);
      return;
    }
    case 8: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $value = _orb().create_any();
      $value.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("value", $value, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String value;
      value = $value.extract_string();
      this.name(value);
      return;
    }
    case 9: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      java.lang.String _result = this.version();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_string(_result);
      _request.set_result(_resultAny);
      return;
    }
    case 10: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $value = _orb().create_any();
      $value.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("value", $value, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String value;
      value = $value.extract_string();
      this.version(value);
      return;
    }
    case 11: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.Container _result = this.defined_in();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.ContainerHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 12: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      java.lang.String _result = this.absolute_name();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_string(_result);
      _request.set_result(_resultAny);
      return;
    }
    case 13: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.Repository _result = this.containing_repository();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.RepositoryHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 14: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.ContainedPackage.Description _result = this.describe();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.ContainedPackage.DescriptionHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 15: {
      org.omg.CORBA.NVList _params = _orb().create_list(3);
      org.omg.CORBA.Any $new_container = _orb().create_any();
      $new_container.type(org.omg.CORBA.ContainerHelper.type());
      _params.add_value("new_container", $new_container, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $new_name = _orb().create_any();
      $new_name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("new_name", $new_name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $new_version = _orb().create_any();
      $new_version.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("new_version", $new_version, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      org.omg.CORBA.Container new_container;
      new_container = org.omg.CORBA.ContainerHelper.extract($new_container);
      java.lang.String new_name;
      new_name = $new_name.extract_string();
      java.lang.String new_version;
      new_version = $new_version.extract_string();
      this.move(new_container, new_name, new_version);
      return;
    }
    case 16: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.DefinitionKind _result = this.def_kind();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.DefinitionKindHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 17: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      this.destroy();
      return;
    }
    }
  }
}
