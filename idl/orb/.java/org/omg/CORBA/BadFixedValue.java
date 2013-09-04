//
// BadFixedValue.java (exception)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class BadFixedValue
   extends org.omg.CORBA.UserException {

  public int offset;

  public BadFixedValue() {
    super(BadFixedValueHelper.id());
  }

  public BadFixedValue(int _offset) {
    super(BadFixedValueHelper.id());

    this.offset = _offset;
  }

  public BadFixedValue(String reason, int _offset) {
    super(BadFixedValueHelper.id()+" "+reason);

    this.offset = _offset;
  }

}
