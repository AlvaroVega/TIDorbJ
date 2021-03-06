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
package es.tid.TIDorbj.core.comm.iiop;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;

import es.tid.TIDorbj.core.iop.DefaultIOR;
import es.tid.TIDorbj.core.iop.IOR;

/**
 * IORAddressingInfo structure defined in the GIOP Module.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */
public class IORAddressingInfo {

    public int m_selected_profile_index;

    public IOR m_ior;

    public IIOPProfile getProfileIIOP()
    {
        if (m_selected_profile_index == -1)
            return null;
        else
            return (IIOPProfile) m_ior.getProfile(m_selected_profile_index);
    }

    public IORAddressingInfo(int selected_profile_index, IOR ior)
    {
        this.m_selected_profile_index = selected_profile_index;
        this.m_ior = ior;
    }

    public static IORAddressingInfo 
    	read(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        int index;

        index = input.read_ulong();

        IOR ior = new DefaultIOR();

        ior.read(input);

        if (index >= ior.memberCount())
            throw new MARSHAL("Invalid selected Profile Number: " + index,
                              0,
                              CompletionStatus.COMPLETED_NO);

        return new IORAddressingInfo(index, ior);
    }

    public void write(es.tid.TIDorbj.core.cdr.CDROutputStream output)
    {
        output.write_ulong(m_selected_profile_index);
        m_ior.write(output);
    }
}