//
// OptionsDirectionPairListHolder.java (holder)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

final public class OptionsDirectionPairListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.Security.OptionsDirectionPair[] value; 
  public OptionsDirectionPairListHolder() {
    value = new org.omg.Security.OptionsDirectionPair[0];
  }

  public OptionsDirectionPairListHolder(org.omg.Security.OptionsDirectionPair[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Security.OptionsDirectionPairListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Security.OptionsDirectionPairListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Security.OptionsDirectionPairListHelper.type();
  };

}
