/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 153 $
* Date: $Date: 2007-12-12 08:57:20 +0100 (Wed, 12 Dec 2007) $
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
package es.tid.TIDorbj.core.cdr;

import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.BAD_TYPECODE;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;

import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.typecode.TypeCodeCache;
import es.tid.TIDorbj.core.typecode.TypeCodeFactory;
import es.tid.TIDorbj.core.typecode.TypeCodeImpl;
import es.tid.TIDorbj.core.typecode.TypeCodeMarshaler;

/**
 * <code>TypeCode</code> Demarshaling class. Reads the typecode kind and
 * creates a new typecode finishing its remarshaling.
 * 
 * <p>
 * Copyright 2000 Telef&oacute;nica I+D. Printed in Spain (Europe). All Rights
 * Reserved.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

class TypeCodeReader
{

    /**
     * Read a <code>TypeCode</code> from the InputStream.
     * 
     * @param input
     *            the input stream
     */
    public static TypeCode read(CDRInputStream input)
    {

        // aligns to get the typeCode position

        input.alignment(CDR.LONG_SIZE);

        // gets the typecode position

        PointerCDR typecode_position = input.getPointer();

        // read the tc_kind value
        int kind = input.read_long();

        if (kind == -1) // indirection
        {
            return readIndirection(input);
        }

        // typecode kind
        TCKind tc_kind = null;

        try {
            tc_kind = TCKind.from_int(kind);
        }
        catch (BAD_PARAM bp) {
            throw new BAD_TYPECODE("Invalid kind: " + bp.toString(), 0,
                                   CompletionStatus.COMPLETED_NO);
        }

        // Typecode readed
        TypeCodeImpl type = null;

        if (TypeCodeFactory.isComplex(tc_kind)) {
            TypeCode cached_type = findInCache(input);

            if (cached_type != null) {
                input.skipEncapsulation();
                input.getContextCDR()
                     .putPosition(typecode_position.getAbsolutePosition(),
                                  cached_type);
                return cached_type;
            }

            type = TypeCodeFactory.getComplexTypeCode(tc_kind);
            if (input.m_orb.m_conf.exhaustive_equal) {
                type.setExhaustiveEqual(input.m_orb.m_conf.exhaustive_equal);
            }
            type.partialUnmarshal(input);

            // it is a complex typecode: insert the typecode in the table and
            // unmarshal

            input.getContextCDR()
                 .putPosition(typecode_position.getAbsolutePosition(), type);

            return putInCache(input, type);

        } else if (TypeCodeFactory.isSemiComplex(tc_kind)) {

            type = TypeCodeFactory.getSemiComplexTypeCode(tc_kind);
            if (input.m_orb.m_conf.exhaustive_equal) {
                type.setExhaustiveEqual(input.m_orb.m_conf.exhaustive_equal);
            }
            type.partialUnmarshal(input);

            return type;

        } else { // is basic

            type = TypeCodeFactory.getBasicTypeCode(tc_kind);

            if (type == null)
                throw new org.omg.CORBA.INTERNAL("Error reading typecode");
            if (input.m_orb.m_conf.exhaustive_equal) {
                type.setExhaustiveEqual(input.m_orb.m_conf.exhaustive_equal);
            }
            return type;
        }
    }

    /**
     * Read a <code>TypeCode</code> from the InputStream.
     * 
     * @param input
     *            the input stream
     */
    public static TypeCode readIndirection(CDRInputStream input)
    {
        AbsolutePosition referenced_position = input.readIndirection();

        // search in the indirection node

        java.lang.Object obj = input.getContextCDR()
                                    .lookupObject(referenced_position);

        if (obj != null) {
            if (obj instanceof TypeCode) {
                return (TypeCode) obj;
            } else {
                throw new MARSHAL("Invalid indirection: no typecode indirected");
            }
        }

        // Visibroker makes indirections to basic types, is it one?

        CDRInputStream indirection_input = input.copy();

        indirection_input.setPosition(referenced_position);

        TCKind tc_kind = TCKind.from_int(indirection_input.read_long());

        TypeCodeImpl type = TypeCodeFactory.getBasicTypeCode(tc_kind);

        if (type == null) // it is not a basic typeCode: ERROR!!!
            throw new MARSHAL("Invalid indirection: no typecode indirected.");

        return type;
    }

    public static TypeCode findInCache(CDRInputStream input)
    {
        TIDORB orb = (TIDORB) input.orb();

        if (orb == null)
            return null;

        TypeCodeCache cache = orb.getTypeCodeCache();

        if (cache != null) {

            // lectura adelantada del repository id para ver si ya est� en la
            // cache

            CDRInputStream encap = input.copy();

            encap.enterEncapsulation();

            String rep_id = encap.read_string();

            encap = null;

            return cache.find(rep_id);
        }

        return null;
    }

    public static TypeCode putInCache(CDRInputStream input, TypeCode tc)
    {
        TIDORB orb = (TIDORB) input.orb();

        if (orb == null)
            return tc;

        TypeCodeCache cache = orb.getTypeCodeCache();

        if (cache != null) {
            return cache.put(tc);
        }
        return tc;
    }

    public static void skip(CDRInputStream input)
    {

        int kind; // typecode kind value
        TCKind tc_kind; // typecode kind
        TypeCodeImpl type = null; // typecode

        // read the tc_kind value
        kind = input.read_long();

        if (kind == -1) { // indirection
            // skip indirection offset
            input.skipLong();
        }

        try {
            tc_kind = TCKind.from_int(kind);
        }
        catch (BAD_PARAM bp) {
            throw new BAD_TYPECODE("Invalid kind: " + bp.toString(), 0,
                                   CompletionStatus.COMPLETED_NO);
        }

        TypeCodeMarshaler.skipParams(tc_kind, input);
    }
}