//
// _RepositoryStub.java (stub)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class _RepositoryStub
 extends org.omg.CORBA.portable.ObjectImpl
 implements Repository {

  public java.lang.String[] _ids() {
    return __ids;
  }

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/Repository:1.0",
    "IDL:omg.org/CORBA/Container:1.0",
    "IDL:omg.org/CORBA/IRObject:1.0"  };

  public org.omg.CORBA.Contained lookup_id(java.lang.String search_id) {
    org.omg.CORBA.Request _request = this._request("lookup_id");
    _request.set_return_type(org.omg.CORBA.ContainedHelper.type());
    org.omg.CORBA.Any $search_id = _request.add_named_in_arg("search_id");
    $search_id.insert_string(search_id);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.Contained _result;
    _result = org.omg.CORBA.ContainedHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.TypeCode get_canonical_typecode(org.omg.CORBA.TypeCode tc) {
    org.omg.CORBA.Request _request = this._request("get_canonical_typecode");
    _request.set_return_type(org.omg.CORBA.TypeCodeHelper.type());
    org.omg.CORBA.Any $tc = _request.add_named_in_arg("tc");
    org.omg.CORBA.TypeCodeHelper.insert($tc,tc);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.TypeCode _result;
    _result = org.omg.CORBA.TypeCodeHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.PrimitiveDef get_primitive(org.omg.CORBA.PrimitiveKind kind) {
    org.omg.CORBA.Request _request = this._request("get_primitive");
    _request.set_return_type(org.omg.CORBA.PrimitiveDefHelper.type());
    org.omg.CORBA.Any $kind = _request.add_named_in_arg("kind");
    org.omg.CORBA.PrimitiveKindHelper.insert($kind,kind);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.PrimitiveDef _result;
    _result = org.omg.CORBA.PrimitiveDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.StringDef create_string(int bound) {
    org.omg.CORBA.Request _request = this._request("create_string");
    _request.set_return_type(org.omg.CORBA.StringDefHelper.type());
    org.omg.CORBA.Any $bound = _request.add_named_in_arg("bound");
    $bound.insert_ulong(bound);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.StringDef _result;
    _result = org.omg.CORBA.StringDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.WstringDef create_wstring(int bound) {
    org.omg.CORBA.Request _request = this._request("create_wstring");
    _request.set_return_type(org.omg.CORBA.WstringDefHelper.type());
    org.omg.CORBA.Any $bound = _request.add_named_in_arg("bound");
    $bound.insert_ulong(bound);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.WstringDef _result;
    _result = org.omg.CORBA.WstringDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.SequenceDef create_sequence(int bound, org.omg.CORBA.IDLType element_type) {
    org.omg.CORBA.Request _request = this._request("create_sequence");
    _request.set_return_type(org.omg.CORBA.SequenceDefHelper.type());
    org.omg.CORBA.Any $bound = _request.add_named_in_arg("bound");
    $bound.insert_ulong(bound);
    org.omg.CORBA.Any $element_type = _request.add_named_in_arg("element_type");
    org.omg.CORBA.IDLTypeHelper.insert($element_type,element_type);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.SequenceDef _result;
    _result = org.omg.CORBA.SequenceDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.ArrayDef create_array(int length, org.omg.CORBA.IDLType element_type) {
    org.omg.CORBA.Request _request = this._request("create_array");
    _request.set_return_type(org.omg.CORBA.ArrayDefHelper.type());
    org.omg.CORBA.Any $length = _request.add_named_in_arg("length");
    $length.insert_ulong(length);
    org.omg.CORBA.Any $element_type = _request.add_named_in_arg("element_type");
    org.omg.CORBA.IDLTypeHelper.insert($element_type,element_type);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.ArrayDef _result;
    _result = org.omg.CORBA.ArrayDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.FixedDef create_fixed(short digits, short scale) {
    org.omg.CORBA.Request _request = this._request("create_fixed");
    _request.set_return_type(org.omg.CORBA.FixedDefHelper.type());
    org.omg.CORBA.Any $digits = _request.add_named_in_arg("digits");
    $digits.insert_ushort(digits);
    org.omg.CORBA.Any $scale = _request.add_named_in_arg("scale");
    $scale.insert_short(scale);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.FixedDef _result;
    _result = org.omg.CORBA.FixedDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.Contained lookup(java.lang.String search_name) {
    org.omg.CORBA.Request _request = this._request("lookup");
    _request.set_return_type(org.omg.CORBA.ContainedHelper.type());
    org.omg.CORBA.Any $search_name = _request.add_named_in_arg("search_name");
    $search_name.insert_string(search_name);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.Contained _result;
    _result = org.omg.CORBA.ContainedHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.Contained[] contents(org.omg.CORBA.DefinitionKind limit_type, boolean exclude_inherited) {
    org.omg.CORBA.Request _request = this._request("contents");
    _request.set_return_type(org.omg.CORBA.ContainedSeqHelper.type());
    org.omg.CORBA.Any $limit_type = _request.add_named_in_arg("limit_type");
    org.omg.CORBA.DefinitionKindHelper.insert($limit_type,limit_type);
    org.omg.CORBA.Any $exclude_inherited = _request.add_named_in_arg("exclude_inherited");
    $exclude_inherited.insert_boolean(exclude_inherited);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.Contained[] _result;
    _result = org.omg.CORBA.ContainedSeqHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.Contained[] lookup_name(java.lang.String search_name, int levels_to_search, org.omg.CORBA.DefinitionKind limit_type, boolean exclude_inherited) {
    org.omg.CORBA.Request _request = this._request("lookup_name");
    _request.set_return_type(org.omg.CORBA.ContainedSeqHelper.type());
    org.omg.CORBA.Any $search_name = _request.add_named_in_arg("search_name");
    $search_name.insert_string(search_name);
    org.omg.CORBA.Any $levels_to_search = _request.add_named_in_arg("levels_to_search");
    $levels_to_search.insert_long(levels_to_search);
    org.omg.CORBA.Any $limit_type = _request.add_named_in_arg("limit_type");
    org.omg.CORBA.DefinitionKindHelper.insert($limit_type,limit_type);
    org.omg.CORBA.Any $exclude_inherited = _request.add_named_in_arg("exclude_inherited");
    $exclude_inherited.insert_boolean(exclude_inherited);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.Contained[] _result;
    _result = org.omg.CORBA.ContainedSeqHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.ContainerPackage.Description[] describe_contents(org.omg.CORBA.DefinitionKind limit_type, boolean exclude_inherited, int max_returned_objs) {
    org.omg.CORBA.Request _request = this._request("describe_contents");
    _request.set_return_type(org.omg.CORBA.ContainerPackage.DescriptionSeqHelper.type());
    org.omg.CORBA.Any $limit_type = _request.add_named_in_arg("limit_type");
    org.omg.CORBA.DefinitionKindHelper.insert($limit_type,limit_type);
    org.omg.CORBA.Any $exclude_inherited = _request.add_named_in_arg("exclude_inherited");
    $exclude_inherited.insert_boolean(exclude_inherited);
    org.omg.CORBA.Any $max_returned_objs = _request.add_named_in_arg("max_returned_objs");
    $max_returned_objs.insert_long(max_returned_objs);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.ContainerPackage.Description[] _result;
    _result = org.omg.CORBA.ContainerPackage.DescriptionSeqHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.ModuleDef create_module(java.lang.String id, java.lang.String name, java.lang.String version) {
    org.omg.CORBA.Request _request = this._request("create_module");
    _request.set_return_type(org.omg.CORBA.ModuleDefHelper.type());
    org.omg.CORBA.Any $id = _request.add_named_in_arg("id");
    $id.insert_string(id);
    org.omg.CORBA.Any $name = _request.add_named_in_arg("name");
    $name.insert_string(name);
    org.omg.CORBA.Any $version = _request.add_named_in_arg("version");
    $version.insert_string(version);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.ModuleDef _result;
    _result = org.omg.CORBA.ModuleDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.ConstantDef create_constant(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.IDLType type, org.omg.CORBA.Any value) {
    org.omg.CORBA.Request _request = this._request("create_constant");
    _request.set_return_type(org.omg.CORBA.ConstantDefHelper.type());
    org.omg.CORBA.Any $id = _request.add_named_in_arg("id");
    $id.insert_string(id);
    org.omg.CORBA.Any $name = _request.add_named_in_arg("name");
    $name.insert_string(name);
    org.omg.CORBA.Any $version = _request.add_named_in_arg("version");
    $version.insert_string(version);
    org.omg.CORBA.Any $type = _request.add_named_in_arg("type");
    org.omg.CORBA.IDLTypeHelper.insert($type,type);
    org.omg.CORBA.Any $value = _request.add_named_in_arg("value");
    $value.insert_any(value);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.ConstantDef _result;
    _result = org.omg.CORBA.ConstantDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.StructDef create_struct(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.StructMember[] members) {
    org.omg.CORBA.Request _request = this._request("create_struct");
    _request.set_return_type(org.omg.CORBA.StructDefHelper.type());
    org.omg.CORBA.Any $id = _request.add_named_in_arg("id");
    $id.insert_string(id);
    org.omg.CORBA.Any $name = _request.add_named_in_arg("name");
    $name.insert_string(name);
    org.omg.CORBA.Any $version = _request.add_named_in_arg("version");
    $version.insert_string(version);
    org.omg.CORBA.Any $members = _request.add_named_in_arg("members");
    org.omg.CORBA.StructMemberSeqHelper.insert($members,members);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.StructDef _result;
    _result = org.omg.CORBA.StructDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.UnionDef create_union(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.IDLType discriminator_type, org.omg.CORBA.UnionMember[] members) {
    org.omg.CORBA.Request _request = this._request("create_union");
    _request.set_return_type(org.omg.CORBA.UnionDefHelper.type());
    org.omg.CORBA.Any $id = _request.add_named_in_arg("id");
    $id.insert_string(id);
    org.omg.CORBA.Any $name = _request.add_named_in_arg("name");
    $name.insert_string(name);
    org.omg.CORBA.Any $version = _request.add_named_in_arg("version");
    $version.insert_string(version);
    org.omg.CORBA.Any $discriminator_type = _request.add_named_in_arg("discriminator_type");
    org.omg.CORBA.IDLTypeHelper.insert($discriminator_type,discriminator_type);
    org.omg.CORBA.Any $members = _request.add_named_in_arg("members");
    org.omg.CORBA.UnionMemberSeqHelper.insert($members,members);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.UnionDef _result;
    _result = org.omg.CORBA.UnionDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.EnumDef create_enum(java.lang.String id, java.lang.String name, java.lang.String version, java.lang.String[] members) {
    org.omg.CORBA.Request _request = this._request("create_enum");
    _request.set_return_type(org.omg.CORBA.EnumDefHelper.type());
    org.omg.CORBA.Any $id = _request.add_named_in_arg("id");
    $id.insert_string(id);
    org.omg.CORBA.Any $name = _request.add_named_in_arg("name");
    $name.insert_string(name);
    org.omg.CORBA.Any $version = _request.add_named_in_arg("version");
    $version.insert_string(version);
    org.omg.CORBA.Any $members = _request.add_named_in_arg("members");
    org.omg.CORBA.EnumMemberSeqHelper.insert($members,members);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.EnumDef _result;
    _result = org.omg.CORBA.EnumDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.AliasDef create_alias(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.IDLType original_type) {
    org.omg.CORBA.Request _request = this._request("create_alias");
    _request.set_return_type(org.omg.CORBA.AliasDefHelper.type());
    org.omg.CORBA.Any $id = _request.add_named_in_arg("id");
    $id.insert_string(id);
    org.omg.CORBA.Any $name = _request.add_named_in_arg("name");
    $name.insert_string(name);
    org.omg.CORBA.Any $version = _request.add_named_in_arg("version");
    $version.insert_string(version);
    org.omg.CORBA.Any $original_type = _request.add_named_in_arg("original_type");
    org.omg.CORBA.IDLTypeHelper.insert($original_type,original_type);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.AliasDef _result;
    _result = org.omg.CORBA.AliasDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.InterfaceDef create_interface(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.InterfaceDef[] base_interfaces, boolean is_abstract) {
    org.omg.CORBA.Request _request = this._request("create_interface");
    _request.set_return_type(org.omg.CORBA.InterfaceDefHelper.type());
    org.omg.CORBA.Any $id = _request.add_named_in_arg("id");
    $id.insert_string(id);
    org.omg.CORBA.Any $name = _request.add_named_in_arg("name");
    $name.insert_string(name);
    org.omg.CORBA.Any $version = _request.add_named_in_arg("version");
    $version.insert_string(version);
    org.omg.CORBA.Any $base_interfaces = _request.add_named_in_arg("base_interfaces");
    org.omg.CORBA.InterfaceDefSeqHelper.insert($base_interfaces,base_interfaces);
    org.omg.CORBA.Any $is_abstract = _request.add_named_in_arg("is_abstract");
    $is_abstract.insert_boolean(is_abstract);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.InterfaceDef _result;
    _result = org.omg.CORBA.InterfaceDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.ValueDef create_value(java.lang.String id, java.lang.String name, java.lang.String version, boolean is_custom, boolean is_abstract, org.omg.CORBA.ValueDef base_value, boolean is_truncatable, org.omg.CORBA.ValueDef[] abstract_base_values, org.omg.CORBA.InterfaceDef[] supported_interfaces, org.omg.CORBA.Initializer[] initializers) {
    org.omg.CORBA.Request _request = this._request("create_value");
    _request.set_return_type(org.omg.CORBA.ValueDefHelper.type());
    org.omg.CORBA.Any $id = _request.add_named_in_arg("id");
    $id.insert_string(id);
    org.omg.CORBA.Any $name = _request.add_named_in_arg("name");
    $name.insert_string(name);
    org.omg.CORBA.Any $version = _request.add_named_in_arg("version");
    $version.insert_string(version);
    org.omg.CORBA.Any $is_custom = _request.add_named_in_arg("is_custom");
    $is_custom.insert_boolean(is_custom);
    org.omg.CORBA.Any $is_abstract = _request.add_named_in_arg("is_abstract");
    $is_abstract.insert_boolean(is_abstract);
    org.omg.CORBA.Any $base_value = _request.add_named_in_arg("base_value");
    org.omg.CORBA.ValueDefHelper.insert($base_value,base_value);
    org.omg.CORBA.Any $is_truncatable = _request.add_named_in_arg("is_truncatable");
    $is_truncatable.insert_boolean(is_truncatable);
    org.omg.CORBA.Any $abstract_base_values = _request.add_named_in_arg("abstract_base_values");
    org.omg.CORBA.ValueDefSeqHelper.insert($abstract_base_values,abstract_base_values);
    org.omg.CORBA.Any $supported_interfaces = _request.add_named_in_arg("supported_interfaces");
    org.omg.CORBA.InterfaceDefSeqHelper.insert($supported_interfaces,supported_interfaces);
    org.omg.CORBA.Any $initializers = _request.add_named_in_arg("initializers");
    org.omg.CORBA.InitializerSeqHelper.insert($initializers,initializers);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.ValueDef _result;
    _result = org.omg.CORBA.ValueDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.ValueBoxDef create_value_box(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.IDLType original_type_def) {
    org.omg.CORBA.Request _request = this._request("create_value_box");
    _request.set_return_type(org.omg.CORBA.ValueBoxDefHelper.type());
    org.omg.CORBA.Any $id = _request.add_named_in_arg("id");
    $id.insert_string(id);
    org.omg.CORBA.Any $name = _request.add_named_in_arg("name");
    $name.insert_string(name);
    org.omg.CORBA.Any $version = _request.add_named_in_arg("version");
    $version.insert_string(version);
    org.omg.CORBA.Any $original_type_def = _request.add_named_in_arg("original_type_def");
    org.omg.CORBA.IDLTypeHelper.insert($original_type_def,original_type_def);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.ValueBoxDef _result;
    _result = org.omg.CORBA.ValueBoxDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.ExceptionDef create_exception(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.StructMember[] members) {
    org.omg.CORBA.Request _request = this._request("create_exception");
    _request.set_return_type(org.omg.CORBA.ExceptionDefHelper.type());
    org.omg.CORBA.Any $id = _request.add_named_in_arg("id");
    $id.insert_string(id);
    org.omg.CORBA.Any $name = _request.add_named_in_arg("name");
    $name.insert_string(name);
    org.omg.CORBA.Any $version = _request.add_named_in_arg("version");
    $version.insert_string(version);
    org.omg.CORBA.Any $members = _request.add_named_in_arg("members");
    org.omg.CORBA.StructMemberSeqHelper.insert($members,members);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.ExceptionDef _result;
    _result = org.omg.CORBA.ExceptionDefHelper.extract(_request.return_value());
    return _result;
  }

  public org.omg.CORBA.NativeDef create_native(java.lang.String id, java.lang.String name, java.lang.String version) {
    org.omg.CORBA.Request _request = this._request("create_native");
    _request.set_return_type(org.omg.CORBA.NativeDefHelper.type());
    org.omg.CORBA.Any $id = _request.add_named_in_arg("id");
    $id.insert_string(id);
    org.omg.CORBA.Any $name = _request.add_named_in_arg("name");
    $name.insert_string(name);
    org.omg.CORBA.Any $version = _request.add_named_in_arg("version");
    $version.insert_string(version);
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.NativeDef _result;
    _result = org.omg.CORBA.NativeDefHelper.extract(_request.return_value());
    return _result;
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




}
