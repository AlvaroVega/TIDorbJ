//
// UtcTHolder.java (holder)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.TimeBase;

final public class UtcTHolder
   implements org.omg.CORBA.portable.Streamable {

  public UtcT value; 
  public UtcTHolder() {
  }

  public UtcTHolder(UtcT initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.TimeBase.UtcTHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.TimeBase.UtcTHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.TimeBase.UtcTHelper.type();
  };

}
