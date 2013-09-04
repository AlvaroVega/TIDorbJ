package es.tid.TIDorbj.core.poa;

import java.util.Comparator;

import org.omg.Messaging.RequestEndTimePolicy;

import es.tid.TIDorbj.core.policy.PolicyContext;

/**
 * Comparator that uses the Messaging::RequestEndTimePolicy to decide the order
 * in a request queue. In case of there were not any policy to decide, the
 * TemporalRequestComparator will be used.
 * @author caceres
 * 
 */
public class DeadlineRequestComparator
    implements Comparator
{
    
    public final static int LESS_THAN = -1;
    public final static int EQUALS = 0;
    public final static int GREATER_THAN = 1;
    
    
    TemporalRequestComparator temporalComparator;
    
    public DeadlineRequestComparator()
    {
        temporalComparator = new TemporalRequestComparator();
    }
    
    
   
        
    public int compare(Object o1, Object o2)
    {
        QueuedRequest request1 = (QueuedRequest) o1;
        QueuedRequest request2 = (QueuedRequest) o2;        
    
        PolicyContext context1 = request1.getPolicyContext();
        PolicyContext context2 = request2.getPolicyContext();
        
        RequestEndTimePolicy end1 = null;
        RequestEndTimePolicy end2 = null;

        if (context1 != null) {
            end1 = context1.getRequestEndTimePolicy();
        }

        if (context2 != null) {
            end2 = context2.getRequestEndTimePolicy();
        }
        
        if(end1 == null) {
            if(end2 == null) {
                return temporalComparator.compare(o1,o2);
            } else {
                return GREATER_THAN;
            }
        } else {
            if(end2 == null) {
                return LESS_THAN;
            } else { // both non-null
             
                long val1 = end1.end_time().time;
                long val2 = end2.end_time().time;
                
                if(val1 > val2) {
                    return GREATER_THAN;
                } else if(val1 == val2) {
                    return temporalComparator.compare(o1,o2);
                } else {
                    return LESS_THAN;
                }
            }
        }       
    }
 
}
