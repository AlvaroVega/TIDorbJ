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

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Active Object Map.
 * 
 * @autor Javier Fdz. Mejuto
 * @version 1.0
 */
class ActiveObjectMap
{

    protected Hashtable m_table = new Hashtable();

    /**
     * Adds a new oid to the Active Object Map.
     * 
     * @param oid
     *            Object Id.
     * @param servant
     *            Servant which corresponds to oid.
     * @param uniqueId
     *            True if UNIQUE_ID policy is present in this POA.
     * @exception ServantAlreadyActive
     *                If servant is already in the AOM.
     * @exception ObjectAlreadyActive
     *                If uniqueId is true and oid is already in the AOM.
     */
    public void put(OID oid, org.omg.PortableServer.Servant servant,
                    boolean uniqueId)
        throws org.omg.PortableServer.POAPackage.ServantAlreadyActive,
        org.omg.PortableServer.POAPackage.ObjectAlreadyActive
    {

        if (m_table.containsKey(oid)) {
            throw new org.omg.PortableServer.POAPackage.ObjectAlreadyActive();
        }

        AOMElement element = new AOMElement(servant);
        if (uniqueId && m_table.contains(element)) {
            throw new org.omg.PortableServer.POAPackage.ServantAlreadyActive();
        }

        m_table.put(oid, element);
    }

    /**
     * Gets the servant associated to oid.
     * 
     * @param oid
     *            Object Id.
     * @return The servant.
     * @exception ObjectNotActive
     *                If oid is not in the AOM.
     */
    public org.omg.PortableServer.Servant get(OID oid)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive
    {
        AOMElement element = (AOMElement) m_table.get(oid);
        if (element == null) {
            throw new org.omg.PortableServer.POAPackage.ObjectNotActive();
        }
        return element.servant;
    }

    /**
     * Removes the servant associated to oid.
     * 
     * @param oid
     *            Object Id.
     * @return Retuns the servant removed (if any).
     * @exception ObjectNotActive
     *                If oid is not in the AOM.
     */
    public org.omg.PortableServer.Servant remove(OID oid)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive
    {
        AOMElement element = (AOMElement) m_table.remove(oid);
        if (element == null) {
            throw new org.omg.PortableServer.POAPackage.ObjectNotActive();
        }
        return element.servant;
    }

    /**
     * Tests if a servant is in the Active Object Map.
     * 
     * @return Retuns true if the servant is in the Active Object Map.
     */
    public boolean contains(org.omg.PortableServer.Servant servant)
    {
        AOMElement element = new AOMElement(servant);
        return m_table.contains(element);
    }

    /**
     * Tests if an oid is in the Active Object Map.
     * 
     * @return Retuns true if the oid is in the Active Object Map.
     */
    public boolean isActive(OID oid)
    {
        return (oid != null) && (m_table.containsKey(oid));
    }

    /**
     * Gets the first oid associated to a servant.
     * 
     * @param The
     *            servant.
     * @return The Object Id.
     */
    public OID get(org.omg.PortableServer.Servant servant)
    {
        Enumeration oids = m_table.keys();
        Enumeration AOMelems = m_table.elements();
        for (; AOMelems.hasMoreElements();) {
            OID curr_oid = (OID) oids.nextElement();
            AOMElement curr_AOMelems = (AOMElement) AOMelems.nextElement();
            if (curr_AOMelems.servant == servant)
                return curr_oid;
        }
        return null;
    }

    /**
     * Adds a new user for this oid.
     * 
     * @param The
     *            Object Id.
     * @exception ObjectNotActive
     *                If oid is not in the AOM.
     */
    public void addUser(OID oid)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive
    {
        AOMElement element = (AOMElement) m_table.get(oid);
        if (element == null) {
            throw new org.omg.PortableServer.POAPackage.ObjectNotActive();
        }
        element.num_users++;
    }

    /**
     * Removes a user for this oid.
     * 
     * @param The
     *            Object Id.
     * @return Returns false if there are still users, otherwise returns true.
     * @exception ObjectNotActive
     *                If oid is not in the AOM.
     */
    public boolean removeUser(OID oid)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive
    {
        AOMElement element = (AOMElement) m_table.get(oid);
        if (element == null) {
            throw new org.omg.PortableServer.POAPackage.ObjectNotActive();
        }
        element.num_users--;
        if (element.num_users <= 0) {
            return true; // nobody uses oid anymore
        } else {
            return false; // someone is still using oid
        }
    }

    /**
     * Marks an oid as "destroying".
     * 
     * @param The
     *            Object Id.
     */
    public void destroy(OID oid)
        throws org.omg.PortableServer.POAPackage.ObjectNotActive
    {
        AOMElement element = (AOMElement) m_table.get(oid);
        if (element == null) {
            throw new org.omg.PortableServer.POAPackage.ObjectNotActive();
        }
        element.destroying = true;
    }

    /**
     * Tests if an oid is "destroying".
     * 
     * @return The entry in the Active Object Map. It may be used for
     *         synchronization purposes.
     */
    public java.lang.Object isDestroying(OID oid)
    {
        AOMElement element = (AOMElement) m_table.get(oid);
        if ((element != null) && (element.destroying)) {
            return element;
        } else {
            return null;
        }
    }

    /**
     * Returns an enumeration of all oids in the Active Object Map.
     * 
     * @return The enumeration.
     */
    public java.util.Enumeration getOIDs()
    {
        return new AOMEnumeration(m_table);
    }

    /**
     * Inner class for the Active Object Map entries.
     */
    public class AOMElement
    {

        int num_users = 1; // the first user is the POA where oid is created

        org.omg.PortableServer.Servant servant = null;

        boolean destroying = false;

        public AOMElement(org.omg.PortableServer.Servant servant)
        {
            this.servant = servant;
        }

        public boolean equals(Object other)
        {
            if (other instanceof AOMElement) {
                return servant == ((AOMElement) other).servant;
            } else {
                return false;
            }
        }

    }

    /**
     * Inner class for the enumeration that getOIDs() returns.
     */
    public class AOMEnumeration
        implements java.util.Enumeration
    {

        private java.util.Enumeration keys;

        public AOMEnumeration(Hashtable table)
        {
            this.keys = table.keys();
        }

        public boolean hasMoreElements()
        {
            return keys.hasMoreElements();
        }

        public Object nextElement()
        {
            return keys.nextElement();
        }

    }

}