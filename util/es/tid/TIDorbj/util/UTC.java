/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 453 $
* Date: $Date: 2010-04-27 16:52:41 +0200 (Tue, 27 Apr 2010) $
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

package es.tid.TIDorbj.util;

import java.util.TimeZone;

/**
 * Utility class to convert time to UTC using Gregorian Calendar.
 * Gregorian calendar base time is 15th October 1582 00:00.
 * Java System time takes as base time 1th January 1970.
 * This class also has operations to convert types of the TimeBase module .
 * 
 * @author caceres
 *
 */

public class UTC
{    
    /**
     * 1/1/1970 00:00 time in Gregorian Calendar
     */
    public final static long GREGORIAN_CUTOVER = 12219292800000L;
        
       
    /**
     * Gets the current time in millisecons from 15th October 1582 00:00
     * @return the value in millisecons
     */
    public static long currentUtcTimeMillis()
    {
        return System.currentTimeMillis() +
                GREGORIAN_CUTOVER;  // current time is form 1th Jan 1973 0:0 
    }

    /**
     * Converts milliseconds to TimeBase::TimeT units (1 = 100 ns.)
     * @param timeInMillis
     * @return
     */
    public static long toTimeT(long timeInMilliseconds)
    {
        return timeInMilliseconds * 10000;
    }
    
    /**
    * Converts TimeBase::TimeT units (1 = 100 ns.) to milliseconds 
    * @param timeInMillis
    * @return
    */
   public static long toTimeInMillis(long timeT)
   {
       return timeT / 10000;
   }

}
