//
// SecAttribute.java (struct)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class SecAttribute
   implements org.omg.CORBA.portable.IDLEntity {

  public org.omg.Security.AttributeType attribute_type;
  public byte[] defining_authority;
  public byte[] value;

  public SecAttribute() {
  }

  public SecAttribute(org.omg.Security.AttributeType attribute_type, byte[] defining_authority, byte[] value) {
    this.attribute_type = attribute_type;
    this.defining_authority = defining_authority;
    this.value = value;
  }

}
