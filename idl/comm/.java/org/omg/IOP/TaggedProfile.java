//
// TaggedProfile.java (struct)
//
// File generated: Thu May 19 07:31:38 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IOP;

public class TaggedProfile
   implements org.omg.CORBA.portable.IDLEntity {

  public int tag;
  public byte[] profile_data;

  public TaggedProfile() {
  }

  public TaggedProfile(int tag, byte[] profile_data) {
    this.tag = tag;
    this.profile_data = profile_data;
  }

}
