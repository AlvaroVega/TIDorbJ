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
package es.tid.TIDorbj.core;

import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.Encapsulation;
import es.tid.TIDorbj.util.Base64Codec;

/**
 * ObjectKey data structure defined in the GIOP Module.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */
public class ObjectKey
{
    private String str;

    Encapsulation m_marshaled_key;
    
    public ObjectKey(){
    }

    public ObjectKey(Encapsulation key) {
        this.m_marshaled_key = key;
    }

    public boolean equal(ObjectKey other)
    {
        return ( m_marshaled_key == null && m_marshaled_key == other.m_marshaled_key ) || 
			   ( m_marshaled_key.equal( other.m_marshaled_key ) );
    }

    public void read(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        m_marshaled_key = input.readEncapsulation();
    }

    public void write(es.tid.TIDorbj.core.cdr.CDROutputStream output)
    {
        if (m_marshaled_key != null) {
            output.writeEncapsulation(m_marshaled_key);
    	} else {
            throw new org.omg.CORBA.INTERNAL("Malformed Objectkey");
    	}
    }

    public void setMarshaledKey( Encapsulation marshaled_key ) {
        this.m_marshaled_key = marshaled_key;
    }

    public Encapsulation getMarshaledKey() {
        return m_marshaled_key;
    }

    //TODO: tryToGetURLKey should be removed, and also the one in IIOPCorbaLoc
    public String getURL() {
        //TODO: check if this should be stored locally for further invocations
        String url;
        try {
            int size = this.m_marshaled_key.getLength();
            int offset = this.m_marshaled_key.getOffset();
            
            CDRInputStream input = new CDRInputStream(
                this.m_marshaled_key.getORB(),
                this.m_marshaled_key.getOctetSequence()
            );
    
            if (offset > 0) {
                input.skip(offset);
            }
    
            char[] url_key = new char[size];
    
            input.read_char_array(url_key, 0, size);
    
            

            url =  new String(url_key);
        } catch ( Throwable th ){
            th.printStackTrace();
            url = null;
        }
        return url;
    }//getURL

    public synchronized String toString()
    {
        if(str == null) {
            str = Base64Codec.encode(m_marshaled_key.getOctetSequence());
        }
        
        return str;
    }
}