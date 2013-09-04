//
// NamingContextExt.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming;

public interface NamingContextExtOperations
   extends org.omg.CosNaming.NamingContextOperations {

  java.lang.String to_string(org.omg.CosNaming.NameComponent[] n)
    throws org.omg.CosNaming.NamingContextPackage.InvalidName;

  org.omg.CosNaming.NameComponent[] to_name(java.lang.String sn)
    throws org.omg.CosNaming.NamingContextPackage.InvalidName;

  java.lang.String to_url(java.lang.String addr, java.lang.String sn)
    throws org.omg.CosNaming.NamingContextExtPackage.InvalidAddress, org.omg.CosNaming.NamingContextPackage.InvalidName;

  org.omg.CORBA.Object resolve_str(java.lang.String sn)
    throws org.omg.CosNaming.NamingContextPackage.NotFound, org.omg.CosNaming.NamingContextPackage.CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName;


}
