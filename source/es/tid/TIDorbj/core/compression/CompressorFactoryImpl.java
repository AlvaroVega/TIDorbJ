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
* Last modified by: $Author $
*
* (C) Copyright 2008 Telefónica Investigación y Desarrollo
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

package es.tid.TIDorbj.core.compression;

import java.util.Hashtable;

import org.omg.Compression.*;
import es.tid.TIDorbj.core.TIDORB;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;

/**
 * <code>CompressorFactoryImpl</code> compressor factory class.
 *
 * @author <a href="mailto:avega at tid dot es">Alvaro Vega Garcia</a>
 * @version 1.0
 */
public class CompressorFactoryImpl extends CompressorFactoryLocalBase
{
    TIDORB m_orb;

    /**
     * <code>m_compressor_id</code> identifies the compressor family.
     *
     */
    short m_compressor_id;
    
    Hashtable m_compressors;
    
    /**
     * Creates a new <code>CompressorFactoryImpl</code> instance.
     *
     * @param orb a <code>TIDORB</code> value
     * @param compressor_id a <code>short</code> value
     */
    public CompressorFactoryImpl(TIDORB orb, short compressor_id) 
    {
        this.m_orb  = orb;
        this.m_compressor_id = compressor_id;

        switch (m_compressor_id) {
        case COMPRESSORID_ZLIB.value:
            break;
        default:
            throw new NO_IMPLEMENT();
        }
        m_compressors = new Hashtable();
    }
    
    
    /**
     * Returns <code>compressor_id</code> value.
     *
     * @return a <code>short</code> value
     */
    public short compressor_id()
    {
        return m_compressor_id;
    }
    
    /**
     * <code>get_compressor</code> returns a compressor according with the 
     * required level
     *
     * @param compression_level a <code>short</code> value
     * @return a <code>Compressor</code> value
     */
    synchronized public Compressor get_compressor(short compression_level)
    {
        if (compression_level > 9 )
            throw new BAD_PARAM("Compression level not valid", 44, 
                                CompletionStatus.COMPLETED_NO);
        Short comp_level = new Short(compression_level);
        Compressor compressor = (Compressor) m_compressors.get(comp_level);
        
        if (compressor != null) {
            return compressor;
        } else {
            
            Compressor new_compressor;
            
            switch (m_compressor_id) {
            case COMPRESSORID_GZIP.value:
            case COMPRESSORID_PKZIP.value:
            case COMPRESSORID_BZIP2.value:
                throw new NO_IMPLEMENT();
            case COMPRESSORID_ZLIB.value:
                new_compressor = new ZlibCompressorImpl(m_orb, compression_level, 
                                                        this);
                break;
            default:
                throw new NO_IMPLEMENT();
            }
            m_compressors.put(comp_level, new_compressor);

            return new_compressor;
        }

    }
    
    
    
}
