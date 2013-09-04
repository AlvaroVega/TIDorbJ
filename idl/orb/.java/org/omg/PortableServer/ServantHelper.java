package org.omg.PortableServer;

public class ServantHelper
{
	
	public static void insert( org.omg.CORBA.Any a, org.omg.PortableServer.Servant t )
	{
		throw new org.omg.CORBA.MARSHAL();
	}

	public static org.omg.PortableServer.Servant extract( org.omg.CORBA.Any a )
	{
		throw new org.omg.CORBA.MARSHAL();
	}

	private static org.omg.CORBA.TypeCode _tc = null;


	public static org.omg.CORBA.TypeCode type()
	{
		if ( _tc != null )
			return _tc;
		else
		{
			org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
			_tc = orb.create_native_tc(id(),"Servant");
			return _tc;
		}
	}

	public static String id()
	{
		return new String("IDL:omg.org/PortableServer/Servant:1.0");
	}
	
	public static org.omg.PortableServer.Servant read( org.omg.CORBA.portable.InputStream istream )
	{
		throw new org.omg.CORBA.MARSHAL();
	}

	
	public static void write( org.omg.CORBA.portable.OutputStream ostream, org.omg.PortableServer.Servant value )
	{
		throw new org.omg.CORBA.MARSHAL();
	}

}
