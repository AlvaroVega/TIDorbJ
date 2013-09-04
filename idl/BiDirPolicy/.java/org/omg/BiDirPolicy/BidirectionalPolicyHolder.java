//
// BidirectionalPolicyHolder.java (holder)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.BiDirPolicy;

final public class BidirectionalPolicyHolder
   implements org.omg.CORBA.portable.Streamable {

  public BidirectionalPolicy value; 
  public BidirectionalPolicyHolder() {
  }

  public BidirectionalPolicyHolder(BidirectionalPolicy initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.BiDirPolicy.BidirectionalPolicyHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.BiDirPolicy.BidirectionalPolicyHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.BiDirPolicy.BidirectionalPolicyHelper.type();
  };

}
