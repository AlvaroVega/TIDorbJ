//
// CompressionException.java (exception)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

final public class CompressionException
   extends org.omg.CORBA.UserException {

  public int reason;
  public java.lang.String description;

  public CompressionException() {
    super(CompressionExceptionHelper.id());
  }

  public CompressionException(int _reason, java.lang.String _description) {
    super(CompressionExceptionHelper.id());

    this.reason = _reason;
    this.description = _description;
  }

  public CompressionException(String reason, int _reason, java.lang.String _description) {
    super(CompressionExceptionHelper.id()+" "+reason);

    this.reason = _reason;
    this.description = _description;
  }

}
