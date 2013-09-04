//
// ServiceConfigurationListHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

final public class ServiceConfigurationListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CSIIOP.ServiceConfiguration[] value; 
  public ServiceConfigurationListHolder() {
    value = new org.omg.CSIIOP.ServiceConfiguration[0];
  }

  public ServiceConfigurationListHolder(org.omg.CSIIOP.ServiceConfiguration[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CSIIOP.ServiceConfigurationListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CSIIOP.ServiceConfigurationListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CSIIOP.ServiceConfigurationListHelper.type();
  };

}
