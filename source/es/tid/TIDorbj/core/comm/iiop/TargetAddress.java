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

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.comm.giop.AddressingDisposition;
import es.tid.TIDorbj.core.iop.TaggedProfile;
import es.tid.TIDorbj.core.iop.TaggedProfileReader;

/**
 * Union <code>TargetAddress</code> defined in GIOP module (version 1.2).
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

//TODO: jprojas: should be refactored to use a generic profile and moved to giop
//package
public class TargetAddress
{

    short m_discriminator;

    protected ObjectKey m_object_key;

    protected IIOPProfile m_profile;

    protected IORAddressingInfo m_ior;

    public TargetAddress()
    {
        reset();
    }

    public short setObjectKey(ObjectKey value)
    {
        reset();
        m_discriminator = AddressingDisposition._KeyAddr;
        m_object_key = value;
        return m_discriminator;
    }

    public IIOPProfile profile()
    {
        return m_profile;
    }

    public short profile(IIOPProfile value)
    {
        reset();
        m_discriminator = AddressingDisposition._ProfileAddr;
        m_profile = value;
        return m_discriminator;
    }

    public IORAddressingInfo ior()
    {
        return m_ior;
    }

    public short ior(IORAddressingInfo value)
    {
        reset();
        m_discriminator = AddressingDisposition._ReferenceAddr;
        m_ior = value;
        return m_discriminator;
    }

    public short discriminator()
    {
        return m_discriminator;
    }

    private void reset()
    {
        m_discriminator = -1;
        m_object_key = null;
        m_profile = null;
        m_ior = null;
    }

    public ObjectKey getObjectKey()
    {
        if (m_discriminator < 0)
            return null;
        switch (m_discriminator)
        {
            case AddressingDisposition._KeyAddr:
                return m_object_key;
            case AddressingDisposition._ProfileAddr:
                return m_profile.getObjectKey();
            case AddressingDisposition._ReferenceAddr:
                TaggedProfile tag_profile;
                tag_profile = 
                    m_ior.m_ior.getProfile(m_ior.m_selected_profile_index);
                if (tag_profile instanceof IIOPProfile)
                    return ((IIOPProfile) tag_profile).getObjectKey();
                else
                    return null; // I can not know how select get the object key
            default:
                return null;
        }
    }

    public boolean write(es.tid.TIDorbj.core.cdr.CDROutputStream out)
    {
        if (m_discriminator < 0)
            return false;
        out.write_short(m_discriminator);
        switch (m_discriminator)
        {
            case AddressingDisposition._KeyAddr:
                m_object_key.write(out);
            break;
            case AddressingDisposition._ProfileAddr:
                m_profile.write(out);
            break;
            case AddressingDisposition._ReferenceAddr:
                m_ior.write(out);
            break;
            default:
                return false;
        }
        return true;
    }

    public void read(es.tid.TIDorbj.core.cdr.CDRInputStream in)
    {
        reset();
        AddressingDisposition disposition = 
            AddressingDisposition.from_short(in.read_short());

        if (disposition == null) {
            throw new MARSHAL("Invalid TargetAddress discriminator",
                              0,
                              CompletionStatus.COMPLETED_NO);
        }

        m_discriminator = disposition.value();

        switch (m_discriminator)
        {
            case AddressingDisposition._KeyAddr:
                m_object_key = new ObjectKey();
                m_object_key.read(in);
            break;
            case AddressingDisposition._ProfileAddr:
                TaggedProfile tag_profile = TaggedProfileReader.read(in);
                if (tag_profile instanceof IIOPProfile)
                    m_profile = (IIOPProfile) tag_profile;
                else
                    throw new MARSHAL("IIOP Profile expected.",
                                      0,
                                      CompletionStatus.COMPLETED_NO);

            break;
            case AddressingDisposition._ReferenceAddr:
                m_ior = IORAddressingInfo.read(in);
            break;
        }
    }

}