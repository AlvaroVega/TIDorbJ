/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 349 $
* Date: $Date: 2009-01-07 09:56:41 +0100 (Wed, 07 Jan 2009) $
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
package es.tid.TIDorbj.core.messaging;


import org.omg.IOP.TAG_POLICIES;

import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;
import es.tid.TIDorbj.core.iop.TaggedComponent;
import es.tid.TIDorbj.core.policy.PolicyContext;

import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * Supported Qos Policines defined in CORBA Messaging module
 * @author caceres
 * 
 */
public class PoliciesComponent extends TaggedComponent
{
    private PolicyContext policies;
   
  
    public PoliciesComponent() { 
        super(TAG_POLICIES.value);
        this.policies = null;       
    }
    
    public PoliciesComponent(PolicyContext policies) { 
        super(TAG_POLICIES.value);
        this.policies = policies;       
    }  
    
    public void partialRead(CDRInputStream input)
    {
        policies = new PolicyContext(null);
        
        input.enterEncapsulation();
        
        policies.partialRead(input);
        
        input.exitEncapsulation();
        
    }
      
    
    public void write(CDROutputStream output)
    {
        output.write_ulong(TAG_POLICIES.value);               

        // enter ecapsulation

        output.enterEncapsulation();
        
        if(policies == null) {
            output.write_ulong(0);
        } else {
            policies.write(output);
        }
        
        output.exitEncapsulation();       
            
         
    }
    
    public PolicyContext getPolicies()
    {        
        return policies;
    }

    public String toString()
    {
        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);
        policies.dump(print_buffer);
        return buffer.toString();
    }
    
}
