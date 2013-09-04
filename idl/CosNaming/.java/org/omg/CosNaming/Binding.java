//
// Binding.java (struct)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming;

public class Binding
   implements org.omg.CORBA.portable.IDLEntity {

  public org.omg.CosNaming.NameComponent[] binding_name;
  public org.omg.CosNaming.BindingType binding_type;

  public Binding() {
  }

  public Binding(org.omg.CosNaming.NameComponent[] binding_name, org.omg.CosNaming.BindingType binding_type) {
    this.binding_name = binding_name;
    this.binding_type = binding_type;
  }

}
