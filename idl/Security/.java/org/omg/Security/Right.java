//
// Right.java (struct)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class Right
   implements org.omg.CORBA.portable.IDLEntity {

  public org.omg.Security.ExtensibleFamily rights_family;
  public java.lang.String the_right;

  public Right() {
    the_right = "";
  }

  public Right(org.omg.Security.ExtensibleFamily rights_family, java.lang.String the_right) {
    this.rights_family = rights_family;
    this.the_right = the_right;
  }

}
