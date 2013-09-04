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

import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.Compression.*;
import es.tid.TIDorbj.core.TIDORB;


/**
 * <code>CompressorImpl</code> is the abstract implementation of a compressor.
 *
 * @author <a href="mailto:avega at tid dot es">Alvaro Vega Garcia</a>
 * @version 1.0
 */
public abstract class CompressorImpl extends CompressorLocalBase
{

    TIDORB                m_orb;
    short                 m_level;
    long                  m_compressed_bytes;
    long                  m_uncompressed_bytes;
    float                 m_ratio;
    CompressorFactory     m_factory;


    /**
     * Creates a new <code>CompressorImpl</code> instance.
     *
     */
    public CompressorImpl() 
    {
        this.m_orb     = null;
        this.m_factory = null;
        this.m_level   = 0;
        this.m_compressed_bytes   = 0;
        this.m_uncompressed_bytes = 0;
        this.m_ratio   = 0;
    }

    /**
     * Creates a new <code>CompressorImpl</code> instance.
     *
     * @param level a <code>short</code> value
     * @param factory a <code>CompressorFactory</code> value
     */
    public CompressorImpl(short level, CompressorFactory factory) 
    {
        this.m_orb     = null;
        this.m_factory = factory;
        this.m_level   = level;
        this.m_compressed_bytes   = 0;
        this.m_uncompressed_bytes = 0;
        this.m_ratio   = 0;
    }

    /**
     * Creates a new <code>CompressorImpl</code> instance.
     *
     * @param orb a <code>TIDORB</code> value
     * @param level a <code>short</code> value
     * @param factory a <code>CompressorFactory</code> value
     */
    public CompressorImpl(TIDORB orb, short level, CompressorFactory factory) 
    {
        this.m_orb     = orb;
        this.m_factory = factory;
        this.m_level   = level;
        this.m_compressed_bytes   = 0;
        this.m_uncompressed_bytes = 0;
        this.m_ratio   = 0;
    }
    
    /**
     * <code>compress</code> performs compression over source byte sequence and
     * puts the result in target.
     *
     * @param source a <code>byte</code> value
     * @param target an <code>org.omg.CORBA.OctetSeqHolder</code> value
     * @exception org.omg.Compression.CompressionException if an error occurs
     */
    public abstract void compress(byte[] source, 
                                  org.omg.CORBA.OctetSeqHolder target)
        throws org.omg.Compression.CompressionException;

    /**
     * <code>decompress</code> performs decompression over source and puts the result
     * in target.
     *
     * @param source a <code>byte</code> value
     * @param target an <code>org.omg.CORBA.OctetSeqHolder</code> value
     * @exception org.omg.Compression.CompressionException if an error occurs
     */
    public abstract void decompress(byte[] source, 
                                    org.omg.CORBA.OctetSeqHolder target)
        throws org.omg.Compression.CompressionException;
    
    /**
     * <code>compressor_factory</code> returns its compressor factory.
     *
     * @return an <code>org.omg.Compression.CompressorFactory</code> value
     */
    public org.omg.Compression.CompressorFactory compressor_factory()
    {
        return m_factory;
    }
    
    /**
     * <code>compression_level</code> returns its compression level.
     *
     * @return a <code>short</code> value
     */
    public short compression_level()
    {
        return m_level;
    }
    
    /**
     * Returns <code>compressed_bytes</code> written by this compressor.
     *
     * @return a <code>long</code> value
     */
    public long compressed_bytes()
    {
        return m_compressed_bytes;
    }
    
    /**
     * Returns <code>uncompressed_bytes</code> read by this compressor.
     *
     * @return a <code>long</code> value
     */
    public long uncompressed_bytes()
    {
        return m_uncompressed_bytes;
    }
    
    /**
     * Return <code>compression_ratio</code> reached with this compressor.
     *
     * @return an <code>int</code> value
     */
    public float compression_ratio()
    {
        return m_ratio;
    }


}
