//
// PolicyValue.java (struct)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

public class PolicyValue
   implements org.omg.CORBA.portable.IDLEntity {

  public int ptype;
  public byte[] pvalue;

  public PolicyValue() {
  }

  public PolicyValue(int ptype, byte[] pvalue) {
    this.ptype = ptype;
    this.pvalue = pvalue;
  }

}
