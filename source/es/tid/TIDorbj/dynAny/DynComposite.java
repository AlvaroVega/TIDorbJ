/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 453 $
* Date: $Date: 2010-04-27 16:52:41 +0200 (Tue, 27 Apr 2010) $
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
package es.tid.TIDorbj.dynAny;

import java.util.Vector;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.OutputStream;
import org.omg.DynamicAny.DynAny;
import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
import org.omg.DynamicAny.DynAnyPackage.InvalidValue;
import org.omg.DynamicAny.DynAnyPackage.TypeMismatch;

import es.tid.TIDorbj.core.TIDORB;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.typecode.TypeCodeFactory;

/**
 * Base class for composite Dynamic Anys. It implements the Streamable interface
 * for alowing the generated anys (with to_any) write its value in an output
 * stream. The generated anys are contructed with:
 * <p>
 * 
 * <code> Any to_any()
 *<p>{
 *<p>    Any a= new Any();
 *<p>    a.insert_Streamable(this);
 *<p>    return a;
 *<p>} </code> Due to this construction unneccesary remarshalings are avoided.
 * <p>
 * The DynComposite has two operation modes:
 * <ul>
 * <li><bold>Inserting Mode </bold>: a <code>TypeCode</code> is given and the
 * any is constructec from other Dynamic Anys.
 * <li><bold>Extraction Mode </bold>: a <code>Any</code> is given and the
 * DynAny structures are obtained from the any and its input stream.
 * </ul>
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 1.0
 */
public abstract class DynComposite extends DynAnyBase
{
    /**
     * The marshaled value of the Dynamic Any for extracting.
     */

    CDRInputStream m_complete_value;

    /**
     * The next component marshaled value of the Dynamic Any for extracting.
     */

    CDRInputStream m_next_value;

    /**
     * Current component of the array.
     */

    int m_current_index;

    /**
     * Number of Components of the Dynany. This value must be initialized by the
     * child classes.
     */

    int m_component_count;

    /**
     * Array of components.
     */

    Vector m_components;

    protected DynComposite(DynAnyFactoryImpl factory, TIDORB orb)
    {
        super(factory, orb);
        m_components = new Vector();
        m_component_count = 0;
        m_current_index = -1;
        m_complete_value = null;
        m_next_value = null;
    }

    /**
     * Constructor for insertions.
     */

    protected DynComposite(DynAnyFactoryImpl factory, TIDORB orb,
                           TypeCode type, TypeCode real_type)
    {
        this(factory, orb);

        m_dyn_type = type;
        m_base_type = real_type;
    }

    /**
     * Constructor for extraction.
     */
    protected DynComposite(DynAnyFactoryImpl factory, TIDORB orb, Any any,
                           TypeCode real_type)
    {
        this(factory, orb);

        m_dyn_type = any.type();
        m_base_type = real_type;

        getStreams(any);
    }

    // DynComposite methods

    /**
     * Clears the value of the dynAny and try to destroy the components. It is
     * done when going to insert a new value.
     */

    protected void reset()
    {
        if (m_component_count == 0)
            m_current_index = -1;
        else
            m_current_index = 0;

        int size = m_components.size();

        for (int i = 0; i < size; i++) {
            ((DynAny) m_components.elementAt(i)).destroy();
        }

        m_components.removeAllElements();

        m_complete_value = null;
        m_next_value = null;
    }

    /**
     * @param position
     *            the component position
     * @return the <code>TypeCode</code> of the component at the given
     *         position
     */
    protected abstract TypeCode getComponentType(int position);

    /**
     * Internal operation to notify that the last component has been
     * dissasembled and the DynAny can be wroten writing its components.
     *  
     */
    protected void lastComponent()
    {
        m_complete_value = null;
        m_next_value = null;
    }

    /**
     * Streamable _read operation
     */
    public void _read(org.omg.CORBA.portable.InputStream is)
    {
        reset();

        Any any = m_orb.create_any();

        any.read_value(is, m_base_type);

        getStreams(any);

    }

    /**
     * Streamable _write operation
     */
    public void _write(org.omg.CORBA.portable.OutputStream os)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (os == null)
            throw new BAD_PARAM("Null OutputStream reference", 0,
                                CompletionStatus.COMPLETED_NO);

