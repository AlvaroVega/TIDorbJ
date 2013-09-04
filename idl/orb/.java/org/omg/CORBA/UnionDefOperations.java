//
// UnionDef.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public interface UnionDefOperations
   extends org.omg.CORBA.TypedefDefOperations, org.omg.CORBA.ContainerOperations {

  org.omg.CORBA.TypeCode discriminator_type();

  org.omg.CORBA.IDLType discriminator_type_def();
  void discriminator_type_def(org.omg.CORBA.IDLType value);


  org.omg.CORBA.UnionMember[] members();
  void members(org.omg.CORBA.UnionMember[] value);



}
