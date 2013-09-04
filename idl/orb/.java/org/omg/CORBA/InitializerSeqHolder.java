//
// InitializerSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class InitializerSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.Initializer[] value; 
  public InitializerSeqHolder() {
    value = new org.omg.CORBA.Initializer[0];
  }

  public InitializerSeqHolder(org.omg.CORBA.Initializer[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.InitializerSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.InitializerSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.InitializerSeqHelper.type();
  };

}
