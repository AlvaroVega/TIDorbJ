//
// OptionsDirectionPair.java (struct)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class OptionsDirectionPair
   implements org.omg.CORBA.portable.IDLEntity {

  public short options;
  public org.omg.Security.CommunicationDirection direction;

  public OptionsDirectionPair() {
  }

  public OptionsDirectionPair(short options, org.omg.Security.CommunicationDirection direction) {
    this.options = options;
    this.direction = direction;
  }

}
