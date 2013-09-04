//
// ValueDescSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:40 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SendingContext.CodeBasePackage;

final public class ValueDescSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.ValueDefPackage.FullValueDescription[] value; 
  public ValueDescSeqHolder() {
    value = new org.omg.CORBA.ValueDefPackage.FullValueDescription[0];
  }

  public ValueDescSeqHolder(org.omg.CORBA.ValueDefPackage.FullValueDescription[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper.type();
  };

}
