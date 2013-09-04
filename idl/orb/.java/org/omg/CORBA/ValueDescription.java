//
// ValueDescription.java (struct)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class ValueDescription
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String name;
  public java.lang.String id;
  public boolean is_abstract;
  public boolean is_custom;
  public java.lang.String defined_in;
  public java.lang.String version;
  public java.lang.String[] supported_interfaces;
  public java.lang.String[] abstract_base_values;
  public boolean is_truncatable;
  public java.lang.String base_value;

  public ValueDescription() {
    name = "";
    id = "";
    defined_in = "";
    version = "";
    supported_interfaces = new String[0];
    abstract_base_values = new String[0];
    base_value = "";
  }

  public ValueDescription(java.lang.String name, java.lang.String id, boolean is_abstract, boolean is_custom, java.lang.String defined_in, java.lang.String version, java.lang.String[] supported_interfaces, java.lang.String[] abstract_base_values, boolean is_truncatable, java.lang.String base_value) {
    this.name = name;
    this.id = id;
    this.is_abstract = is_abstract;
    this.is_custom = is_custom;
    this.defined_in = defined_in;
    this.version = version;
    this.supported_interfaces = supported_interfaces;
    this.abstract_base_values = abstract_base_values;
    this.is_truncatable = is_truncatable;
    this.base_value = base_value;
  }

}
