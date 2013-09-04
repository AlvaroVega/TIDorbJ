//
// InterfaceDefPOA.java (skeleton)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class InterfaceDefPOA
 extends org.omg.PortableServer.DynamicImplementation
 implements InterfaceDefOperations {

  public InterfaceDef _this() {
    return InterfaceDefHelper.narrow(super._this_object());
  };

  public InterfaceDef _this(org.omg.CORBA.ORB orb) {
    return InterfaceDefHelper.narrow(super._this_object(orb));
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/InterfaceDef:1.0",
    "IDL:omg.org/CORBA/Container:1.0",
    "IDL:omg.org/CORBA/IRObject:1.0",
    "IDL:omg.org/CORBA/Contained:1.0",
    "IDL:omg.org/CORBA/IDLType:1.0"
  };

  private static java.util.Dictionary _methods = new java.util.Hashtable();
  static {
    _methods.put("_get_base_interfaces", new Integer(0));
    _methods.put("_set_base_interfaces", new Integer(1));
    _methods.put("_get_is_abstract", new Integer(2));
    _methods.put("_set_is_abstract", new Integer(3));
    _methods.put("is_a", new Integer(4));
    _methods.put("describe_interface", new Integer(5));
    _methods.put("create_attribute", new Integer(6));
    _methods.put("create_operation", new Integer(7));
    _methods.put("lookup", new Integer(8));
    _methods.put("contents", new Integer(9));
    _methods.put("lookup_name", new Integer(10));
    _methods.put("describe_contents", new Integer(11));
    _methods.put("create_module", new Integer(12));
    _methods.put("create_constant", new Integer(13));
    _methods.put("create_struct", new Integer(14));
    _methods.put("create_union", new Integer(15));
    _methods.put("create_enum", new Integer(16));
    _methods.put("create_alias", new Integer(17));
    _methods.put("create_interface", new Integer(18));
    _methods.put("create_value", new Integer(19));
    _methods.put("create_value_box", new Integer(20));
    _methods.put("create_exception", new Integer(21));
    _methods.put("create_native", new Integer(22));
    _methods.put("_get_def_kind", new Integer(23));
    _methods.put("destroy", new Integer(24));
    _methods.put("_get_id", new Integer(25));
    _methods.put("_set_id", new Integer(26));
    _methods.put("_get_name", new Integer(27));
    _methods.put("_set_name", new Integer(28));
    _methods.put("_get_version", new Integer(29));
    _methods.put("_set_version", new Integer(30));
    _methods.put("_get_defined_in", new Integer(31));
    _methods.put("_get_absolute_name", new Integer(32));
    _methods.put("_get_containing_repository", new Integer(33));
    _methods.put("describe", new Integer(34));
    _methods.put("move", new Integer(35));
    _methods.put("_get_type", new Integer(36));
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
      org.omg.CORBA.InterfaceDef[] _result = this.base_interfaces();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.InterfaceDefSeqHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 1: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $value = _orb().create_any();
      $value.type(org.omg.CORBA.InterfaceDefSeqHelper.type());
      _params.add_value("value", $value, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      org.omg.CORBA.InterfaceDef[] value;
      value = org.omg.CORBA.InterfaceDefSeqHelper.extract($value);
      this.base_interfaces(value);
      return;
    }
    case 2: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      boolean _result = this.is_abstract();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_boolean(_result);
      _request.set_result(_resultAny);
      return;
    }
    case 3: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $value = _orb().create_any();
      $value.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean));
      _params.add_value("value", $value, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      boolean value;
      value = $value.extract_boolean();
      this.is_abstract(value);
      return;
    }
    case 4: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $interface_id = _orb().create_any();
      $interface_id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("interface_id", $interface_id, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String interface_id;
      interface_id = $interface_id.extract_string();
      boolean _result = this.is_a(interface_id);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_boolean(_result);
      _request.set_result(_resultAny);
      return;
    }
    case 5: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.InterfaceDefPackage.FullInterfaceDescription _result = this.describe_interface();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.InterfaceDefPackage.FullInterfaceDescriptionHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 6: {
      org.omg.CORBA.NVList _params = _orb().create_list(5);
      org.omg.CORBA.Any $id = _orb().create_any();
      $id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("id", $id, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $name = _orb().create_any();
      $name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("name", $name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $version = _orb().create_any();
      $version.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("version", $version, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $type = _orb().create_any();
      $type.type(org.omg.CORBA.IDLTypeHelper.type());
      _params.add_value("type", $type, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $mode = _orb().create_any();
      $mode.type(org.omg.CORBA.AttributeModeHelper.type());
      _params.add_value("mode", $mode, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String id;
      id = $id.extract_string();
      java.lang.String name;
      name = $name.extract_string();
      java.lang.String version;
      version = $version.extract_string();
      org.omg.CORBA.IDLType type;
      type = org.omg.CORBA.IDLTypeHelper.extract($type);
      org.omg.CORBA.AttributeMode mode;
      mode = org.omg.CORBA.AttributeModeHelper.extract($mode);
      org.omg.CORBA.AttributeDef _result = this.create_attribute(id, name, version, type, mode);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.AttributeDefHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 7: {
      org.omg.CORBA.NVList _params = _orb().create_list(8);
      org.omg.CORBA.Any $id = _orb().create_any();
      $id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("id", $id, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $name = _orb().create_any();
      $name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("name", $name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $version = _orb().create_any();
      $version.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("version", $version, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $result = _orb().create_any();
      $result.type(org.omg.CORBA.IDLTypeHelper.type());
      _params.add_value("result", $result, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $mode = _orb().create_any();
      $mode.type(org.omg.CORBA.OperationModeHelper.type());
      _params.add_value("mode", $mode, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $params = _orb().create_any();
      $params.type(org.omg.CORBA.ParDescriptionSeqHelper.type());
      _params.add_value("params", $params, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $exceptions = _orb().create_any();
      $exceptions.type(org.omg.CORBA.ExceptionDefSeqHelper.type());
      _params.add_value("exceptions", $exceptions, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $contexts = _orb().create_any();
      $contexts.type(org.omg.CORBA.ContextIdSeqHelper.type());
      _params.add_value("contexts", $contexts, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String id;
      id = $id.extract_string();
      java.lang.String name;
      name = $name.extract_string();
      java.lang.String version;
      version = $version.extract_string();
      org.omg.CORBA.IDLType result;
      result = org.omg.CORBA.IDLTypeHelper.extract($result);
      org.omg.CORBA.OperationMode mode;
      mode = org.omg.CORBA.OperationModeHelper.extract($mode);
      org.omg.CORBA.ParameterDescription[] params;
      params = org.omg.CORBA.ParDescriptionSeqHelper.extract($params);
      org.omg.CORBA.ExceptionDef[] exceptions;
      exceptions = org.omg.CORBA.ExceptionDefSeqHelper.extract($exceptions);
      java.lang.String[] contexts;
      contexts = org.omg.CORBA.ContextIdSeqHelper.extract($contexts);
      org.omg.CORBA.OperationDef _result = this.create_operation(id, name, version, result, mode, params, exceptions, contexts);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.OperationDefHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 8: {
      org.omg.CORBA.NVList _params = _orb().create_list(1);
      org.omg.CORBA.Any $search_name = _orb().create_any();
      $search_name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("search_name", $search_name, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String search_name;
      search_name = $search_name.extract_string();
      org.omg.CORBA.Contained _result = this.lookup(search_name);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.ContainedHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 9: {
      org.omg.CORBA.NVList _params = _orb().create_list(2);
      org.omg.CORBA.Any $limit_type = _orb().create_any();
      $limit_type.type(org.omg.CORBA.DefinitionKindHelper.type());
      _params.add_value("limit_type", $limit_type, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $exclude_inherited = _orb().create_any();
      $exclude_inherited.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean));
      _params.add_value("exclude_inherited", $exclude_inherited, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      org.omg.CORBA.DefinitionKind limit_type;
      limit_type = org.omg.CORBA.DefinitionKindHelper.extract($limit_type);
      boolean exclude_inherited;
      exclude_inherited = $exclude_inherited.extract_boolean();
      org.omg.CORBA.Contained[] _result = this.contents(limit_type, exclude_inherited);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.ContainedSeqHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 10: {
      org.omg.CORBA.NVList _params = _orb().create_list(4);
      org.omg.CORBA.Any $search_name = _orb().create_any();
      $search_name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("search_name", $search_name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $levels_to_search = _orb().create_any();
      $levels_to_search.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long));
      _params.add_value("levels_to_search", $levels_to_search, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $limit_type = _orb().create_any();
      $limit_type.type(org.omg.CORBA.DefinitionKindHelper.type());
      _params.add_value("limit_type", $limit_type, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $exclude_inherited = _orb().create_any();
      $exclude_inherited.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean));
      _params.add_value("exclude_inherited", $exclude_inherited, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String search_name;
      search_name = $search_name.extract_string();
      int levels_to_search;
      levels_to_search = $levels_to_search.extract_long();
      org.omg.CORBA.DefinitionKind limit_type;
      limit_type = org.omg.CORBA.DefinitionKindHelper.extract($limit_type);
      boolean exclude_inherited;
      exclude_inherited = $exclude_inherited.extract_boolean();
      org.omg.CORBA.Contained[] _result = this.lookup_name(search_name, levels_to_search, limit_type, exclude_inherited);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.ContainedSeqHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 11: {
      org.omg.CORBA.NVList _params = _orb().create_list(3);
      org.omg.CORBA.Any $limit_type = _orb().create_any();
      $limit_type.type(org.omg.CORBA.DefinitionKindHelper.type());
      _params.add_value("limit_type", $limit_type, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $exclude_inherited = _orb().create_any();
      $exclude_inherited.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean));
      _params.add_value("exclude_inherited", $exclude_inherited, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $max_returned_objs = _orb().create_any();
      $max_returned_objs.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long));
      _params.add_value("max_returned_objs", $max_returned_objs, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      org.omg.CORBA.DefinitionKind limit_type;
      limit_type = org.omg.CORBA.DefinitionKindHelper.extract($limit_type);
      boolean exclude_inherited;
      exclude_inherited = $exclude_inherited.extract_boolean();
      int max_returned_objs;
      max_returned_objs = $max_returned_objs.extract_long();
      org.omg.CORBA.ContainerPackage.Description[] _result = this.describe_contents(limit_type, exclude_inherited, max_returned_objs);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.ContainerPackage.DescriptionSeqHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 12: {
      org.omg.CORBA.NVList _params = _orb().create_list(3);
      org.omg.CORBA.Any $id = _orb().create_any();
      $id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("id", $id, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $name = _orb().create_any();
      $name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("name", $name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $version = _orb().create_any();
      $version.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("version", $version, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String id;
      id = $id.extract_string();
      java.lang.String name;
      name = $name.extract_string();
      java.lang.String version;
      version = $version.extract_string();
      org.omg.CORBA.ModuleDef _result = this.create_module(id, name, version);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.ModuleDefHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 13: {
      org.omg.CORBA.NVList _params = _orb().create_list(5);
      org.omg.CORBA.Any $id = _orb().create_any();
      $id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("id", $id, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $name = _orb().create_any();
      $name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("name", $name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $version = _orb().create_any();
      $version.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("version", $version, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $type = _orb().create_any();
      $type.type(org.omg.CORBA.IDLTypeHelper.type());
      _params.add_value("type", $type, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $value = _orb().create_any();
      $value.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_any));
      _params.add_value("value", $value, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String id;
      id = $id.extract_string();
      java.lang.String name;
      name = $name.extract_string();
      java.lang.String version;
      version = $version.extract_string();
      org.omg.CORBA.IDLType type;
      type = org.omg.CORBA.IDLTypeHelper.extract($type);
      org.omg.CORBA.Any value;
      value = $value.extract_any();
      org.omg.CORBA.ConstantDef _result = this.create_constant(id, name, version, type, value);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.ConstantDefHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 14: {
      org.omg.CORBA.NVList _params = _orb().create_list(4);
      org.omg.CORBA.Any $id = _orb().create_any();
      $id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("id", $id, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $name = _orb().create_any();
      $name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("name", $name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $version = _orb().create_any();
      $version.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("version", $version, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $members = _orb().create_any();
      $members.type(org.omg.CORBA.StructMemberSeqHelper.type());
      _params.add_value("members", $members, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String id;
      id = $id.extract_string();
      java.lang.String name;
      name = $name.extract_string();
      java.lang.String version;
      version = $version.extract_string();
      org.omg.CORBA.StructMember[] members;
      members = org.omg.CORBA.StructMemberSeqHelper.extract($members);
      org.omg.CORBA.StructDef _result = this.create_struct(id, name, version, members);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.StructDefHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 15: {
      org.omg.CORBA.NVList _params = _orb().create_list(5);
      org.omg.CORBA.Any $id = _orb().create_any();
      $id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("id", $id, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $name = _orb().create_any();
      $name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("name", $name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $version = _orb().create_any();
      $version.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("version", $version, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $discriminator_type = _orb().create_any();
      $discriminator_type.type(org.omg.CORBA.IDLTypeHelper.type());
      _params.add_value("discriminator_type", $discriminator_type, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $members = _orb().create_any();
      $members.type(org.omg.CORBA.UnionMemberSeqHelper.type());
      _params.add_value("members", $members, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String id;
      id = $id.extract_string();
      java.lang.String name;
      name = $name.extract_string();
      java.lang.String version;
      version = $version.extract_string();
      org.omg.CORBA.IDLType discriminator_type;
      discriminator_type = org.omg.CORBA.IDLTypeHelper.extract($discriminator_type);
      org.omg.CORBA.UnionMember[] members;
      members = org.omg.CORBA.UnionMemberSeqHelper.extract($members);
      org.omg.CORBA.UnionDef _result = this.create_union(id, name, version, discriminator_type, members);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.UnionDefHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 16: {
      org.omg.CORBA.NVList _params = _orb().create_list(4);
      org.omg.CORBA.Any $id = _orb().create_any();
      $id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("id", $id, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $name = _orb().create_any();
      $name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("name", $name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $version = _orb().create_any();
      $version.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("version", $version, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $members = _orb().create_any();
      $members.type(org.omg.CORBA.EnumMemberSeqHelper.type());
      _params.add_value("members", $members, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String id;
      id = $id.extract_string();
      java.lang.String name;
      name = $name.extract_string();
      java.lang.String version;
      version = $version.extract_string();
      java.lang.String[] members;
      members = org.omg.CORBA.EnumMemberSeqHelper.extract($members);
      org.omg.CORBA.EnumDef _result = this.create_enum(id, name, version, members);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.EnumDefHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 17: {
      org.omg.CORBA.NVList _params = _orb().create_list(4);
      org.omg.CORBA.Any $id = _orb().create_any();
      $id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("id", $id, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $name = _orb().create_any();
      $name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("name", $name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $version = _orb().create_any();
      $version.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("version", $version, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $original_type = _orb().create_any();
      $original_type.type(org.omg.CORBA.IDLTypeHelper.type());
      _params.add_value("original_type", $original_type, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String id;
      id = $id.extract_string();
      java.lang.String name;
      name = $name.extract_string();
      java.lang.String version;
      version = $version.extract_string();
      org.omg.CORBA.IDLType original_type;
      original_type = org.omg.CORBA.IDLTypeHelper.extract($original_type);
      org.omg.CORBA.AliasDef _result = this.create_alias(id, name, version, original_type);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.AliasDefHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 18: {
      org.omg.CORBA.NVList _params = _orb().create_list(5);
      org.omg.CORBA.Any $id = _orb().create_any();
      $id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("id", $id, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $name = _orb().create_any();
      $name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("name", $name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $version = _orb().create_any();
      $version.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("version", $version, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $base_interfaces = _orb().create_any();
      $base_interfaces.type(org.omg.CORBA.InterfaceDefSeqHelper.type());
      _params.add_value("base_interfaces", $base_interfaces, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $is_abstract = _orb().create_any();
      $is_abstract.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean));
      _params.add_value("is_abstract", $is_abstract, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String id;
      id = $id.extract_string();
      java.lang.String name;
      name = $name.extract_string();
      java.lang.String version;
      version = $version.extract_string();
      org.omg.CORBA.InterfaceDef[] base_interfaces;
      base_interfaces = org.omg.CORBA.InterfaceDefSeqHelper.extract($base_interfaces);
      boolean is_abstract;
      is_abstract = $is_abstract.extract_boolean();
      org.omg.CORBA.InterfaceDef _result = this.create_interface(id, name, version, base_interfaces, is_abstract);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.InterfaceDefHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 19: {
      org.omg.CORBA.NVList _params = _orb().create_list(10);
      org.omg.CORBA.Any $id = _orb().create_any();
      $id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("id", $id, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $name = _orb().create_any();
      $name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("name", $name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $version = _orb().create_any();
      $version.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("version", $version, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $is_custom = _orb().create_any();
      $is_custom.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean));
      _params.add_value("is_custom", $is_custom, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $is_abstract = _orb().create_any();
      $is_abstract.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean));
      _params.add_value("is_abstract", $is_abstract, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $base_value = _orb().create_any();
      $base_value.type(org.omg.CORBA.ValueDefHelper.type());
      _params.add_value("base_value", $base_value, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $is_truncatable = _orb().create_any();
      $is_truncatable.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean));
      _params.add_value("is_truncatable", $is_truncatable, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $abstract_base_values = _orb().create_any();
      $abstract_base_values.type(org.omg.CORBA.ValueDefSeqHelper.type());
      _params.add_value("abstract_base_values", $abstract_base_values, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $supported_interfaces = _orb().create_any();
      $supported_interfaces.type(org.omg.CORBA.InterfaceDefSeqHelper.type());
      _params.add_value("supported_interfaces", $supported_interfaces, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $initializers = _orb().create_any();
      $initializers.type(org.omg.CORBA.InitializerSeqHelper.type());
      _params.add_value("initializers", $initializers, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String id;
      id = $id.extract_string();
      java.lang.String name;
      name = $name.extract_string();
      java.lang.String version;
      version = $version.extract_string();
      boolean is_custom;
      is_custom = $is_custom.extract_boolean();
      boolean is_abstract;
      is_abstract = $is_abstract.extract_boolean();
      org.omg.CORBA.ValueDef base_value;
      base_value = org.omg.CORBA.ValueDefHelper.extract($base_value);
      boolean is_truncatable;
      is_truncatable = $is_truncatable.extract_boolean();
      org.omg.CORBA.ValueDef[] abstract_base_values;
      abstract_base_values = org.omg.CORBA.ValueDefSeqHelper.extract($abstract_base_values);
      org.omg.CORBA.InterfaceDef[] supported_interfaces;
      supported_interfaces = org.omg.CORBA.InterfaceDefSeqHelper.extract($supported_interfaces);
      org.omg.CORBA.Initializer[] initializers;
      initializers = org.omg.CORBA.InitializerSeqHelper.extract($initializers);
      org.omg.CORBA.ValueDef _result = this.create_value(id, name, version, is_custom, is_abstract, base_value, is_truncatable, abstract_base_values, supported_interfaces, initializers);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.ValueDefHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 20: {
      org.omg.CORBA.NVList _params = _orb().create_list(4);
      org.omg.CORBA.Any $id = _orb().create_any();
      $id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("id", $id, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $name = _orb().create_any();
      $name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("name", $name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $version = _orb().create_any();
      $version.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("version", $version, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $original_type_def = _orb().create_any();
      $original_type_def.type(org.omg.CORBA.IDLTypeHelper.type());
      _params.add_value("original_type_def", $original_type_def, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String id;
      id = $id.extract_string();
      java.lang.String name;
      name = $name.extract_string();
      java.lang.String version;
      version = $version.extract_string();
      org.omg.CORBA.IDLType original_type_def;
      original_type_def = org.omg.CORBA.IDLTypeHelper.extract($original_type_def);
      org.omg.CORBA.ValueBoxDef _result = this.create_value_box(id, name, version, original_type_def);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.ValueBoxDefHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 21: {
      org.omg.CORBA.NVList _params = _orb().create_list(4);
      org.omg.CORBA.Any $id = _orb().create_any();
      $id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("id", $id, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $name = _orb().create_any();
      $name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("name", $name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $version = _orb().create_any();
      $version.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("version", $version, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $members = _orb().create_any();
      $members.type(org.omg.CORBA.StructMemberSeqHelper.type());
      _params.add_value("members", $members, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String id;
      id = $id.extract_string();
      java.lang.String name;
      name = $name.extract_string();
      java.lang.String version;
      version = $version.extract_string();
      org.omg.CORBA.StructMember[] members;
      members = org.omg.CORBA.StructMemberSeqHelper.extract($members);
      org.omg.CORBA.ExceptionDef _result = this.create_exception(id, name, version, members);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.ExceptionDefHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 22: {
      org.omg.CORBA.NVList _params = _orb().create_list(3);
      org.omg.CORBA.Any $id = _orb().create_any();
      $id.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("id", $id, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $name = _orb().create_any();
      $name.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("name", $name, org.omg.CORBA.ARG_IN.value);
      org.omg.CORBA.Any $version = _orb().create_any();
      $version.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
      _params.add_value("version", $version, org.omg.CORBA.ARG_IN.value);
      _request.arguments(_params);
      java.lang.String id;
      id = $id.extract_string();
      java.lang.String name;
      name = $name.extract_string();
      java.lang.String version;
      version = $version.extract_string();
      org.omg.CORBA.NativeDef _result = this.create_native(id, name, version);
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.NativeDefHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 23: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.DefinitionKind _result = this.def_kind();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.DefinitionKindHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 24: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      this.destroy();
      return;
    }
    case 25: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      java.lang.String _result = this.id();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_string(_result);
      _request.set_result(_resultAny);
      return;
    }
    case 26: {
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
    case 27: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      java.lang.String _result = this.name();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_string(_result);
      _request.set_result(_resultAny);
      return;
    }
    case 28: {
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
    case 29: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      java.lang.String _result = this.version();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_string(_result);
      _request.set_result(_resultAny);
      return;
    }
    case 30: {
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
    case 31: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.Container _result = this.defined_in();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.ContainerHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 32: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      java.lang.String _result = this.absolute_name();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      _resultAny.insert_string(_result);
      _request.set_result(_resultAny);
      return;
    }
    case 33: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.Repository _result = this.containing_repository();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.RepositoryHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 34: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.ContainedPackage.Description _result = this.describe();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.ContainedPackage.DescriptionHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    case 35: {
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
    case 36: {
      org.omg.CORBA.NVList _params = _orb().create_list(0);
      _request.arguments(_params);
      org.omg.CORBA.TypeCode _result = this.type();
      org.omg.CORBA.Any _resultAny = _orb().create_any();
      org.omg.CORBA.TypeCodeHelper.insert(_resultAny, _result);
      _request.set_result(_resultAny);
      return;
    }
    }
  }
}
