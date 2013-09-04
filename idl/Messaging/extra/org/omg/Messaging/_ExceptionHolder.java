/***** Copyright (c) 1999 Object Management Group. Unlimited rights to 
       duplicate and use this code are hereby granted provided that this 
       copyright notice is included.
*****/


package org.omg.Messaging;

public abstract class _ExceptionHolder
   implements org.omg.CORBA.portable.StreamableValue{

  private static String[] _truncatable_ids = {
      org.omg.Messaging._ExceptionHolderHelper.id()
  };

  public String[] _truncatable_ids(){
    return _truncatable_ids;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    this.is_system_exception = is.read_boolean();
    this.byte_order = is.read_boolean();
    this.marshaled_exception = org.omg.Messaging.MarshaledExceptionHelper.read(is);
  }

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    os.write_boolean(this.is_system_exception);
    os.write_boolean(this.byte_order);
    org.omg.Messaging.MarshaledExceptionHelper.write(os,this.marshaled_exception);
  }

  public org.omg.CORBA.TypeCode _type(){
    return org.omg.Messaging._ExceptionHolderHelper.type();
  }

  public abstract void raise_exception()
    throws org.omg.CORBA.UserException;

  public abstract void raise_exception_with_list(org.omg.CORBA.TypeCode[] exc_list)
    throws org.omg.CORBA.UserException;

  protected boolean is_system_exception;

  protected boolean byte_order;

  protected byte[] marshaled_exception;


}
