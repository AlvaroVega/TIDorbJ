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

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INV_OBJREF;

import es.tid.TIDorbj.core.ObjectKey;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.comm.iiop.IORAddressingInfo;
import es.tid.TIDorbj.core.comm.iiop.IIOPProfile;
import es.tid.TIDorbj.core.comm.iiop.TargetAddress;
import es.tid.TIDorbj.core.comm.ssliop.SSLComponent;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.iop.TaggedProfile;
import es.tid.TIDorbj.core.util.Corbaloc;
import es.tid.TIDorbj.util.Base16Codec;
import es.tid.TIDorbj.core.policy.PolicyContext;
import es.tid.TIDorbj.core.ziop.ZIOP;

/**
 * Representation of an Internet Object Reference (IOR). An IOR is compounded
 * by;
 * <ul>
 * <li>at least one <code>TaggedProfile</code>
 * <li>the repositoryId of the reference
 * </ul>
 * In addition, the ior has its stringified representation and its marshaled
 * value.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 2.0
 */

public class IIOPIOR extends IOR
{

    String toString;

    // IIOP optimizations

    boolean iiopProfileSearched;

    IIOPProfile iiopProfile;

    int iiopProfilePosition;

    int hash_code = -1;

    boolean ziopSearched;

    boolean hasZIOPPolicies;

    public IIOPIOR() {
        this.toString = null;
        this.iiopProfile = null;
        this.iiopProfileSearched = false;
        this.iiopProfilePosition = 0;
        this.ziopSearched = false;
        this.hasZIOPPolicies = false;
    }

    public IIOPIOR(String id, TaggedProfile[] profiles) {
        super( id, profiles );
        this.toString = null;
        this.ziopSearched = false;
        this.hasZIOPPolicies = false;
    }

    public IIOPIOR( IOR ior ){
        assign( ior );
    }
    
    public void assign(IIOPIOR ior) {
        super.profiles = ior.profiles;
        super.type_id  = ior.type_id;
        this.toString  = ior.toString;
    }


    public static IIOPIOR fromString(TIDORB orb, String ior_str) {
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
        IIOPIOR ior = new IIOPIOR();

        CDRInputStream internet_cdr = new CDRInputStream(orb, buffer);

        internet_cdr.setByteOrder(internet_cdr.read_boolean());

        ior.read(internet_cdr);

        ior.toString = ior_str;

        return ior;
    }


    public synchronized IIOPProfile profileIIOP() {
        if (iiopProfile != null)
            return iiopProfile;

        if (iiopProfileSearched)
            throw new org.omg.CORBA.INV_OBJREF();

        iiopProfileSearched = true;

        int count = super.memberCount();

        for (int i = 0; i < count; i++) {
            if ( super.getProfile( i ).tag == org.omg.IOP.TAG_INTERNET_IOP.value) {
                iiopProfilePosition = i;
                iiopProfile = ( IIOPProfile ) getProfile( i );
                return iiopProfile;
            }
        }

        throw new org.omg.CORBA.INV_OBJREF();
    }
    
    public ObjectKey getObjectKey() {
        IIOPProfile profile = this.profileIIOP();

        return profile.getObjectKey();
    }

    public TargetAddress toObjectKeyAddress() {
        TargetAddress address = new TargetAddress();

        address.setObjectKey(getObjectKey());

        return address;
    }

    public TargetAddress toProfileAddress() {
        
        IIOPProfile profile = profileIIOP();

        TargetAddress address = new TargetAddress();

        address.profile(profile);

        return address;
    }

    public TargetAddress toIORAddress() {
        IIOPProfile profile = profileIIOP();

        TargetAddress address = new TargetAddress();

        address.ior(new IORAddressingInfo(iiopProfilePosition, this));

        return address;
    }

    /* (non-Javadoc)
     * @see es.tid.TIDorbj.core.iop.IOR#toURL()
     */
    public String toURL() {
        return Corbaloc.toURL( this );
    }

    public boolean is_ZIOP() {
        if (!ziopSearched) {
            hasZIOPPolicies = ZIOP.checkCompressionEnabled(profileIIOP().getPolicies());
            ziopSearched = true;
        }
        return hasZIOPPolicies;
    }

    public PolicyContext policies() {
        IIOPProfile profile = profileIIOP();
        return profile.getPolicies();
    }

    public org.omg.SSLIOP.SSL get_SSL() {
        if (!this.iiopProfileSearched) 
            return this.profileIIOP().getSSLComponent().getSSL();
        else 
            throw new org.omg.CORBA.INV_OBJREF("No SSL Component found in IOR", 0,
                                               CompletionStatus.COMPLETED_NO); 
    }


}
