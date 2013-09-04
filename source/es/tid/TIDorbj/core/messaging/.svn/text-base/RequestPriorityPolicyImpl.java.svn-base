package es.tid.TIDorbj.core.messaging;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.BAD_POLICY_VALUE;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.Messaging.PriorityRange;
import org.omg.Messaging.PriorityRangeHelper;
import org.omg.Messaging.REQUEST_PRIORITY_POLICY_TYPE;
import org.omg.Messaging.RequestPriorityPolicy;
import org.omg.Messaging.RequestPriorityPolicyLocalBase;


import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

/**
 * @author caceres
 */
public class RequestPriorityPolicyImpl extends RequestPriorityPolicyLocalBase
{
    PriorityRange range;
         

    /**
     * @param range
     */
    public RequestPriorityPolicyImpl(PriorityRange range)
    {
        this.range = range;
    }
    public PriorityRange priority_range()
    {        
        return range;
    }

    public int policy_type()
    {
        
        return REQUEST_PRIORITY_POLICY_TYPE.value;
    }

    public Policy copy()
    {
        return this;
    }

    
    public void destroy()
    {    

    }
    
    public static void write(CDROutputStream output, 
                             RequestPriorityPolicy policy)
    {         
        PriorityRangeHelper.write(output, policy.priority_range());          
    }
    
    public static RequestPriorityPolicyImpl read(CDRInputStream input)
    {
        PriorityRange policy_value =  PriorityRangeHelper.read(input);
        
        if(policy_value.min > policy_value.max) {
            throw new MARSHAL();
        }
        
        return new RequestPriorityPolicyImpl(policy_value);        
    }
    
    public static RequestPriorityPolicyImpl createPolicy(Any val)
    throws org.omg.CORBA.PolicyError
    {
	    try {
	        PriorityRange policy_value =  PriorityRangeHelper.extract(val);
	
	        return new RequestPriorityPolicyImpl(policy_value);
	
	    }
	    catch (BAD_PARAM bp) {
	        throw new PolicyError(BAD_POLICY_VALUE.value);
	    }
    }

}
