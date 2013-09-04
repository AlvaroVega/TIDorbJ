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

import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.portable.InputStream;

import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;

public class CodecImpl extends org.omg.IOP.CodecLocalBase
{

    private es.tid.TIDorbj.core.TIDORB m_orb;

    private es.tid.TIDorbj.core.comm.giop.GIOPVersion m_version;

    public CodecImpl(es.tid.TIDorbj.core.TIDORB orb,
                     es.tid.TIDorbj.core.comm.giop.GIOPVersion version)
    {
        m_orb = orb;
        m_version = version;
    }

    public byte[] encode(org.omg.CORBA.Any data)
        throws org.omg.IOP.CodecPackage.InvalidTypeForEncoding
    {
        if (data == null) {
            throw new BAD_PARAM();
        }

        CDROutputStream out = (CDROutputStream) m_orb.create_output_stream();

        out.setVersion(m_version);
        // write the any into an encapsulation

        out.enterEncapsulation();

        try {
            out.write_any(data);
        }
        catch (org.omg.CORBA.BAD_OPERATION bo) {
            throw new org.omg.IOP.CodecPackage.InvalidTypeForEncoding();
        }

        out.exitEncapsulation();

        // read the byte array

        InputStream input = out.create_input_stream();

        int length = input.read_ulong();

        byte[] value = new byte[length];

        input.read_octet_array(value, 0, length);

        return value;

    }

    public org.omg.CORBA.Any decode(byte[] data)
        throws org.omg.IOP.CodecPackage.FormatMismatch
    {
        if (data == null) {
            throw new BAD_PARAM();
        }

        CDRInputStream input = new CDRInputStream(m_orb, data);

        input.setByteOrder(input.read_boolean());

        input.setVersion(m_version);
        try {
            return input.read_any();
        }
        catch (org.omg.CORBA.BAD_OPERATION bo) {
            throw new org.omg.IOP.CodecPackage.FormatMismatch();
        }
    }

    public byte[] encode_value(org.omg.CORBA.Any data)
        throws org.omg.IOP.CodecPackage.InvalidTypeForEncoding
    {
        if (data == null) {
            throw new BAD_PARAM();
        }

        CDROutputStream out = (CDROutputStream) m_orb.create_output_stream();

        out.setVersion(m_version);

        // write the any into an encapsulation
        out.enterEncapsulation();

        try {
            data.write_value(out);
        }
        catch (org.omg.CORBA.BAD_OPERATION bo) {
            throw new org.omg.IOP.CodecPackage.InvalidTypeForEncoding();
        }

        out.exitEncapsulation();

        // read the byte array

        InputStream input = out.create_input_stream();

        int length = input.read_ulong();

        byte[] value = new byte[length];

        input.read_octet_array(value, 0, length);

        return value;
    }

    public org.omg.CORBA.Any decode_value(byte[] data, 
                                          org.omg.CORBA.TypeCode tc)
        throws org.omg.IOP.CodecPackage.FormatMismatch,
        org.omg.IOP.CodecPackage.TypeMismatch
    {
        if ((data == null) || (tc == null)) {
            throw new BAD_PARAM();
        }

        CDRInputStream input = new CDRInputStream(m_orb, data);

        input.setVersion(m_version);

        input.setByteOrder(input.read_boolean());

        org.omg.CORBA.Any any = m_orb.create_any();

        try {
            any.read_value(input, tc);
        }
        catch (org.omg.CORBA.BAD_OPERATION bo) {
            throw new org.omg.IOP.CodecPackage.TypeMismatch();
        }
        catch (org.omg.CORBA.MARSHAL bo) {
            throw new org.omg.IOP.CodecPackage.FormatMismatch();
        }

        return any;
    }
}