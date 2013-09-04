//
// X509CertificateChainHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

final public class X509CertificateChainHolder
   implements org.omg.CORBA.portable.Streamable {

  public byte[] value; 
  public X509CertificateChainHolder() {
    value = new byte[0];
  }

  public X509CertificateChainHolder(byte[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CSI.X509CertificateChainHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CSI.X509CertificateChainHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CSI.X509CertificateChainHelper.type();
  };

}
