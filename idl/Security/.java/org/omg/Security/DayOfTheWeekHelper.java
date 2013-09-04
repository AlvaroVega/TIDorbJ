//
// DayOfTheWeekHelper.java (helper)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Security;

abstract public class DayOfTheWeekHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, DayOfTheWeek value) {
    any.insert_Streamable(new DayOfTheWeekHolder(value));
  };

  public static DayOfTheWeek extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof DayOfTheWeekHolder){
          return ((DayOfTheWeekHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      java.lang.String[] members = new java.lang.String[7];
      members[0] = "Monday";
      members[1] = "Tuesday";
      members[2] = "Wednesday";
      members[3] = "Thursday";
      members[4] = "Friday";
      members[5] = "Saturday";
      members[6] = "Sunday";
      _type = _orb().create_enum_tc(id(), "DayOfTheWeek", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Security/DayOfTheWeek:1.0";
  };

  public static DayOfTheWeek read(org.omg.CORBA.portable.InputStream is) {
    return DayOfTheWeek.from_int(is.read_long());
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, DayOfTheWeek val) {
    os.write_long(val.value());
  };

}
