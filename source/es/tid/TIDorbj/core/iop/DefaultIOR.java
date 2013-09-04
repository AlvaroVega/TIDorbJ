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
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.NO_IMPLEMENT;

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.comm.iiop.TargetAddress;
import es.tid.TIDorbj.util.Base16Codec;

/**
 * @author jprojas
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultIOR extends IOR {

    /**
     * 
     */
    public DefaultIOR() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param type_id
     * @param profiles
     */
    public DefaultIOR( String type_id, TaggedProfile[] profiles ) {
        super( type_id, profiles );
    }

    public static IOR fromString(TIDORB orb, String ior_str) {
        
        int length = ior_str.length();
        if (((length % 2) != 0) || (length < 14))
            throw new INV_OBJREF("IOR format error: invalid ior length.", 0,
                                 CompletionStatus.COMPLETED_NO);
        if (!ior_str.startsWith("IOR:"))
            throw new INV_OBJREF("Invalid IOR format id: \"IOR:\".", 0,
                                 CompletionStatus.COMPLETED_NO);

        byte[] buffer = new byte[(length - 4) / 2];
        char[] ior_chars = ior_str.toCharArray();
        int j = 4;
        try {
            for (int i = 0; i < buffer.length; i++)
                buffer[i] = 
                    (byte) (16 * (Base16Codec.getValue(ior_chars[j++])) 
                        + Base16Codec.getValue(ior_chars[j++]));
        }
        catch (Exception e) {
            throw new INV_OBJREF(e.getMessage(), 0,
                                 CompletionStatus.COMPLETED_NO);
        }
        // create the the ior with the new buffer
        IOR ior = new DefaultIOR();

        CDRInputStream internet_cdr = new CDRInputStream(orb, buffer);

        internet_cdr.setByteOrder(internet_cdr.read_boolean());

        ior.read(internet_cdr);

        ior.toString = ior_str;

        return ior;
    }
    
    public String toURL(){
        return toString();
    }
    
    public ObjectKey getObjectKey() {
        throw new NO_IMPLEMENT();
    }

    public TargetAddress toObjectKeyAddress() {
        throw new NO_IMPLEMENT();
    }

    public TargetAddress toProfileAddress() {
        throw new NO_IMPLEMENT();
    }

    public TargetAddress toIORAddress() {
        throw new NO_IMPLEMENT();
    }

}
