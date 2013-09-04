//
// AttrDescriptionSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class AttrDescriptionSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.AttributeDescription[] value; 
  public AttrDescriptionSeqHolder() {
    value = new org.omg.CORBA.AttributeDescription[0];
  }

  public AttrDescriptionSeqHolder(org.omg.CORBA.AttributeDescription[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.AttrDescriptionSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.AttrDescriptionSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.AttrDescriptionSeqHelper.type();
  };

}
