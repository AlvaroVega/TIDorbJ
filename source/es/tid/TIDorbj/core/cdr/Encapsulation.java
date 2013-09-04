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
package es.tid.TIDorbj.core.cdr;

import org.omg.CORBA.ORB;

import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.comm.giop.GIOPVersion;

/**
 * Represents the content data of an encapsulation (including the byte-order).
 * An Encapsulation can be read to copy the content data from an input stream.
 * <p>
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class Encapsulation
{

    /**
     * The empty encapsulation.
     */

    private static Encapsulation st_empty = null;

    /**
     * The ORB.
     */
    protected TIDORB m_orb;

    /**
     * GIOPVersion
     */
    protected GIOPVersion m_version;

    /**
     * The octet sequence data.
     */
    protected byte[] m_data;

    /**
     * Start Position of the encapsulation in the byte array.
     */
    int m_data_offset;

    /**
     * Length of the encapsulation in the byte array.
     */
    int m_data_length;

    /**
     * Protected Constructor. Creates an empty encapsulation.
     */
    protected Encapsulation(TIDORB orb, GIOPVersion ver)
    {
        this.m_orb = orb;
        m_version = ver;
        m_data_length = 0;
        m_data_offset = 0;
    }

    /**
     * Protected Constructor.
     * 
     * @param orb
     *            The ORB. 
     * @param key_buffer 
     *            The octet sequence data.
     * @param offset
     *            Start Position of the encapsulation in the byte array.
     * @param length
     *            Length of the encapsulation in the byte array.
     */
    protected Encapsulation(TIDORB orb, GIOPVersion ver, byte[] key_buffer,
                            int offset, int length)
    {
        this.m_orb = orb;
        m_version = ver;
        m_data = key_buffer;
        m_data_offset = offset;
        m_data_length = length;
    }

    public TIDORB getORB()
    {
        return m_orb;
    }

    /**
     * @return the encapsulation length (in octets).
     */
    public int getLength()
    {
        return m_data_length;
    }

    /**
     * @return the encapsulation start position in the octet sequence.
     */
    public int getOffset()
    {
        return m_data_offset;
    }

    /**
     * @return the encapsulation octet sequence.
     */
    public byte[] getOctetSequence()
    {
        return m_data;
    }

    /**
     * Compares two encapsulations.
     */
    public boolean equal(Encapsulation other)
    {
        if (other == null)
            return false;

        if (m_data_length != other.m_data_length)
            return false;

        int i = m_data_offset;
        int j = other.m_data_offset;

        for (int length = 0; length < m_data_length; length++) {
            if (m_data[i++] != other.m_data[j++])
                return false;
        }

        return true;
    }

    /**
     * Gets a sub-encapsulation in the octet sequence.
     * 
     * @param offset
     *            Start Position of the encapsulation in the byte array.
     * @param length
     *            Length of the encapsulation in the byte array.
     */
    public Encapsulation getChild(int offset, int length)
    {
        if ((offset >= m_data_length)
            || ((m_data_length - offset - length) < 0))
            return null;
        else
            return new Encapsulation(m_orb, m_version, m_data, offset, length);
    }

    /**
     * @return the empty encapsulation.
     */
    public static Encapsulation getEmpty()
    {
        if (st_empty == null)
            st_empty = new Encapsulation(null, GIOPVersion.VERSION_1_0);

        return st_empty;
    }

    /**
     * Static Encapsulation Creator operation.
     * 
     * @param orb
     *            The ORB. key_buffer The octet sequence data.
     * @param offset
     *            Start Position of the encapsulation in the byte array.
     * @param length
     *            Length of the encapsulation in the byte array.
     * @return a new Encapsulation.
     */

    public static Encapsulation create(TIDORB orb, 
                                       GIOPVersion ver,
                                       byte[] key_buffer, 
                                       int offset, 
                                       int length)
    {
        if (length < 0)
            return null;

        if (length == 0)
            return getEmpty();

        if ((key_buffer == null) || (offset < 0)
            || (key_buffer.length - offset - length < 0))
            return null;

        return new Encapsulation(orb, ver, key_buffer, offset, length);
    }

    /**
     * @return an input stream for reading the encapsulation data.
     */
    public CDRInputStream createInputStream()
    {
        if (m_data_length == 0)
            return null;

        CDRInputStream input = new CDRInputStream(m_orb, m_data);

        if (m_data_offset > 0) {
            input.skip(m_data_offset);
            input.fixStarting();
        }

        input.setByteOrder(input.read_boolean());
        input.setVersion(m_version);

        return input;
    }

    /**
     * Writes the encapsulation data in an output stream.
     * 
     * @param out
     *            the output stream.
     */
    public void write(CDROutputStream out)
    {
        out.write_ulong(m_data_length);

        if (m_data_length > 0)
            out.write_octet_array(m_data, m_data_offset, m_data_length);
    }

    /**
     * Reads a new encapsulation from an input stream.
     * 
     * @param out
     *            the output stream.
     */

    public static Encapsulation read(CDRInputStream input, GIOPVersion ver)
    {
        ORB orb = input.orb();

        Encapsulation encap = null;
        if (orb instanceof TIDORB)
            encap = new Encapsulation((TIDORB) orb, ver);
        else
            encap = new Encapsulation(null, ver);

        encap.doRead(input);

        return encap;
    }

    private void doRead(CDRInputStream input)
    {
        m_data_length = input.read_ulong();

        if (m_data_length > 0) {
            m_data = new byte[m_data_length];
            input.read_octet_array(m_data, 0, m_data_length);
        }
    }
}