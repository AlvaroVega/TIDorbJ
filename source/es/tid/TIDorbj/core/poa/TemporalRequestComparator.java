package es.tid.TIDorbj.core.poa;

import java.util.Comparator;

/**
 * @author caceres
 * 
 */
public class TemporalRequestComparator
    implements Comparator
{
    
    public final static int LESS_THAN = -1;
    public final static int EQUALS = 0;
    public final static int GREATER_THAN = 1;
    
    
    public TemporalRequestComparator()
    {
        
    }  
        
    public int compare(Object o1, Object o2)
    {
        QueuedRequest request1 = (QueuedRequest) o1;
        QueuedRequest request2 = (QueuedRequest) o2;
        
        long serial1 = request1.getSerial();
        long serial2 = request2.getSerial();
        
        if(serial1 > serial2) {
            return GREATER_THAN;
        } else if(serial1 == serial2) {
            return EQUALS;
        } else {
            return LESS_THAN;
        }
    }
 
}
