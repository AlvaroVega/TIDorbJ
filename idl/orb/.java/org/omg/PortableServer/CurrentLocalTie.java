//
// CurrentLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer;

public class CurrentLocalTie
 extends CurrentLocalBase
 {

  private CurrentOperations _delegate;
  public CurrentLocalTie(CurrentOperations delegate) {
    this._delegate = delegate;
  };

  public CurrentOperations _delegate() {
    return this._delegate;
  };

  public org.omg.PortableServer.POA get_POA()
    throws org.omg.PortableServer.CurrentPackage.NoContext {
    return this._delegate.get_POA(
    );
  };

  public byte[] get_object_id()
    throws org.omg.PortableServer.CurrentPackage.NoContext {
    return this._delegate.get_object_id(
    );
  };

  public org.omg.CORBA.Object get_reference()
    throws org.omg.PortableServer.CurrentPackage.NoContext {
    return this._delegate.get_reference(
    );
  };

  public org.omg.PortableServer.Servant get_servant()
    throws org.omg.PortableServer.CurrentPackage.NoContext {
    return this._delegate.get_servant(
    );
  };



}
