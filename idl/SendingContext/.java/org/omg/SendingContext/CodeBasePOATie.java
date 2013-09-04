//
// CodeBasePOATie.java (tie)
//
// File generated: Thu May 19 07:31:40 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SendingContext;

public class CodeBasePOATie
 extends CodeBasePOA
 implements CodeBaseOperations {

  private CodeBaseOperations _delegate;
  public CodeBasePOATie(CodeBaseOperations delegate) {
    this._delegate = delegate;
  };

  public CodeBaseOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/SendingContext/CodeBase:1.0",
    "IDL:omg.org/SendingContext/RunTime:1.0"  };

  public org.omg.CORBA.Repository get_ir() {
    return this._delegate.get_ir(
    );
  };

  public java.lang.String implementation(java.lang.String x) {
    return this._delegate.implementation(
    x
    );
  };

  public java.lang.String[] implementations(java.lang.String[] x) {
    return this._delegate.implementations(
    x
    );
  };

  public org.omg.CORBA.ValueDefPackage.FullValueDescription meta(java.lang.String x) {
    return this._delegate.meta(
    x
    );
  };

  public org.omg.CORBA.ValueDefPackage.FullValueDescription[] metas(java.lang.String[] x) {
    return this._delegate.metas(
    x
    );
  };

  public java.lang.String[] bases(java.lang.String x) {
    return this._delegate.bases(
    x
    );
  };



}
