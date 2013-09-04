//
// ServantLocatorLocalTie.java (tie)
//
// File generated: Mon May 03 12:56:49 CEST 2004
//   by TIDorbJ idl2Java 1.0.3
//

package org.omg.PortableServer;

public class ServantLocatorLocalTie
 extends ServantLocatorLocalBase
 {

  private ServantLocatorOperations _delegate;
  public ServantLocatorLocalTie(ServantLocatorOperations delegate) {
    this._delegate = delegate;
  };

  public ServantLocatorOperations _delegate() {
    return this._delegate;
  };

  public org.omg.PortableServer.Servant preinvoke(byte[] oid, org.omg.PortableServer.POA adapter, java.lang.String operation, org.omg.PortableServer.ServantLocatorPackage.CookieHolder the_cookie)
    throws org.omg.PortableServer.ForwardRequest {
    return this._delegate.preinvoke(
    oid, 
    adapter, 
    operation, 
    the_cookie
    );
  };

  public void postinvoke(byte[] oid, org.omg.PortableServer.POA adapter, java.lang.String operation, java.lang.Object the_cookie, org.omg.PortableServer.Servant the_servant) {
    this._delegate.postinvoke(
    oid, 
    adapter, 
    operation, 
    the_cookie, 
    the_servant
    );
  };



}
