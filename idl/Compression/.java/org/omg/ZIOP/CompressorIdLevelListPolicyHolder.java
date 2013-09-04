//
// CompressorIdLevelListPolicyHolder.java (holder)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.ZIOP;

final public class CompressorIdLevelListPolicyHolder
   implements org.omg.CORBA.portable.Streamable {

  public CompressorIdLevelListPolicy value; 
  public CompressorIdLevelListPolicyHolder() {
  }

  public CompressorIdLevelListPolicyHolder(CompressorIdLevelListPolicy initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.ZIOP.CompressorIdLevelListPolicyHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.ZIOP.CompressorIdLevelListPolicyHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.ZIOP.CompressorIdLevelListPolicyHelper.type();
  };

}
