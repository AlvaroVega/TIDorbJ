/***** Copyright (c) 1999 Object Management Group. Unlimited rights to 
       duplicate and use this code are hereby granted provided that this 
       copyright notice is included.
*****/

package org.omg.CORBA;

public final class ValueBaseHelper {

    private static org.omg.CORBA.ORB _orb() {
	return org.omg.CORBA.ORB.init();
    }
   
    public static String id() {
	return "IDL:omg.org/CORBA/ValueBase:1.0";
    };

    private static org.omg.CORBA.TypeCode _type = null;

   
    public static org.omg.CORBA.TypeCode type()
    {
	if (_type == null){
	    
	    org.omg.CORBA.ValueMember[] _members = new org.omg.CORBA.ValueMember[0];
	    
	    _type = _orb().create_value_tc(id(), 
					   "CustomMarshal", 
					   org.omg.CORBA.VM_NONE.value,
					   null, 
					   _members);
        }
	
	return _type;
    }

    public static void insert(org.omg.CORBA.Any any,
			      java.io.Serializable _value) 
    {
	any.insert_Value(_value);	
    }

    public static java.io.Serializable extract(org.omg.CORBA.Any any) 
    {
        return any.extract_Value();
    }

     public static java.io.Serializable read(
                org.omg.CORBA.portable.InputStream _input) 
    {
	return ((org.omg.CORBA_2_3.portable.InputStream)_input).read_value();
        
    }

    public static void write(org.omg.CORBA.portable.OutputStream _output, 
			     java.io.Serializable _value)
    {
	((org.omg.CORBA_2_3.portable.OutputStream) _output).write_value(_value);   
    }

}
