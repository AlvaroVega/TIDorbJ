//
// Parameter.java (struct)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Dynamic;

public class Parameter
   implements org.omg.CORBA.portable.IDLEntity {

  public org.omg.CORBA.Any argument;
  public org.omg.CORBA.ParameterMode mode;

  public Parameter() {
  }

  public Parameter(org.omg.CORBA.Any argument, org.omg.CORBA.ParameterMode mode) {
    this.argument = argument;
    this.mode = mode;
  }

}
