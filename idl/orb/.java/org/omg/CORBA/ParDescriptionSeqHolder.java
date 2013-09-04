//
// ParDescriptionSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class ParDescriptionSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.ParameterDescription[] value; 
  public ParDescriptionSeqHolder() {
    value = new org.omg.CORBA.ParameterDescription[0];
  }

  public ParDescriptionSeqHolder(org.omg.CORBA.ParameterDescription[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.ParDescriptionSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.ParDescriptionSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.ParDescriptionSeqHelper.type();
  };

}
