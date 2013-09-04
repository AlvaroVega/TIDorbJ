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
package es.tid.TIDorbj.core.cdr;

import org.omg.CORBA.MARSHAL;

import es.tid.TIDorbj.core.util.CodeBaseURL;
import es.tid.TIDorbj.core.util.RepositoryId;

public class ValueTypeInfo
{

    public final static int NULL_TAG = 0;

    /* value tags constants */
    public final static int CODE_BASE_BIT = 0x00000001;

    public final static int SINGLE_REPOSITORY_ID = 0x00000002;

    public final static int REPOSITORY_ID_LIST = 0x00000006;

    public final static int INDIRECTION_TAG = 0xffffffff;

    public final static int FRAGMENTED_BIT = 0x00000008;

    public final static int MIN_VALUE_TAG = 0x7ffff00;

    public final static int MAX_VALUE_TAG = 0x7ffffff;

    public final static int MIN_CHUNK_SIZE = 0;

    public final static int MAX_CHUNK_SIZE = 0x7ffff00;

    private CodeBaseURL m_code_base_url;

    private RepositoryId[] m_repository_ids;

    private int m_value_tag;

    private java.io.Serializable m_value;

    public ValueTypeInfo()
    {
        m_code_base_url = null;
        m_repository_ids = null;
        m_value_tag = MIN_VALUE_TAG;
        m_value = null;
    }

    protected ValueTypeInfo(int value_tag)
    {
        m_code_base_url = null;
        m_repository_ids = null;
        m_value_tag = value_tag;
        m_value = null;
    }

    public boolean isNull()
    {
        return m_value_tag == NULL_TAG;
    }

    public boolean isIndirection()
    {
        return m_value_tag == INDIRECTION_TAG;
    }

    public boolean isFragmented()
    {
        return ((!isIndirection()) 
            	&& ((m_value_tag & FRAGMENTED_BIT) == FRAGMENTED_BIT));
    }

    public boolean is_state()
    {
        return (m_value_tag != NULL_TAG) && (m_value_tag != INDIRECTION_TAG);
    }

    public RepositoryId[] get_repository_ids()
    {
        return m_repository_ids;
    }

    public String get_id()
    {
        if ((m_repository_ids == null) || (m_repository_ids.length == 0))
            return null;
        return m_repository_ids[0].m_value;
    }

