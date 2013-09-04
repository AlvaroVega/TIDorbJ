//
// CompressionLowValuePolicyHolder.java (holder)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.ZIOP;

final public class CompressionLowValuePolicyHolder
   implements org.omg.CORBA.portable.Streamable {

  public CompressionLowValuePolicy value; 
  public CompressionLowValuePolicyHolder() {
  }

  public CompressionLowValuePolicyHolder(CompressionLowValuePolicy initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.ZIOP.CompressionLowValuePolicyHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.ZIOP.CompressionLowValuePolicyHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.ZIOP.CompressionLowValuePolicyHelper.type();
  };

}
