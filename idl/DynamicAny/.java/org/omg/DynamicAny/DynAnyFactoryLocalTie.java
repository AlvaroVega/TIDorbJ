//
// DynAnyFactoryLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny;

public class DynAnyFactoryLocalTie
 extends DynAnyFactoryLocalBase
 {

  private DynAnyFactoryOperations _delegate;
  public DynAnyFactoryLocalTie(DynAnyFactoryOperations delegate) {
    this._delegate = delegate;
  };

  public DynAnyFactoryOperations _delegate() {
    return this._delegate;
  };

  public org.omg.DynamicAny.DynAny create_dyn_any(org.omg.CORBA.Any value)
    throws org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode {
    return this._delegate.create_dyn_any(
    value
    );
  };

  public org.omg.DynamicAny.DynAny create_dyn_any_from_type_code(org.omg.CORBA.TypeCode type)
    throws org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode {
    return this._delegate.create_dyn_any_from_type_code(
    type
    );
  };


}
