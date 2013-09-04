//
// SelectorValue.java (struct)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class SelectorValue
   implements org.omg.CORBA.portable.IDLEntity {

  public int selector;
  public org.omg.CORBA.Any value;

  public SelectorValue() {
  }

  public SelectorValue(int selector, org.omg.CORBA.Any value) {
    this.selector = selector;
    this.value = value;
  }

}
