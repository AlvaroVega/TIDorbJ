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
package es.tid.TIDorbj.core.iop;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import org.omg.IOP.TAG_INTERNET_IOP;
import org.omg.IOP.TAG_MULTIPLE_COMPONENTS;

import es.tid.TIDorbj.core.comm.iiop.IIOPProfile;

/**
 * Helper class for reading GIOP TaggedProfile.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */
 
//TODO: maybe a TaggedProfile factory could help in generalizing profiles management
//generating profiles depending on the ID, each profile could read itself from the CDR input
public abstract class TaggedProfileReader
{

    public static TaggedProfile 
    	read(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        int profile_id = input.read_ulong();

        switch (profile_id)
        {
            case TAG_INTERNET_IOP.value:
                IIOPProfile profile_iiop = new IIOPProfile();
                profile_iiop.partialRead(input);
                return profile_iiop;
            case TAG_MULTIPLE_COMPONENTS.value:
                ProfileMultipleComponents multi_comp =
                    new ProfileMultipleComponents();
                multi_comp.partialRead(input);
                return multi_comp;
            default:
                throw new MARSHAL("Invalid Profile TAG.",
                                  0,
                                  CompletionStatus.COMPLETED_NO);
        }
    }

}