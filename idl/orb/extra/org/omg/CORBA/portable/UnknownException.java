/***** Copyright (c) 1999 Object Management Group. Unlimited rights to 
       duplicate and use this code are hereby granted provided that this 
       copyright notice is included.
*****/

package org.omg.CORBA.portable;

public class UnknownException extends org.omg.CORBA.SystemException {

    public Throwable originalEx;
    public UnknownException(Throwable ex) {
        super("", 0, org.omg.CORBA.CompletionStatus.COMPLETED_NO);
        originalEx = ex;
    }
}

