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
* (C) Copyright 2005 Telefónica Investigación y Desarrollo
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
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.Messaging.REQUEST_START_TIME_POLICY_TYPE;
import org.omg.Messaging.RequestStartTimePolicy;
import org.omg.Messaging.RequestStartTimePolicyHelper;
import org.omg.Messaging.RequestStartTimePolicyLocalBase;
import org.omg.TimeBase.UtcT;
import org.omg.TimeBase.UtcTHelper;

import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

/**
 * @author caceres
 */

public class RequestStartTimePolicyImpl extends RequestStartTimePolicyLocalBase
{
    UtcT startTime;       
   
    /**
     * @param time
     */
    public RequestStartTimePolicyImpl(UtcT time)
    {        
        startTime = time;     
       
        
    }
    
    public UtcT start_time()
    {        
        return startTime;
    }
    
       
    public int policy_type()
    {        
        return REQUEST_START_TIME_POLICY_TYPE.value;
    }
         

    
    public void destroy()
    {    

    }
    
    public Policy copy()
    {        
        return new RequestStartTimePolicyImpl(startTime);
    }
    
    public static void write(CDROutputStream output, Policy policy)
    {
        RequestStartTimePolicy start_policy = 
            RequestStartTimePolicyHelper.narrow(policy);
        
        UtcTHelper.write(output, start_policy.start_time());          
    }
    
    /**
     * @param input
     * @return
     */
    public static RequestStartTimePolicyImpl read(CDRInputStream input)
    {
        UtcT end_time = UtcTHelper.read(input);
        return new RequestStartTimePolicyImpl(end_time);        
    }

    public static RequestStartTimePolicyImpl createPolicy(Any val)
	throws org.omg.CORBA.PolicyError
	{
	    try {
	        UtcT policy_value = org.omg.TimeBase.UtcTHelper.extract(val);
	
	        return new RequestStartTimePolicyImpl(policy_value);
	    }
	    catch (BAD_PARAM bp) {
	        throw new PolicyError(BAD_POLICY_VALUE.value);
	    }
	}

}
