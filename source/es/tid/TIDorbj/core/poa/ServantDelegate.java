/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 2 $
* Date: $Date: 2005-12-19 08:58:21 +0100 (Mon, 19 Dec 2005) $
* Last modified by: $Author: caceres $
*
* (C) Copyright 2004 Telefónica Investigación y Desarrollo
*     S.A.Unipersonal (Telefónica I+D)
*
* Info about members and contributors of the MORFEO project
* is available at:
*
*   http://www.morfeo-project.org/TIDorbJ/CREDITS
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
*
* If you want to use this software an plan to distribute a
* proprietary application in any way, and you are not licensing and
* distributing your source code under GPL, you probably need to
* purchase a commercial license of the product.  More info about
* licensing options is available at:
*
*   http://www.morfeo-project.org/TIDorbJ/Licensing
*/    
package es.tid.TIDorbj.core.poa;

/**
 * Delegate class for servants.
 * 
 * @autor Javier Fdz. Mejuto
 * @version 1.0
 */
public class ServantDelegate
    implements org.omg.PortableServer.portable.Delegate
{

    private org.omg.CORBA.ORB m_orb = null;

    private org.omg.PortableServer.POA m_poa = null;

    private byte[] m_oid = null;

    /**
     * Constructor.
     * 
     * @param poa
     *            The POA.
     * @param oid
     *            The Object Id.
     */
    public ServantDelegate(org.omg.PortableServer.POA poa, byte[] oid)
    {
        this.m_poa = poa;
        this.m_orb = ((es.tid.TIDorbj.core.poa.POAImpl) poa).m_orb;
        this.m_oid = oid;
    }

    /**
     * @param self
     *            The servant.
     * @return The ORB.
     */
    public org.omg.CORBA.ORB orb(org.omg.PortableServer.Servant self)
    {
        return this.m_orb;
    }

    /**
     * @param self
     *            The servant.
     * @return Return an object reference for this servant.
     */
    public org.omg.CORBA.Object this_object(org.omg.PortableServer.Servant self)
    {
        try {
            return m_poa.servant_to_reference(self);
        }
        catch (org.omg.PortableServer.POAPackage.WrongPolicy e) {
            return null;
        }
        catch (org.omg.PortableServer.POAPackage.ServantNotActive e) {
            return null;
        }
    }

    /**
     * @param self
     *            The servant.
     * @return This POA.
     */
    public org.omg.PortableServer.POA poa(org.omg.PortableServer.Servant self)
    {
        return this.m_poa;
    }

    /**
     * @param self
     *            The servant.
     * @return Return the Object Id.
     */
    public byte[] object_id(org.omg.PortableServer.Servant self)
    {
        return m_oid;
    }

    /**
     * @param self
     *            The servant.
     * @return Return the default POA.
     */
    public org.omg.PortableServer.POA
    	default_POA(org.omg.PortableServer.Servant self)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * @param self
     *            The servant.
     * @param repository_id
     *            The Repository Id.
     * @return Return true if the servant is an object with this Repository Id.
     */
    public boolean is_a(org.omg.PortableServer.Servant self,
                        java.lang.String repository_id)
    {
        String[] reps = self._all_interfaces(m_poa, m_oid);
        for (int i = 0; i < reps.length; i++) {
            if (repository_id.equals(reps[i]))
                return true;
        }
        return false;
    }

    public boolean non_existent(org.omg.PortableServer.Servant self)
    {
        return false;
    }

    public org.omg.CORBA.InterfaceDef 
    	get_interface(org.omg.PortableServer.Servant self)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public org.omg.CORBA.Object 
    	get_interface_def(org.omg.PortableServer.Servant self)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Sets object Id (useful for default servants).
     * 
     * @param oid
     *            The Object Id.
     */
    protected void setObjectId(byte[] oid)
    {
        this.m_oid = oid;
    }

}