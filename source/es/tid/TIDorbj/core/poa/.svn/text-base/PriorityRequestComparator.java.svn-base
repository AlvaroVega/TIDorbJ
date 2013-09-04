package es.tid.TIDorbj.core.poa;

import java.util.Comparator;

import org.omg.Messaging.PriorityRange;
import org.omg.Messaging.RequestPriorityPolicy;

import es.tid.TIDorbj.core.policy.PolicyContext;

/**
 * Comparator that uses the request priority to decide the order
 * in a request queue. In case of simillar priorities, the
 * TemporalRequestComparator will be used.
 * @author caceres
 * 
 */
public class PriorityRequestComparator
    implements Comparator
{    
    public final static int LESS_THAN = -1;
    public final static int EQUALS = 0;
    public final static int GREATER_THAN = 1;
    public final static int DEFAULT_PRIORITY = 0;
    
    TemporalRequestComparator temporalComparator;
          

    /**
     * @param policy
     */
    public PriorityRequestComparator()
    {  
        temporalComparator = new TemporalRequestComparator();
    }
     
    protected short getPriority(RequestPriorityPolicy policy) 
    {
        short priority = DEFAULT_PRIORITY;
       
        
        if(policy != null) {
            PriorityRange range = policy.priority_range();
            priority = (short) 
            	Math.round((float) (range.max + range.min) / 2.0);           
        }
        
        return priority;        
    }
        
    public int compare(Object o1, Object o2)
    {
        QueuedRequest request1 = (QueuedRequest) o1;
        QueuedRequest request2 = (QueuedRequest) o2;
        
        PolicyContext context1 = request1.getPolicyContext();
        PolicyContext context2 = request2.getPolicyContext();

        RequestPriorityPolicy reqPriority1 = null;
        RequestPriorityPolicy reqPriority2 = null;
        
        if (context1 != null) {
            reqPriority1 = context1.getRequestPriorityPolicy();
        }

        if (context2 != null) {
            reqPriority2 = context2.getRequestPriorityPolicy();
        }
            
        short priority1 = getPriority(reqPriority1);
        short priority2 = getPriority(reqPriority2);
               
                      
        if(priority1 > priority2) {
            return LESS_THAN;
        } else if(priority1 == priority2) {
            
            return temporalComparator.compare(o1, o2);
            
        } else {
            return GREATER_THAN;
        }               
    }

}
