//
// StringDefPOATie.java (tie)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class StringDefPOATie
 extends StringDefPOA
 implements StringDefOperations {

  private StringDefOperations _delegate;
  public StringDefPOATie(StringDefOperations delegate) {
    this._delegate = delegate;
  };

  public StringDefOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/StringDef:1.0",
    "IDL:omg.org/CORBA/IDLType:1.0",
    "IDL:omg.org/CORBA/IRObject:1.0"  };

  public int bound() {
    return this._delegate.bound();
  }

  public void bound(int value) {
    this._delegate.bound(value);
  }

  public org.omg.CORBA.TypeCode type() {
    return this._delegate.type();
  }

  public org.omg.CORBA.DefinitionKind def_kind() {
    return this._delegate.def_kind();
  }

  public void destroy() {
    this._delegate.destroy(
    );
  };




}
