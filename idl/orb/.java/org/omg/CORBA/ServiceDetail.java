//
// ServiceDetail.java (struct)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class ServiceDetail
   implements org.omg.CORBA.portable.IDLEntity {

  public int service_detail_type;
  public byte[] service_detail;

  public ServiceDetail() {
  }

  public ServiceDetail(int service_detail_type, byte[] service_detail) {
    this.service_detail_type = service_detail_type;
    this.service_detail = service_detail;
  }

}
