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
package es.tid.TIDorbj.core.comm.giop; // ziop??

import org.omg.CORBA.INTERNAL;
import org.omg.Compression.CompressorIdLevel;
import org.omg.Compression.Compressor;

import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.util.Trace;
import es.tid.TIDorbj.core.cdr.BufferCDR;
import es.tid.TIDorbj.core.cdr.CDROutputStream;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.ChunkCDR;
import es.tid.TIDorbj.core.comm.Connection;
import es.tid.TIDorbj.core.comm.giop.GIOPMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPHeader;

import es.tid.TIDorbj.core.comm.giop.GIOPRequestMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPReplyMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPCancelRequestMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPLocateRequestMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPLocateReplyMessage;
import es.tid.TIDorbj.core.comm.giop.GIOPFragmentMessage;

import org.omg.CORBA.COMM_FAILURE;

public class ZIOPMessage 
    extends GIOPMessage
{
    BufferCDR m_original_message_buffer;

    CDRInputStream  m_message_buffer_in;

    CDROutputStream m_message_buffer_out;

    int m_fragment_size;
    
    int m_original_length;

    public ZIOPMessage(GIOPMessage giop_message, int fragment_size)
    {
        super(giop_message.getHeader());
        m_original_message_buffer = giop_message.getMessageBuffer();
        m_message_buffer_in  = null;
        m_message_buffer_out = null;
        m_fragment_size = fragment_size;
    }
    
    public ZIOPMessage(GIOPHeader header, int fragment_size)
    {
        super(header);
        m_original_message_buffer = null;
        m_message_buffer_in  = null;
        m_message_buffer_out = null;
        m_fragment_size = fragment_size;
    }
    

    public void receiveBody(Connection conn,
                            byte[] header_bytes)
    {
        super.receiveBody(conn, header_bytes);

        m_message_buffer_in = new CDRInputStream(conn.orb(), m_message_buffer);
        m_message_buffer_in.setByteOrder(m_header.getByteOrder());
        
        m_message_buffer_in.setVersion(m_header.getVersion());
        m_message_buffer_in.setMessage(true);
        
        m_message_completed = !(m_header.hasMoreFragments());
       
        byte[] compressed = new byte[0];
        int compressed_length;
        compressed_length = m_header.getSize() - 8; // CompressionData without data buffer
        compressed = new byte[compressed_length];

        // Read CompressionData
        int original_length;
        m_compressor.compressor_id = m_message_buffer_in.read_ushort();
        original_length = m_message_buffer_in.read_ulong(); 
        m_message_buffer_in.read_octet_array(compressed, 0, compressed_length);
        
        Compressor compressor_ptr = null;
        try {
            compressor_ptr = 
                conn.orb().getCompressionManager().get_compressor(m_compressor.compressor_id, 
                                                                  (short)0);
        } catch (org.omg.Compression.UnknownCompressorId ex) {
            return;
        }
        
        org.omg.CORBA.OctetSeqHolder uncompressed = new org.omg.CORBA.OctetSeqHolder();

        try {
            compressor_ptr.decompress(compressed, uncompressed);
        } catch (org.omg.Compression.CompressionException ex) {
            return;
        }

        if (conn.orb().m_trace != null) {
            String[] msg = {
                "Uncompressing ZIOP message ",
                " from " + compressed.length + " bytes ",
                " to " + uncompressed.value.length + " bytes ",
                "using compressor " + m_compressor.compressor_id };
            conn.orb().printTrace(Trace.USER, msg);
        }
        
        
        // From uncompressed to CDRInputStream
        ChunkCDR chunk = 
            new ChunkCDR(uncompressed.value.length);
        
        System.arraycopy(uncompressed.value, 0, chunk.getBuffer(), 0, uncompressed.value.length);

        chunk.setAvailable(uncompressed.value.length); 
        m_original_message_buffer = new BufferCDR(chunk);
        
    }

    public int get_fragment_size(TIDORB orb)
    {
        return m_fragment_size;
    }
    
    public boolean perform_compression(TIDORB orb, 
                                       CompressorIdLevel compressor,
                                       int low_value,
                                       float min_ratio)
    {
        this.createMessageBufferOutput(orb);

        this.getHeader().setCompressed(true);
  

        Compressor compressor_ptr = null;        
        try { 
            compressor_ptr = 
                orb.getCompressionManager().get_compressor(compressor.compressor_id, 
                                                           compressor.compression_level);
        } catch (org.omg.Compression.UnknownCompressorId ex) {
            return false;
        }
  
  
        // Write 'body_buffer_out' bufferCDR into 'source' OctetSeq
        byte[] source = new byte[0];
        int source_length = 0;
        BufferCDR buffer = m_original_message_buffer;
        int num_chunks = buffer.getNumAvailableChunks();
        ChunkCDR chunk = null;
        for(int i = 0; i < num_chunks; i++) {
            chunk = buffer.getChunk(i);
            int current_length = source_length;
            source_length += chunk.getAvailable();
            byte[] aux_buffer = new byte[source_length];
            if (source_length > 0)
                System.arraycopy(source, 0, aux_buffer, 0, current_length);
            System.arraycopy(chunk.getBuffer(), 0, aux_buffer, current_length, 
                             chunk.getAvailable());
            source = aux_buffer;
        }

        // Check (source_length > CompressorLowValue) to apply or not compression
        if (source.length < low_value) {
            return false;
        }

        // Apply compression to source
        org.omg.CORBA.OctetSeqHolder compressed = new org.omg.CORBA.OctetSeqHolder();
        try { 
            compressor_ptr.compress(source, compressed);
        } catch (org.omg.Compression.CompressionException ex) {
            return false;
        }

        // Write compressed OctetSeq into message_buffer_out 
        m_message_buffer_out.write_ushort(compressor.compressor_id);
        m_message_buffer_out.write_ulong(source.length);

        // Check min ratio
        float ratio = ((float)1 - ((float)compressed.value.length / (float)source.length));
        if ( ratio < min_ratio ) {
            return false;
        }

        m_message_buffer_out.write_octet_array(compressed.value, 0 , compressed.value.length);

        m_message_completed = true;

        if (orb.m_trace != null) {
            String[] msg = {
                "Compressing GIOP message ",
                "from " + source.length + " bytes ",
                "to " +  compressed.value.length + " bytes ",
                "using compressor " + compressor.compressor_id,
                " with level " + compressor.compression_level,
                " achieving ratio " + (ratio*100) + " %"
            };
            orb.printTrace(Trace.USER, msg);
        }

        m_header.setSize(m_message_buffer.getAvailable() - 
                         GIOPHeader.HEADER_SIZE);
        m_headers_marshaled = false;

        return true;
    }

    public void connect_GIOPMessage(Connection conn)
    {
        TIDORB _orb = conn.orb();

        switch (m_header.getMsgType().m_value)
            {
            case MsgType._Request:
                {
                    GIOPRequestMessage message = new GIOPRequestMessage(m_header);

                    CDRInputStream original_buffer_in = 
                        new CDRInputStream(_orb, m_original_message_buffer);
                    
                    original_buffer_in.fixStarting();
                    
                    message.setBody(m_original_message_buffer, original_buffer_in);
                    
                    message.set_compressor(m_compressor);

                    if (_orb.m_trace != null){
                        _orb.printTrace(Trace.DUMP, "GIOP message chunk received - HEXDUMP " +
                                        message.getMessageBuffer().getChunk(0).getLength() +
                                        " bytes");
                        _orb.printDump(Trace.DUMP,
                                       message.getMessageBuffer().getChunk(0).getBuffer(),
                                       message.getMessageBuffer().getChunk(0).getLength());
                        
                        _orb.printTrace(Trace.DEEP_DEBUG, toString() + ": "
                                        + message.toString()
                                        + " has been received!");
                    }

                    conn.manageMessage((GIOPRequestMessage) message);
                    break;
                }
            case MsgType._Reply:
                {
                    GIOPReplyMessage message = new GIOPReplyMessage(m_header);

                    CDRInputStream original_buffer_in = 
                        new CDRInputStream(_orb, m_original_message_buffer);
                    
                    original_buffer_in.fixStarting();
                    
                    message.setBody(m_original_message_buffer, original_buffer_in);
                    
                    // Skip GIOP Reply header
                    switch (m_header.getVersion().getMinor()) {
                    case 0:
                    case 1:
                        message.unmarshalReplyHeader1_1();
                        break;
                    case 2:
                        message.unmarshalReplyHeader1_2();
                        break;
                    }

                    if (_orb.m_trace != null){
                        _orb.printTrace(Trace.DUMP, "GIOP message chunk received - HEXDUMP " +
                                        message.getMessageBuffer().getChunk(0).getLength() +
                                        " bytes");
                        _orb.printDump(Trace.DUMP,
                                       message.getMessageBuffer().getChunk(0).getBuffer(),
                                       message.getMessageBuffer().getChunk(0).getLength());
                        
                        _orb.printTrace(Trace.DEEP_DEBUG, toString() + ": "
                                        + message.toString()
                                        + " has been received!");
                    }

                    conn.manageMessage((GIOPReplyMessage) message);
                    break;
                }
            case MsgType._CancelRequest:
                {
                    GIOPCancelRequestMessage message = new GIOPCancelRequestMessage(m_header);

                    CDRInputStream original_buffer_in = 
                        new CDRInputStream(_orb, m_original_message_buffer);
                    
                    original_buffer_in.fixStarting();
                    
                    message.setBody(m_original_message_buffer);

                    conn.manageMessage((GIOPCancelRequestMessage) message);
                    break;
                }
            case MsgType._LocateRequest:
                {
                    GIOPLocateRequestMessage message = new GIOPLocateRequestMessage(m_header);

                    CDRInputStream original_buffer_in = 
                        new CDRInputStream(_orb, m_original_message_buffer);
                    
                    original_buffer_in.fixStarting();
                    
                    message.setBody(m_original_message_buffer, original_buffer_in);

                    conn.manageMessage((GIOPLocateRequestMessage) message);
                    break;
                }
            case MsgType._LocateReply:
                {
                    GIOPLocateReplyMessage message = new GIOPLocateReplyMessage(m_header);

                    CDRInputStream original_buffer_in = 
                        new CDRInputStream(_orb, m_original_message_buffer);
                    
                    original_buffer_in.fixStarting();
                    
                    message.setBody(m_original_message_buffer, original_buffer_in);

                    conn.manageMessage((GIOPLocateReplyMessage) message);
                    break;
                }
            case MsgType._Fragment:
                {
                    GIOPFragmentMessage message = new GIOPFragmentMessage(m_header);

                    CDRInputStream original_buffer_in = 
                        new CDRInputStream(_orb, m_original_message_buffer);
                    
                    original_buffer_in.fixStarting();
                    
                    message.setBody(m_original_message_buffer);

                    conn.manageMessage((GIOPFragmentMessage) message);
                    break;
                }
            case MsgType._CloseConnection:
                conn.closeByPair();
                return;
            case MsgType._MessageError:
                conn.closeByError(new COMM_FAILURE("Connection closed due to pair "
                                                   + "message error."));
                return;
            }
        
    }

    public void createMessageBufferOutput(TIDORB orb)
    {
        if (m_message_buffer == null)
            m_message_buffer = new BufferCDR( this.get_fragment_size(orb) );
        else
            m_message_buffer.recycle();

        m_message_buffer_out = new CDROutputStream(orb, m_message_buffer);

        m_message_buffer_out.setVersion(m_header.getVersion());

        m_message_buffer_out.setMessage(true);
    }


}
