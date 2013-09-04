//
// AttributeDef.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public interface AttributeDefOperations
   extends org.omg.CORBA.ContainedOperations {

  org.omg.CORBA.TypeCode type();

  org.omg.CORBA.IDLType type_def();
  void type_def(org.omg.CORBA.IDLType value);


  org.omg.CORBA.AttributeMode mode();
  void mode(org.omg.CORBA.AttributeMode value);



}
