//
// NamingContext.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming;

public interface NamingContextOperations {

  void bind(org.omg.CosNaming.NameComponent[] n, org.omg.CORBA.Object obj)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, org.omg.CosNaming.NamingContextPackage.AlreadyBound;

  void rebind(org.omg.CosNaming.NameComponent[] n, org.omg.CORBA.Object obj)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName;

  void bind_context(org.omg.CosNaming.NameComponent[] n, org.omg.CosNaming.NamingContext nc)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, org.omg.CosNaming.NamingContextPackage.AlreadyBound;

  void rebind_context(org.omg.CosNaming.NameComponent[] n, org.omg.CosNaming.NamingContext nc)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName;

  org.omg.CORBA.Object resolve(org.omg.CosNaming.NameComponent[] n)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName;

  void unbind(org.omg.CosNaming.NameComponent[] n)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName;

  org.omg.CosNaming.NamingContext new_context();

  org.omg.CosNaming.NamingContext bind_new_context(org.omg.CosNaming.NameComponent[] n)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.AlreadyBound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName;

  void destroy()
    throws org.omg.CosNaming.NamingContextPackage.NotEmpty;

  void list(int how_many, org.omg.CosNaming.BindingListHolder bl, org.omg.CosNaming.BindingIteratorHolder bi);


}
