package org.omg.CORBA_2_5;

public abstract class ORB extends org.omg.CORBA_2_3.ORB
{
  public String id()
  {
    throw new org.omg.CORBA.NO_IMPLEMENT() ;
  }

  public void register_initial_reference(String object_name,
                                         org.omg.CORBA.Object object)
    throws org.omg.CORBA.ORBPackage.InvalidName
  {
    throw new org.omg.CORBA.NO_IMPLEMENT() ;
  }

  public org.omg.CORBA.TypeCode create_local_interface_tc(String id, String name)
  {
    throw new org.omg.CORBA.NO_IMPLEMENT() ;
  }
}