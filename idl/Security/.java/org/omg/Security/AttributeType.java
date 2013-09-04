//
// AttributeType.java (struct)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class AttributeType
   implements org.omg.CORBA.portable.IDLEntity {

  public org.omg.Security.ExtensibleFamily attribute_family;
  public int attribute_type;

  public AttributeType() {
  }

  public AttributeType(org.omg.Security.ExtensibleFamily attribute_family, int attribute_type) {
    this.attribute_family = attribute_family;
    this.attribute_type = attribute_type;
  }

}
