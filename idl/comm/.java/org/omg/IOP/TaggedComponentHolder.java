//
// TaggedComponentHolder.java (holder)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

final public class TaggedComponentHolder
   implements org.omg.CORBA.portable.Streamable {

  public TaggedComponent value; 
  public TaggedComponentHolder() {
  }

  public TaggedComponentHolder(TaggedComponent initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.IOP.TaggedComponentHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.IOP.TaggedComponentHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.IOP.TaggedComponentHelper.type();
  };

}
