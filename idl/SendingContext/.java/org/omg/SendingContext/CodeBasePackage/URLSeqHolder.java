//
// URLSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:40 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SendingContext.CodeBasePackage;

final public class URLSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public java.lang.String[] value; 
  public URLSeqHolder() {
    value = new java.lang.String[0];
  }

  public URLSeqHolder(java.lang.String[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.SendingContext.CodeBasePackage.URLSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.SendingContext.CodeBasePackage.URLSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.SendingContext.CodeBasePackage.URLSeqHelper.type();
  };

}
