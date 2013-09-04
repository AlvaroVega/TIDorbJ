//
// NameComponent.java (struct)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming;

public class NameComponent
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String id;
  public java.lang.String kind;

  public NameComponent() {
    id = "";
    kind = "";
  }

  public NameComponent(java.lang.String id, java.lang.String kind) {
    this.id = id;
    this.kind = kind;
  }

}
