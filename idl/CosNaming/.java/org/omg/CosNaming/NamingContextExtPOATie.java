//
// NamingContextExtPOATie.java (tie)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming;

public class NamingContextExtPOATie
 extends NamingContextExtPOA
 implements NamingContextExtOperations {

  private NamingContextExtOperations _delegate;
  public NamingContextExtPOATie(NamingContextExtOperations delegate) {
    this._delegate = delegate;
  };

  public NamingContextExtOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CosNaming/NamingContextExt:1.0",
    "IDL:omg.org/CosNaming/NamingContext:1.0"  };

  public java.lang.String to_string(org.omg.CosNaming.NameComponent[] n)
    throws org.omg.CosNaming.NamingContextPackage.InvalidName {
    return this._delegate.to_string(
    n
    );
  };

  public org.omg.CosNaming.NameComponent[] to_name(java.lang.String sn)
    throws org.omg.CosNaming.NamingContextPackage.InvalidName {
    return this._delegate.to_name(
    sn
    );
  };

  public java.lang.String to_url(java.lang.String addr, java.lang.String sn)
    throws org.omg.CosNaming.NamingContextExtPackage.InvalidAddress, org.omg.CosNaming.NamingContextPackage.InvalidName {
    return this._delegate.to_url(
    addr, 
    sn
    );
  };

  public org.omg.CORBA.Object resolve_str(java.lang.String sn)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
    return this._delegate.resolve_str(
    sn
    );
  };

  public void bind(org.omg.CosNaming.NameComponent[] n, org.omg.CORBA.Object obj)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, org.omg.CosNaming.NamingContextPackage.AlreadyBound {
    this._delegate.bind(
    n, 
    obj
    );
  };

  public void rebind(org.omg.CosNaming.NameComponent[] n, org.omg.CORBA.Object obj)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
    this._delegate.rebind(
    n, 
    obj
    );
  };

  public void bind_context(org.omg.CosNaming.NameComponent[] n, org.omg.CosNaming.NamingContext nc)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, org.omg.CosNaming.NamingContextPackage.AlreadyBound {
    this._delegate.bind_context(
    n, 
    nc
    );
  };

  public void rebind_context(org.omg.CosNaming.NameComponent[] n, org.omg.CosNaming.NamingContext nc)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
    this._delegate.rebind_context(
    n, 
    nc
    );
  };

  public org.omg.CORBA.Object resolve(org.omg.CosNaming.NameComponent[] n)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
    return this._delegate.resolve(
    n
    );
  };

  public void unbind(org.omg.CosNaming.NameComponent[] n)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
    this._delegate.unbind(
    n
    );
  };

  public org.omg.CosNaming.NamingContext new_context() {
    return this._delegate.new_context(
    );
  };

  public org.omg.CosNaming.NamingContext bind_new_context(org.omg.CosNaming.NameComponent[] n)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.AlreadyBound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {
    return this._delegate.bind_new_context(
    n
    );
  };

  public void destroy()
    throws org.omg.CosNaming.NamingContextPackage.NotEmpty {
    this._delegate.destroy(
    );
  };

  public void list(int how_many, org.omg.CosNaming.BindingListHolder bl, org.omg.CosNaming.BindingIteratorHolder bi) {
    this._delegate.list(
    how_many, 
    bl, 
    bi
    );
  };



}
