//
// StructMember.java (struct)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class StructMember
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String name;
  public org.omg.CORBA.TypeCode type;
  public org.omg.CORBA.IDLType type_def;

  public StructMember() {
    name = "";
  }

  public StructMember(java.lang.String name, org.omg.CORBA.TypeCode type, org.omg.CORBA.IDLType type_def) {
    this.name = name;
    this.type = type;
    this.type_def = type_def;
  }

}
