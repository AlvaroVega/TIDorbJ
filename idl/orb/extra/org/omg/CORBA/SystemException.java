/***** Copyright (c) 1999 Object Management Group. Unlimited rights to 
       duplicate and use this code are hereby granted provided that this 
       copyright notice is included.
*****/

package org.omg.CORBA;

abstract public class SystemException extends java.lang.RuntimeException {

    public int minor;
    public CompletionStatus completed;

    protected SystemException(String reason, int minor,
                    CompletionStatus completed) {
        super(reason);
        this.minor = minor;
        this.completed = completed;
    }
    
    public String toString()
    {
    	StringBuffer buffer = new StringBuffer();
    	
    	buffer.append(super.toString());
    	buffer.append(" Minor: ");
    	buffer.append(minor);
    	buffer.append(" CompletionStatus: ");
    	switch(completed.value()) {
    		case CompletionStatus._COMPLETED_NO:
    		 	buffer.append("COMPLETED_NO.");
		    	break;
    		case CompletionStatus._COMPLETED_YES:
		    	buffer.append("COMPLETED_YES.");
		    	break;
				case CompletionStatus._COMPLETED_MAYBE:
		    	buffer.append("COMPLETED_MAYBE.");
		    	break;
    	}
    	
    	return buffer.toString();
    }
}