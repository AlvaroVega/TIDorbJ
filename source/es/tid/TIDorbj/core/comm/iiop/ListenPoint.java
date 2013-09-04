/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 478 $
* Date: $Date: 2011-04-29 16:42:47 +0200 (Fri, 29 Apr 2011) $
* Last modified by: $Author: avega $
*
* (C) Copyright 2004 Telef�nica Investigaci�n y Desarrollo
*     S.A.Unipersonal (Telef�nica I+D)
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
package es.tid.TIDorbj.core.comm.iiop;

/**
 * ListenPoint structure defined in the GIOP Module.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */
public class ListenPoint
{
    private String m_str;

    public String m_host;

    public int m_port;

    public int m_ssl_port;

    public ListenPoint(String host, int port)
    {
        m_str = null;
        m_host = host;
        m_port = port;
        m_ssl_port = 0;
    }

    public boolean equals(Object obj)
    {
        if (obj instanceof ListenPoint) {
            ListenPoint other = (ListenPoint) obj;
            return (m_port == other.m_port) && (m_host.equals(other.m_host));
        }

        return false;
    }

    public int hashCode()
    {
        return m_host.hashCode() + m_port;
    }

    public static ListenPoint read(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        return new ListenPoint(input.read_string(),
                               0xffff & input.read_ushort());
    }

    public static void write(es.tid.TIDorbj.core.cdr.CDROutputStream output,
                             ListenPoint point)
    {
        output.write_string(point.m_host);
        output.write_ushort((short) point.m_port);
    }

    public String toString()
    {
        if (m_str == null) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("ListenPoint(");
            buffer.append(String.valueOf(m_port));
            buffer.append('@');
            buffer.append(m_host);
            buffer.append(')');
            m_str = buffer.toString();
        }
        return m_str;
    }
}
