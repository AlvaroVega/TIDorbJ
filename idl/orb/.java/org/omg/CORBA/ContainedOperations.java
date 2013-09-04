//
// Contained.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public interface ContainedOperations
   extends org.omg.CORBA.IRObjectOperations {

  java.lang.String id();
  void id(java.lang.String value);


  java.lang.String name();
  void name(java.lang.String value);


  java.lang.String version();
  void version(java.lang.String value);


  org.omg.CORBA.Container defined_in();

  java.lang.String absolute_name();

  org.omg.CORBA.Repository containing_repository();

  org.omg.CORBA.ContainedPackage.Description describe();

  void move(org.omg.CORBA.Container new_container, java.lang.String new_name, java.lang.String new_version);


}
