//
// SequenceDef.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public interface SequenceDefOperations
   extends org.omg.CORBA.IDLTypeOperations {

  int bound();
  void bound(int value);


  org.omg.CORBA.TypeCode element_type();

  org.omg.CORBA.IDLType element_type_def();
  void element_type_def(org.omg.CORBA.IDLType value);



}