        DynAny dyn = null;
        try {
            for (int i = 0; i < m_component_count; i++) {
                dyn = getComponent(i);
                if (dyn instanceof DynAnyBase)
                    ((DynAnyBase) dyn)._write(os);
                else
                    dyn.to_any().write_value(os);
            }
        }
        catch (TypeMismatch tm) {
            throw new INTERNAL();
        }
    }

    /**
     * DynAny destructor. Invoked by container DynAnys.
     */
    protected void destroyNow()
    {
        super.destroyNow();

        reset();

        m_components = null;
    }

    /**
     * Obtains the marshaled value from the any.
     * 
     * @param any
     *            the new value
     */
    protected void getStreams(org.omg.CORBA.Any any)
    {
        if (any == null)
            throw new BAD_PARAM("Null Any reference", 0,
                                CompletionStatus.COMPLETED_NO);

        try {
            m_complete_value = (CDRInputStream) 
            	any.create_input_stream();
        }
        catch (ClassCastException cce) {
            OutputStream out = m_orb.create_output_stream();
            any.write_value(out);
            m_complete_value = (CDRInputStream) out.create_input_stream();
        }

        m_next_value = m_complete_value.copy();
    }

    /**
     * Validates and prepares the dyn_any assignment.
     * 
     * @param dyn_any
     *            the new value
     * @throws TypeMismatch
     *             if the new value TypeCode is not equivalent
     */
    protected void preAssign(org.omg.DynamicAny.DynAny dyn_any)
        throws TypeMismatch
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.", 0,
                                       CompletionStatus.COMPLETED_NO);

        if (dyn_any == null)
            throw new BAD_PARAM("Null DynAny reference", 0,
                                CompletionStatus.COMPLETED_NO);

        if (!m_base_type.equivalent(dyn_any.type())) {
            throw new TypeMismatch();
        }

        // both are the same type.

        reset();

        // component_count = dyn_any.component_count();
    }

    /**
     * Copy the content of the DynComposite instance to other.
     * 
     * @param dyn_any
     *            the destination DynComposite
     */
    protected void copyTo(DynComposite dyn_any)
    {

        dyn_any.m_component_count = m_component_count;

        if (m_component_count == 0) {
            dyn_any.m_current_index = -1;
            return;
        }

        if (m_complete_value != null) {
            dyn_any.m_complete_value = m_complete_value.copy();
            dyn_any.m_next_value = m_complete_value.copy();
            return;
        }

        try {
            for (int i = 0; i < m_component_count; i++)
                dyn_any.m_components.addElement(getComponent(i).copy());

        }
        catch (TypeMismatch tm) {
            throw new INTERNAL("Error with current_component");
        }
    }

    /**
     * Creates the DynAny at the position, read its value if exists, and insert
     * it in the component vector.
     */
    protected DynAny getComponent(int position)
        throws TypeMismatch
    {
        if (m_destroyed)
            throw new org.omg.CORBA.OBJECT_NOT_EXIST("DynArray destroyed.");

        if (position == -1)
            return null;

        int next_component_index = m_components.size();

        if (position > next_component_index)
            throw new INTERNAL("Component position out of sequence");

        if (position == next_component_index) { // initialize the component,

            // when the DynAny has been introduced in a any (with to_any()
            // operation),
            // prevents multihreaded writes (os.write_any() -> dyn_any._write()
            // ->
            // dyn_any.get_element(i)._write()

            synchronized (this) {
                if (position == m_components.size()) {
                    DynAnyBase dyn_component = null;

                    try {
                        dyn_component = (DynAnyBase)
                        	m_factory.create_dyn_any_from_type_code(
                        	    getComponentType(position));

                    }
                    catch (InconsistentTypeCode itc) {
                        throw new 
                        TypeMismatch("Inconsistent TypeCode of member: "
                                     + position
                                     + " . "
                                     + itc.toString()
                                     + " was thrown");
                    }

                    dyn_component.setUserDestroy(false); // component
                    // destruction

                    m_components.addElement(dyn_component);

                    if (m_next_value != null) { // demarshal the component
                        dyn_component._read(m_next_value);
                    }

                    if (position == (m_component_count - 1)) {
                        m_next_value = null;
                        m_complete_value = null;
                    }
                }
            }

        }

        return (DynAny) m_components.elementAt(position);
    }

    /**
     * Set or creates a new component at <code>position</code> with the given
     * value.
     */
    protected void setComponent(int position, Any value)
        throws TypeMismatch,
        InvalidValue
    {
        try {
            if (!getComponentType(position).equivalent(value.type()))
                throw new TypeMismatch("Unexpected value type in component "
                                       + position);

            int next_component_index = m_components.size();

            if (position > next_component_index)
                throw new INTERNAL("Component position out of sequence");

            if (position == next_component_index) { // create the component
                DynAny dyn_component = m_factory.create_dyn_any(value);

                m_components.addElement(dyn_component);
            } else {
                DynAny curr_dyn = (DynAny) 
                	m_components.elementAt(m_current_index);
                curr_dyn.from_any(value);
            }

        }
        catch (InconsistentTypeCode itc) {
            throw new TypeMismatch("Inconsistent TypeCode of member: "
                                   + m_current_index + " . " + itc.toString()
                                   + " was thrown");
        }
    }

    /**
     * Set or creates a new component at <code>position</code> with the given
     * value.
     */
    protected void setComponent(int position, DynAny value)
        throws TypeMismatch
    {

        if (!getComponentType(position).equivalent(value.type()))
            throw new TypeMismatch("Unexpedted value type in component "
                                   + position);

        int next_component_index = m_components.size();

        if (position > next_component_index)
            throw new INTERNAL("Component position out of sequence");

        if (position == next_component_index) { // create the component
            m_components.addElement(value);
        } else {
            ((DynAny) m_components.elementAt(position)).destroy();
            m_components.insertElementAt(value, m_current_index);
        }
    }

    // DynAny operations

    public void from_any(org.omg.CORBA.Any value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (!m_base_type.equivalent(value.type()))
            throw new TypeMismatch();

        reset();

        getStreams(value);
    }

    public org.omg.CORBA.Any to_any()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        Any new_any = m_orb.create_any();

        new_any.type(type());

        org.omg.CORBA.portable.OutputStream output =
            new_any.create_output_stream();

        _write(output);

        return new_any;
    }

    public void assign(org.omg.DynamicAny.DynAny dyn_any)
        throws TypeMismatch
    {
        preAssign(dyn_any);

        if (m_component_count == 0)
            return;

        int other_aux_current = m_component_count - 1;

        while (dyn_any.next())
            other_aux_current--;

        dyn_any.rewind();

        try {
            for (int i = 0; i < m_component_count; i++)
                setComponent(i, dyn_any.current_component().copy());

        }
        catch (TypeMismatch tm) {
            reset();
            dyn_any.seek(other_aux_current);
            throw tm;
        }

        m_current_index = 0;

        dyn_any.seek(other_aux_current);

    }

    public boolean equal(org.omg.DynamicAny.DynAny dyn_any)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (dyn_any == null)
            throw new BAD_PARAM("Null DynAny reference");

        if (!m_base_type.equivalent(dyn_any.type()))
            return false;

        if (m_component_count != dyn_any.component_count())
            return false;

        if (m_component_count == 0)
            return true;

        int aux_current = m_current_index;

        int other_aux_current = m_component_count - 1;

        try {
            while (dyn_any.next())
                other_aux_current--;

            dyn_any.rewind();

            for (int i = 0; i < m_component_count; i++) {
                if (!getComponent(i).equal(dyn_any.current_component())) {
                    dyn_any.seek(other_aux_current);
                    return false;
                }

                dyn_any.next();
            }
        }
        catch (TypeMismatch tm) {
            throw new INTERNAL(tm.toString());
        }
        finally {
            dyn_any.seek(other_aux_current);
        }

        return true;
    }

    public org.omg.CORBA.Any[] get_elements()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        Any[] any_array = new Any[m_component_count];

        if (m_component_count > 0) {
            try {
                for (int i = 0; i < m_component_count; i++) {
                    any_array[i] = getComponent(i).to_any();
                }
            }
            catch (TypeMismatch tm) {
                /* unreachable */
                throw new INTERNAL(tm.toString());
            }

        }

        return any_array;

    }

    protected void set_elements(org.omg.CORBA.Any[] value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value == null)
            throw new BAD_PARAM("null array reference");

        reset();

        if (value.length == 0)
            return;

        if (m_component_count != value.length)
            throw new 
                InvalidValue("Value length differs from component number");

        for (int i = 0; i < m_component_count; i++) {
            if (value[i] == null)
                throw new BAD_PARAM("null Any array element");

            setComponent(i, value[i]);
        }
    }

    protected org.omg.DynamicAny.DynAny[] get_elements_as_dyn_any()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        DynAny[] any_array = new DynAny[m_component_count];

        if (m_component_count > 0) {
            try {
                for (int i = 0; i < m_component_count; i++) {
                    any_array[i] = getComponent(i);
                }
            }
            catch (TypeMismatch tm) {
                /* unreachable */
                throw new INTERNAL(tm.toString());
            }
        }

        return any_array;
    }

    protected void set_elements_as_dyn_any(org.omg.DynamicAny.DynAny[] value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value == null)
            throw new BAD_PARAM("Null DynAny[] reference");

        reset();

        if (value.length == 0)
            return;

        if (m_component_count != value.length)
            throw new InvalidValue("Value length differs from members number");

        for (int i = 0; i < m_component_count; i++) {
            if (value[i] == null)
                throw new BAD_PARAM("null Any array element");

            setComponent(i, value[i]);
        }
    }

    public org.omg.DynamicAny.DynAny current_component()
        throws TypeMismatch
    {
        return getComponent(m_current_index);
    }

    public int component_count()
    {
        return m_component_count;
    }

    public boolean seek(int index)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (index < 0) {
            m_current_index = -1;
            return false;
        }

        if (m_component_count == 0)
            return false;

        if (m_component_count <= index)
            return false;

        m_current_index = 0;

        for (int i = 0; i < index; i++) {
            next();
        }

        return true;
    }

    public void rewind()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_component_count > 0)
            seek(0);
    }

    public boolean next()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_component_count <= (m_current_index + 1))
            return false;

        // if the current object has not been initialized, do it

        if (m_current_index == m_components.size())
            try {
                current_component();
            }
            catch (TypeMismatch tm) {
                return false;
            }

        // leaves the current component ready
        m_current_index++;
        return true;
    }

    public void insert_boolean(boolean value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_boolean))
            throw new TypeMismatch();

        current_component().insert_boolean(value);
    }

    public void insert_char(char value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_char))
            throw new TypeMismatch();

        current_component().insert_char(value);
    }

    public void insert_wchar(char value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_wchar))
            throw new TypeMismatch();

        current_component().insert_wchar(value);
    }

    public void insert_octet(byte value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_octet))
            throw new TypeMismatch();

        current_component().insert_octet(value);
    }

    public void insert_short(short value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_short))
            throw new TypeMismatch();

        current_component().insert_short(value);
    }

    public void insert_ushort(short value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_ushort))
            throw new TypeMismatch();

        current_component().insert_ushort(value);
    }

    public void insert_long(int value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_long))
            throw new TypeMismatch();

        current_component().insert_long(value);
    }

    public void insert_ulong(int value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_ulong))
            throw new TypeMismatch();

        current_component().insert_ulong(value);
    }

    public void insert_longlong(long value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_longlong))
            throw new TypeMismatch();

        current_component().insert_longlong(value);
    }

    public void insert_ulonglong(long value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_ulonglong))
            throw new TypeMismatch();

        current_component().insert_ulonglong(value);
    }

    public void insert_float(float value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_float))
            throw new TypeMismatch();

        current_component().insert_float(value);
    }

    public void insert_double(double value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_double))
            throw new TypeMismatch();

        current_component().insert_double(value);
    }

    public void insert_string(String value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_string))
            throw new TypeMismatch();

        current_component().insert_string(value);
    }

    public void insert_wstring(String value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_wstring))
            throw new TypeMismatch();

        current_component().insert_wstring(value);
    }

    public void insert_any(org.omg.CORBA.Any value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_any))
            throw new TypeMismatch();

        current_component().insert_any(value);
    }

    public void insert_dyn_any(org.omg.DynamicAny.DynAny value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_any))
            throw new TypeMismatch();

        current_component().insert_dyn_any(value);
    }

    public void insert_typecode(org.omg.CORBA.TypeCode value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
             .equivalent(TypeCodeFactory.tc_TypeCode))
            throw new TypeMismatch();

        current_component().insert_typecode(value);
    }

    public void insert_val(java.io.Serializable value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        current_component().insert_val(value);
    }

    public void insert_abstract(java.lang.Object value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // current_component().insert_abstract(value);
        ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
         .insert_abstract(value);
    }

    public void insert_reference(org.omg.CORBA.Object value)
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_objref))
            throw new TypeMismatch();

        current_component().insert_reference(value);
    }

    public boolean get_boolean()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_boolean))
            throw new TypeMismatch();

        return current_component().get_boolean();
    }

    public char get_char()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_char))
            throw new TypeMismatch();

        return current_component().get_char();
    }

    public char get_wchar()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_wchar))
            throw new TypeMismatch();

        return current_component().get_wchar();
    }

    public byte get_octet()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_octet))
            throw new TypeMismatch();

        return current_component().get_octet();
    }

    public short get_short()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_short))
            throw new TypeMismatch();

        return current_component().get_short();
    }

    public short get_ushort()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_ushort))
            throw new TypeMismatch();

        return current_component().get_ushort();
    }

    public int get_long()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_long))
            throw new TypeMismatch();

        return current_component().get_long();
    }

    public int get_ulong()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_ulong))
            throw new TypeMismatch();

        return current_component().get_ulong();
    }

    public long get_longlong()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_longlong))
            throw new TypeMismatch();

        return current_component().get_ulonglong();
    }

    public long get_ulonglong()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_ulonglong))
            throw new TypeMismatch();

        return current_component().get_ulonglong();
    }

    public float get_float()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_float))
            throw new TypeMismatch();

        return current_component().get_float();
    }

    public double get_double()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_double))
            throw new TypeMismatch();

        return current_component().get_double();
    }

    public String get_string()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_string))
            throw new TypeMismatch();

        return current_component().get_string();
    }

    public String get_wstring()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_wstring))
            throw new TypeMismatch();

        return current_component().get_wstring();
    }

    public org.omg.CORBA.Any get_any()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_any))
            throw new TypeMismatch();

        return current_component().get_any();
    }

    public org.omg.DynamicAny.DynAny get_dyn_any()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_any))
            throw new TypeMismatch();

        return current_component().get_dyn_any();
    }

    public org.omg.CORBA.TypeCode get_typecode()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_TypeCode))
            throw new TypeMismatch();

        return current_component().get_typecode();
    }

    public java.io.Serializable get_val()
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        return current_component().get_val();
    }

    public java.lang.Object get_abstract()
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
        throws TypeMismatch,
        InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        if (!getComponentType(m_current_index)
            .equivalent(TypeCodeFactory.tc_objref))
            throw new TypeMismatch();

        return current_component().get_reference();
    }

    // CORBA 2.5

    public void insert_boolean_seq(boolean[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value == null)
            throw new BAD_PARAM("null array reference");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // current_component().insert_boolean_seq(value);
        ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
         .insert_boolean_seq(value);
    }

    public void insert_octet_seq(byte[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value == null)
            throw new BAD_PARAM("null array reference");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // current_component().insert_octet_seq(value);
        ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
         .insert_octet_seq(value);
    }

    public void insert_char_seq(char[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value == null)
            throw new BAD_PARAM("null array reference");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // current_component().insert_char_seq(value);
        ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
         .insert_char_seq(value);
    }

    public void insert_short_seq(short[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value == null)
            throw new BAD_PARAM("null array reference");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // current_component().insert_short_seq(value);
        ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
         .insert_short_seq(value);
    }

    public void insert_ushort_seq(short[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value == null)
            throw new BAD_PARAM("null array reference");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // current_component().insert_ushort_seq(value);
        ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
         .insert_ushort_seq(value);
    }

    public void insert_long_seq(int[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value == null)
            throw new BAD_PARAM("null array reference");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // current_component().insert_long_seq(value);
        ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
         .insert_long_seq(value);
    }

    public void insert_ulong_seq(int[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value == null)
            throw new BAD_PARAM("null array reference");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // current_component().insert_ulong_seq(value);
        ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
         .insert_ulong_seq(value);
    }

    public void insert_float_seq(float[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value == null)
            throw new BAD_PARAM("null array reference");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // current_component().insert_float_seq(value);
        ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
         .insert_float_seq(value);
    }

    public void insert_double_seq(double[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value == null)
            throw new BAD_PARAM("null array reference");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // current_component().insert_double_seq(value);
        ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
         .insert_double_seq(value);
    }

    public void insert_longlong_seq(long[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value == null)
            throw new BAD_PARAM("null array reference");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // current_component().insert_longlong_seq(value);
        ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
         .insert_longlong_seq(value);
    }

    public void insert_ulonglong_seq(long[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value == null)
            throw new BAD_PARAM("null array reference");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // current_component().insert_ulonglong_seq(value);
        ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
         .insert_ulonglong_seq(value);
    }

    public void insert_wchar_seq(char[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (value == null)
            throw new BAD_PARAM("null array reference");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // current_component().insert_wchar_seq(value);
        ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
         .insert_wchar_seq(value);
    }

    public boolean[] get_boolean_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // return current_component().get_boolean_seq();
        return ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
                .get_boolean_seq();
    }

    public byte[] get_octet_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // return current_component().get_octet_seq();
        return ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
                .get_octet_seq();
    }

    public char[] get_char_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // return current_component().get_char_seq();
        return ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
                .get_char_seq();
    }

    public short[] get_short_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // return current_component().get_short_seq();
        return ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
                .get_short_seq();
    }

    public short[] get_ushort_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // return current_component().get_ushort_seq();
        return ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
                .get_ushort_seq();
    }

    public int[] get_long_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // return current_component().get_long_seq();
        return ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
                .get_long_seq();
    }

    public int[] get_ulong_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // return current_component().get_ulong_seq();
        return ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
                .get_ulong_seq();
    }

    public float[] get_float_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // return current_component().get_float_seq();
        return ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
                .get_float_seq();
    }

    public double[] get_double_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // return current_component().get_double_seq();
        return ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
                .get_double_seq();
    }

    public long[] get_longlong_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // return current_component().get_longlong_seq();
        return ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
                .get_longlong_seq();
    }

    public long[] get_ulonglong_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // return current_component().get_ulonglong_seq();
        return ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
                .get_ulonglong_seq();
    }

    public char[] get_wchar_seq()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST("DynAny destroyed.");

        if (m_current_index == -1)
            throw new InvalidValue("Current position is -1");

        // DynAny de J2SDK 1.4.1 no cumple CORBA 2.6
        // return current_component().get_wchar_seq();
        return ((es.tid.TIDorbj.dynAny.DynAnyBase) current_component())
                 .get_wchar_seq();
    }

    protected void insert_boolean_members(boolean[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value.length != m_component_count) {
            throw new InvalidValue("Invalid length");
        }

        reset();

        for (int i = 0; i < value.length; i++) {
            current_component().insert_boolean(value[i]);
            next();
        }

        rewind();
    }

    protected void insert_octet_members(byte[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value.length != m_component_count) {
            throw new InvalidValue("Invalid length");
        }

        reset();

        for (int i = 0; i < value.length; i++) {
            current_component().insert_octet(value[i]);
            next();
        }

        rewind();
    }

    protected void insert_char_members(char[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value.length != m_component_count) {
            throw new InvalidValue("Invalid length");
        }

        reset();

        for (int i = 0; i < value.length; i++) {
            current_component().insert_char(value[i]);
            next();
        }

        rewind();
    }

    protected void insert_short_members(short[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value.length != m_component_count) {
            throw new InvalidValue("Invalid length");
        }

        reset();

        for (int i = 0; i < value.length; i++) {
            current_component().insert_short(value[i]);
            next();
        }

        rewind();
    }

    protected void insert_ushort_members(short[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value.length != m_component_count) {
            throw new InvalidValue("Invalid length");
        }

        reset();

        for (int i = 0; i < value.length; i++) {
            current_component().insert_ushort(value[i]);
            next();
        }

        rewind();
    }

    protected void insert_long_members(int[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value.length != m_component_count) {
            throw new InvalidValue("Invalid length");
        }

        reset();

        for (int i = 0; i < value.length; i++) {
            current_component().insert_long(value[i]);
            next();
        }

        rewind();
    }

    protected void insert_ulong_members(int[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value.length != m_component_count) {
            throw new InvalidValue("Invalid length");
        }

        reset();

        for (int i = 0; i < value.length; i++) {
            current_component().insert_ulong(value[i]);
            next();
        }

        rewind();
    }

    protected void insert_float_members(float[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value.length != m_component_count) {
            throw new InvalidValue("Invalid length");
        }

        reset();

        for (int i = 0; i < value.length; i++) {
            current_component().insert_float(value[i]);
            next();
        }

        rewind();
    }

    protected void insert_double_members(double[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value.length != m_component_count) {
            throw new InvalidValue("Invalid length");
        }

        reset();

        for (int i = 0; i < value.length; i++) {
            current_component().insert_double(value[i]);
            next();
        }

        rewind();
    }

    protected void insert_longlong_members(long[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value.length != m_component_count) {
            throw new InvalidValue("Invalid length");
        }

        reset();

        for (int i = 0; i < value.length; i++) {
            current_component().insert_longlong(value[i]);
            next();
        }

        rewind();
    }

    protected void insert_ulonglong_members(long[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value.length != m_component_count) {
            throw new InvalidValue("Invalid length");
        }

        reset();

        for (int i = 0; i < value.length; i++) {
            current_component().insert_ulonglong(value[i]);
            next();
        }

        rewind();
    }

    protected void insert_wchar_members(char[] value)
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        if (value.length != m_component_count) {
            throw new InvalidValue("Invalid length");
        }

        reset();

        for (int i = 0; i < value.length; i++) {
            current_component().insert_wchar(value[i]);
            next();
        }

        rewind();
    }

    protected boolean[] get_boolean_members()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        boolean[] value = new boolean[m_component_count];

        if (m_component_count > 0) {
            for (int i = 0; i < m_component_count; i++) {
                value[i] = getComponent(i).get_boolean();
            }
        }

        return value;
    }

    protected byte[] get_octet_members()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        byte[] value = new byte[m_component_count];

        if (m_component_count > 0) {
            for (int i = 0; i < m_component_count; i++) {
                value[i] = getComponent(i).get_octet();
            }
        }

        return value;
    }

    protected char[] get_char_members()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        char[] value = new char[m_component_count];

        if (m_component_count > 0) {
            for (int i = 0; i < m_component_count; i++) {
                value[i] = getComponent(i).get_char();
            }
        }

        return value;
    }

    protected short[] get_short_members()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        short[] value = new short[m_component_count];

        if (m_component_count > 0) {
            for (int i = 0; i < m_component_count; i++) {
                value[i] = getComponent(i).get_short();
            }
        }

        return value;
    }

    protected short[] get_ushort_members()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        short[] value = new short[m_component_count];

        if (m_component_count > 0) {
            for (int i = 0; i < m_component_count; i++) {
                value[i] = getComponent(i).get_ushort();
            }
        }

        return value;
    }

    protected int[] get_long_members()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        int[] value = new int[m_component_count];

        if (m_component_count > 0) {
            for (int i = 0; i < m_component_count; i++) {
                value[i] = getComponent(i).get_long();
            }
        }

        return value;
    }

    protected int[] get_ulong_members()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        int[] value = new int[m_component_count];

        if (m_component_count > 0) {
            for (int i = 0; i < m_component_count; i++) {
                value[i] = getComponent(i).get_ulong();
            }
        }

        return value;
    }

    protected float[] get_float_members()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        float[] value = new float[m_component_count];

        if (m_component_count > 0) {
            for (int i = 0; i < m_component_count; i++) {
                value[i] = getComponent(i).get_float();
            }
        }

        return value;
    }

    protected double[] get_double_members()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        double[] value = new double[m_component_count];

        if (m_component_count > 0) {
            for (int i = 0; i < m_component_count; i++) {
                value[i] = getComponent(i).get_double();
            }
        }

        return value;
    }

    protected long[] get_longlong_members()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        long[] value = new long[m_component_count];

        if (m_component_count > 0) {
            for (int i = 0; i < m_component_count; i++) {
                value[i] = getComponent(i).get_longlong();
            }
        }

        return value;
    }

    protected long[] get_ulonglong_members()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        long[] value = new long[m_component_count];

        if (m_component_count > 0) {
            for (int i = 0; i < m_component_count; i++) {
                value[i] = getComponent(i).get_ulonglong();
            }
        }

        return value;
    }

    protected char[] get_wchar_members()
        throws org.omg.DynamicAny.DynAnyPackage.TypeMismatch,
        org.omg.DynamicAny.DynAnyPackage.InvalidValue
    {
        char[] value = new char[m_component_count];

        if (m_component_count > 0) {
            for (int i = 0; i < m_component_count; i++) {
                value[i] = getComponent(i).get_wchar();
            }
        }

        return value;
    }
}
