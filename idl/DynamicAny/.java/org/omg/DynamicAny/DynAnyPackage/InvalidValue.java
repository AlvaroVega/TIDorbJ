//
// InvalidValue.java (exception)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny.DynAnyPackage;

final public class InvalidValue
   extends org.omg.CORBA.UserException {


  public InvalidValue() {
    super(InvalidValueHelper.id());
  }

  public InvalidValue(String reason) {
    super(InvalidValueHelper.id()+" "+reason);

  }

}
