//
// ServiceInformation.java (struct)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class ServiceInformation
   implements org.omg.CORBA.portable.IDLEntity {

  public int[] service_options;
  public org.omg.CORBA.ServiceDetail[] service_details;

  public ServiceInformation() {
  }

  public ServiceInformation(int[] service_options, org.omg.CORBA.ServiceDetail[] service_details) {
    this.service_options = service_options;
    this.service_details = service_details;
  }

}
