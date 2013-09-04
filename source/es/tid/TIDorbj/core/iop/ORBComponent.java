/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 453 $
* Date: $Date: 2010-04-27 16:52:41 +0200 (Tue, 27 Apr 2010) $
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
package es.tid.TIDorbj.core.iop;

import java.io.StringWriter;
import java.io.PrintWriter;

import es.tid.TIDorbj.core.ConfORB;
import org.omg.IOP.TAG_ORB_TYPE;

/**
 * ORBComponent data struct defined in the GIOP Module.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */
public class ORBComponent extends TaggedComponent
{

    public int m_orb_type;

    public ORBComponent()
    {
        super(TAG_ORB_TYPE.value);
        m_orb_type = 0;
    }

    public ORBComponent(int type)
    {
        super(TAG_ORB_TYPE.value);
        m_orb_type = type;
    }

    public void write(es.tid.TIDorbj.core.cdr.CDROutputStream out)
    {
        out.write_ulong(m_tag);

        // enter ecapsulation

        out.enterEncapsulation();

        out.write_ulong(m_orb_type);

        out.exitEncapsulation();
    }

    public void partialRead(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        input.enterEncapsulation();
        m_orb_type = input.read_ulong();
        input.exitEncapsulation();
    }

    public String toString()
    {
        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);
        print_buffer.print('\n');
        print_buffer.print('\t');
        print_buffer.print('\t');
        if (m_orb_type == ConfORB.ORB_TYPE.m_orb_type) {
            print_buffer.print("ORB type ID: ");
            print_buffer.print(m_orb_type);
            print_buffer.print(" (TIDORB - Telefonica I+D)");
        } else {
            print_buffer.print("ORB: ");
            print_buffer.print(m_orb_type);
        }

        return buffer.toString();
    }

}
