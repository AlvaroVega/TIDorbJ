//
// CompoundSecMech.java (struct)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSIIOP;

public class CompoundSecMech
   implements org.omg.CORBA.portable.IDLEntity {

  public short target_requires;
  public org.omg.IOP.TaggedComponent transport_mech;
  public org.omg.CSIIOP.AS_ContextSec as_context_mech;
  public org.omg.CSIIOP.SAS_ContextSec sas_context_mech;

  public CompoundSecMech() {
  }

  public CompoundSecMech(short target_requires, org.omg.IOP.TaggedComponent transport_mech, org.omg.CSIIOP.AS_ContextSec as_context_mech, org.omg.CSIIOP.SAS_ContextSec sas_context_mech) {
    this.target_requires = target_requires;
    this.transport_mech = transport_mech;
    this.as_context_mech = as_context_mech;
    this.sas_context_mech = sas_context_mech;
  }

}
