//
// UnknownCompressorId.java (exception)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

final public class UnknownCompressorId
   extends org.omg.CORBA.UserException {


  public UnknownCompressorId() {
    super(UnknownCompressorIdHelper.id());
  }

  public UnknownCompressorId(String reason) {
    super(UnknownCompressorIdHelper.id()+" "+reason);

  }

}
