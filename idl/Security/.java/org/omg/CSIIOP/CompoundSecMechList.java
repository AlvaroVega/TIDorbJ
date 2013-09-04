//
// CompoundSecMechList.java (struct)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

public class CompoundSecMechList
   implements org.omg.CORBA.portable.IDLEntity {

  public boolean stateful;
  public org.omg.CSIIOP.CompoundSecMech[] mechanism_list;

  public CompoundSecMechList() {
  }

  public CompoundSecMechList(boolean stateful, org.omg.CSIIOP.CompoundSecMech[] mechanism_list) {
    this.stateful = stateful;
    this.mechanism_list = mechanism_list;
  }

}
