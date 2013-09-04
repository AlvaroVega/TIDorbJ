//
// BiDirIIOPServiceContext.java (struct)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IIOP;

public class BiDirIIOPServiceContext
   implements org.omg.CORBA.portable.IDLEntity {

  public org.omg.IIOP.ListenPoint[] listen_points;

  public BiDirIIOPServiceContext() {
  }

  public BiDirIIOPServiceContext(org.omg.IIOP.ListenPoint[] listen_points) {
    this.listen_points = listen_points;
  }

}
