//
// ServiceConfiguration.java (struct)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

public class ServiceConfiguration
   implements org.omg.CORBA.portable.IDLEntity {

  public int syntax;
  public byte[] name;

  public ServiceConfiguration() {
  }

  public ServiceConfiguration(int syntax, byte[] name) {
    this.syntax = syntax;
    this.name = name;
  }

}
