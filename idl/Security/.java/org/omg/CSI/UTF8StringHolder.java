//
// UTF8StringHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

final public class UTF8StringHolder
   implements org.omg.CORBA.portable.Streamable {

  public byte[] value; 
  public UTF8StringHolder() {
    value = new byte[0];
  }

  public UTF8StringHolder(byte[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CSI.UTF8StringHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CSI.UTF8StringHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CSI.UTF8StringHelper.type();
  };

}
