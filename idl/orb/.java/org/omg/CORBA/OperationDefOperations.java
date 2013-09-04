//
// OperationDef.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public interface OperationDefOperations
   extends org.omg.CORBA.ContainedOperations {

  org.omg.CORBA.TypeCode result();

  org.omg.CORBA.IDLType result_def();
  void result_def(org.omg.CORBA.IDLType value);


  org.omg.CORBA.ParameterDescription[] params();
  void params(org.omg.CORBA.ParameterDescription[] value);


  org.omg.CORBA.OperationMode mode();
  void mode(org.omg.CORBA.OperationMode value);


  java.lang.String[] contexts();
  void contexts(java.lang.String[] value);


  org.omg.CORBA.ExceptionDef[] exceptions();
  void exceptions(org.omg.CORBA.ExceptionDef[] value);



}
