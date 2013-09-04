//
// MechandOptions.java (struct)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class MechandOptions
   implements org.omg.CORBA.portable.IDLEntity {

  public java.lang.String mechanism_type;
  public short options_supported;

  public MechandOptions() {
    mechanism_type = "";
  }

  public MechandOptions(java.lang.String mechanism_type, short options_supported) {
    this.mechanism_type = mechanism_type;
    this.options_supported = options_supported;
  }

}
