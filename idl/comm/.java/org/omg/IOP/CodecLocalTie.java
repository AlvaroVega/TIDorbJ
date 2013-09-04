//
// CodecLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

public class CodecLocalTie
 extends CodecLocalBase
 {

  private CodecOperations _delegate;
  public CodecLocalTie(CodecOperations delegate) {
    this._delegate = delegate;
  };

  public CodecOperations _delegate() {
    return this._delegate;
  };

  public byte[] encode(org.omg.CORBA.Any data)
    throws org.omg.IOP.CodecPackage.InvalidTypeForEncoding {
    return this._delegate.encode(
    data
    );
  };

  public org.omg.CORBA.Any decode(byte[] data)
    throws org.omg.IOP.CodecPackage.FormatMismatch {
    return this._delegate.decode(
    data
    );
  };

  public byte[] encode_value(org.omg.CORBA.Any data)
    throws org.omg.IOP.CodecPackage.InvalidTypeForEncoding {
    return this._delegate.encode_value(
    data
    );
  };

  public org.omg.CORBA.Any decode_value(byte[] data, org.omg.CORBA.TypeCode tc)
    throws org.omg.IOP.CodecPackage.FormatMismatch, org.omg.IOP.CodecPackage.TypeMismatch {
    return this._delegate.decode_value(
    data, 
    tc
    );
  };


}
