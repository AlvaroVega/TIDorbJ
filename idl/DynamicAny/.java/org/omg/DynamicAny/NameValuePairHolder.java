//
// NameValuePairHolder.java (holder)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny;

final public class NameValuePairHolder
   implements org.omg.CORBA.portable.Streamable {

  public NameValuePair value; 
  public NameValuePairHolder() {
  }

  public NameValuePairHolder(NameValuePair initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.DynamicAny.NameValuePairHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.DynamicAny.NameValuePairHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.DynamicAny.NameValuePairHelper.type();
  };

}
