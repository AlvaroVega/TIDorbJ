//
// DynArray.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny;

public interface DynArrayOperations
   extends org.omg.DynamicAny.DynAnyOperations {

  org.omg.CORBA.Any[] get_elements();

  void set_elements(org.omg.CORBA.Any[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue;

  org.omg.DynamicAny.DynAny[] get_elements_as_dyn_any();

  void set_elements_as_dyn_any(org.omg.DynamicAny.DynAny[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue;


}
