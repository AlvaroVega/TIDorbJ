//
// ProfileBody_1_0Holder.java (holder)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IIOP;

final public class ProfileBody_1_0Holder
   implements org.omg.CORBA.portable.Streamable {

  public ProfileBody_1_0 value; 
  public ProfileBody_1_0Holder() {
  }

  public ProfileBody_1_0Holder(ProfileBody_1_0 initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.IIOP.ProfileBody_1_0Helper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.IIOP.ProfileBody_1_0Helper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.IIOP.ProfileBody_1_0Helper.type();
  };

}
