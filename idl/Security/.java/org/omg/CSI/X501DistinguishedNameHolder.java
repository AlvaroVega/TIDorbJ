//
// X501DistinguishedNameHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

final public class X501DistinguishedNameHolder
   implements org.omg.CORBA.portable.Streamable {

  public byte[] value; 
  public X501DistinguishedNameHolder() {
    value = new byte[0];
  }

  public X501DistinguishedNameHolder(byte[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CSI.X501DistinguishedNameHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CSI.X501DistinguishedNameHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CSI.X501DistinguishedNameHelper.type();
  };

}
