//
// UnknownEncoding.java (exception)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP.CodecFactoryPackage;

final public class UnknownEncoding
   extends org.omg.CORBA.UserException {


  public UnknownEncoding() {
    super(UnknownEncodingHelper.id());
  }

  public UnknownEncoding(String reason) {
    super(UnknownEncodingHelper.id()+" "+reason);

  }

}
