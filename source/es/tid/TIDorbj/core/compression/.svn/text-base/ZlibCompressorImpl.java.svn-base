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

import java.util.zip.*;

import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.Compression.*;
import es.tid.TIDorbj.core.TIDORB;


/**
 * <code>ZlibCompressorImpl</code> Zlib compressor.
 *
 * @author <a href="mailto:avega at tid dot es">Alvaro Vega Garcia</a>
 * @version 1.0
 */
public class ZlibCompressorImpl extends CompressorImpl
{

    /**
     * Describe constant <code>CHUNK_SIZE</code> here.
     *
     */
    private int chunk_size;


    /**
     * Creates a new <code>ZlibCompressorImpl</code> instance.
     *
     * @param orb a <code>TIDORB</code> value
     * @param level a <code>short</code> value
     * @param factory a <code>CompressorFactory</code> value
     */
    public ZlibCompressorImpl(TIDORB orb, short level, CompressorFactory factory)
    {
        super(orb, level, factory);
        chunk_size = orb.m_conf.ziop_chunk_size;
    }

    /**
     * <code>compress</code> performs compression over source and puts result 
     * in target 
     *
     * @param source a <code>byte</code> value
     * @param target an <code>org.omg.CORBA.OctetSeqHolder</code> value
     * @exception org.omg.Compression.CompressionException if an error occurs
     */
    synchronized public void compress(byte[] source, 
                                      org.omg.CORBA.OctetSeqHolder target)
        throws org.omg.Compression.CompressionException
    {
        int have = 0;
        int source_length = source.length;

        byte[] target_buffer = new byte[0];
        int target_length = 0;

        Deflater compresser = new Deflater(m_level);

        compresser.setInput(source, 0, source.length);
        compresser.finish();

        byte[] out = new byte[chunk_size];

        do {
            have = compresser.deflate(out, 0, chunk_size);
      
            if (have > 0) {
                // Copy 'out' buffer to 'target' OctetSeq
                int current_length = target_length;
                byte[] aux_buffer = new byte[current_length + have];
                target_length += have;
                System.arraycopy(target_buffer, 0, aux_buffer, 0, current_length);
                System.arraycopy(out, 0, aux_buffer, current_length, have);
                target_buffer = aux_buffer;
            }
        } while (have > 0 );

        compresser.end();
        target.value = target_buffer;

        m_compressed_bytes   += target.value.length;
        m_uncompressed_bytes += source.length;
    }

    /**
     * <code>decompress</code> performs decompression on source sequence and puts
     * result in target.
     *
     * @param source a <code>byte</code> value
     * @param target an <code>org.omg.CORBA.OctetSeqHolder</code> value
     * @exception org.omg.Compression.CompressionException if an error occurs
     */
    synchronized public void decompress(byte[] source, 
                                        org.omg.CORBA.OctetSeqHolder target)
        throws org.omg.Compression.CompressionException
    {

        int have = 0;
        int source_length = source.length;

        byte[] target_buffer = new byte[0];
        int target_length = 0;

        Inflater decompresser = new Inflater();
        decompresser.setInput(source, 0, source.length);

        byte[] out = new byte[chunk_size];
 
        do {

            try {
                have = decompresser.inflate(out, 0, chunk_size);
            } catch (java.util.zip.DataFormatException dfe) {
                throw new org.omg.Compression.CompressionException();
            }

            if ( have > 0) {
                // Copy 'out' buffer to 'target' OctetSeq
                int current_length = target_length;
                byte[] aux_buffer = new byte[current_length + have];
                target_length += have;
                System.arraycopy(target_buffer, 0, aux_buffer, 0, current_length);
                System.arraycopy(out, 0, aux_buffer, current_length, have);
                target_buffer = aux_buffer;
            }
        } while (have > 0 );

        decompresser.end();
        target.value = target_buffer;
    }
    
}
