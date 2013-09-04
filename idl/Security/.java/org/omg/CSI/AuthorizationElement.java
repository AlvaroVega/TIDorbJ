//
// AuthorizationElement.java (struct)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

public class AuthorizationElement
   implements org.omg.CORBA.portable.IDLEntity {

  public int the_type;
  public byte[] the_element;

  public AuthorizationElement() {
  }

  public AuthorizationElement(int the_type, byte[] the_element) {
    this.the_type = the_type;
    this.the_element = the_element;
  }

}