    public boolean is_truncable(String rep_id)
    {
        String value_id = null;

        if (m_repository_ids != null) {
            for (int i = 0; i < m_repository_ids.length; i++) {
                value_id = m_repository_ids[i].m_value;

                if (rep_id.equals(value_id)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void set_repository_ids(String[] ids)
    {
        RepositoryId[] rep_ids = new RepositoryId[ids.length];

        for (int i = 0; i < ids.length; i++) {
            rep_ids[i] = new RepositoryId(ids[i]);
        }
        set_repository_ids(rep_ids);
    }

    public void set_repository_ids(RepositoryId[] ids)
    {
        m_repository_ids = ids;

        if ((ids == null) || (ids.length == 0)) {
            m_value_tag &= ~REPOSITORY_ID_LIST;
        } else if (ids.length == 1) {
            m_value_tag &= ~REPOSITORY_ID_LIST;
            m_value_tag |= SINGLE_REPOSITORY_ID;
        } else {
            m_value_tag |= REPOSITORY_ID_LIST;
        }
    }

    public void set_value(java.io.Serializable val)
    {
        m_value = val;
    }

    public java.io.Serializable get_value()
    {
        return m_value;
    }

    public boolean has_code_base_url()
    {
        return m_code_base_url != null;
    }

    public CodeBaseURL get_code_base_url()
    {
        return m_code_base_url;
    }

    public void set_code_base_url(CodeBaseURL url)
    {
        m_code_base_url = url;

        if (url == null)
            m_value_tag &= ~CODE_BASE_BIT;
        else
            m_value_tag |= CODE_BASE_BIT;
    }

    public void set_fragmented(boolean value)
    {
        if (value)
            m_value_tag |= FRAGMENTED_BIT;
        else
            m_value_tag &= ~FRAGMENTED_BIT;
    }

    public static ValueTypeInfo read(CDRInputStream in)
    {
        int value = in.read_long();

        ValueTypeInfo info = new ValueTypeInfo(value);

        info.read_info(in);

        return info;
    }

    public static void write_null(org.omg.CORBA.portable.OutputStream os)
    {
        os.write_ulong(NULL_TAG);
    }

    public void write(CDROutputStream out)
    {
        out.write_long(m_value_tag);
        write_code_base_url(out);
        write_type_info(out);
    }

    protected void read_info(CDRInputStream input)
    {
        if ((m_value_tag != INDIRECTION_TAG) && (m_value_tag != NULL_TAG)) {
            read_code_base_url(input);
            read_type_info(input);
        }
    }

    protected void read_code_base_url(CDRInputStream input)
    {
        if ((m_value_tag & CODE_BASE_BIT) == 0x0)
            return;

        input.alignment(CDR.LONG_SIZE);

        AbsolutePosition this_url_position = input.getAbsolutePosition();

        int code_base_tag = input.read_long();

        if (code_base_tag == INDIRECTION_TAG) {
            AbsolutePosition url_position = input.readIndirection();
            java.lang.Object obj = input.getContextCDR()
                                        .lookupObject(url_position);

            if ((obj != null) && (obj instanceof CodeBaseURL))
                m_code_base_url = (CodeBaseURL) obj;
            else
                throw new MARSHAL("Invalid URL indirection");
        } else { // read code_base_url
            char[] chars = new char[code_base_tag];
            input.read_char_array(chars, 0, code_base_tag);
            m_code_base_url = new CodeBaseURL(new String(chars));
            input.getContextCDR().putPosition(this_url_position,
                                              m_code_base_url);
        }
    }

    protected void write_code_base_url(CDROutputStream out)
    {
        if (m_code_base_url != null)
            out.write_string(m_code_base_url.m_url);
    }

    protected void read_type_info(CDRInputStream input)
    {
        if ((m_value_tag & REPOSITORY_ID_LIST) == REPOSITORY_ID_LIST) {
            // read ids sequence
            int length = input.read_long();

            if (length < 0)
                throw new MARSHAL("Invalid RepositoryId seq length < 0");

            m_repository_ids = new RepositoryId[length];

            for (int i = 0; i < length; i++)
                m_repository_ids[i] = read_repository_id(input);

        } else if ((m_value_tag & SINGLE_REPOSITORY_ID) == SINGLE_REPOSITORY_ID) {
            //  int length = input.read_long();

            //  if(length != 1)
            //    throw new MARSHAL("Invalid RepositoryId seq length != 1");

            m_repository_ids = new RepositoryId[1];

            m_repository_ids[0] = read_repository_id(input);
        } // else there is no type data
    }

    protected RepositoryId read_repository_id(CDRInputStream input)
    {
        input.alignment(CDR.LONG_SIZE);
        AbsolutePosition id_position = input.getAbsolutePosition();

        int repository_id_tag = input.read_long();
        if (repository_id_tag == INDIRECTION_TAG) {
            AbsolutePosition url_position = input.readIndirection();
            java.lang.Object obj = input.getContextCDR()
                                        .lookupObject(url_position);

            if ((obj != null) && (obj instanceof RepositoryId))
                return (RepositoryId) obj;
            else
                throw new MARSHAL("Invalid RepositoryId indirection");
        } else {
            char[] chars = new char[repository_id_tag];
            input.read_char_array(chars, 0, repository_id_tag);
            RepositoryId id =
                new RepositoryId(new String(chars, 0,
                                            repository_id_tag - 1));
            input.getContextCDR().putPosition(id_position, id);
            return id;
        }
    }

    protected void write_type_info(CDROutputStream out)
    {
        if (m_code_base_url != null)
            out.writeReferenceableString(m_code_base_url.m_url);

        if (m_repository_ids != null) {
            if ((m_value_tag & REPOSITORY_ID_LIST) == REPOSITORY_ID_LIST) {
                out.write_long(m_repository_ids.length);
                for (int i = 0; i < m_repository_ids.length; i++)
                    out.writeReferenceableString(m_repository_ids[i].m_value);
            } else { // SINGLE_REPOSITORY_ID
                out.writeReferenceableString(m_repository_ids[0].m_value);
            }
        }
    }
}