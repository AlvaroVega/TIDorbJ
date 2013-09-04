//
// TaggedComponentSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

final public class TaggedComponentSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.IOP.TaggedComponent[] value; 
  public TaggedComponentSeqHolder() {
    value = new org.omg.IOP.TaggedComponent[0];
  }

  public TaggedComponentSeqHolder(org.omg.IOP.TaggedComponent[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.IOP.TaggedComponentSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.IOP.TaggedComponentSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.IOP.TaggedComponentSeqHelper.type();
  };

}
