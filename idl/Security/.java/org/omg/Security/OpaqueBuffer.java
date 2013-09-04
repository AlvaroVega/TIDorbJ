//
// OpaqueBuffer.java (struct)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class OpaqueBuffer
   implements org.omg.CORBA.portable.IDLEntity {

  public byte[] buffer;
  public int startpos;
  public int endpos;

  public OpaqueBuffer() {
  }

  public OpaqueBuffer(byte[] buffer, int startpos, int endpos) {
    this.buffer = buffer;
    this.startpos = startpos;
    this.endpos = endpos;
  }

}
