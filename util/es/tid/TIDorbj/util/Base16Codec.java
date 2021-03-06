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
package es.tid.TIDorbj.util;

public abstract class Base16Codec
{

    public static byte[] decode(String str)
        throws Exception
    {
        int str_length = str.length();

        if ((str_length % 2) != 0)
            throw new Exception("Invalid String length");

        byte[] data = new byte[str_length / 2];

        decode(str, data, 0);

        return data;
    }

    public static void decode(String str, byte[] data, int data_offset)
        throws Exception
    {
        char[] str_chars = str.toCharArray();

        int final_position = data_offset + (str.length() / 2);

        int j = 0;

        for (int i = data_offset; i < final_position; i++)
            data[i] = (byte) (16 * (getValue(str_chars[j++])) 
                	  + getValue(str_chars[j++]));

        str_chars = null;
    }

    public static String encode(byte[] data)
    {
        return encode(data, 0, data.length);
    }

    public static String encode(byte[] data, int offset, int length)
    {

        char[] digits = new char[2 * length];
        int octet = 0;
        int j = 0;

        int final_position = offset + length;

        for (int i = offset; i < final_position; i++) {
            octet = 0xff & data[i];
            digits[j++] = toBase16[octet >> 4];
            digits[j++] = toBase16[octet & 0xf];
        }

        return new String(digits);
    }

    public static final char[] toBase16 =
    	{ '0', '1', '2', '3', '4', '5', '6',
    	  '7', '8', '9', 'a', 'b', 'c', 'd',
    	  'e', 'f' };

    public static char getDigit(int hex_value)
        throws Exception
    {
        if (hex_value > toBase16.length)
            throw new Exception("Invalid hexadecimal value");

        return toBase16[hex_value];
    }

    public static int getValue(char digit)
        throws Exception
    {
        if ((digit >= '0') && (digit <= '9'))
            return digit - '0';
        if ((digit >= 'A') && (digit <= 'F'))
            return digit - 'A' + 0xA;
        if ((digit >= 'a') && (digit <= 'f'))
            return digit - 'a' + 0xA;
        //error
        throw new Exception("No hexadecimal digit.");
    }
}