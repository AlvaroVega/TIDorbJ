//
// PolicyManager.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public interface PolicyManagerOperations {

  org.omg.CORBA.Policy[] get_policy_overrides(int[] ts);

  void set_policy_overrides(org.omg.CORBA.Policy[] policies, org.omg.CORBA.SetOverrideType set_add)
    throws org.omg.CORBA.InvalidPolicies;


}
