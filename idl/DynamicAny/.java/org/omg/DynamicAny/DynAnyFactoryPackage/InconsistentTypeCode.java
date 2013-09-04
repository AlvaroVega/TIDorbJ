//
// InconsistentTypeCode.java (exception)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny.DynAnyFactoryPackage;

final public class InconsistentTypeCode
   extends org.omg.CORBA.UserException {


  public InconsistentTypeCode() {
    super(InconsistentTypeCodeHelper.id());
  }

  public InconsistentTypeCode(String reason) {
    super(InconsistentTypeCodeHelper.id()+" "+reason);

  }

}
