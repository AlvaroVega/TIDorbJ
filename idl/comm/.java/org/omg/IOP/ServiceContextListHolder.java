//
// ServiceContextListHolder.java (holder)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

final public class ServiceContextListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.IOP.ServiceContext[] value; 
  public ServiceContextListHolder() {
    value = new org.omg.IOP.ServiceContext[0];
  }

  public ServiceContextListHolder(org.omg.IOP.ServiceContext[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.IOP.ServiceContextListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.IOP.ServiceContextListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.IOP.ServiceContextListHelper.type();
  };

}
