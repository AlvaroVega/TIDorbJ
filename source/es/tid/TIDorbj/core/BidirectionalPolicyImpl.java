/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 82 $
* Date: $Date: 2007-07-05 07:42:09 +0200 (Thu, 05 Jul 2007) $
* Last modified by: $Author: avega $
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
package es.tid.TIDorbj.core;

import org.omg.BiDirPolicy.BOTH;
import org.omg.BiDirPolicy.BidirectionalPolicy;
import org.omg.BiDirPolicy.BidirectionalPolicyHelper;
import org.omg.BiDirPolicy.BidirectionalPolicyValueHelper;
import org.omg.BiDirPolicy.NORMAL;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.BAD_POLICY_VALUE;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;

import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

/**
 * Implementation of BidirectionalPolicy
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */
public class BidirectionalPolicyImpl extends PseudoObject
    implements org.omg.BiDirPolicy.BidirectionalPolicy
{
    /**
     * The policy value: BidirPolicy::BOTH or BiDirPolicy::NORMAL
     */
    private short m_value;

    public BidirectionalPolicyImpl(short value)
    {
        m_value = value;
    }

    public short value()
    {
        if (m_destroyed) {
            throw new OBJECT_NOT_EXIST();
        }

        return m_value;
    }

    public int policy_type()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST();

        return org.omg.BiDirPolicy.BIDIRECTIONAL_POLICY_TYPE.value;
    }

    public org.omg.CORBA.Policy copy()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST();

        return new BidirectionalPolicyImpl(m_value);
    }

    public void destroy()
    {
        m_destroyed = true;
    }

    // OBJECT methods

    public boolean _is_a(java.lang.String repositoryIdentifier)
    {
        if (m_destroyed) {
            throw new OBJECT_NOT_EXIST("Policy destroyed.");
        }

        if (repositoryIdentifier == null)
            throw new BAD_PARAM("Null string reference");

        if (repositoryIdentifier.equals(
              "IDL:omg.org/BiDirPolicy/BidirectionalPolicy:1.0")) {
            return true;
        }

        return super._is_a(repositoryIdentifier);

    }
    
    public static Policy read(CDRInputStream input)
    {
        short value = input.read_short();
          
        if(value != BOTH.value && value != NORMAL.value) {
            throw new MARSHAL();
        }
        
        return new BidirectionalPolicyImpl(value);
    }

    /**
     * @param output
     */
    public static void write(CDROutputStream output, Policy policy)
    {
        BidirectionalPolicy bidir_policy = 
            BidirectionalPolicyHelper.narrow(policy);
        
        output.write_ushort(bidir_policy.value());
        
    }
    
    public static BidirectionalPolicyImpl createPolicy(Any val)
    throws org.omg.CORBA.PolicyError
{
    try {
        short policy_value = BidirectionalPolicyValueHelper.extract(val);

        if ((policy_value != org.omg.BiDirPolicy.BOTH.value)
            && (policy_value != org.omg.BiDirPolicy.NORMAL.value))
            throw new PolicyError(BAD_POLICY_VALUE.value);

        return new BidirectionalPolicyImpl(policy_value);

    }
    catch (BAD_PARAM bp) {
        throw new PolicyError(BAD_POLICY_VALUE.value);
    }
}
}