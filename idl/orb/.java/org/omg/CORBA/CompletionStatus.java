package org.omg.CORBA;

public class CompletionStatus implements org.omg.CORBA.portable.IDLEntity {

	protected int completion_value;
	
	protected CompletionStatus(int _value) {
        completion_value = _value;
  }
  
  public int value() 
  {
  	return completion_value; 
  }

  public static CompletionStatus from_int(int val) 
  	throws org.omg.CORBA.BAD_PARAM 
  {
    switch (val) {
    case _COMPLETED_YES:
        return COMPLETED_YES;
    case _COMPLETED_NO:
        return COMPLETED_NO;
    case _COMPLETED_MAYBE:
        return COMPLETED_MAYBE;
    default:
        throw new org.omg.CORBA.BAD_PARAM();
    }
  }

  public static final int _COMPLETED_YES = 0;
  public static final CompletionStatus COMPLETED_YES = 
        new CompletionStatus(_COMPLETED_YES);

  public static final int _COMPLETED_NO = 1;
  public static final CompletionStatus COMPLETED_NO = 
      new CompletionStatus(_COMPLETED_NO);

  public static final int _COMPLETED_MAYBE = 2;
  public static final CompletionStatus COMPLETED_MAYBE = 
      new CompletionStatus(_COMPLETED_MAYBE);
   
}
