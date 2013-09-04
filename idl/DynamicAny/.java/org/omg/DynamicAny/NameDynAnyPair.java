//
// NameDynAnyPair.java (struct)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny;

public class NameDynAnyPair
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String id;
  public org.omg.DynamicAny.DynAny value;

  public NameDynAnyPair() {
    id = "";
  }

  public NameDynAnyPair(java.lang.String id, org.omg.DynamicAny.DynAny value) {
    this.id = id;
    this.value = value;
  }

}
