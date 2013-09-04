//
// FullInterfaceDescription.java (struct)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA.InterfaceDefPackage;

public class FullInterfaceDescription
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String name;
  public java.lang.String id;
  public java.lang.String defined_in;
  public java.lang.String version;
  public org.omg.CORBA.OperationDescription[] operations;
  public org.omg.CORBA.AttributeDescription[] attributes;
  public java.lang.String[] base_interfaces;
  public org.omg.CORBA.TypeCode type;
  public boolean is_abstract;

  public FullInterfaceDescription() {
    name = "";
    id = "";
    defined_in = "";
    version = "";
    base_interfaces = new String[0];
  }

  public FullInterfaceDescription(java.lang.String name, java.lang.String id, java.lang.String defined_in, java.lang.String version, org.omg.CORBA.OperationDescription[] operations, org.omg.CORBA.AttributeDescription[] attributes, java.lang.String[] base_interfaces, org.omg.CORBA.TypeCode type, boolean is_abstract) {
    this.name = name;
    this.id = id;
    this.defined_in = defined_in;
    this.version = version;
    this.operations = operations;
    this.attributes = attributes;
    this.base_interfaces = base_interfaces;
    this.type = type;
    this.is_abstract = is_abstract;
  }

}
