//
// ExtensibleFamily.java (struct)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class ExtensibleFamily
   implements org.omg.CORBA.portable.IDLEntity {

  public short family_definer;
  public short family;

  public ExtensibleFamily() {
  }

  public ExtensibleFamily(short family_definer, short family) {
    this.family_definer = family_definer;
    this.family = family;
  }

}
