/***** Copyright (c) 1999 Object Management Group. Unlimited rights to 
       duplicate and use this code are hereby granted provided that this 
       copyright notice is included.
*****/

package org.omg.CORBA;

import es.tid.TIDorbj.util.SystemProperties;

abstract public class ORB {
	/**
	 * ORB Class name property name: <code>org.omg.CORBA.ORBClass</code>
	 */
	private final static String orb_key = "org.omg.CORBA.ORBClass";
	/**
	 * ORB Singleton Class name property name: <code>org.omg.CORBA.ORBSingletonClass</code>
	 */
	private final static String singleton_key  = 
																		"org.omg.CORBA.ORBSingletonClass";
																	
	/**
	 * TIDorb ORB Class name: <code>es.tid.TIDorbj.core.TIDORB</code>
	 */
	private final static String TIDorb_class_name = "es.tid.TIDorbj.core.TIDORB";
	
	/**
	 * TIDorb ORB Class name: <code>es.tid.TIDorbj.core.SingletonORB</code>
	 */
	private final static String TIDorb_singleton_class_name = 
																		"es.tid.TIDorbj.core.SingletonORB";

	private static ORB singleton_orb = null;

	private static ORB create_orb(String class_name)
  {
  	try {
  		return (ORB) Class.forName(class_name).newInstance();

  	} catch (Exception e) {
	  		throw new INITIALIZE("Cannot create a " + class_name + " instance");
  	}
  }

	
	public static ORB init() 
	{
		if (singleton_orb == null) {
		  String class_name = null;
		
		  try {
			  class_name = SystemProperties.findProperty(singleton_key);
			} catch (SecurityException ex) {
			  //Applet Security violation
			}
			
			if(class_name != null)
				singleton_orb = create_orb(class_name);
			else
				singleton_orb = create_orb(TIDorb_singleton_class_name);
		}
		
		return singleton_orb;
			
  }

  public static ORB init(String[] args, java.util.Properties props) 
  {
	// search ORB class
  	
  	String str_class = null;

  	if(args != null) {
  		int size = args.length - 1; 
  		for(int i = 0; i < size; i++) {
  			if (args[i].equals(orb_key)) {
  				str_class = args[i+1];
  				break;
  			}
  		}
  	}   
  	if (str_class == null) {  
  		if(props != null)
  			str_class = props.getProperty(orb_key);
  	}
  	
  	if (str_class == null) {  	    
  			str_class = SystemProperties.findProperty(orb_key);
  	}
 	
  	if(str_class == null)
  		str_class = TIDorb_class_name;
  		
  	ORB new_orb = create_orb(str_class);
  	
  	new_orb.set_parameters(args, props);
  	
  	return new_orb;
  }
  
  public static ORB init(java.applet.Applet app, java.util.Properties props) 
  {
	// search ORB class
  	
  	String str_class = null;

  	if(app != null)
  		str_class = app.getParameter(orb_key);
  		
  	if ((str_class == null) && (props != null)) {
  			str_class = props.getProperty(orb_key);
   	}
  	
  	if (str_class == null) {
  	  try {  
  			str_class = SystemProperties.findProperty(orb_key);
  		} catch (SecurityException se) {}
  	}
 	
  	if(str_class == null)
  		str_class = TIDorb_class_name;
  		
  	ORB new_orb = create_orb(str_class);
  	
  	new_orb.set_parameters(app, props);
  	
  	return new_orb;
  }

  abstract protected void set_parameters(String[] args,
                                    java.util.Properties props);

  abstract protected void set_parameters(java.applet.Applet app,
                                    java.util.Properties props);

  public void connect(org.omg.CORBA.Object obj) 
  {
  	throw new org.omg.CORBA.NO_IMPLEMENT();
  }

  public void disconnect(org.omg.CORBA.Object obj) 
  {
    throw new org.omg.CORBA.NO_IMPLEMENT();
  }

  abstract public String[] list_initial_services();

  abstract public 
  	org.omg.CORBA.Object resolve_initial_references(String object_name)
    throws org.omg.CORBA.ORBPackage.InvalidName;

  abstract public String object_to_string(org.omg.CORBA.Object obj);

  abstract public org.omg.CORBA.Object string_to_object(String str);

  abstract public NVList create_list(int count);

  public NVList create_operation_list(org.omg.CORBA.Object oper) 
  {
  	throw new org.omg.CORBA.NO_IMPLEMENT();
  }

  abstract public NamedValue create_named_value(String s, Any any, int flags);

  abstract public ExceptionList create_exception_list();

  abstract public ContextList create_context_list();

  abstract public Context get_default_context();

  abstract public Environment create_environment();

  abstract public org.omg.CORBA.portable.OutputStream create_output_stream();

  abstract public void send_multiple_requests_oneway(Request[] req);

  abstract public void send_multiple_requests_deferred(Request[] req);

  abstract public boolean poll_next_response();

  abstract public Request get_next_response() throws WrongTransaction;

  abstract public TypeCode get_primitive_tc(TCKind tcKind);

  abstract public TypeCode create_struct_tc(String id, String name,
                                    StructMember[] members);

  abstract public TypeCode create_union_tc(String id, String name,
                                    TypeCode discriminator_type,
                                    UnionMember[] members);

  abstract public TypeCode create_enum_tc(String id, String name,
                                    String[] members);

  abstract public TypeCode create_alias_tc(String id, String name,
                                    TypeCode original_type);

  abstract public TypeCode create_exception_tc(String id, String name,
                                               StructMember[] members);

  abstract public TypeCode create_interface_tc(String id, String name);

  abstract public TypeCode create_string_tc(int bound);

  abstract public TypeCode create_wstring_tc(int bound);

  abstract public TypeCode create_sequence_tc(int bound, 
                                    TypeCode element_type);

  abstract public TypeCode create_recursive_sequence_tc(int bound, int offset);

  abstract public TypeCode create_array_tc(int length, TypeCode element_type);

  public org.omg.CORBA.TypeCode create_native_tc(String id, String name) 
  {
    throw new org.omg.CORBA.NO_IMPLEMENT();
  }

  public org.omg.CORBA.TypeCode create_abstract_interface_tc(
                                    String id,
                                    String name) {
    throw new org.omg.CORBA.NO_IMPLEMENT();
  }


  public org.omg.CORBA.TypeCode create_fixed_tc(short digits, short scale) 
  {
    throw new org.omg.CORBA.NO_IMPLEMENT();
  }


   public org.omg.CORBA.TypeCode create_value_tc(String id,
                                    String name,
                                    short type_modifier,
                                    TypeCode concrete_base,
                                    ValueMember[] members) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public org.omg.CORBA.TypeCode create_recursive_tc(String id) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }   

    public org.omg.CORBA.TypeCode create_value_box_tc(String id,
                                    String name,
                                    TypeCode boxed_type) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }
    
    abstract public Any create_any();

    public org.omg.CORBA.Current get_current() {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public void run() {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public void shutdown(boolean wait_for_completion) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }
    
    public void destroy() {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public boolean work_pending() {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public void perform_work() {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public boolean get_service_information(short service_type,
                                    ServiceInformationHolder service_info) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public org.omg.CORBA.Policy create_policy(int type, org.omg.CORBA.Any val)
                        throws org.omg.CORBA.PolicyError {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

}
