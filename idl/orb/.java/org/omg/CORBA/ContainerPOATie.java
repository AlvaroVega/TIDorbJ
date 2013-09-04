//
// ContainerPOATie.java (tie)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class ContainerPOATie
 extends ContainerPOA
 implements ContainerOperations {

  private ContainerOperations _delegate;
  public ContainerPOATie(ContainerOperations delegate) {
    this._delegate = delegate;
  };

  public ContainerOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/Container:1.0",
    "IDL:omg.org/CORBA/IRObject:1.0"  };

  public org.omg.CORBA.Contained lookup(java.lang.String search_name) {
    return this._delegate.lookup(
    search_name
    );
  };

  public org.omg.CORBA.Contained[] contents(org.omg.CORBA.DefinitionKind limit_type, boolean exclude_inherited) {
    return this._delegate.contents(
    limit_type, 
    exclude_inherited
    );
  };

  public org.omg.CORBA.Contained[] lookup_name(java.lang.String search_name, int levels_to_search, org.omg.CORBA.DefinitionKind limit_type, boolean exclude_inherited) {
    return this._delegate.lookup_name(
    search_name, 
    levels_to_search, 
    limit_type, 
    exclude_inherited
    );
  };

  public org.omg.CORBA.ContainerPackage.Description[] describe_contents(org.omg.CORBA.DefinitionKind limit_type, boolean exclude_inherited, int max_returned_objs) {
    return this._delegate.describe_contents(
    limit_type, 
    exclude_inherited, 
    max_returned_objs
    );
  };

  public org.omg.CORBA.ModuleDef create_module(java.lang.String id, java.lang.String name, java.lang.String version) {
    return this._delegate.create_module(
    id, 
    name, 
    version
    );
  };

  public org.omg.CORBA.ConstantDef create_constant(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.IDLType type, org.omg.CORBA.Any value) {
    return this._delegate.create_constant(
    id, 
    name, 
    version, 
    type, 
    value
    );
  };

  public org.omg.CORBA.StructDef create_struct(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.StructMember[] members) {
    return this._delegate.create_struct(
    id, 
    name, 
    version, 
    members
    );
  };

  public org.omg.CORBA.UnionDef create_union(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.IDLType discriminator_type, org.omg.CORBA.UnionMember[] members) {
    return this._delegate.create_union(
    id, 
    name, 
    version, 
    discriminator_type, 
    members
    );
  };

  public org.omg.CORBA.EnumDef create_enum(java.lang.String id, java.lang.String name, java.lang.String version, java.lang.String[] members) {
    return this._delegate.create_enum(
    id, 
    name, 
    version, 
    members
    );
  };

  public org.omg.CORBA.AliasDef create_alias(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.IDLType original_type) {
    return this._delegate.create_alias(
    id, 
    name, 
    version, 
    original_type
    );
  };

  public org.omg.CORBA.InterfaceDef create_interface(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.InterfaceDef[] base_interfaces, boolean is_abstract) {
    return this._delegate.create_interface(
    id, 
    name, 
    version, 
    base_interfaces, 
    is_abstract
    );
  };

  public org.omg.CORBA.ValueDef create_value(java.lang.String id, java.lang.String name, java.lang.String version, boolean is_custom, boolean is_abstract, org.omg.CORBA.ValueDef base_value, boolean is_truncatable, org.omg.CORBA.ValueDef[] abstract_base_values, org.omg.CORBA.InterfaceDef[] supported_interfaces, org.omg.CORBA.Initializer[] initializers) {
    return this._delegate.create_value(
    id, 
    name, 
    version, 
    is_custom, 
    is_abstract, 
    base_value, 
    is_truncatable, 
    abstract_base_values, 
    supported_interfaces, 
    initializers
    );
  };

  public org.omg.CORBA.ValueBoxDef create_value_box(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.IDLType original_type_def) {
    return this._delegate.create_value_box(
    id, 
    name, 
    version, 
    original_type_def
    );
  };

  public org.omg.CORBA.ExceptionDef create_exception(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.StructMember[] members) {
    return this._delegate.create_exception(
    id, 
    name, 
    version, 
    members
    );
  };

  public org.omg.CORBA.NativeDef create_native(java.lang.String id, java.lang.String name, java.lang.String version) {
    return this._delegate.create_native(
    id, 
    name, 
    version
    );
  };

  public org.omg.CORBA.DefinitionKind def_kind() {
    return this._delegate.def_kind();
  }

  public void destroy() {
    this._delegate.destroy(
    );
  };



}
