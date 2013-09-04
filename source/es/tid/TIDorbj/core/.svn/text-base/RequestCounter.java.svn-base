package es.tid.TIDorbj.core;


public class RequestCounter
{
    private long m_serial;
    
    public RequestCounter()
    {
        m_serial = 0L;
    }
    
    public synchronized long next()
    {
        return m_serial++;
    }

}
