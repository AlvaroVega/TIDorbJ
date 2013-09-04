//
// RoutingTypeRange.java (struct)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

public class RoutingTypeRange
   implements org.omg.CORBA.portable.IDLEntity {

  public short min;
  public short max;

  public RoutingTypeRange() {
  }

  public RoutingTypeRange(short min, short max) {
    this.min = min;
    this.max = max;
  }

}
