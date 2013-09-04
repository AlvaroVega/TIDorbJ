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

package es.tid.TIDorbj.core.ziop;

import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.policy.PolicyContext;
import es.tid.TIDorbj.util.Trace;

import org.omg.Compression.*;
import org.omg.ZIOP.*;


/**
 * <code>ZIOP</code> common facilities to dealing with ZIOP policies 
 * CompressionEnabling Policy.
 *
 * @author <a href="mailto:avega at tid dot es">Alvaro Vega Garcia</a>
 * @version 1.0
 */
public class ZIOP
{     


    /**
     * <code>checkCompressionEnabled</code> cheks if the context contains a 
     * CompressionEnablingPolicy enabled
     *
     * @param context a <code>PolicyContext</code> value
     * @return a <code>boolean</code> value
     */
    public static boolean checkCompressionEnabled(PolicyContext context)
    {
        if (context != null) {
            
            CompressionEnablingPolicy compressionEnablingPolicy =
                context.getCompressionEnablingPolicy();
            
            if (compressionEnablingPolicy != null) {
                return compressionEnablingPolicy.compression_enabled();
            }
        }
        return false;
    }

    /**
     * <code>checkCompressionEnabled</code> checks if both context provided
     * contains CompressionEnablingPolicy enabled.
     *
     * @param context a <code>PolicyContext</code> value
     * @param ior_context a <code>PolicyContext</code> value
     * @return a <code>boolean</code> value
     */
    public static boolean checkCompressionEnabled(PolicyContext context,
                                                  PolicyContext ior_context)
    {
        if ( (context != null) && (ior_context != null) ) {

            CompressionEnablingPolicy compressionEnablingPolicy =
                context.getCompressionEnablingPolicy();
            
            CompressionEnablingPolicy compressionEnablingPolicy_ior =
                ior_context.getCompressionEnablingPolicy();
            
            if ( (compressionEnablingPolicy != null) && 
                 (compressionEnablingPolicy_ior != null) ){
                return ( compressionEnablingPolicy.compression_enabled() && 
                         compressionEnablingPolicy_ior.compression_enabled() );
            }
        }
        return false;
    }

    /**
     * Retuns <code>getClientCompressor</code> a CompressorIdLevel that could 
     * be used in client side, taken in accont client context and IOR (server
     * context).
     *
     * @param context a <code>PolicyContext</code> value
     * @param ior_context a <code>PolicyContext</code> value
     * @return a <code>CompressorIdLevel</code> value
     */
    public static CompressorIdLevel getClientCompressor(PolicyContext context,
                                                        PolicyContext ior_context,
                                                        boolean assume_ziop_server)
    {

        CompressorIdLevel result = new CompressorIdLevel((short)0, (short)0);

        if (context != null) {

            CompressorIdLevelListPolicy compressionIdLevelListPolicy =
                context.getCompressorIdLevelListPolicy();
            
            if (ior_context != null) {
                
                CompressorIdLevelListPolicy compressionIdLevelListPolicy_ior =
                    ior_context.getCompressorIdLevelListPolicy();
                
                
                if ( (compressionIdLevelListPolicy != null) && 
                     (compressionIdLevelListPolicy_ior != null) ){
                    
                    CompressorIdLevel[] compressors_ctx = 
                        compressionIdLevelListPolicy.compressor_ids();
                    CompressorIdLevel[] compressors_ior = 
                        compressionIdLevelListPolicy_ior.compressor_ids();
                    
                    // Compare both lists
                    for (int i = 0; i < compressors_ctx.length; i++) {
                        
                        for (int j = 0; j < compressors_ior.length; j++) {
                            
                            if ( compressors_ctx[i].compressor_id == 
                                 compressors_ior[j].compressor_id ){ 
                                result.compressor_id     = 
                                    compressors_ctx[i].compressor_id;
                                result.compression_level = 
                                    compressors_ctx[i].compression_level;
                                return result;
                            }
                        }
                    }
                }

            } else {
                
                if (assume_ziop_server) {

                    if (compressionIdLevelListPolicy != null) {
                        CompressorIdLevel[] compressors_ctx = 
                            compressionIdLevelListPolicy.compressor_ids();
                        if (compressors_ctx.length > 0) {
                            result.compressor_id     = 
                                compressors_ctx[0].compressor_id;
                            result.compression_level = 
                                compressors_ctx[0].compression_level;
                        }
                    }
                }
               
            }
        }
        return result;
    }


    /**
     * Retuns <code>getLevelCompressor</code> a CompressorLevel that could 
     * be used in client side, taken in accont client context and IOR (server
     * context).
     *
     * @param compressor_id a <code>short</code> value
     * @param context a <code>PolicyContext</code> value
     * @return a <code>short</code> value
     */
    public static short getLevelCompressor(short compressor_id,
                                           PolicyContext context)
    {
        if (context != null) {

            CompressorIdLevelListPolicy compressionIdLevelListPolicy =
                context.getCompressorIdLevelListPolicy();
            
            if (compressionIdLevelListPolicy != null) {
                CompressorIdLevel[] compressors_ctx = 
                    compressionIdLevelListPolicy.compressor_ids();
                
                // Compare both lists
                for (int i = 0; i < compressors_ctx.length; i++) {
                    if (compressors_ctx[i].compressor_id == compressor_id) {
                        return compressors_ctx[i].compression_level;
                    }
                }
            }
        }
        return 0;
    }



    /**
     * <code>getLowValue</code> from a context.
     *
     * @param context a <code>PolicyContext</code> value
     * @return an <code>int</code> value
     */
    public static int getLowValue(PolicyContext context)
    {
        CompressionLowValuePolicy compressionLowValuePolicy =
            context.getCompressionLowValuePolicy();

        if (compressionLowValuePolicy != null) {
            return compressionLowValuePolicy.low_value();
        }
        else
            return 0;
    }

    /**
     * <code>getMinRatio</code> value from a context.
     *
     * @param context a <code>PolicyContext</code> value
     * @return an <code>int</code> value
     */
    public static float getMinRatio(PolicyContext context)
    {
        CompressionMinRatioPolicy compressionMinRatioPolicy =
            context.getCompressionMinRatioPolicy();

        if (compressionMinRatioPolicy != null) {
            return compressionMinRatioPolicy.ratio();
        }
        else
            return 0;
    }

}
