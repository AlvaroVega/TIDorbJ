package es.tid.TIDorbj.core.policy;


import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Policy;

import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;
import es.tid.TIDorbj.core.cdr.Encapsulation;


/**
 * @author caceres
 *
 */
public class UnsuportedPolicy extends LocalObject
    implements Policy
{

   int type;
   Encapsulation value;
      
   
    public int policy_type()
    {        
        return type;
    }

    
    public Policy copy()
    {        
        return this;
    }

    
    public void destroy()
    {        
        
    }
    
    public void partialRead(CDRInputStream input) {
        value  = input.readEncapsulation();
        
    }
    
    public void write(CDROutputStream output)
    {
        output.write_ulong(type);
        if(value != null) {
            output.writeEncapsulation(value);
        }
    }
    

 
}
