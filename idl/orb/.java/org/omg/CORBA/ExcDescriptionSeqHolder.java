//
// ExcDescriptionSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class ExcDescriptionSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.ExceptionDescription[] value; 
  public ExcDescriptionSeqHolder() {
    value = new org.omg.CORBA.ExceptionDescription[0];
  }

  public ExcDescriptionSeqHolder(org.omg.CORBA.ExceptionDescription[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.ExcDescriptionSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.ExcDescriptionSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.ExcDescriptionSeqHelper.type();
  };

}
