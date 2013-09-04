//
// UtcT.java (struct)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.TimeBase;

public class UtcT
   implements org.omg.CORBA.portable.IDLEntity {

  public long time;
  public int inacclo;
  public short inacchi;
  public short tdf;

  public UtcT() {
  }

  public UtcT(long time, int inacclo, short inacchi, short tdf) {
    this.time = time;
    this.inacclo = inacclo;
    this.inacchi = inacchi;
    this.tdf = tdf;
  }

}
