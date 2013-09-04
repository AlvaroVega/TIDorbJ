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

public class TranslateURLEscapes
{
    public static String putEscapes(String name)
    {
        int position;
        String cnescapes = translateEscapes(name);
        position = cnescapes.indexOf("%5c%5c");
        while (position != -1) // Change \
        {
            cnescapes = cnescapes.substring(0, position) + "\\\\"
                        + cnescapes.substring(position + 6);
            position = cnescapes.indexOf("%5c%5c", position);
        }
        position = cnescapes.indexOf("%5c/");
        while (position != -1) // Change /
        {
            cnescapes = cnescapes.substring(0, position) + "\\<"
                        + cnescapes.substring(position + 4);
            position = cnescapes.indexOf("%5c/", position);
        }
        position = cnescapes.indexOf("%5c.");
        while (position != -1) // Change .
        {
            cnescapes = cnescapes.substring(0, position) + "\\>"
                        + cnescapes.substring(position + 4);
            position = cnescapes.indexOf("%5c.", position);
        }
        return cnescapes;
    }

    public static String translateEscapes(String str)
    {
        String strescapes = "";
        String no_escapes = ";/:?@&=+$,-_.!~*'()";
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (no_escapes.indexOf(c) == -1 && !Character.isLetterOrDigit(c))
                strescapes = strescapes + "%" + Integer.toHexString((int) c);
            else
                strescapes = strescapes + c;
        }
        return strescapes;
    }

    public static String resolveEscapes(String str)
    {
        int position;
        String strescapes = str;
        position = strescapes.indexOf("\\\\"); // Resolve \
        while (position != -1) {
            strescapes = strescapes.substring(0, position) + "%5c"
                         + strescapes.substring(position + 2);
            position = strescapes.indexOf("\\\\", position);
        }
        position = strescapes.indexOf("\\<"); // Resolve /
        while (position != -1) {
            strescapes = strescapes.substring(0, position) + "/"
                         + strescapes.substring(position + 2);
            position = strescapes.indexOf("\\<", position);
        }
        position = strescapes.indexOf("\\>"); // Resolve .
        while (position != -1) {
            strescapes = strescapes.substring(0, position) + "."
                         + strescapes.substring(position + 2);
            position = strescapes.indexOf("\\>", position);
        }
        return strescapes;
    }

    public static String stringfy(String str)
    {
        int position;
        String stringfied = str;
        String hex;
        int nunicode;
        position = stringfied.indexOf("\\"); // Reading \
        while (position != -1) {
            stringfied = stringfied.substring(0, position) + "\\\\"
                         + stringfied.substring(position + 1);
            position = stringfied.indexOf("\\", position + 2);
        }
        position = stringfied.indexOf("."); // Reading .
        while (position != -1) {
            stringfied = stringfied.substring(0, position) + "\\."
                         + stringfied.substring(position + 1);
            position = stringfied.indexOf(".", position + 2);
        }
        position = stringfied.indexOf("/"); // Reading /
        while (position != -1) {
            stringfied = stringfied.substring(0, position) + "\\/"
                         + stringfied.substring(position + 1);
            position = stringfied.indexOf("/", position + 2);
        }
        position = stringfied.indexOf("%5c"); // Reading \ hex
        while (position != -1) {
            stringfied = stringfied.substring(0, position) + "\\\\"
                         + stringfied.substring(position + 3);
            position = stringfied.indexOf("%5c", position);
        }
        return translateUnicode(stringfied);
    }

    public static String translateUnicode(String str)
    {
        String hex;
        int nunicode;
        String unicode = str;
        int position = unicode.indexOf("%"); // Reading %hexhex
        while (position != -1) {
            hex = unicode.substring(position + 1, position + 3);
            nunicode = Integer.parseInt(hex, 16);
            unicode = unicode.substring(0, position) + (char) nunicode
                      + unicode.substring(position + 3);
            position = unicode.indexOf("%", position);
        }
        return unicode;
    }

}