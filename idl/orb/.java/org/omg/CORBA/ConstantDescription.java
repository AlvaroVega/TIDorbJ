//
// ConstantDescription.java (struct)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class ConstantDescription
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String name;
  public java.lang.String id;
  public java.lang.String defined_in;
  public java.lang.String version;
  public org.omg.CORBA.TypeCode type;
  public org.omg.CORBA.Any value;

  public ConstantDescription() {
    name = "";
    id = "";
    defined_in = "";
    version = "";
  }

  public ConstantDescription(java.lang.String name, java.lang.String id, java.lang.String defined_in, java.lang.String version, org.omg.CORBA.TypeCode type, org.omg.CORBA.Any value) {
    this.name = name;
    this.id = id;
    this.defined_in = defined_in;
    this.version = version;
    this.type = type;
    this.value = value;
  }

}
