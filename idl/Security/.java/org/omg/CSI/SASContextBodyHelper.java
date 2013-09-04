//
// SASContextBodyHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

abstract public class SASContextBodyHelper {

  public static void insert(org.omg.CORBA.Any any, SASContextBody value) {
    any.insert_Streamable(new SASContextBodyHolder(value));
  };

  public static SASContextBody extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof SASContextBodyHolder){
          return ((SASContextBodyHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
     if (_type == null) {
       org.omg.CORBA.UnionMember[] members = new org.omg.CORBA.UnionMember[4];
       org.omg.CORBA.Any _any0 = _orb().create_any();
       _any0.insert_short((short) 0);
       members[0] = new org.omg.CORBA.UnionMember("establish_msg", _any0, org.omg.CSI.EstablishContextHelper.type(), null);
       org.omg.CORBA.Any _any1 = _orb().create_any();
       _any1.insert_short((short) 1);
       members[1] = new org.omg.CORBA.UnionMember("complete_msg", _any1, org.omg.CSI.CompleteEstablishContextHelper.type(), null);
       org.omg.CORBA.Any _any2 = _orb().create_any();
       _any2.insert_short((short) 4);
       members[2] = new org.omg.CORBA.UnionMember("error_msg", _any2, org.omg.CSI.ContextErrorHelper.type(), null);
       org.omg.CORBA.Any _any3 = _orb().create_any();
       _any3.insert_short((short) 5);
       members[3] = new org.omg.CORBA.UnionMember("in_context_msg", _any3, org.omg.CSI.MessageInContextHelper.type(), null);
       _type = _orb().create_union_tc(id(), "SASContextBody", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_short), members);
     };
     return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSI/SASContextBody:1.0";
  };

  public static SASContextBody read(org.omg.CORBA.portable.InputStream is) {
    SASContextBody _union_result = new SASContextBody();
    short _disc_value = is.read_short();
    switch (_disc_value){
     case 0: {
       org.omg.CSI.EstablishContext _tmp = org.omg.CSI.EstablishContextHelper.read(is);
       _union_result.establish_msg(_tmp);
       break;
     }
     case 1: {
       org.omg.CSI.CompleteEstablishContext _tmp = org.omg.CSI.CompleteEstablishContextHelper.read(is);
       _union_result.complete_msg(_tmp);
       break;
     }
     case 4: {
       org.omg.CSI.ContextError _tmp = org.omg.CSI.ContextErrorHelper.read(is);
       _union_result.error_msg(_tmp);
       break;
     }
     case 5: {
       org.omg.CSI.MessageInContext _tmp = org.omg.CSI.MessageInContextHelper.read(is);
       _union_result.in_context_msg(_tmp);
       break;
     }
     default: {
       _union_result.__default(_disc_value);
       break;
     }

    }
    return _union_result;

  }

  public static void write(org.omg.CORBA.portable.OutputStream os, SASContextBody _value) {
    os.write_short(_value.discriminator());

    if (_value._isDefault) {
       return;
    }
    switch (_value.discriminator()){
     case 0: {
       org.omg.CSI.EstablishContext establish_msg = _value.establish_msg();
       org.omg.CSI.EstablishContextHelper.write(os,establish_msg);
       break;
     }
     case 1: {
       org.omg.CSI.CompleteEstablishContext complete_msg = _value.complete_msg();
       org.omg.CSI.CompleteEstablishContextHelper.write(os,complete_msg);
       break;
     }
     case 4: {
       org.omg.CSI.ContextError error_msg = _value.error_msg();
       org.omg.CSI.ContextErrorHelper.write(os,error_msg);
       break;
     }
     case 5: {
       org.omg.CSI.MessageInContext in_context_msg = _value.in_context_msg();
       org.omg.CSI.MessageInContextHelper.write(os,in_context_msg);
       break;
     }
    }

  }

}
