//
// WStringValueHelper.java (helper)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class WStringValueHelper
   implements org.omg.CORBA.portable.BoxedValueHelper{

  private static org.omg.CORBA.ORB _orb() {
      return org.omg.CORBA.ORB.init();
  }

  private static String  _id = "IDL:CORBA/WStringValue:1.0";

  private static WStringValueHelper _instance = new WStringValueHelper();

  public static void insert(org.omg.CORBA.Any a, java.lang.String t){
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, t);
    a.read_value (out.create_input_stream (), type ());
  }

  public static java.lang.String extract(org.omg.CORBA.Any a){
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type(){
    if (_type == null){
          _type = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_wstring);
          _type = org.omg.CORBA.ORB.init ().create_value_box_tc (_id, "WStringValue", _type);    }
    return _type;
  }

  public static String id(){
    return _id;
  }

  public static java.lang.String read(org.omg.CORBA.portable.InputStream is){
    if (!(is instanceof org.omg.CORBA_2_3.portable.InputStream)){
      throw new org.omg.CORBA.BAD_PARAM();
    }
    return (java.lang.String)((org.omg.CORBA_2_3.portable.InputStream)is).read_value(_instance);
  }

  public static void write(org.omg.CORBA.portable.OutputStream os, java.lang.String val){
    if (!(os instanceof org.omg.CORBA_2_3.portable.OutputStream)){
      throw new org.omg.CORBA.BAD_PARAM();
    }
    ((org.omg.CORBA_2_3.portable.OutputStream)os).write_value(val, _instance);
  }

  public java.io.Serializable read_value(org.omg.CORBA.portable.InputStream is){
    return is.read_wstring();
  }

  public void write_value(org.omg.CORBA.portable.OutputStream os, java.io.Serializable _value){
    if (_value instanceof java.lang.String){
             os.write_wstring((java.lang.String) _value);
    }
    else throw new org.omg.CORBA.BAD_PARAM();
  }

  public java.lang.String get_id(){
    return _id;
  }

}
