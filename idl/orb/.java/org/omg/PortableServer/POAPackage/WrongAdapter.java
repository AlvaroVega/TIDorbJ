//
// WrongAdapter.java (exception)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer.POAPackage;

final public class WrongAdapter
   extends org.omg.CORBA.UserException {


  public WrongAdapter() {
    super(WrongAdapterHelper.id());
  }

  public WrongAdapter(String reason) {
    super(WrongAdapterHelper.id()+" "+reason);

  }

}
