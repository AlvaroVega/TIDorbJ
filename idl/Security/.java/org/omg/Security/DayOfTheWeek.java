//
// DayOfTheWeek.java (enum)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

public class DayOfTheWeek
   implements org.omg.CORBA.portable.IDLEntity {

  public static final int _Monday = 0;
  public static final DayOfTheWeek Monday = new DayOfTheWeek(_Monday);
  public static final int _Tuesday = 1;
  public static final DayOfTheWeek Tuesday = new DayOfTheWeek(_Tuesday);
  public static final int _Wednesday = 2;
  public static final DayOfTheWeek Wednesday = new DayOfTheWeek(_Wednesday);
  public static final int _Thursday = 3;
  public static final DayOfTheWeek Thursday = new DayOfTheWeek(_Thursday);
  public static final int _Friday = 4;
  public static final DayOfTheWeek Friday = new DayOfTheWeek(_Friday);
  public static final int _Saturday = 5;
  public static final DayOfTheWeek Saturday = new DayOfTheWeek(_Saturday);
  public static final int _Sunday = 6;
  public static final DayOfTheWeek Sunday = new DayOfTheWeek(_Sunday);

  public int value() { return _value; }
  public static DayOfTheWeek from_int(int value) {
    switch (value) {
      case 0: return Monday;
      case 1: return Tuesday;
      case 2: return Wednesday;
      case 3: return Thursday;
      case 4: return Friday;
      case 5: return Saturday;
      case 6: return Sunday;
    };
    return null;
  };
  protected DayOfTheWeek (int value) { _value = value; };
  public java.lang.Object readResolve()
    throws java.io.ObjectStreamException
  {
    return from_int(value());
  }
  private int _value;
}
