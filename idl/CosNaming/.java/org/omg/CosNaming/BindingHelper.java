//
// BindingHelper.java (helper)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming;

abstract public class BindingHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, Binding value) {
    any.insert_Streamable(new BindingHolder(value));
  };

  public static Binding extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof BindingHolder){
          return ((BindingHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[2];
      members[0] = new org.omg.CORBA.StructMember("binding_name", org.omg.CosNaming.NameHelper.type(), null);
      members[1] = new org.omg.CORBA.StructMember("binding_type", org.omg.CosNaming.BindingTypeHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "Binding", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CosNaming/Binding:1.0";
  };

  public static Binding read(org.omg.CORBA.portable.InputStream is) {
    Binding result = new Binding();
    result.binding_name = org.omg.CosNaming.NameHelper.read(is);
    result.binding_type = org.omg.CosNaming.BindingTypeHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, Binding val) {
    org.omg.CosNaming.NameHelper.write(os,val.binding_name);
    org.omg.CosNaming.BindingTypeHelper.write(os,val.binding_type);
  };

}
