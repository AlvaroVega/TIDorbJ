//
// InterfaceDescription.java (struct)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class InterfaceDescription
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String name;
  public java.lang.String id;
  public java.lang.String defined_in;
  public java.lang.String version;
  public java.lang.String[] base_interfaces;
  public boolean is_abstract;

  public InterfaceDescription() {
    name = "";
    id = "";
    defined_in = "";
    version = "";
    base_interfaces = new String[0];
  }

  public InterfaceDescription(java.lang.String name, java.lang.String id, java.lang.String defined_in, java.lang.String version, java.lang.String[] base_interfaces, boolean is_abstract) {
    this.name = name;
    this.id = id;
    this.defined_in = defined_in;
    this.version = version;
    this.base_interfaces = base_interfaces;
    this.is_abstract = is_abstract;
  }

}
