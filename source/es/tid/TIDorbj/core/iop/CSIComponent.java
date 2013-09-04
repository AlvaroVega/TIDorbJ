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
import java.lang.Byte;

import es.tid.TIDorbj.core.ConfORB;
import org.omg.IOP.TAG_CSI_SEC_MECH_LIST;
import org.omg.CSIIOP.CompoundSecMechList;
import org.omg.CSIIOP.CompoundSecMechListHelper;
import org.omg.CSIIOP.ServiceConfiguration;
import org.omg.CSI.ITTAbsent;
import org.omg.CSI.ITTAnonymous;
import org.omg.CSI.ITTPrincipalName;
import org.omg.CSI.ITTX509CertChain;
import org.omg.CSI.ITTDistinguishedName;



/**
 * CSIComponent data struct defined in the GIOP Module.
 * 
 * @autor 
 * @version 1.0
 */
public class CSIComponent extends TaggedComponent
{

    private CompoundSecMechList m_mec_list;

    public CSIComponent()
    {
        super(TAG_CSI_SEC_MECH_LIST.value);
        m_mec_list = new CompoundSecMechList();
    }

    public CSIComponent(CompoundSecMechList mec_list)
    {
        super(TAG_CSI_SEC_MECH_LIST.value);
        m_mec_list = mec_list;
    }

    public void write(es.tid.TIDorbj.core.cdr.CDROutputStream out)
    {
        out.write_ulong(m_tag);

        // enter ecapsulation

        out.enterEncapsulation();

        CompoundSecMechListHelper.write(out, m_mec_list);

        out.exitEncapsulation();
    }

    public void partialRead(es.tid.TIDorbj.core.cdr.CDRInputStream input)
    {
        input.enterEncapsulation();
        m_mec_list = CompoundSecMechListHelper.read(input);
        input.exitEncapsulation();
    }

