//
// RepositoryIdSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class RepositoryIdSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public java.lang.String[] value; 
  public RepositoryIdSeqHolder() {
    value = new java.lang.String[0];
  }

  public RepositoryIdSeqHolder(java.lang.String[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.RepositoryIdSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.RepositoryIdSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.RepositoryIdSeqHelper.type();
  };

}
