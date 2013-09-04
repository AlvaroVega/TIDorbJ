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
package es.tid.TIDorbj.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;

import es.tid.TIDorbj.core.typecode.FixedTypeCode;
import es.tid.TIDorbj.util.Base16Codec;

/**
 * Particular Holder for FixedType.
 * 
 * @autor Juan A. Ca&acute;ceres
 * @version 0.1
 */

public class FixedHolder
    implements org.omg.CORBA.portable.Streamable
{

    TypeCode m_fixed_type;

    public java.math.BigDecimal value;

    public FixedHolder(TypeCode type)
    {
        m_fixed_type = type;
        value = null;
    }

    public FixedHolder(TypeCode type, java.math.BigDecimal initial)
    {
        m_fixed_type = type;
        value = initial;
    }

    public void _read(org.omg.CORBA.portable.InputStream is)
    {
        int value_length = FixedTypeCode.valueLength(m_fixed_type);

        StringBuffer buffer = new StringBuffer(value_length);

        int pairs = value_length / 2;

        try {

            for (int i = 1; i < pairs; i++) {
                byte octet = is.read_octet();
                buffer.append(Base16Codec.getDigit(0xf & (octet >> 4)));
                buffer.append(Base16Codec.getDigit(0xf & octet));
            }

            // last pair
            byte octet = is.read_octet();

            buffer.append(Base16Codec.getDigit(0xf & (octet >> 4)));

            boolean positive;
            switch (0xf & octet)
            {
                case 0xD:
                    positive = false;
                break;
                case 0xC:
                    positive = true;
                break;
                default:
                    throw new MARSHAL("Fixed_read: Invalid signum value", 0,
                                      CompletionStatus.COMPLETED_NO);
            }

            BigInteger value_int = new BigInteger(buffer.toString());

            if (positive)
                value = new BigDecimal(value_int, m_fixed_type.fixed_scale());
            else
                value = new BigDecimal(value_int.negate(),
                                       m_fixed_type.fixed_scale());

        }
        catch (MARSHAL m) {
            throw m;
        }
        catch (Exception e) {
            throw new MARSHAL(e.getMessage(), 0, CompletionStatus.COMPLETED_NO);
        }

    }

    public void _write(org.omg.CORBA.portable.OutputStream os)
    {
        if (value == null)
            value = new BigDecimal(0.0);

        int value_length = FixedTypeCode.valueLength(m_fixed_type);

        int digits = 0;
        try {

            digits = m_fixed_type.fixed_digits();
        }
        catch (BadKind bk) {/* unreachable */}

        BigInteger unscaled = value.setScale(0).toBigInteger();

        char[] literal = unscaled.toString().toCharArray();

        int literal_count = (value.signum() < 0) ? 1 : 0;//skip the signum char

        int literal_digits = literal.length - literal_count;

        if (digits < literal_digits)
            throw new MARSHAL("Fixed Overflow.", 0,
                              CompletionStatus.COMPLETED_NO);

        int value_pairs = (literal_digits - 1) / 2; // number of digit_digit
        // octets
        // 1 is substracted from literals represents
        //the last digit int digit_signum pair

        int zero_value_pairs = (literal_digits - 1) % 2; // 0_digit octet is 0
        // or 1
        int zero_pairs = (value_length / 2) - zero_value_pairs - value_pairs
                         - 1;
        // 0_0 octets
        // 1 is substracted from zero_pairs represents the zero_signum pair

        for (int i = 0; i < zero_pairs; i++)
            write_pair(os, 0, 0);

        try {
            if (zero_value_pairs != 0)
                write_pair(os, 0,
                           Base16Codec.getValue(literal[literal_count++]));

            for (int i = 0; i < zero_pairs; i++)
                write_pair(os, Base16Codec.getValue(literal[literal_count++]),
                           Base16Codec.getValue(literal[literal_count++]));

            write_pair(os, Base16Codec.getValue(literal[literal_count++]),
                       (value.signum() < 0) ? 0xD : 0xC);
        }
        catch (MARSHAL m) {
            throw m;
        }
        catch (Exception e) {
            throw new MARSHAL(e.getMessage(), 0, CompletionStatus.COMPLETED_NO);
        }
    }

    public org.omg.CORBA.TypeCode _type()
    {
        return m_fixed_type;
    }

    static void write_pair(org.omg.CORBA.portable.OutputStream os, int digit1,
                           int digit2)
    {
        os.write_octet((byte) ((digit1 << 4) | (digit2)));
    }

}