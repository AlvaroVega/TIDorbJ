/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 1 $
* Date: $Date: 2008-12-01 08:58:21 +0100 (Mon, 01 Dec 2008) $
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
package es.tid.TIDorbj.core.ziop;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.BAD_POLICY_VALUE;
import org.omg.CORBA.PolicyError;
import org.omg.Compression.*;
import org.omg.ZIOP.*;

import es.tid.TIDorbj.core.cdr.CDROutputStream;
import es.tid.TIDorbj.core.cdr.CDRInputStream;

/**
 * <code>CompressionMinRatioPolicyImpl</code> policy implmementation.
 *
 * @author <a href="mailto:avega at tid dot es">Alvaro Vega Garcia</a>
 * @version 1.0
 */
public class CompressionMinRatioPolicyImpl 
    extends CompressionMinRatioPolicyLocalBase
{

    float m_value;
    
    /**
     * Creates a new <code>CompressionMinRatioPolicyImpl</code> instance.
     *
     * @param val an <code>int</code> value
     */
    public CompressionMinRatioPolicyImpl(float val)
    {
        m_value = val;
    }

    /**
     * Returns <code>ratio</code> .
     *
     * @return an <code>int</code> value
     */
    public float ratio()
    {
        return m_value;
    }

    /**
     * Returns <code>policy_type</code> .
     *
     * @return an <code>int</code> value
     */
    public int policy_type()
    {
        return COMPRESSION_MIN_RATIO_POLICY_ID.value;
    }

    /**
     * <code>copy</code> current policy.
     *
     * @return an <code>org.omg.CORBA.Policy</code> value
     */
    public org.omg.CORBA.Policy copy()
    {
        return new CompressionMinRatioPolicyImpl(m_value);
    }

    /**
     * <code>destroy</code> current policy.
     *
     */
    public void destroy()
    {
        // do nothing!
    }
    
    /**
     * <code>createPolicy</code> .
     *
     * @param val an <code>Any</code> value
     * @return a <code>CompressionMinRatioPolicyImpl</code> value
     * @exception org.omg.CORBA.PolicyError if an error occurs
     */
    public static CompressionMinRatioPolicyImpl createPolicy(Any val)
        throws org.omg.CORBA.PolicyError
    {
        try {
            float policy_value = 
                CompressionMinRatioPolicyValueHelper.extract(val);
            
            return new CompressionMinRatioPolicyImpl(policy_value);
            
        }
        catch (BAD_PARAM bp) {
            throw new PolicyError(BAD_POLICY_VALUE.value);
        }
    }
    
    /**
     * <code>write</code> policy in to a encapsulation.
     *
     * @param output a <code>CDROutputStream</code> value
     * @param policy a <code>CompressionMinRatioPolicy</code> value
     */
    public static void write(CDROutputStream output, 
                             CompressionMinRatioPolicy policy)
    {      
        
        CompressionMinRatioPolicyValueHelper.write(output, 
                                                   policy.ratio());
    }
    
    

}
