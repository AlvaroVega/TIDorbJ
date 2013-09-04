/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 395 $
* Date: $Date: 2009-05-27 16:10:32 +0200 (Wed, 27 May 2009) $
* Last modified by: $Author $
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
package es.tid.TIDorbj.core.messaging;

import org.omg.Messaging.*;
import org.omg.TimeBase.UtcT;


import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.policy.PolicyContext;
import es.tid.TIDorbj.util.Trace;
import es.tid.TIDorbj.util.UTC;

/**
 * Supported Qos Policines defined in CORBA Messaging module
 * @author caceres
 * 
 */
public class QoS
{     
    /**
     * Get the RequestEnd time from the RequestEndTimePolicy and 
     * the RelativeRoundtripPolicy
     * @param context
     * @return
     */
    public static RequestEndTimePolicy 
    	getEffectiveRequestEndTimePolicy(PolicyContext context)
    { 
        RequestEndTimePolicy requestEndTimePolicy =
            	context.getRequestEndTimePolicy();
        RelativeRoundtripTimeoutPolicy relativeRountripTimeoutPolicy =
            	context.getRelativeRoundtripTimeoutPolicy();
        
        if(relativeRountripTimeoutPolicy == null) {
            return requestEndTimePolicy;            
        } else {
            
            long endTime = UTC.toTimeT(UTC.currentUtcTimeMillis())                 
                + relativeRountripTimeoutPolicy.relative_expiry();
            
            if((requestEndTimePolicy != null) 
                && (endTime > requestEndTimePolicy.end_time().time) ) {
                return requestEndTimePolicy;                
            } else {
                return new RequestEndTimePolicyImpl(endTime);
            }
            
        }        
    }
         
    
     
    
    /**
     * Checks if the request can be sent and the time while the sender can be
     * waiting for the response.
     * This operation sleeps the time until the RequestStartTimePolicy 
     * allows the invocation.
     * @param orb The ORB     
     * @return the time to wait the response
     * @throws org.omg.CORBA.TIMEOUT if the request time has expired
     */
    public static long checkRequestTime(TIDORB orb,
                                        PolicyContext context)
    {   
        RequestStartTimePolicy requestStartTimePolicy=
            context.getRequestStartTimePolicy();
               
        long currentTime = 0;        
                                            
        if(requestStartTimePolicy != null) {
            currentTime = UTC.currentUtcTimeMillis(); 
            checkStart(orb, context, currentTime); 
        }
        
        long requestTimeout = 
            getEffectiveRequestTimeoutInMillis(context, currentTime);     
       
    
        if(requestTimeout < 0) {
            String msg = 
                "QoS RequestEndTimePolicy/RelativeRountripTimeoutPolicy exceeded";
            msg += " " + requestTimeout;
            throw new org.omg.CORBA.TIMEOUT(msg);
        } else {
            return requestTimeout;
        }
            
       
        
    }

   
    /**
     * @param orb
     * @param currentTime in ns.
     * @return the time to wait the request in ms. (greather or equal than 0)
     */
    private static long getEffectiveRequestTimeoutInMillis(PolicyContext context,
                                                            long currentTime)
    {           
        RelativeRoundtripTimeoutPolicy relativeRountripTimeoutPolicy =
            context.getRelativeRoundtripTimeoutPolicy();
        
        RequestEndTimePolicy requestEndTimePolicy =
            context.getRequestEndTimePolicy();
        
        if(requestEndTimePolicy == null) {
            if(relativeRountripTimeoutPolicy != null) {
                return UTC.toTimeInMillis(
                             relativeRountripTimeoutPolicy.relative_expiry());
            } else {
                return 0;
            }
        } else {
            if(currentTime == 0) {
                currentTime = UTC.currentUtcTimeMillis();
            }
            
            long endTimeout = 
                UTC.toTimeInMillis(requestEndTimePolicy.end_time().time)
                - currentTime;                             
        
            if(relativeRountripTimeoutPolicy != null) {
                long relativeTimeout = 
                    UTC.toTimeInMillis(relativeRountripTimeoutPolicy.relative_expiry());
                return (endTimeout < relativeTimeout)? endTimeout : relativeTimeout;
            } else {
                return endTimeout;
            }
        }        
    }

    /**
     * @param orb
     * @param currentTime
     * @param startTimePolicy
     * @return
     */
    private static boolean checkStart(TIDORB orb,
                                      PolicyContext context,
                                      long currentTime)
    {   
        RequestStartTimePolicy requestStartTimePolicy =
            context.getRequestStartTimePolicy();
        
        // UtcT.time is in order of 100 ns. (1 ns = 10^6 ms.) 100 ns = 10^4
        
        long startTime = 
            UTC.toTimeInMillis(requestStartTimePolicy.start_time().time);
        
        long sleepMillis = startTime - currentTime;
        
        if(sleepMillis > 0) {           
            
            try {
                Thread.sleep(sleepMillis);
            }
            catch (InterruptedException e) {
                if(orb.m_trace != null ) {
                    String msg = 
                    	"QoS: Request has received an InterruptedException ";
                    
                    orb.printTrace( Trace.ERROR, msg);
                }
            }  
            
            if(orb.m_trace != null ) {
                String[] msg = 
                	{"QoS: Request has slept ",
                	 Long.toString(sleepMillis),
                	 " ms. util Messaging::RequestStartTimePolicy value"
                	};
                
                orb.printTrace( Trace.DEEP_DEBUG, msg);
            }
            
            return true;
        }
        
        return false;
    }
     

    /**
     * Validates QoS RequestEndTimePolicy in the ServerSide
     * @param policySupplier
     * @param trace
     * @return
     */
    public static boolean validateServerRequestEndTimePolicy(PolicyContext context)
    { 
        if(context == null) {
            return true;
        }
        
        RequestEndTimePolicy requestEndTimePolicy = 
            context.getRequestEndTimePolicy();
        
        if(requestEndTimePolicy == null) {
            return true;            
        } else {            
            long current_time = UTC.currentUtcTimeMillis();
            UtcT utc_time = requestEndTimePolicy.end_time();
            // Request TIMEOUT?
            if(current_time > UTC.toTimeInMillis(utc_time.time)) {               
                
                return false;
            }
            
            return true;
        }        
        
    }

   


}
