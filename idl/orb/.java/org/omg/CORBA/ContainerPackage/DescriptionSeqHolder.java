//
// DescriptionSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA.ContainerPackage;

final public class DescriptionSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.ContainerPackage.Description[] value; 
  public DescriptionSeqHolder() {
    value = new org.omg.CORBA.ContainerPackage.Description[0];
  }

  public DescriptionSeqHolder(org.omg.CORBA.ContainerPackage.Description[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.ContainerPackage.DescriptionSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.ContainerPackage.DescriptionSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.ContainerPackage.DescriptionSeqHelper.type();
  };

}
