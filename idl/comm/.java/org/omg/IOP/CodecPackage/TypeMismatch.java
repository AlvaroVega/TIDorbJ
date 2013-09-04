//
// TypeMismatch.java (exception)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP.CodecPackage;

final public class TypeMismatch
   extends org.omg.CORBA.UserException {


  public TypeMismatch() {
    super(TypeMismatchHelper.id());
  }

  public TypeMismatch(String reason) {
    super(TypeMismatchHelper.id()+" "+reason);

  }

}
