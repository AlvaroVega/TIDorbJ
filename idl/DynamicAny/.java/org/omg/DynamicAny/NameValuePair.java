//
// NameValuePair.java (struct)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny;

public class NameValuePair
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String id;
  public org.omg.CORBA.Any value;

  public NameValuePair() {
    id = "";
  }

  public NameValuePair(java.lang.String id, org.omg.CORBA.Any value) {
    this.id = id;
    this.value = value;
  }

}
