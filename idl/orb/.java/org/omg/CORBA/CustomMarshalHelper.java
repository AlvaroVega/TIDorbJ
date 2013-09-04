//
// CustomMarshalHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

abstract public class CustomMarshalHelper{

  private static org.omg.CORBA.ORB _orb() {
      return org.omg.CORBA.ORB.init();
  }

  private static String  _id = "IDL:CORBA/CustomMarshal:1.0";

  public static void insert(org.omg.CORBA.Any a, org.omg.CORBA.CustomMarshal t){
    a.insert_Value((java.io.Serializable) t , type());
  }

  public static org.omg.CORBA.CustomMarshal extract(org.omg.CORBA.Any a){
    java.io.Serializable v = a.extract_Value();
    if(v instanceof org.omg.CORBA.CustomMarshal)
      return (org.omg.CORBA.CustomMarshal) v;
    else 
      throw new org.omg.CORBA.BAD_PARAM("Any does not contains a org.omg.CORBA.CustomMarshal value.");
  }

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type(){
    if (_type == null){
          org.omg.CORBA.ValueMember[] _members = new org.omg.CORBA.ValueMember[0];
          org.omg.CORBA.TypeCode member_tc = null;

          _type = org.omg.CORBA.ORB.init ().create_value_tc(_id, "CustomMarshal", org.omg.CORBA.VM_ABSTRACT.value, null, _members);
    }
    return _type;
  }

  public static String id(){
    return _id;
  }

  public static org.omg.CORBA.CustomMarshal read(org.omg.CORBA.portable.InputStream is){
    return (org.omg.CORBA.CustomMarshal)((org.omg.CORBA_2_3.portable.InputStream) is).read_value(id());
  }

  public static void write(org.omg.CORBA.portable.OutputStream os, org.omg.CORBA.CustomMarshal val){
    ((org.omg.CORBA_2_3.portable.OutputStream) os).write_value(val, id());
  }

}
