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
package es.tid.TIDorbj.core.util.exception;

import org.omg.CORBA.CompletionStatus;

/**
 * Write/Read <code>SystemException</code>s.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class SystemExceptionEncoder
{

    private static java.util.Hashtable st_creators_table = null;

    private static java.util.Hashtable st_ids_table = null;

    private synchronized static void initCreators()
    {
        if (st_creators_table != null)
            return;

        st_creators_table = new java.util.Hashtable();

        st_creators_table.put(SystemExceptionNames.BAD_CONTEXT_id,
                              new BAD_CONTEXT_Creator());
        st_creators_table.put(SystemExceptionNames.BAD_INV_ORDER_id,
                              new BAD_INV_ORDER_Creator());
        st_creators_table.put(SystemExceptionNames.BAD_OPERATION_id,
                              new BAD_OPERATION_Creator());
        st_creators_table.put(SystemExceptionNames.BAD_PARAM_id,
                              new BAD_PARAM_Creator());
        st_creators_table.put(SystemExceptionNames.BAD_TYPECODE_id,
                              new BAD_TYPECODE_Creator());
        st_creators_table.put(SystemExceptionNames.COMM_FAILURE_id,
                              new COMM_FAILURE_Creator());
        st_creators_table.put(SystemExceptionNames.DATA_CONVERSION_id,
                              new DATA_CONVERSION_Creator());
        st_creators_table.put(SystemExceptionNames.FREE_MEM_id,
                              new FREE_MEM_Creator());
        st_creators_table.put(SystemExceptionNames.IMP_LIMIT_id,
                              new IMP_LIMIT_Creator());
        st_creators_table.put(SystemExceptionNames.INITIALIZE_id,
                              new INITIALIZE_Creator());
        st_creators_table.put(SystemExceptionNames.INTERNAL_id,
                              new INTERNAL_Creator());
        st_creators_table.put(SystemExceptionNames.INTF_REPOS_id,
                              new INTF_REPOS_Creator());
        st_creators_table.put(SystemExceptionNames.INV_FLAG_id,
                              new INV_FLAG_Creator());
        st_creators_table.put(SystemExceptionNames.INV_IDENT_id,
                              new INV_IDENT_Creator());
        st_creators_table.put(SystemExceptionNames.INV_OBJREF_id,
                              new INV_OBJREF_Creator());
        st_creators_table.put(SystemExceptionNames.INV_POLICY_id,
                              new INV_POLICY_Creator());
        st_creators_table.put(SystemExceptionNames.INVALID_TRANSACTION_id,
                              new INVALID_TRANSACTION_Creator());
        st_creators_table.put(SystemExceptionNames.MARSHAL_id,
                              new MARSHAL_Creator());
        st_creators_table.put(SystemExceptionNames.NO_IMPLEMENT_id,
                              new NO_IMPLEMENT_Creator());
        st_creators_table.put(SystemExceptionNames.NO_MEMORY_id,
                              new NO_MEMORY_Creator());
        st_creators_table.put(SystemExceptionNames.NO_PERMISSION_id,
                              new NO_PERMISSION_Creator());
        st_creators_table.put(SystemExceptionNames.NO_RESOURCES_id,
                              new NO_RESOURCES_Creator());
        st_creators_table.put(SystemExceptionNames.NO_RESPONSE_id,
                              new NO_RESPONSE_Creator());
        st_creators_table.put(SystemExceptionNames.OBJECT_NOT_EXIST_id,
                              new OBJECT_NOT_EXIST_Creator());
        st_creators_table.put(SystemExceptionNames.OBJ_ADAPTER_id,
                              new OBJ_ADAPTER_Creator());
        st_creators_table.put(SystemExceptionNames.PERSIST_STORE_id,
                              new PERSIST_STORE_Creator());
        st_creators_table.put(SystemExceptionNames.TIMEOUT_id,
                              new TIMEOUT_Creator());
        st_creators_table.put(SystemExceptionNames.TRANSACTION_REQUIRED_id,
                              new TRANSACTION_REQUIRED_Creator());
        st_creators_table.put(SystemExceptionNames.TRANSACTION_ROLLEDBACK_id,
                              new TRANSACTION_ROLLEDBACK_Creator());
        st_creators_table.put(SystemExceptionNames.TRANSIENT_id,
                              new TRANSIENT_Creator());
        st_creators_table.put(SystemExceptionNames.UNKNOWN_id,
                              new UNKNOWN_Creator());
    }

    private synchronized static void initIds()
    {
        if (st_ids_table != null)
            return;

        st_ids_table = new java.util.Hashtable();

        st_ids_table.put(SystemExceptionNames.BAD_CONTEXT_name,
                         SystemExceptionNames.BAD_CONTEXT_id);
        st_ids_table.put(SystemExceptionNames.BAD_INV_ORDER_name,
                         SystemExceptionNames.BAD_INV_ORDER_id);
        st_ids_table.put(SystemExceptionNames.BAD_OPERATION_name,
                         SystemExceptionNames.BAD_OPERATION_id);
        st_ids_table.put(SystemExceptionNames.BAD_PARAM_name,
                         SystemExceptionNames.BAD_PARAM_id);
        st_ids_table.put(SystemExceptionNames.BAD_TYPECODE_name,
                         SystemExceptionNames.BAD_TYPECODE_id);
        st_ids_table.put(SystemExceptionNames.COMM_FAILURE_name,
                         SystemExceptionNames.COMM_FAILURE_id);
        st_ids_table.put(SystemExceptionNames.DATA_CONVERSION_name,
                         SystemExceptionNames.DATA_CONVERSION_id);
        st_ids_table.put(SystemExceptionNames.FREE_MEM_name,
                         SystemExceptionNames.FREE_MEM_id);
        st_ids_table.put(SystemExceptionNames.IMP_LIMIT_name,
                         SystemExceptionNames.IMP_LIMIT_id);
        st_ids_table.put(SystemExceptionNames.INITIALIZE_name,
                         SystemExceptionNames.INITIALIZE_id);
        st_ids_table.put(SystemExceptionNames.INTERNAL_name,
                         SystemExceptionNames.INTERNAL_id);
        st_ids_table.put(SystemExceptionNames.INTF_REPOS_name,
                         SystemExceptionNames.INTF_REPOS_id);
        st_ids_table.put(SystemExceptionNames.INV_FLAG_name,
                         SystemExceptionNames.INV_FLAG_id);
        st_ids_table.put(SystemExceptionNames.INV_IDENT_name,
                         SystemExceptionNames.INV_IDENT_id);
        st_ids_table.put(SystemExceptionNames.INV_OBJREF_name,
                         SystemExceptionNames.INV_OBJREF_id);
        st_ids_table.put(SystemExceptionNames.INV_POLICY_name,
                         SystemExceptionNames.INV_POLICY_id);
        st_ids_table.put(SystemExceptionNames.INVALID_TRANSACTION_name,
                         SystemExceptionNames.INVALID_TRANSACTION_id);
        st_ids_table.put(SystemExceptionNames.MARSHAL_name,
                         SystemExceptionNames.MARSHAL_id);
        st_ids_table.put(SystemExceptionNames.NO_IMPLEMENT_name,
                         SystemExceptionNames.NO_IMPLEMENT_id);
        st_ids_table.put(SystemExceptionNames.NO_MEMORY_name,
                         SystemExceptionNames.NO_MEMORY_id);
        st_ids_table.put(SystemExceptionNames.NO_PERMISSION_name,
                         SystemExceptionNames.NO_PERMISSION_id);
        st_ids_table.put(SystemExceptionNames.NO_RESOURCES_name,
                         SystemExceptionNames.NO_RESOURCES_id);
        st_ids_table.put(SystemExceptionNames.NO_RESPONSE_name,
                         SystemExceptionNames.NO_RESPONSE_id);
        st_ids_table.put(SystemExceptionNames.OBJECT_NOT_EXIST_name,
                         SystemExceptionNames.OBJECT_NOT_EXIST_id);
        st_ids_table.put(SystemExceptionNames.OBJ_ADAPTER_name,
                         SystemExceptionNames.OBJ_ADAPTER_id);
        st_ids_table.put(SystemExceptionNames.PERSIST_STORE_name,
                         SystemExceptionNames.PERSIST_STORE_id);
        st_ids_table.put(SystemExceptionNames.TIMEOUT_name,
                         SystemExceptionNames.TIMEOUT_id);
        st_ids_table.put(SystemExceptionNames.TRANSACTION_REQUIRED_name,
                         SystemExceptionNames.TRANSACTION_REQUIRED_id);
        st_ids_table.put(SystemExceptionNames.TRANSACTION_ROLLEDBACK_name,
                         SystemExceptionNames.TRANSACTION_ROLLEDBACK_id);
        st_ids_table.put(SystemExceptionNames.TRANSIENT_name,
                         SystemExceptionNames.TRANSIENT_id);
        st_ids_table.put(SystemExceptionNames.UNKNOWN_name,
                         SystemExceptionNames.UNKNOWN_id);
    }

    public static void write(org.omg.CORBA.portable.OutputStream out,
                             org.omg.CORBA.SystemException sys)
    {
        if (st_ids_table == null)
            initIds();

        out.write_string((String) st_ids_table.get(sys.getClass().getName()));
        out.write_ulong(sys.minor);
        out.write_ulong(sys.completed.value());
    }

    public static org.omg.CORBA.SystemException
    	read(org.omg.CORBA.portable.InputStream in)
    {
        String name = in.read_string();
        int minor = in.read_ulong();
        int completed = in.read_ulong();

        CompletionStatus completion = CompletionStatus.from_int(completed);

        if (st_creators_table == null)
            initCreators();

        SystemExceptionCreator creator = (SystemExceptionCreator) 
        	st_creators_table.get(name);

        if (creator == null)
            throw new org.omg.CORBA.MARSHAL("Invalid System Exception Name");

        return creator.create(minor, completion);

    }
}