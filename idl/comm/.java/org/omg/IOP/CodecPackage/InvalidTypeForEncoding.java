//
// InvalidTypeForEncoding.java (exception)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP.CodecPackage;

final public class InvalidTypeForEncoding
   extends org.omg.CORBA.UserException {


  public InvalidTypeForEncoding() {
    super(InvalidTypeForEncodingHelper.id());
  }

  public InvalidTypeForEncoding(String reason) {
    super(InvalidTypeForEncodingHelper.id()+" "+reason);

  }

}
