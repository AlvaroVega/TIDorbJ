//
// DynValue.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny;

public interface DynValueOperations
   extends org.omg.DynamicAny.DynValueCommonOperations {

  java.lang.String current_member_name()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue;

  org.omg.CORBA.TCKind current_member_kind()
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue;

  org.omg.DynamicAny.NameValuePair[] get_members();

  void set_members(org.omg.DynamicAny.NameValuePair[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue;

  org.omg.DynamicAny.NameDynAnyPair[] get_members_as_dyn_any();

  void set_members_as_dyn_any(org.omg.DynamicAny.NameDynAnyPair[] value)
    throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch, org.omg.DynamicAny.DynAnyPackage.InvalidValue;


}
