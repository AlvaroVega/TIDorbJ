//
// AttributeModeHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class AttributeModeHolder
   implements org.omg.CORBA.portable.Streamable {

  public AttributeMode value; 
  public AttributeModeHolder() {
  }

  public AttributeModeHolder(AttributeMode initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.AttributeModeHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.AttributeModeHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.AttributeModeHelper.type();
  };

}
