//
// IntervalTHolder.java (holder)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.TimeBase;

final public class IntervalTHolder
   implements org.omg.CORBA.portable.Streamable {

  public IntervalT value; 
  public IntervalTHolder() {
  }

  public IntervalTHolder(IntervalT initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.TimeBase.IntervalTHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.TimeBase.IntervalTHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.TimeBase.IntervalTHelper.type();
  };

}
