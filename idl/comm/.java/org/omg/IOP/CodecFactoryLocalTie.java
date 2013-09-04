//
// CodecFactoryLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

public class CodecFactoryLocalTie
 extends CodecFactoryLocalBase
 {

  private CodecFactoryOperations _delegate;
  public CodecFactoryLocalTie(CodecFactoryOperations delegate) {
    this._delegate = delegate;
  };

  public CodecFactoryOperations _delegate() {
    return this._delegate;
  };

  public org.omg.IOP.Codec create_codec(org.omg.IOP.Encoding enc)
    throws org.omg.IOP.CodecFactoryPackage.UnknownEncoding {
    return this._delegate.create_codec(
    enc
    );
  };


}
