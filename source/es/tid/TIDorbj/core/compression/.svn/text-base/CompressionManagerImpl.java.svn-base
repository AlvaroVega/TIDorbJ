/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source
* Version: 1 
* Date: $Date: 2008-12-01 08:58:21 +0100 (Mon, 01 Dec 2008) $
* Last modified by: $Author $
*
* (C) Copyright 2008 Telef�nica Investigaci�n y Desarrollo
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

package es.tid.TIDorbj.core.compression;

import java.util.Hashtable;

import org.omg.Compression.*;
import es.tid.TIDorbj.core.TIDORB;


/**
 * <code>CompressionManagerImpl</code> Compression manager.
 *
 * @author <a href="mailto:avega at tid dot es">Alvaro Vega Garcia</a>
 * @version 1.0
 */
public class CompressionManagerImpl extends CompressionManagerLocalBase
{


    TIDORB m_orb;

    /**
     * <code>m_factories</code> Data structure to store factories.
     *
     */
    Hashtable m_factories;

    /**
     * Creates a new <code>CompressionManagerImpl</code> instance.
     *
     * @param orb a <code>TIDORB</code> value
     */
    public CompressionManagerImpl(TIDORB orb) 
    {
        this.m_orb  = orb;
        m_factories = new Hashtable();

        // Creates and registers default provided compressor: ZLib
        Short zlib_id = new Short(COMPRESSORID_ZLIB.value);
        CompressorFactory zlib_factory = 
            new CompressorFactoryImpl(orb, zlib_id.shortValue());
        m_factories.put(zlib_id, zlib_factory);
    }

    /**
     * <code>register_factory</code> Register a compression factory in the 
     * current manager
     *
     * @param compressor_factory a <code>CompressorFactory</code> value
     * @exception FactoryAlreadyRegistered if an error occurs
     */
    synchronized public void register_factory(CompressorFactory compressor_factory)
        throws FactoryAlreadyRegistered
    {
        Short comp_id = new Short(compressor_factory.compressor_id());

        CompressorFactory factory = 
            (CompressorFactory) m_factories.get(comp_id);
        
        if (factory == null) {
            m_factories.put(comp_id, compressor_factory);
        }
        else
            throw new FactoryAlreadyRegistered();
    }
    
    /**
     * <code>unregister_factory</code> Unregister a compression factory.
     *
     * @param compressor_id a <code>short</code> value
     * @exception UnknownCompressorId if an error occurs
     */
    synchronized public void unregister_factory(short compressor_id)
        throws UnknownCompressorId
    {
        Short comp_id = new Short(compressor_id);
        CompressorFactory factory = 
            (CompressorFactory) m_factories.get(comp_id);
        
        if (factory != null) {
            m_factories.remove(comp_id);
        }
        else
            throw new UnknownCompressorId();
    }


    /**
     * <code>get_factory</code> returns the required factory.
     *
     * @param compressor_id a <code>short</code> value
     * @return a <code>CompressorFactory</code> value
     * @exception UnknownCompressorId if an error occurs
     */
    synchronized public CompressorFactory get_factory(short compressor_id)
        throws UnknownCompressorId
    {
      
        Short comp_id = new Short(compressor_id);
        CompressorFactory factory = 
            (CompressorFactory) m_factories.get(comp_id);
        
        if (factory == null) {
            if ( (compressor_id != COMPRESSORID_GZIP.value)  &&
                 (compressor_id != COMPRESSORID_PKZIP.value) &&
                 (compressor_id != COMPRESSORID_BZIP2.value) &&
                 (compressor_id != COMPRESSORID_ZLIB.value)  && 
                 (compressor_id != COMPRESSORID_LZMA.value)  &&  
                 (compressor_id != COMPRESSORID_LZOP.value)  &&  
                 (compressor_id != COMPRESSORID_RZIP.value)  &&  
                 (compressor_id != COMPRESSORID_7X.value)    &&    
                 (compressor_id != COMPRESSORID_XAR.value)   )   
                throw new UnknownCompressorId();
            else
                factory = new CompressorFactoryImpl(m_orb, compressor_id);
        }
        return factory;

    }
    
    /**
     *  <code>get_compressor</code> returns the required compressor.
     *
     * @param compressor_id a <code>short</code> value
     * @param compression_level a <code>short</code> value
     * @return a <code>Compressor</code> value
     * @exception UnknownCompressorId if an error occurs
     */
    synchronized public Compressor get_compressor(short compressor_id, 
                                                  short compression_level)
        throws UnknownCompressorId
    {
        Short comp_id = new Short(compressor_id);
        CompressorFactory factory = (CompressorFactory) m_factories.get(comp_id);
        
        if (factory != null) {
            return factory.get_compressor(compression_level);
        }
        else 
            throw new UnknownCompressorId();

    }
    
    /**
     * <code>get_factories</code> returns all factories.
     *
     * @return a <code>CompressorFactory[]</code> value
     */
    synchronized public CompressorFactory[] get_factories()
    {

        Object[] keys = m_factories.keySet().toArray();

        CompressorFactory[] factories = new CompressorFactory[keys.length];

        for (int i = 0; i < keys.length; i++) {
            factories[i] = (CompressorFactory) m_factories.get(keys[i]);
        }

        return factories;
    }


}
