//
// DomainManagerListHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class DomainManagerListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.DomainManager[] value; 
  public DomainManagerListHolder() {
    value = new org.omg.CORBA.DomainManager[0];
  }

  public DomainManagerListHolder(org.omg.CORBA.DomainManager[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.DomainManagerListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.DomainManagerListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.DomainManagerListHelper.type();
  };

}
