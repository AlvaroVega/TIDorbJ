/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 397 $
* Date: $Date: 2009-06-18 08:42:11 +0200 (Thu, 18 Jun 2009) $
* Last modified by: $Author: avega $
*
* (C) Copyright 2009 Telefonica Investigacion y Desarrollo
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
package es.tid.TIDorbj.core.iop;

import java.io.StringWriter;
import java.io.PrintWriter;

import es.tid.TIDorbj.core.ConfORB;
import org.omg.IOP.TAG_SSL_SEC_TRANS;
import org.omg.SSLIOP.SSL;
import org.omg.SSLIOP.SSLHelper;

/**
 * SSLComponent data struct defined in the GIOP Module.
 * 
 * @autor 
 * @version 1.0
 */
public class SSLComponent extends TaggedComponent
{

    private SSL m_ssl;

    public SSLComponent()
    {
        super(TAG_SSL_SEC_TRANS.value);
        m_ssl = new SSL();
    }

    public SSLComponent(SSL ssl)
    {
        super(TAG_SSL_SEC_TRANS.value);
        m_ssl = ssl;
    }

    public void write(es.tid.TIDorbj.core.cdr.CDROutputStream out)
    {
        out.write_ulong(m_tag);

        // enter ecapsulation

        out.enterEncapsulation();

        SSLHelper.write(out, m_ssl);

        out.exitEncapsulation();
    }

    public void partialRead(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        input.enterEncapsulation();
        m_ssl = SSLHelper.read(input);
        input.exitEncapsulation();
    }

    public String toString()
    {
        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);
        print_buffer.print('\n');
        print_buffer.print('\t');
        print_buffer.print('\t');
        print_buffer.print("port: ");
        if (m_ssl.port < 0) {
            int myint = 65536 + m_ssl.port;
            print_buffer.print(myint); 
        }
        else {
            print_buffer.print(m_ssl.port); 
        }
        print_buffer.print('\n');
        print_buffer.print('\t');
        print_buffer.print('\t');
        print_buffer.print("target_supports: ");
        print_buffer.print(print_AssociationOptions(m_ssl.target_supports));
        print_buffer.print('\n');
        print_buffer.print('\t');
        print_buffer.print('\t');
        print_buffer.print("target_requires: ");
        print_buffer.print(print_AssociationOptions(m_ssl.target_requires));
        return buffer.toString();
    }


    public static String print_AssociationOptions(int mask) {

        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);

        if ( (mask & org.omg.Security.NoProtection.value) > 0) {
            print_buffer.print("\n \t \t \t NoProtection");
        }
        if ( (mask & org.omg.Security.Integrity.value) > 0) {
            print_buffer.print("\n \t \t \t Integrity");
        }
        if ( (mask & org.omg.Security.Confidentiality.value) > 0) {
            print_buffer.print("\n \t \t \t Confidentiality");
        }
        if ( (mask & org.omg.Security.DetectReplay.value) > 0) {
            print_buffer.print("\n \t \t \t DetectReplay");
        }
        if ( (mask & org.omg.Security.DetectMisordering.value) > 0) {
            print_buffer.print("\n \t \t \t DetectMisordering");
        }
        if ( (mask & org.omg.Security.EstablishTrustInTarget.value) > 0) {
            print_buffer.print("\n \t \t \t EstablishTrustInTarget");
        }
        if ( (mask & org.omg.Security.EstablishTrustInClient.value) > 0) {
            print_buffer.print("\n \t \t \t EstablishTrustInClient");
        }
        if ( (mask & org.omg.Security.NoDelegation.value) > 0) {
            print_buffer.print("\n \t \t \t NoDelegation");
        }
        if ( (mask & org.omg.Security.SimpleDelegation.value) > 0) {
            print_buffer.print("\n \t \t \t SimpleDelegation");
        }
        if ( (mask & org.omg.Security.CompositeDelegation.value) > 0) {
            print_buffer.print("\n \t \t \t CompositeDelegation");
        }
        if ( (mask & org.omg.CSIIOP.IdentityAssertion.value) > 0) {
            print_buffer.print("\n \t \t \t IdentityAssertion");
        }
        if ( (mask & org.omg.CSIIOP.DelegationByClient.value) > 0) {
            print_buffer.print("\n \t \t \t DelegationByClient");
        }
        return buffer.toString();
    }

}
