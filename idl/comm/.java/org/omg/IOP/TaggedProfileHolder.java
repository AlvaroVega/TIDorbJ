//
// TaggedProfileHolder.java (holder)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

final public class TaggedProfileHolder
   implements org.omg.CORBA.portable.Streamable {

  public TaggedProfile value; 
  public TaggedProfileHolder() {
  }

  public TaggedProfileHolder(TaggedProfile initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.IOP.TaggedProfileHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.IOP.TaggedProfileHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.IOP.TaggedProfileHelper.type();
  };

}
