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
 * System Generated OID.
 * 
 * @author Juan A. C&aacute;ceres
 * @version 1.0
 */

public class SystemOID extends OID
{

    private final static int LONG_SIZE = 8;

    private long m_poa_id;

    private long m_serial;

    public SystemOID(long poa_id, long serial)
    {
        m_poa_id = poa_id;
        m_serial = serial;
        m_value = new byte[2 * LONG_SIZE];
        write_long(poa_id, m_value, 0);
        write_long(serial, m_value, LONG_SIZE);
    }

    public SystemOID(long poa_id, long serial, byte[] val)
    {
        super(val);
        this.m_poa_id = poa_id;
        this.m_serial = serial;
    }

    public SystemOID next()
    {
        return new SystemOID(m_poa_id, m_serial + 1);
    }

    public long getPOAId()
    {
        return m_poa_id;
    }

    public long getSerial()
    {
        return m_serial;
    }

    public static SystemOID fromOID(OID oid)
    {
        SystemOID other = null;

        try {
            other = (SystemOID) oid;
        }
        catch (ClassCastException cce) {
            return fromByteArray(oid.toByteArray());
        }

        return new SystemOID(other.m_poa_id, other.m_serial, other.m_value);
    }

    public static SystemOID fromByteArray(byte[] buffer)
    {
        if ((buffer == null) || (buffer.length != 2 * LONG_SIZE))
            return null;

        long poa_id = read_long(buffer, 0);

        long serial = read_long(buffer, LONG_SIZE);

        return new SystemOID(poa_id, serial, buffer);
    }

    public static void write_long(long value, byte[] buffer, int offset)
    {
        int position = offset;
        buffer[position++] = (byte) (value >>> 56);
        buffer[position++] = (byte) (value >>> 48);
        buffer[position++] = (byte) (value >>> 40);
        buffer[position++] = (byte) (value >>> 32);
        buffer[position++] = (byte) (value >>> 24);
        buffer[position++] = (byte) (value >>> 16);
        buffer[position++] = (byte) (value >>> 8);
        buffer[position++] = (byte) (value >>> 0);
    }

    public static long read_long(byte[] buffer, int offset)
    {

        int position = offset;

        return (((long) buffer[position++] & 0xffL) << 56)
               | (((long) buffer[position++] & 0xffL) << 48)
               | (((long) buffer[position++] & 0xffL) << 40)
               | (((long) buffer[position++] & 0xffL) << 32)
               | (((long) buffer[position++] & 0xffL) << 24)
               | (((long) buffer[position++] & 0xffL) << 16)
               | (((long) buffer[position++] & 0xffL) << 8)
               | ((long) buffer[position++] & 0xffL);
    }

}