    public String toString()
    {
        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);
        print_buffer.print('\n');
        print_buffer.print('\t');
        print_buffer.print('\t');
        print_buffer.print("stateful: ");
        print_buffer.print(m_mec_list.stateful); 
        print_buffer.print('\n');
        print_buffer.print('\t');
        print_buffer.print('\t');
        for (int i = 0; i < m_mec_list.mechanism_list.length; i++) {
            print_buffer.print("mechanism: ");
            print_buffer.print('\n');
            print_buffer.print('\t');
            print_buffer.print('\t');
            print_buffer.print("   target_requires: " + 
                               SSLComponent.print_AssociationOptions(
                                  m_mec_list.mechanism_list[i].target_requires));
            print_buffer.print('\n');
            print_buffer.print('\t');
            print_buffer.print('\t');
            print_buffer.print("   transport_mech: " + 
                               CSIComponent.print_TransportMechTag(
                                  m_mec_list.mechanism_list[i].transport_mech.tag));
            print_buffer.print('\n');
            print_buffer.print('\t');
            print_buffer.print('\t');
            print_buffer.print("   as_context_mech: ");
            print_buffer.print('\n');
            print_buffer.print('\t');
            print_buffer.print('\t');
            print_buffer.print("      target_supports: " +
                               SSLComponent.print_AssociationOptions(
                                 m_mec_list.mechanism_list[i].as_context_mech.target_supports));
            print_buffer.print('\n');
            print_buffer.print('\t');
            print_buffer.print('\t');
            print_buffer.print("      target_requires: " +
                               SSLComponent.print_AssociationOptions(
                                 m_mec_list.mechanism_list[i].as_context_mech.target_requires));
            print_buffer.print('\n');
            print_buffer.print('\t');
            print_buffer.print('\t');
            print_buffer.print("      client_authentication_mech: " + 
                               print_OID(m_mec_list.mechanism_list[i].as_context_mech.client_authentication_mech));
            print_buffer.print('\n');
            print_buffer.print('\t');
            print_buffer.print('\t');
            print_buffer.print("      target_name: " +
                               print_GSS_NT_ExportedName(
                                 m_mec_list.mechanism_list[i].as_context_mech.target_name));

            print_buffer.print('\n');
            print_buffer.print('\t');
            print_buffer.print('\t');
            print_buffer.print("   sas_context_mech: ");
            print_buffer.print('\n');
            print_buffer.print('\t');
            print_buffer.print('\t');
            print_buffer.print("      target_supports: " +
                               SSLComponent.print_AssociationOptions(
                                 m_mec_list.mechanism_list[i].sas_context_mech.target_supports));
            print_buffer.print('\n');
            print_buffer.print('\t');
            print_buffer.print('\t');
            print_buffer.print("      target_requires: " +
                               SSLComponent.print_AssociationOptions(
                                 m_mec_list.mechanism_list[i].sas_context_mech.target_requires));
            print_buffer.print('\n');
            print_buffer.print('\t');
            print_buffer.print('\t');
            print_buffer.print("      privilege_authorities: " + 
                               CSIComponent.print_PrivilegeAuthorities(
                                 m_mec_list.mechanism_list[i].sas_context_mech.privilege_authorities));
            print_buffer.print('\n');
            print_buffer.print('\t');
            print_buffer.print('\t');
            print_buffer.print("      supported_naming_mechanims: " +
                               print_OIDList(
                                 m_mec_list.mechanism_list[i].sas_context_mech.supported_naming_mechanisms));
            print_buffer.print('\n');
            print_buffer.print('\t');
            print_buffer.print('\t');
            print_buffer.print("      supported_identity_types: " +
                               print_IdentityTokenTypes(
                                 m_mec_list.mechanism_list[i].sas_context_mech.supported_identity_types));
        }
        return buffer.toString();
    }



    public static String print_PrivilegeAuthorities(ServiceConfiguration[] privilege_authorities) {

        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);

        for (int i = 0; i < privilege_authorities.length; i++) {
            print_buffer.print("\n \t \t \t sintax: " + privilege_authorities[i].syntax);
            print_buffer.print("\n \t \t \t name: " + privilege_authorities[i].name);
        }

        return buffer.toString();
    }

    public static String print_OID(byte[] oid) {

        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);

        print_buffer.print("\n \t \t \t OID: ");

        for (int i = 0; i < oid.length; i++) {
            String hex = Integer.toHexString(oid[i]);
            int hlength = hex.length();
            switch (hlength) {
            case 0:
                break;
            case 1:
                print_buffer.print("0" + hex);
                break;
            case 2:
                print_buffer.print(hex);
                break;
            default:
                print_buffer.print(hex.substring(hlength-2, hlength));
            }
            print_buffer.print(" ");
        }

        return buffer.toString();
    }


    public static String print_OIDList(byte[][] oid_list) {

        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);

        for (int i = 0; i < oid_list.length; i++) {
            print_buffer.print(print_OID(oid_list[i]));
        }

        return buffer.toString();
    }

    public static String print_IdentityTokenTypes(int type) {

        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);

        switch(type) {
        case org.omg.CSI.ITTAbsent.value:
            print_buffer.print("\n \t \t \t Absent");
            break;
        case org.omg.CSI.ITTAnonymous.value:
            print_buffer.print("\n \t \t \t Anonymous");
            break;
        case org.omg.CSI.ITTPrincipalName.value:
            print_buffer.print("\n \t \t \t PrincipalName");
            break;
        case org.omg.CSI.ITTX509CertChain.value:
            print_buffer.print("\n \t \t \t ITTX509CertChain");
            break;
        case org.omg.CSI.ITTDistinguishedName.value:
            print_buffer.print("\n \t \t \t ITTDistinguishedName");
            break;
        }
        return buffer.toString();
    }


    public static String print_TransportMechTag(int tag) {

        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);

        switch(tag) {
        case org.omg.IOP.TAG_SSL_SEC_TRANS.value:
            print_buffer.print("\n \t \t \t TAG_SSL_SEC_TRANS");
            break;
        case org.omg.IOP.TAG_TLS_SEC_TRANS.value:
            print_buffer.print("\n \t \t \t TAG_TLS_SEC_TRANS");
            break;
        case org.omg.IOP.TAG_NULL_TAG.value:
            print_buffer.print("\n \t \t \t TAG_NULL_TAG");
            break;
        }
        return buffer.toString();
    }


    public static String print_GSS_NT_ExportedName(byte[] name) {

        StringWriter buffer = new StringWriter();
        PrintWriter print_buffer = new PrintWriter(buffer);

        print_buffer.print("\n \t \t \t  ");

        for (int i = 0; i < name.length; i++) {
            String hex = Integer.toHexString(name[i]);
            int hlength = hex.length();
            switch (hlength) {
            case 0:
                break;
            case 1:
                print_buffer.print("0" + hex);
                break;
            case 2:
                print_buffer.print(hex);
                break;
            default:
                print_buffer.print(hex.substring(hlength-2, hlength));
            }
            print_buffer.print(" ");

        }

        return buffer.toString();

    }


}
