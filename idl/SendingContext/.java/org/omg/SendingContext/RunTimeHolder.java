//
// RunTimeHolder.java (holder)
//
// File generated: Thu May 19 07:31:40 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SendingContext;

final public class RunTimeHolder
   implements org.omg.CORBA.portable.Streamable {

  public RunTime value; 
  public RunTimeHolder() {
  }

  public RunTimeHolder(RunTime initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.SendingContext.RunTimeHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.SendingContext.RunTimeHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.SendingContext.RunTimeHelper.type();
  };

}
