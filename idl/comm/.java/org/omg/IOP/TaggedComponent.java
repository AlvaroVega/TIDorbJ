//
// TaggedComponent.java (struct)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

public class TaggedComponent
   implements org.omg.CORBA.portable.IDLEntity {

  public int tag;
  public byte[] component_data;

  public TaggedComponent() {
  }

  public TaggedComponent(int tag, byte[] component_data) {
    this.tag = tag;
    this.component_data = component_data;
  }

}
