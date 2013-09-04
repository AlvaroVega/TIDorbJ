//
// PriorityRange.java (struct)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

public class PriorityRange
   implements org.omg.CORBA.portable.IDLEntity {

  public short min;
  public short max;

  public PriorityRange() {
  }

  public PriorityRange(short min, short max) {
    this.min = min;
    this.max = max;
  }

}
