/***** Copyright (c) 1999 Object Management Group. Unlimited rights to 
       duplicate and use this code are hereby granted provided that this 
       copyright notice is included.
*****/

package org.omg.CORBA;

final public class AnyHolder implements org.omg.CORBA.portable.Streamable {

    public org.omg.CORBA.Any value;
    protected	org.omg.CORBA.TypeCode type;

    public AnyHolder() {
    	value = null;
    	type = null;
    }

    public AnyHolder(org.omg.CORBA.Any initial) {
        value = initial;
    }

    public void _read(org.omg.CORBA.portable.InputStream is) 
    {
      value = is.read_any();
    }

    public void _write(org.omg.CORBA.portable.OutputStream os) 
    {
       os.write_any(value);
    }

    public synchronized org.omg.CORBA.TypeCode _type() 
    {
    	if(type == null)
    		type = org.omg.CORBA.ORB.init().get_primitive_tc(TCKind.tk_any);
    	return type;
    }

}
