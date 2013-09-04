//
// AuthorizationElementHolder.java (holder)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

final public class AuthorizationElementHolder
   implements org.omg.CORBA.portable.Streamable {

  public AuthorizationElement value; 
  public AuthorizationElementHolder() {
  }

  public AuthorizationElementHolder(AuthorizationElement initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CSI.AuthorizationElementHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CSI.AuthorizationElementHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CSI.AuthorizationElementHelper.type();
  };

}
