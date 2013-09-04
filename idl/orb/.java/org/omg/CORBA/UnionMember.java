//
// UnionMember.java (struct)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class UnionMember
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String name;
  public org.omg.CORBA.Any label;
  public org.omg.CORBA.TypeCode type;
  public org.omg.CORBA.IDLType type_def;

  public UnionMember() {
    name = "";
  }

  public UnionMember(java.lang.String name, org.omg.CORBA.Any label, org.omg.CORBA.TypeCode type, org.omg.CORBA.IDLType type_def) {
    this.name = name;
    this.label = label;
    this.type = type;
    this.type_def = type_def;
  }

}
