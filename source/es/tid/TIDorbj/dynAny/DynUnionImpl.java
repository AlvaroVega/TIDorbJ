/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 19 $
* Date: $Date: 2006-06-28 10:17:01 +0200 (Wed, 28 Jun 2006) $
* Last modified by: $Author: iredondo $
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
package es.tid.TIDorbj.dynAny;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.BAD_TYPECODE;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.TypeCodePackage.Bounds;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynUnion;
import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

import es.tid.TIDorbj.core.AnyImpl;
import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.typecode.EnumTypeCode;
import es.tid.TIDorbj.core.typecode.TypeCodeFactory;
import es.tid.TIDorbj.core.typecode.UnionTypeCode;
import es.tid.TIDorbj.core.util.EnumHolder;

/**
 * The <code>DynUnion</code> interface represents a <code>DynAny</code>
 * object that is associated with an IDL union. Union values can be traversed
 * using the operations defined in <code>DynAny</code>. The first component
 * in the union corresponds to the discriminator; the second corresponds to the
 * actual value of the union. Calling the method <code>next()</code> twice
 * allows you to access both components.
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */

public class DynUnionImpl extends DynAnyBase
    implements org.omg.DynamicAny.DynUnion
{
    /**
     * Active member.
     */
    DynAnyBase m_active_member;

    /**
     * Active discriminator.
     */
    DynAnyBase m_dyn_discriminator;

    /**
     * Member index in the union <code>TypeCode</code>.
     */
    int m_active_member_index;

    /**
     * Current Component.
     */
    int m_current_index;

    /**
     * Component Count.
     */
    int m_component_count;

    protected DynUnionImpl(DynAnyFactoryImpl factory, TIDORB orb)
    {
        super(factory, orb);
        m_current_index = -1;
        m_component_count = 1;
    }

    /**
     * Constructor. Gets an any object for reading its value. It assumes that
     * the any contains an struct TypeCode.
     * 
     * @param any
     *            the any value.
     */

    protected DynUnionImpl(DynAnyFactoryImpl factory, TIDORB orb, Any any,
                           TypeCode real_type)
    {
        super(factory, orb, any.type(), real_type);

        m_component_count = 2;

        InputStream in = any.create_input_stream();

        _read(in);

    }

    /**
     * Constructor. Gets a simple TypeCode to create a new value.
     * 
     * @param type
     *            the UnionTypeCode value.
     */

    protected DynUnionImpl(DynAnyFactoryImpl factory, TIDORB orb,
                           TypeCode type, TypeCode real_type)
    {
        super(factory, orb, type, real_type);

        try {
            m_active_member_index = m_base_type.default_index();
            if (m_active_member_index > -1)
                m_component_count = 2;
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE(bk.toString());
        }
    }

    public org.omg.DynamicAny.DynAny copy()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        DynUnionImpl new_dyn = new DynUnionImpl(m_factory, m_orb);
        new_dyn.m_dyn_type = m_dyn_type;
        new_dyn.m_base_type = m_base_type;
        new_dyn.m_active_member_index = m_active_member_index;
        new_dyn.m_component_count = m_component_count;

        if (m_component_count == 2) {
            if (m_active_member != null) {
                new_dyn.m_active_member = (DynAnyBase) m_active_member.copy();
            }
            if (m_dyn_discriminator != null) {
                new_dyn.m_dyn_discriminator = 
                    (DynAnyBase) m_dyn_discriminator.copy();
            }
        }

        return new_dyn;
    }

    // DynUnion operations
    public org.omg.DynamicAny.DynAny get_discriminator()
    // CORBA 2.6: throws org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_component_count == 1)
            // throw new InvalidValue("No active member.");
            throw new BAD_OPERATION("No active member.");

        if (m_dyn_discriminator == null) { // default discriminator search

            Any discriminator = m_orb.create_any();
            int i = 0;
            do { // search a value that will be not at the case labels
                setDiscriminatorValue(discriminator, ++i);
            } while (UnionTypeCode.searchMemberIndex(m_base_type, discriminator) 
                     != m_active_member_index);

            try {
                m_dyn_discriminator = (DynAnyBase)
                	m_factory.create_dyn_any(discriminator);
            }
            catch (InconsistentTypeCode itc) {/* unreachable */
                return null;
            }

        }

        return m_dyn_discriminator;
    }

    public void set_discriminator(org.omg.DynamicAny.DynAny d)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        int index = UnionTypeCode.searchMemberIndex(m_base_type, d.to_any());

        if (index < 0)
            throw new TypeMismatch("DynUnion: Invalid label value " + index);

        setIndex(index, d);
    }

    protected void setIndex(int index, DynAny discriminator)
    {
        if (m_active_member_index == index) {
            if ((m_dyn_discriminator == null) && (discriminator != null))
                m_dyn_discriminator = (DynAnyBase) discriminator.copy();
            return;
        }

        if (m_dyn_discriminator != null) {
            m_dyn_discriminator.destroy();
            m_dyn_discriminator = null;
        }

        if (discriminator != null)
            m_dyn_discriminator = (DynAnyBase) discriminator.copy();

        m_current_index = 0;
        m_component_count = 2;
        m_active_member_index = index;

        try {
            if ((m_active_member != null)
                && (!m_active_member.type()
                     .equivalent(m_base_type.member_type(m_active_member_index)))) {
                m_active_member.destroy();
                m_active_member = null;
            }
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE(bk.toString());
        }
        catch (Bounds bd) {
            throw new INTERNAL(bd.toString());
        }
    }

    public void set_to_default_member()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        try {
            // set member idex to defalut index

            int index = m_base_type.default_index();
            if (index < 0)
                throw new TypeMismatch("Union without default case or all "
                                       + " posible discriminator values used.");

            setIndex(index, null);

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE(bk.toString());
        }
    }

    public void set_to_no_active_member()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        try {
            if (m_base_type.default_index() > -1)
                throw new TypeMismatch("Union has default case");

            // are all the discriminator values uses

            if (allCasesUsed())
                throw new 
                TypeMismatch("All posible discriminator cases used in union.");

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE(bk.toString());
        }

        if (m_active_member != null) {
            m_active_member.destroy();
            m_active_member = null;
        }

        if (m_dyn_discriminator != null) {
            m_dyn_discriminator.destroy();
            m_dyn_discriminator = null;
        }

        m_active_member_index = -1;
        m_component_count = 1;
    }

    public boolean has_no_active_member()
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        return m_component_count < 2;
    }

    public org.omg.CORBA.TCKind discriminator_kind()
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        try {
            return m_base_type.discriminator_type().kind();
        }
        catch (BadKind bk) {/* unreachable */
            throw new BAD_TYPECODE(bk.toString());
        }

    }

    public org.omg.DynamicAny.DynAny member()
        throws org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_component_count == 1)
            throw new InvalidValue("No active member");

        if (m_active_member == null) {
            try {
                m_active_member = (DynAnyBase)
                	m_factory.create_dyn_any_from_type_code(
                	    m_base_type.member_type(m_active_member_index));
            }
            catch (BadKind bk) {
                throw new BAD_TYPECODE(bk.toString());
            }
            catch (Bounds bd) {
                throw new INTERNAL(bd.toString());
            }
            catch (InconsistentTypeCode bk) { /* unreachable */}
        }

        return m_active_member;
    }

    public String member_name()
        throws org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");
        if (m_component_count == 1)
            throw new InvalidValue("No active member");

        try {
            return m_base_type.member_name(m_active_member_index);
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE(bk.toString());
        }
        catch (Bounds bd) {
            throw new INTERNAL(bd.toString());
        }
    }

    public void member_name(String arg)
        throws org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_component_count == 1)
            throw new InvalidValue("No active member");

        try {

            int member_count = m_base_type.member_count();

            for (int i = 0; i < member_count; i++) {
                if (arg.equals(m_base_type.member_name(i))) {
                    DynAnyBase dyn_disc = (DynAnyBase)
                    	m_factory.create_dyn_any(m_base_type.member_label(i));
                    setIndex(i, dyn_disc);

                    return;
                }
            }
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE(bk.toString());
        }
        catch (Bounds bd) {
            throw new INTERNAL(bd.toString());
        }
        catch (InconsistentTypeCode itc) {/* unreachable */
            return;
        }

        throw new InvalidValue("Union has not any member named: " + arg);
    }

    public org.omg.CORBA.TCKind member_kind()
        throws org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_active_member_index < 1)
            throw new InvalidValue("No member selected.");

        try {
            return m_base_type.member_type(m_active_member_index).kind();
        }
        catch (BadKind bk) { /* unreachable */
            return null;
        }
        catch (Bounds bd) { /* unreachable */
            return null;
        }
    }

    public org.omg.DynamicAny.DynAny current_component()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            return null;

        try {
            switch (m_current_index)
            {
                case 0:
                    return get_discriminator();
                case 1:
                    return member();
            }
        }
        catch (InvalidValue iv) {
            throw new INTERNAL(iv.toString());
        }

        throw new INTERNAL("Current out of range");
    }

    // Streamable operations

    public void _read(org.omg.CORBA.portable.InputStream in)
    {
        Any discriminator_any = m_orb.create_any();

        try {
            discriminator_any.read_value(in, m_base_type.discriminator_type());

            m_active_member_index =
                UnionTypeCode.searchMemberIndex(m_base_type, discriminator_any);

            if (m_active_member_index < 0)
                throw new BAD_PARAM("Invalid Union label: "
                                    + m_active_member_index);

            m_dyn_discriminator = (DynAnyBase)
            	m_factory.create_dyn_any(discriminator_any);

            m_dyn_discriminator.setUserDestroy(false);

            m_active_member = (DynAnyBase)
            	m_factory.create_dyn_any_from_type_code(
            	    m_base_type.member_type(m_active_member_index));

            m_active_member.setUserDestroy(false);

            ((Streamable) m_active_member)._read(in);

        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE(bk.toString());
        }
        catch (Bounds bd) {
            throw new INTERNAL(bd.toString());
        }
        catch (InconsistentTypeCode bk) { /* unreachable */
        }
    }

    public void _write(org.omg.CORBA.portable.OutputStream out)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_component_count == 1)
            throw new BAD_OPERATION("Uncompleted union.");

        try {
            ((Streamable) get_discriminator())._write(out);
            ((Streamable) member())._write(out);
        }
        catch (InvalidValue iv) {
            throw new INTERNAL(iv.toString());
        }
    }

    // DynAny Operations

    public void assign(org.omg.DynamicAny.DynAny dyn_any)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch
    {
        if (dyn_any == null)
            throw new BAD_PARAM("Null DynAny reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (!m_base_type.equivalent(dyn_any.type()))
            throw new TypeMismatch();

        if (dyn_any instanceof DynUnion) {
            try {
                DynUnion dyn_union = (DynUnion) dyn_any;
                if (dyn_union.has_no_active_member()) {
                    set_to_no_active_member();
                } else {
                    set_discriminator(dyn_union.get_discriminator());
                    member().assign(dyn_union.member());
                }
            }
            catch (InvalidValue iv) {}
        } else
            // invalid error
            throw new TypeMismatch();
    }

    public void from_any(org.omg.CORBA.Any value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value == null)
            throw new BAD_PARAM("Null Any reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (!m_base_type.equivalent(value.type()))
            throw new TypeMismatch();

        try {
            _read(value.create_input_stream());
        }
        catch (MARSHAL m) {
            throw new InvalidValue();
        }
    }

    public org.omg.CORBA.Any to_any()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        AnyImpl new_value = (AnyImpl) m_orb.create_any();
        new_value.type(type());
        org.omg.CORBA.portable.OutputStream output;
        output = new_value.create_output_stream();

        _write(output);

        //new_value.insert_Streamable(this);

        return new_value;
    }

    public boolean equal(org.omg.DynamicAny.DynAny dyn_any)
    {
        if (dyn_any == null)
            throw new BAD_PARAM("Null DynAny reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (!m_base_type.equivalent(dyn_any.type()))
            return false;

        if (dyn_any instanceof DynUnion) {
            try {
                DynUnion dyn_union = (DynUnion) dyn_any;

                if (!get_discriminator().equal(dyn_union.get_discriminator()))
                    return false;

                return member().equal(dyn_union.member());
            }
            catch (InvalidValue iv) {
                throw new INTERNAL(iv.toString());
            }

        } else
            // invalid error
            throw new BAD_PARAM("dyn_any value does not instantiate DynUnion"
            					+ " but its type is union");
    }

    protected void destroyNow()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_active_member != null) {
            m_active_member.destroy();
            m_active_member = null;
        }

        if (m_dyn_discriminator != null) {
            m_dyn_discriminator.destroy();
            m_dyn_discriminator = null;
        }
    }

    protected boolean allCasesUsed()
        throws TypeMismatch
    {
        TypeCode disc_type = null;
        int member_count;
        try {
            disc_type = m_base_type.discriminator_type();

            while (disc_type.kind().value() == TCKind._tk_alias)
                disc_type = disc_type.content_type();

            member_count = m_base_type.member_count();

            switch (disc_type.kind().value())
            {
                case TCKind._tk_short:
                case TCKind._tk_long:
                case TCKind._tk_longlong:
                case TCKind._tk_ushort:
                case TCKind._tk_ulong:
                case TCKind._tk_ulonglong:
                case TCKind._tk_wchar:
                case TCKind._tk_char:
                    return false;
                case TCKind._tk_boolean:
                    return member_count <= 2;
                case TCKind._tk_enum:
                    return disc_type.member_count() <= member_count;
            }
        }
        catch (BadKind bk) {
            throw new INTERNAL(bk.toString());
        }

        throw new TypeMismatch("Invalid discriminator Value");
    }

    protected void setDiscriminatorValue(org.omg.CORBA.Any discriminator,
                                         int value)
    {
        switch (discriminator.type().kind().value())
        {
            case TCKind._tk_short:
                discriminator.insert_short((short) value);
            break;
            case TCKind._tk_long:
                discriminator.insert_long(value);
            break;
            case TCKind._tk_longlong:
                discriminator.insert_longlong(value);
            break;
            case TCKind._tk_ushort:
                discriminator.insert_ushort((short) value);
            break;
            case TCKind._tk_ulong:
                discriminator.insert_ulong(value);
            break;
            case TCKind._tk_ulonglong:
                discriminator.insert_ulonglong(value);
            break;
            case TCKind._tk_boolean:
                discriminator.insert_boolean((value == 0) ? false : true);
            break;
            case TCKind._tk_char:
                discriminator.insert_char((char) value);
            break;
            case TCKind._tk_wchar:
                discriminator.insert_wchar((char) value);
            break;
            case TCKind._tk_enum:
                try {
                    discriminator.insert_Streamable(
                        new EnumHolder((EnumTypeCode)
                                       m_base_type.discriminator_type(),
                                       value));
                }
                catch (BadKind bk) {}
            break;

        }
    }

    // Object methods

    public boolean _is_a(java.lang.String repositoryIdentifier)
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynAny destroyed.");

        if (repositoryIdentifier == null)
            throw new BAD_PARAM("Null string reference");

        if (repositoryIdentifier.equals("IDL:omg.org/DynamicAny/DynUnion:1.0"))
            return true;

        return super._is_a(repositoryIdentifier);

    }

    public void insert_boolean(boolean value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_boolean))
            throw new TypeMismatch();

        if (m_current_index == 0) {
            try {
                DynAny new_disc = 
                    m_factory.create_dyn_any_from_type_code(
                        current_component().type());

                new_disc.insert_boolean(value);

                set_discriminator(new_disc);
            }
            catch (InconsistentTypeCode itc) {
                throw new INTERNAL(itc.toString());
            }
        } else
            current_component().insert_boolean(value);
    }

    public void insert_char(char value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_char))
            throw new TypeMismatch();

        if (m_current_index == 0) {
            try {
                DynAny new_disc = 
                    m_factory.create_dyn_any_from_type_code(
                        current_component().type());

                new_disc.insert_char(value);

                set_discriminator(new_disc);

            }
            catch (InconsistentTypeCode itc) {
                throw new INTERNAL(itc.toString());
            }
        } else
            current_component().insert_char(value);
    }

    public void insert_wchar(char value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_wchar))
            throw new TypeMismatch();

        if (m_current_index == 0) {
            try {
                DynAny new_disc = 
                    m_factory.create_dyn_any_from_type_code(
                    current_component().type());

                new_disc.insert_char(value);

                set_discriminator(new_disc);
            }
            catch (InconsistentTypeCode itc) {
                throw new INTERNAL(itc.toString());
            }
        } else
            current_component().insert_wchar(value);
    }

    public void insert_octet(byte value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (m_current_index == 0)
            throw new TypeMismatch();

        if (!current_component().type().equivalent(TypeCodeFactory.tc_octet))
            throw new TypeMismatch();
        else
            current_component().insert_octet(value);
    }

    public void insert_short(short value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_short))
            throw new TypeMismatch();

        if (m_current_index == 0) {
            try {
                DynAny new_disc =
                    m_factory.create_dyn_any_from_type_code(
                        current_component().type());

                new_disc.insert_short(value);

                set_discriminator(new_disc);
            }
            catch (InconsistentTypeCode itc) {
                throw new INTERNAL(itc.toString());
            }
        } else
            current_component().insert_short(value);
    }

    public void insert_ushort(short value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_ushort))
            throw new TypeMismatch();

        if (m_current_index == 0) {
            try {
                DynAny new_disc =
                    m_factory.create_dyn_any_from_type_code(
                        current_component().type());

                new_disc.insert_ushort(value);

                set_discriminator(new_disc);
            }
            catch (InconsistentTypeCode itc) {
                throw new INTERNAL(itc.toString());
            }
        } else
            current_component().insert_ushort(value);
    }

    public void insert_long(int value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_long))
            throw new TypeMismatch();

        if (m_current_index == 0) {
            try {
                DynAny new_disc = 
                    m_factory.create_dyn_any_from_type_code(
                        current_component().type());

                new_disc.insert_long(value);

                set_discriminator(new_disc);
            }
            catch (InconsistentTypeCode itc) {
                throw new INTERNAL(itc.toString());
            }
        } else
            current_component().insert_long(value);
    }

    public void insert_ulong(int value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_ulong))
            throw new TypeMismatch();

        if (m_current_index == 0) {
            try {
                DynAny new_disc =
                    m_factory.create_dyn_any_from_type_code(
                        current_component() .type());
                
                new_disc.insert_ulong(value);

                set_discriminator(new_disc);
            }
            catch (InconsistentTypeCode itc) {
                throw new INTERNAL(itc.toString());
            }
        } else
            current_component().insert_ulong(value);
    }

    public void insert_longlong(long value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_longlong))
            throw new TypeMismatch();

        if (m_current_index == 0) {
            try {
                DynAny new_disc = 
                    m_factory.create_dyn_any_from_type_code(
                        current_component().type());

                new_disc.insert_longlong(value);
                set_discriminator(new_disc);
            }
            catch (InconsistentTypeCode itc) {
                throw new INTERNAL(itc.toString());
            }
        } else
            current_component().insert_longlong(value);
    }

    public void insert_ulonglong(long value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type()
                                .equivalent(TypeCodeFactory.tc_ulonglong))
            throw new TypeMismatch();

        if (m_current_index == 0) {
            try {
                DynAny new_disc =
                    m_factory.create_dyn_any_from_type_code(
                        current_component().type());

                new_disc.insert_ulonglong(value);

                set_discriminator(new_disc);
            }
            catch (InconsistentTypeCode itc) {
                throw new INTERNAL(itc.toString());
            }
        } else
            current_component().insert_ulonglong(value);
    }

    public void insert_float(float value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_float))
            throw new TypeMismatch();

        if (m_current_index == 0)
            throw new TypeMismatch();
        else
            current_component().insert_float(value);
    }

    public void insert_double(double value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (m_current_index == 0)
            throw new TypeMismatch();

        if (!current_component().type().equivalent(TypeCodeFactory.tc_double))
            throw new TypeMismatch();
        else
            current_component().insert_double(value);
    }

    public void insert_string(String value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");
        if (m_current_index == 0)
            throw new TypeMismatch();
        if (!current_component().type().equivalent(TypeCodeFactory.tc_string))
            throw new TypeMismatch();
        else
            current_component().insert_string(value);
    }

    public void insert_wstring(String value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (m_current_index == 0)
            throw new TypeMismatch();

        if (!current_component().type().equivalent(TypeCodeFactory.tc_wstring))
            throw new TypeMismatch();
        else
            current_component().insert_wstring(value);
    }

    public void insert_any(org.omg.CORBA.Any value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (m_current_index == 0)
            throw new TypeMismatch();

        if (!current_component().type().equivalent(TypeCodeFactory.tc_any))
            throw new TypeMismatch();
        else
            current_component().insert_any(value);
    }

    public void insert_dyn_any(org.omg.DynamicAny.DynAny value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (m_current_index == 0)
            throw new TypeMismatch();

        if (!current_component().type().equivalent(TypeCodeFactory.tc_any))
            throw new TypeMismatch();
        else
            current_component().insert_dyn_any(value);
    }

    public void insert_typecode(org.omg.CORBA.TypeCode value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (m_current_index == 0)
            throw new TypeMismatch();

        if (!current_component().type().equivalent(TypeCodeFactory.tc_TypeCode))
            throw new TypeMismatch();
        else
            current_component().insert_typecode(value);
    }

    public void insert_val(java.io.Serializable value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (m_current_index == 0)
            throw new TypeMismatch();

        current_component().insert_val(value);
    }

    public void insert_reference(org.omg.CORBA.Object value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (m_current_index == 0)
            throw new TypeMismatch();

        if (!current_component().type().equivalent(TypeCodeFactory.tc_objref))
            throw new TypeMismatch();
        else
            current_component().insert_reference(value);
    }

    public boolean get_boolean()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_boolean))
            throw new TypeMismatch();

        return current_component().get_boolean();
    }

    public char get_char()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_char))
            throw new TypeMismatch();

        return current_component().get_char();
    }

    public char get_wchar()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_wchar))
            throw new TypeMismatch();

        return current_component().get_wchar();
    }

    public byte get_octet()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_octet))
            throw new TypeMismatch();

        return current_component().get_octet();
    }

    public short get_short()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_short))
            throw new TypeMismatch();

        return current_component().get_short();
    }

    public short get_ushort()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_ushort))
            throw new TypeMismatch();

        return current_component().get_ushort();
    }

    public int get_long()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_long))
            throw new TypeMismatch();

        return current_component().get_long();
    }

    public int get_ulong()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_ulong))
            throw new TypeMismatch();

        return current_component().get_ulong();
    }

    public long get_longlong()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_longlong))
            throw new TypeMismatch();

        return current_component().get_ulonglong();
    }

    public long get_ulonglong()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type()
                                .equivalent(TypeCodeFactory.tc_ulonglong))
            throw new TypeMismatch();

        return current_component().get_ulonglong();
    }

    public float get_float()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_float))
            throw new TypeMismatch();

        return current_component().get_float();
    }

    public double get_double()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_double))
            throw new TypeMismatch();

        return current_component().get_double();
    }

    public String get_string()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_string))
            throw new TypeMismatch();

        return current_component().get_string();
    }

    public String get_wstring()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_wstring))
            throw new TypeMismatch();

        return current_component().get_wstring();
    }

    public org.omg.CORBA.Any get_any()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_any))
            throw new TypeMismatch();

        return current_component().get_any();
    }

    public org.omg.DynamicAny.DynAny get_dyn_any()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_any))
            throw new TypeMismatch();

        return current_component().get_dyn_any();
    }

    public org.omg.CORBA.TypeCode get_typecode()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_TypeCode))
            throw new TypeMismatch();

        return current_component().get_typecode();
    }

    public java.io.Serializable get_val()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        return current_component().get_val();
    }

    public org.omg.CORBA.Object get_reference()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!current_component().type().equivalent(TypeCodeFactory.tc_objref))
            throw new TypeMismatch();

        return current_component().get_reference();
    }

    // DynAny without components methods

    public int component_count()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

//      return 0;
        return m_component_count;
    }

    public boolean seek(int index)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_current_index == -1)
            return false;

        if (index < component_count()) {
            m_current_index = index;
            return true;
        } else
            return false;
    }

    public void rewind()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_current_index != -1)
            m_current_index = 0;
    }

    public boolean next()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (m_current_index == -1)
            return false;

        if (m_current_index < component_count() - 1) {
            m_current_index++;
            return true;
        } else
            return false;
    }

}