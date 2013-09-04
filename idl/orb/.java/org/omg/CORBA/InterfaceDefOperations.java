//
// InterfaceDef.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public interface InterfaceDefOperations
   extends org.omg.CORBA.ContainerOperations, org.omg.CORBA.ContainedOperations, org.omg.CORBA.IDLTypeOperations {

  org.omg.CORBA.InterfaceDef[] base_interfaces();
  void base_interfaces(org.omg.CORBA.InterfaceDef[] value);


  boolean is_abstract();
  void is_abstract(boolean value);


  boolean is_a(java.lang.String interface_id);

  org.omg.CORBA.InterfaceDefPackage.FullInterfaceDescription describe_interface();

  org.omg.CORBA.AttributeDef create_attribute(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.IDLType type, org.omg.CORBA.AttributeMode mode);

  org.omg.CORBA.OperationDef create_operation(java.lang.String id, java.lang.String name, java.lang.String version, org.omg.CORBA.IDLType result, org.omg.CORBA.OperationMode mode, org.omg.CORBA.ParameterDescription[] params, org.omg.CORBA.ExceptionDef[] exceptions, java.lang.String[] contexts);


}
