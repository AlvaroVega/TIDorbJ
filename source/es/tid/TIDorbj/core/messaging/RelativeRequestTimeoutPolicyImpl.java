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
package es.tid.TIDorbj.core.messaging;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.BAD_POLICY_VALUE;
import org.omg.CORBA.PolicyError;
import org.omg.Messaging.RelativeRequestTimeoutPolicy;
import org.omg.TimeBase.TimeTHelper;

import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

public class RelativeRequestTimeoutPolicyImpl 
	extends org.omg.Messaging.RelativeRequestTimeoutPolicyLocalBase
{

    long m_value;

    public RelativeRequestTimeoutPolicyImpl(long val)
    {
        m_value = val;
    }

    public long relative_expiry()
    {
        return m_value;
    }

    public int policy_type()
    {
        return org.omg.Messaging.RELATIVE_REQ_TIMEOUT_POLICY_TYPE.value;
    }

    public org.omg.CORBA.Policy copy()
    {
        return new RelativeRequestTimeoutPolicyImpl(m_value);
    }

    public void destroy()
    {
    // do nothing!
    }
    
    public static void write(CDROutputStream output, 
                             RelativeRequestTimeoutPolicy policy)
    {         
        TimeTHelper.write(output, policy.relative_expiry());          
    }
    
    public static RelativeRequestTimeoutPolicyImpl read(CDRInputStream input)
    {
        long policy_value = input.read_ulong();
        return new RelativeRequestTimeoutPolicyImpl(policy_value);        
    }
    
    public static RelativeRequestTimeoutPolicyImpl createPolicy(Any val)
    throws org.omg.CORBA.PolicyError
    {
	    try {
	        long policy_value = org.omg.TimeBase.TimeTHelper.extract(val);
	
	        return new RelativeRequestTimeoutPolicyImpl(policy_value);
	
	    }
	    catch (BAD_PARAM bp) {
	        throw new PolicyError(BAD_POLICY_VALUE.value);
	    }
    }

}