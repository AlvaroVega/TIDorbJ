//
// IOR.java (struct)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

public class IOR
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String type_id;
  public org.omg.IOP.TaggedProfile[] profiles;

  public IOR() {
    type_id = "";
  }

  public IOR(java.lang.String type_id, org.omg.IOP.TaggedProfile[] profiles) {
    this.type_id = type_id;
    this.profiles = profiles;
  }

}
