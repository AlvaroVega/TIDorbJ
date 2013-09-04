//
// IntervalT.java (struct)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.TimeBase;

public class IntervalT
   implements org.omg.CORBA.portable.IDLEntity {

  public long lower_bound;
  public long upper_bound;

  public IntervalT() {
  }

  public IntervalT(long lower_bound, long upper_bound) {
    this.lower_bound = lower_bound;
    this.upper_bound = upper_bound;
  }

}
