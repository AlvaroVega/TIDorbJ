//
// _ExceptionHolderHelper.java (helper)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

abstract public class _ExceptionHolderHelper{

  private static org.omg.CORBA.ORB _orb() {
      return org.omg.CORBA.ORB.init();
  }

  private static String  _id = "IDL:Messaging/_ExceptionHolder:1.0";

  public static void insert(org.omg.CORBA.Any a, org.omg.Messaging._ExceptionHolder t){
    a.insert_Value((java.io.Serializable) t , type());
  }

  public static org.omg.Messaging._ExceptionHolder extract(org.omg.CORBA.Any a){
    java.io.Serializable v = a.extract_Value();
    if(v instanceof org.omg.Messaging._ExceptionHolder)
      return (org.omg.Messaging._ExceptionHolder) v;
    else 
      throw new org.omg.CORBA.BAD_PARAM("Any does not contains a org.omg.Messaging._ExceptionHolder value.");
  }

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type(){
    if (_type == null){
          org.omg.CORBA.ValueMember[] _members = new org.omg.CORBA.ValueMember[3];
          org.omg.CORBA.TypeCode member_tc = null;

          member_tc = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean);
          _members[0] = new org.omg.CORBA.ValueMember("is_system_exception", "", _id, "", member_tc, null, org.omg.CORBA.PRIVATE_MEMBER.value);

          member_tc = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean);
          _members[1] = new org.omg.CORBA.ValueMember("byte_order", "", _id, "", member_tc, null, org.omg.CORBA.PRIVATE_MEMBER.value);

          member_tc = org.omg.Messaging.MarshaledExceptionHelper.type();
          _members[2] = new org.omg.CORBA.ValueMember("marshaled_exception", "", _id, "", member_tc, null, org.omg.CORBA.PRIVATE_MEMBER.value);

          _type = org.omg.CORBA.ORB.init ().create_value_tc(_id, "_ExceptionHolder", org.omg.CORBA.VM_NONE.value, null, _members);
    }
    return _type;
  }

  public static String id(){
    return _id;
  }

  public static org.omg.Messaging._ExceptionHolder read(org.omg.CORBA.portable.InputStream is){
    return (org.omg.Messaging._ExceptionHolder)((org.omg.CORBA_2_3.portable.InputStream) is).read_value(id());
  }

  public static void write(org.omg.CORBA.portable.OutputStream os, org.omg.Messaging._ExceptionHolder val){
    ((org.omg.CORBA_2_3.portable.OutputStream) os).write_value(val, id());
  }

}
