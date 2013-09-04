//
// AttributeListHolder.java (holder)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

final public class AttributeListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.Security.SecAttribute[] value; 
  public AttributeListHolder() {
    value = new org.omg.Security.SecAttribute[0];
  }

  public AttributeListHolder(org.omg.Security.SecAttribute[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Security.AttributeListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Security.AttributeListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Security.AttributeListHelper.type();
  };

}
