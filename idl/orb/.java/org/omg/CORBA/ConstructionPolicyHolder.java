//
// ConstructionPolicyHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class ConstructionPolicyHolder
   implements org.omg.CORBA.portable.Streamable {

  public ConstructionPolicy value; 
  public ConstructionPolicyHolder() {
  }

  public ConstructionPolicyHolder(ConstructionPolicy initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.ConstructionPolicyHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.ConstructionPolicyHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.ConstructionPolicyHelper.type();
  };

}
