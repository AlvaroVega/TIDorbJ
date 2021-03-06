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

import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.IOP.TAG_MULTIPLE_COMPONENTS;

/**
 * ProfileMultipleComponents sequence defined in the GIOP Module.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */
public class ProfileMultipleComponents extends TaggedProfile
{

    private TaggedComponent[] m_components;

    es.tid.TIDorbj.core.cdr.CDRInputStream m_profile_data;

    protected ProfileMultipleComponents()
    {
        super(TAG_MULTIPLE_COMPONENTS.value);
        m_components = null;
        m_profile_data = null;
    }

    public ProfileMultipleComponents(TaggedComponent[] tagged_components)
    {
        super(TAG_MULTIPLE_COMPONENTS.value);
        m_components = tagged_components;
    }

    public TaggedComponent[] getTaggedComponents()
    {
        if (m_profile_data != null)
            extract_members();
        return m_components;
    }

    synchronized protected void extract_members()
    {
        if (m_profile_data == null)
            throw new org.omg.CORBA.INTERNAL("Empty Profile");

        m_profile_data.enterEncapsulation();

        int size = m_profile_data.read_ulong();
        if (size < 0)
            throw new org.omg.CORBA.MARSHAL("Invalid component size");
        if (size > 0) {
            m_components = new TaggedComponent[size];
            for (int i = 0; i < size; i++) {
                m_components[i] = TaggedComponentReader.read(m_profile_data);
            }
        }

        m_profile_data = null;
    }

    public boolean equal( Object o ){
        ProfileMultipleComponents pfc;
        if ( o instanceof ProfileMultipleComponents ){
            pfc = ( ProfileMultipleComponents )o;
        } else {
            pfc = null;
        }
        
        if ( pfc == null ){
            return false;
        } else {
            throw new NO_IMPLEMENT();
        }
    }
    
    synchronized public void 
    	write(es.tid.TIDorbj.core.cdr.CDROutputStream output)
    {
        if (m_profile_data != null) {
            extract_members();
        }

        // write tag
        output.write_ulong(tag);

        // write profile data

        output.enterEncapsulation();

        if (m_components == null)
            output.write_ulong(0);
        else {
            output.write_ulong(m_components.length);
            for (int i = 0; i < m_components.length; i++)
                m_components[i].write(output);
        }

        output.exitEncapsulation();
    }

    public void partialRead(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        m_profile_data = input.copy();
        input.skipEncapsulation();

        /*
         * profile_data = input.copy();
         * 
         * profile_data.fix_starting();
         * 
         * int length = input.read_ulong();
         * 
         * for(int i = 0; i <length; i++) TaggedComponent.skip(input);
         */
    }
}