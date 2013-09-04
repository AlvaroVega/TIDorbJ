//
// OperationDescription.java (struct)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class OperationDescription
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String name;
  public java.lang.String id;
  public java.lang.String defined_in;
  public java.lang.String version;
  public org.omg.CORBA.TypeCode result;
  public org.omg.CORBA.OperationMode mode;
  public java.lang.String[] contexts;
  public org.omg.CORBA.ParameterDescription[] parameters;
  public org.omg.CORBA.ExceptionDescription[] exceptions;

  public OperationDescription() {
    name = "";
    id = "";
    defined_in = "";
    version = "";
    contexts = new String[0];
  }

  public OperationDescription(java.lang.String name, java.lang.String id, java.lang.String defined_in, java.lang.String version, org.omg.CORBA.TypeCode result, org.omg.CORBA.OperationMode mode, java.lang.String[] contexts, org.omg.CORBA.ParameterDescription[] parameters, org.omg.CORBA.ExceptionDescription[] exceptions) {
    this.name = name;
    this.id = id;
    this.defined_in = defined_in;
    this.version = version;
    this.result = result;
    this.mode = mode;
    this.contexts = contexts;
    this.parameters = parameters;
    this.exceptions = exceptions;
  }

}
