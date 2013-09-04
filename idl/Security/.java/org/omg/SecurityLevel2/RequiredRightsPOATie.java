//
// RequiredRightsPOATie.java (tie)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

public class RequiredRightsPOATie
 extends RequiredRightsPOA
 implements RequiredRightsOperations {

  private RequiredRightsOperations _delegate;
  public RequiredRightsPOATie(RequiredRightsOperations delegate) {
    this._delegate = delegate;
  };

  public RequiredRightsOperations _delegate() {
    return this._delegate;
  };

  public java.lang.String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectID) {
    return __ids;
  };

  private static java.lang.String[] __ids = {
    "IDL:omg.org/SecurityLevel2/RequiredRights:1.0"  };

  public void get_required_rights(org.omg.CORBA.Object obj, java.lang.String operation_name, java.lang.String interface_name, org.omg.Security.RightsListHolder rights, org.omg.Security.RightsCombinatorHolder rights_combinator) {
    this._delegate.get_required_rights(
    obj, 
    operation_name, 
    interface_name, 
    rights, 
    rights_combinator
    );
  };

  public void set_required_rights(java.lang.String operation_name, java.lang.String interface_name, org.omg.Security.Right[] rights, org.omg.Security.RightsCombinator rights_combinator) {
    this._delegate.set_required_rights(
    operation_name, 
    interface_name, 
    rights, 
    rights_combinator
    );
  };


}
