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
* (C) Copyright 2004 Telefnica Investigacin y Desarrollo
*     S.A.Unipersonal (Telefnica I+D)
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
package es.tid.TIDorbj.core;

import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.BAD_TYPECODE;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;

import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;
import es.tid.TIDorbj.core.typecode.TypeCodeDumper;
import es.tid.TIDorbj.core.typecode.TypeCodeFactory;
import es.tid.TIDorbj.core.typecode.TypeCodeMarshaler;
import es.tid.TIDorbj.core.util.AbstractInterfaceHolder;
import es.tid.TIDorbj.core.util.FixedHolder;
import es.tid.TIDorbj.core.util.StringHolder;
import es.tid.TIDorbj.core.util.ULongHolder;
import es.tid.TIDorbj.core.util.ULongLongHolder;
import es.tid.TIDorbj.core.util.UShortHolder;
import es.tid.TIDorbj.core.util.ValueHolder;
import es.tid.TIDorbj.core.util.WCharHolder;
import es.tid.TIDorbj.core.util.WStringHolder;

/**
 * TIDorb Any pseudobject implementation.
 * 
 * @author Juan A. C&aacute;ceres
 * @version 1.0
 */
public class AnyImpl extends es.tid.CORBA.Any
{

    /**
     * ORB where the any has been created. If <code>null</code> is a ORB
     * singleton.
     */

    TIDORB m_orb;

    protected TypeCode m_type;

    protected CDRInputStream m_marshaled_value;

    protected org.omg.CORBA.portable.Streamable m_value;

    protected org.omg.CORBA.Any m_wrapped_any;

    /**
     * The <code>Streamable</code> value has been inserted by the user with
     * <code>insert_Streamable()</code>
     */
    protected boolean m_user_value;

    public AnyImpl(TIDORB orb)
    {
        m_orb = orb;
        m_type = TypeCodeFactory.tc_null;
        if ((m_orb != null) && (m_orb.m_conf.exhaustive_equal)) { 
            ((es.tid.TIDorbj.core.typecode.TypeCodeImpl)m_type).setExhaustiveEqual(m_orb.m_conf.exhaustive_equal);   
        }
        m_marshaled_value = null;
        m_value = null;
        m_user_value = false;
        m_wrapped_any = null;
    }

    private synchronized void reset_value()
    {
        m_value = null;
        m_marshaled_value = null;
        m_user_value = false;
        m_wrapped_any = null;
    }

    protected boolean hasStreamable()
    {
        return m_value != null;
    }

    // asigns the new vaule and reads from the _marshaled value.
    private void initValue(org.omg.CORBA.portable.Streamable holder)
    {
        if (m_marshaled_value != null) {
            m_marshaled_value.rewind();
            m_value = holder;
            m_value._read(m_marshaled_value);
        } else {
            throw new BAD_OPERATION("Any without value.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }
    }

    public synchronized boolean equal(org.omg.CORBA.Any a)
    {
        if (a == null) {
            throw new BAD_PARAM("Null any reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }
        if (m_wrapped_any != null) {
            return m_wrapped_any.equal(a);
        }

        if (!m_type.equal(a.type())) {
            return false;
        }

        switch (m_type.kind().value())
        {
            case TCKind._tk_null:
            case TCKind._tk_void:
                return true;
            case TCKind._tk_short:
                return extract_short() == a.extract_short();
            case TCKind._tk_long:
                return extract_long() == a.extract_long();
            case TCKind._tk_longlong:
                return extract_longlong() == a.extract_longlong();
            case TCKind._tk_ushort:
                return extract_ushort() == a.extract_ushort();
            case TCKind._tk_ulong:
                return extract_ulong() == a.extract_ulong();
            case TCKind._tk_ulonglong:
                return extract_longlong() == a.extract_longlong();
            case TCKind._tk_float:
                return extract_float() == a.extract_float();
            case TCKind._tk_double:
                return extract_double() == a.extract_double();
            case TCKind._tk_boolean:
                return extract_boolean() == a.extract_boolean();
            case TCKind._tk_char:
                return extract_char() == a.extract_char();
            case TCKind._tk_wchar:
                return extract_wchar() == a.extract_wchar();
            case TCKind._tk_octet:
                return extract_octet() == a.extract_octet();
            case TCKind._tk_any:
                return extract_any().equal(a.extract_any());
            case TCKind._tk_objref:
            {
                org.omg.CORBA.Object obj_ref, obj_ref_a;
                obj_ref = extract_Object();
                obj_ref_a = a.extract_Object();
                if (obj_ref == null) {
                    if (obj_ref_a == null) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return obj_ref._is_equivalent(obj_ref_a);
                }
            }
            case TCKind._tk_string:
                try {
                    if (m_type.length() == 0) { //unbounded string
                        return extract_string().equals(a.extract_string());
                    }
                }
                catch (BadKind bk) {
                    throw new BAD_TYPECODE();
                }
            // bounded string
            break;
            case TCKind._tk_wstring:
                try {
                    if (m_type.length() == 0) { //unbounded string
                        return extract_wstring().equals(a.extract_wstring());
                    }
                }
                catch (BadKind bk) {
                    throw new BAD_TYPECODE();
                }
            // bounded string
            break;
            case TCKind._tk_TypeCode:
                return extract_TypeCode().equal(a.extract_TypeCode());
            case TCKind._tk_fixed:
                return extract_fixed().equals(a.extract_fixed());
        }

        return TypeCodeMarshaler.valuesEqual(m_type, create_input_stream(),
                                             a.create_input_stream());
    }

    protected synchronized void wrapValue(org.omg.CORBA.Any a)
    {
        reset_value();
        m_wrapped_any = a;
    }

    public static void assign(org.omg.CORBA.Any from, org.omg.CORBA.Any to)
    {
        assign(from, to, false);
    }

    public static void assign(org.omg.CORBA.Any from, org.omg.CORBA.Any to,
                              boolean wrap)
    {
        if ((from == null) || (to == null)) {
            throw new BAD_PARAM("Null any reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        TypeCode type = from.type();

        switch (type.kind().value())
        {
            case TCKind._tk_null:
            case TCKind._tk_void:
                to.type(type);
                return;
            case TCKind._tk_short:
                to.insert_short(from.extract_short());
                return;
            case TCKind._tk_long:
                to.insert_long(from.extract_long());
                return;
            case TCKind._tk_longlong:
                to.insert_longlong(from.extract_longlong());
                return;
            case TCKind._tk_ushort:
                to.insert_ushort(from.extract_ushort());
                return;
            case TCKind._tk_ulong:
                to.insert_ulong(from.extract_ulong());
                return;
            case TCKind._tk_ulonglong:
                to.insert_ulonglong(from.extract_ulonglong());
                return;
            case TCKind._tk_float:
                to.insert_float(from.extract_float());
                return;
            case TCKind._tk_double:
                to.insert_double(from.extract_double());
                return;
            case TCKind._tk_boolean:
                to.insert_boolean(from.extract_boolean());
                return;
            case TCKind._tk_char:
                to.insert_char(from.extract_char());
                return;
            case TCKind._tk_wchar:
                to.insert_wchar(from.extract_wchar());
                return;
            case TCKind._tk_octet:
                to.insert_octet(from.extract_octet());
                return;
            case TCKind._tk_any:
                to.insert_any(from.extract_any());
                return;
            case TCKind._tk_objref:
                to.insert_Object(from.extract_Object());
                return;
            case TCKind._tk_value:
                to.insert_Value(from.extract_Value(), from.type());
                return;

            case TCKind._tk_string:
                try {
                    if (type.length() == 0) { //unbounded string
                        to.insert_string(from.extract_string());
                        return;
                    }
                }
                catch (BadKind bk) {
                    throw new BAD_TYPECODE();
                }
            // bounded string
            break;
            case TCKind._tk_wstring:
                try {
                    if (type.length() == 0) { //unbounded string
                        to.insert_wstring(from.extract_wstring());
                        return;
                    }
                }
                catch (BadKind bk) {
                    throw new BAD_TYPECODE();
                }
            // bounded string
            break;
            case TCKind._tk_TypeCode:
                to.insert_TypeCode(from.extract_TypeCode());
                return;
            case TCKind._tk_fixed:
                to.insert_fixed(from.extract_fixed(), type);
                return;
        }

        // while JDKs API Any had no extract_Streamable()
        if (from instanceof AnyImpl) {
            AnyImpl tidorb_from = (AnyImpl) from;
            if (tidorb_from.hasStreamable()) {
                to.insert_Streamable(tidorb_from.extract_Streamable());
                return;
            }
        }

        // Only wraps complex types

        if (wrap && (to instanceof AnyImpl)) {
            ((AnyImpl) to).wrapValue(from);
            return;
        }

        to.read_value(from.create_input_stream(), type);
    }

    public static void assignValue(org.omg.CORBA.Any from, org.omg.CORBA.Any to)
    {
        assignValue(from, to, false);
    }

    public static void assignValue(org.omg.CORBA.Any from,
                                   org.omg.CORBA.Any to, boolean wrap)
    {
        if ((from == null) || (to == null)) {
            throw new BAD_PARAM("Null any reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        if (!to.type().equivalent(from.type()))
            throw new BAD_OPERATION("No Equivalent types.", 0,
                                    CompletionStatus.COMPLETED_NO);

        assign(from, to, wrap);
    }

    public org.omg.CORBA.TypeCode type()
    {
        return m_type;
    }

    /**
     * Changes the any typecode, is needed to minizimie overhead in any creation
     * of alias types: first create the any, and then change the typecode.
     */

    public synchronized void setEquivalentType(TypeCode type)
    {
        if (!m_type.equivalent(type)) {
            throw new BAD_OPERATION("Types are not equivalent.", 0,
                                    CompletionStatus.COMPLETED_NO);
        } else {
            m_type = type;
        }
    }

    public synchronized void type(org.omg.CORBA.TypeCode type)
    {
        if (type == null) {
            throw new BAD_PARAM("Null TypeCode reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        m_type = type;
        reset_value();
    }

    public synchronized void read_value(org.omg.CORBA.portable.InputStream is,
                                        org.omg.CORBA.TypeCode type)
    {
        if ((is == null) || (type == null)) {
            throw new BAD_PARAM("Null reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        // basic types are read directly from the stream

        switch (type.kind().value())
        {
            case TCKind._tk_null:
            case TCKind._tk_void:
                this.type(type);
                return;
            case TCKind._tk_short:
                insert_short(is.read_short());
                return;
            case TCKind._tk_long:
                insert_long(is.read_long());
                return;
            case TCKind._tk_longlong:
                insert_longlong(is.read_longlong());
                return;
            case TCKind._tk_ushort:
                insert_ushort(is.read_ushort());
                return;
            case TCKind._tk_ulong:
                insert_ulong(is.read_ulong());
                return;
            case TCKind._tk_ulonglong:
                insert_ulonglong(is.read_ulonglong());
                return;
            case TCKind._tk_float:
                insert_float(is.read_float());
                return;
            case TCKind._tk_double:
                insert_double(is.read_double());
                return;
            case TCKind._tk_boolean:
                insert_boolean(is.read_boolean());
                return;
            case TCKind._tk_char:
                insert_char(is.read_char());
                return;
            case TCKind._tk_wchar:
                insert_wchar(is.read_wchar());
                return;
            case TCKind._tk_octet:
                insert_octet(is.read_octet());
                return;
            case TCKind._tk_any:
                insert_any(is.read_any());
                return;
            case TCKind._tk_fixed: //special case: fixed values
            {
                FixedHolder fixed_holder = new FixedHolder(m_type);
                fixed_holder._read(is);
                insert_Streamable(fixed_holder);
                return;
            }
            case TCKind._tk_value:
            case TCKind._tk_value_box:
            {
                ValueHolder value_holder = new ValueHolder(m_type);
                value_holder._read(is);
                insert_Streamable(value_holder);
                return;
            }
            case TCKind._tk_abstract_interface:
            {

                AbstractInterfaceHolder interface_holder = 
                    new AbstractInterfaceHolder(m_type);
                interface_holder._read(is);
                insert_Streamable(interface_holder);
                return;
            }
        }

        this.type(type);

        if (is instanceof CDRInputStream) {
            // the value is not a streamable value
            m_marshaled_value = ((CDRInputStream) is).copy();

            // saves the actual position
            m_marshaled_value.fixStarting();

            // set the input stream to the end of the value
            TypeCodeMarshaler.skipValue(m_type, (CDRInputStream) is);
        } else {
            // I only can remarshal the value in my own buffer
            CDROutputStream out = null;

            if (m_orb != null) { // orbsingleton
                out = (CDROutputStream) m_orb.create_output_stream();
            } else {
                out = new CDROutputStream(null, ConfORB.DEFAULT_BLOCK_SIZE);
            }

            TypeCodeMarshaler.remarshalValue(m_type, is, out);
            m_marshaled_value = (CDRInputStream) out.create_input_stream();
        }
    }

    public synchronized void write_value(org.omg.CORBA.portable.OutputStream os)
    {
        if (os == null) {
            throw new BAD_PARAM("Null OutputStream reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        if (m_wrapped_any != null) {
            m_wrapped_any.write_value(os);
            return;
        }

        int kind = m_type.kind().value();

        if ((kind == TCKind._tk_null) || (kind == TCKind._tk_void)) {
            return;
        } else if (m_value != null) {
            m_value._write(os);
        } else if (m_marshaled_value != null) {
            CDRInputStream _marshaled_value_copy = m_marshaled_value.copy();
            _marshaled_value_copy.rewind();
            TypeCodeMarshaler.remarshalValue(m_type, _marshaled_value_copy, os);
        } else {
            throw new BAD_OPERATION("Uncompleted Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }
    }

    public synchronized org.omg.CORBA.portable.OutputStream create_output_stream()
    {
    	org.omg.CORBA.portable.OutputStream out = null;
    	
        if (m_orb == null) {
            out =  new CDROutputStream(null, ConfORB.DEFAULT_BLOCK_SIZE);
        } else {
            out = m_orb.create_output_stream();
        }
        m_marshaled_value = (CDRInputStream) out.create_input_stream();

        return out;
    }

    public synchronized org.omg.CORBA.portable.InputStream create_input_stream()
    {
        if (m_wrapped_any != null) {
            return m_wrapped_any.create_input_stream();
        }

        if (m_marshaled_value == null) {
            createMarshaledValue();
        }

        CDRInputStream new_stream = m_marshaled_value.copy();
        new_stream.rewind();
        return new_stream;
    }

    protected void createMarshaledValue()
    {
        if (m_marshaled_value != null)
            return;

        int kind = m_type.kind().value();

        if ((kind == TCKind._tk_null) || (kind == TCKind._tk_void)) {
            m_marshaled_value = new CDRInputStream(m_orb, new byte[0]);
            return;
        }

        if (m_value != null) {
            CDROutputStream output;

            if (m_orb == null) {
                output = new CDROutputStream(null, ConfORB.DEFAULT_BLOCK_SIZE);
            } else {
                output = (CDROutputStream) m_orb.create_output_stream();
            }
            m_value._write(output);
            m_marshaled_value = (CDRInputStream) output.create_input_stream();
        } else {
            throw new BAD_OPERATION("Any without value.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }
    }

    public synchronized short extract_short()
    {
        if (m_type.kind().value() != TCKind._tk_short) {
            throw new BAD_OPERATION("No short in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }
        try {
            return ((org.omg.CORBA.ShortHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_short();
        }
    }

    public synchronized void insert_short(short s)
    {
        m_value = new org.omg.CORBA.ShortHolder(s);
        m_type = m_value._type();
    }

    public synchronized int extract_long()
    {
        if (m_type.kind().value() != TCKind._tk_long) {
            throw new BAD_OPERATION("No long in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }

        // the value is marshaled in the inputstream

        if (m_value == null) {
            initValue(new org.omg.CORBA.IntHolder());
        }

        try {
            return ((org.omg.CORBA.IntHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_long();
        }
    }

    public synchronized void insert_long(int i)
    {
        reset_value();
        m_value = new org.omg.CORBA.IntHolder(i);
        m_type = m_value._type();
    }

    public synchronized long extract_longlong()
    {
        if (m_type.kind().value() != TCKind._tk_longlong) {
            throw new BAD_OPERATION("No longlong in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }

        // the value is marshaled in the inputstream
        if (m_value == null) {
            initValue(new org.omg.CORBA.LongHolder());
        }

        try {
            return ((org.omg.CORBA.LongHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_longlong();
        }
    }

    public synchronized void insert_longlong(long l)
    {
        reset_value();
        m_value = new org.omg.CORBA.LongHolder(l);
        m_type = m_value._type();
    }

    public synchronized short extract_ushort()
    {
        if (m_type.kind().value() != TCKind._tk_ushort) {
            throw new BAD_OPERATION("No ushort in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }

        // the value is marshaled in the inputstream
        if (m_value == null)
            initValue(new UShortHolder());

        try {
            return ((UShortHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_ushort();
        }
    }

    public synchronized void insert_ushort(short s)
    {
        reset_value();
        m_value = new UShortHolder(s);
        m_type = TypeCodeFactory.tc_ushort;
    }

    public synchronized int extract_ulong()
    {
        if (m_type.kind().value() != TCKind._tk_ulong) {
            throw new BAD_OPERATION("No ulong in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }

        // the value is marshaled in the inputstream

        if (m_value == null)
            initValue(new ULongHolder());

        try {
            return ((ULongHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_ulong();
        }
    }

    public synchronized void insert_ulong(int i)
    {
        reset_value();
        m_value = new ULongHolder(i);
        m_type = TypeCodeFactory.tc_ulong;
    }

    public synchronized long extract_ulonglong()
    {
        if (m_type.kind().value() != TCKind._tk_ulonglong) {
            throw new BAD_OPERATION("No ulonglong in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }

        // the value is marshaled in the inputstream

        if (m_value == null)
            initValue(new ULongLongHolder());
        try {
            return ((ULongLongHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_ulonglong();
        }

    }

    public synchronized void insert_ulonglong(long l)
    {
        reset_value();
        m_value = new ULongLongHolder(l);
        m_type = TypeCodeFactory.tc_ulonglong;
    }

    public synchronized float extract_float()
    {
        if (m_type.kind().value() != TCKind._tk_float) {
            throw new BAD_OPERATION("No float in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }

        // the value is marshaled in the inputstream

        if (m_value == null)
            initValue(new org.omg.CORBA.FloatHolder());
        try {
            return ((org.omg.CORBA.FloatHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_float();
        }
    }

    public synchronized void insert_float(float f)
    {
        reset_value();
        m_value = new org.omg.CORBA.FloatHolder(f);
        m_type = m_value._type();
    }

    public synchronized double extract_double()
    {
        if (m_type.kind().value() != TCKind._tk_double)
            throw new BAD_OPERATION("No float in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);

        // the value is marshaled in the inputstream

        if (m_value == null)
            initValue(new org.omg.CORBA.DoubleHolder());

        try {
            return ((org.omg.CORBA.DoubleHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_double();
        }
    }

    public synchronized void insert_double(double d)
    {
        reset_value();
        m_value = new org.omg.CORBA.DoubleHolder(d);
        m_type = m_value._type();
    }

    public synchronized boolean extract_boolean()
    {
        if (m_type.kind().value() != TCKind._tk_boolean) {
            throw new BAD_OPERATION("No boolean in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }
        // the value is marshaled in the inputstream

        if (m_value == null)
            initValue(new org.omg.CORBA.BooleanHolder());

        try {
            return ((org.omg.CORBA.BooleanHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_boolean();
        }
    }

    public synchronized void insert_boolean(boolean b)
    {
        reset_value();
        m_value = new org.omg.CORBA.BooleanHolder(b);
        m_type = m_value._type();
    }

    public synchronized char extract_char()
    {
        if (m_type.kind().value() != TCKind._tk_char) {
            throw new BAD_OPERATION("No char in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }
        // the value is marshaled in the inputstream

        if (m_value == null)
            initValue(new org.omg.CORBA.CharHolder());

        try {
            return ((org.omg.CORBA.CharHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_char();
        }

    }

    public synchronized void insert_char(char c)
    {
        reset_value();
        m_value = new org.omg.CORBA.CharHolder(c);
        m_type = m_value._type();
    }

    public synchronized char extract_wchar()
    {
        if (m_type.kind().value() != TCKind._tk_wchar) {
            throw new BAD_OPERATION("No wchar in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }
        // the value is marshaled in the inputstream

        if (m_value == null)
            initValue(new WCharHolder());
        try {
            return ((WCharHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_wchar();
        }
    }

    public synchronized void insert_wchar(char c)
    {
        reset_value();
        m_value = new WCharHolder(c);
        m_type = m_value._type();
    }

    public synchronized byte extract_octet()
    {
        if (m_type.kind().value() != TCKind._tk_octet) {
            throw new BAD_OPERATION("No octet in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }
        // the value is marshaled in the inputstream

        if (m_value == null)
            initValue(new org.omg.CORBA.ByteHolder());

        try {
            return ((org.omg.CORBA.ByteHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_octet();
        }

    }

    public synchronized void insert_octet(byte b)
    {
        reset_value();
        m_value = new org.omg.CORBA.ByteHolder(b);
        m_type = m_value._type();
    }

    public synchronized org.omg.CORBA.Any extract_any()
    {
        if (m_type.kind().value() != TCKind._tk_any) {
            throw new BAD_OPERATION("No any in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }
        // the value is marshaled in the inputstream

        if (m_value == null)
            initValue(new org.omg.CORBA.AnyHolder());

        try {
            return ((org.omg.CORBA.AnyHolder) m_value).value;

        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_any();
        }
    }

    public synchronized void insert_any(org.omg.CORBA.Any a)
    {
        if (a == null) {
            throw new BAD_PARAM("Null any reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }
        reset_value();
        m_value = new org.omg.CORBA.AnyHolder(a);
        m_type = TypeCodeFactory.tc_any;
    }

    public synchronized org.omg.CORBA.TypeCode extract_TypeCode()
    {
        if (m_type.kind().value() != TCKind._tk_TypeCode) {
            throw new BAD_OPERATION("No TypeCode in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }

        // the value is marshaled in the inputstream

        if (m_value == null)
            initValue(new org.omg.CORBA.TypeCodeHolder());

        try {
            return ((org.omg.CORBA.TypeCodeHolder) m_value).value;

        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_TypeCode();
        }
    }

    public synchronized void insert_TypeCode(org.omg.CORBA.TypeCode t)
    {
        if (t == null) {
            throw new BAD_PARAM("Null TypeCode reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        reset_value();
        m_value = new org.omg.CORBA.TypeCodeHolder(t);
        m_type = m_value._type();
    }

    public synchronized String extract_string()
    {
        try {
            if ((m_type.kind().value() != TCKind._tk_string)
                || (m_type.length() != 0)) {
                throw new BAD_OPERATION("No unbounded string in Any.", 0,
                                        CompletionStatus.COMPLETED_NO);
            }
        }
        catch (BadKind bk) {
            throw new BAD_TYPECODE();
        }

        // the value is marshaled in the inputstream

        if (m_value == null)
            initValue(new StringHolder(m_type));

        try {

            return ((StringHolder) m_value).value;

        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_string();
        }
    }

    public synchronized void insert_string(String s)
    {
        if (s == null) {
            throw new BAD_PARAM("Null String reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }
        reset_value();
        m_value = new org.omg.CORBA.StringHolder(s);
        m_type = m_value._type();
    }

    public synchronized String extract_wstring()
    {
        if (m_type.kind().value() != TCKind._tk_wstring) {
            throw new BAD_OPERATION("No wstring in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }
        // the value is marshaled in the inputstream

        if (m_value == null)
            initValue(new WStringHolder(m_type));

        try {
            return ((WStringHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_wstring();
        }
    }

    public synchronized void insert_wstring(String s)
    {
        if (s == null) {
            throw new BAD_PARAM("Null String reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        reset_value();
        m_value = new WStringHolder(s);
        m_type = m_value._type();
    }

    public synchronized java.math.BigDecimal extract_fixed()
    {
        if (m_type.kind().value() != TCKind._tk_fixed) {
            throw new BAD_OPERATION("No fixed in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }
        // the value is marshaled in the inputstream

        if (m_value == null)
            initValue(new FixedHolder(m_type));

        try {
            return ((FixedHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            throw new BAD_OPERATION("Can not obtain fixed", 0,
                                    CompletionStatus.COMPLETED_NO);
        }
    }

    public synchronized void insert_fixed(java.math.BigDecimal f,
                                          org.omg.CORBA.TypeCode t)
        throws BAD_INV_ORDER
    {
        if ((f == null) || (t == null)) {
            throw new BAD_PARAM("Null reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        reset_value();
        m_value = new FixedHolder(t, f);
        m_type = t;
    }

    /**
     * @ deprecated
     */
    public org.omg.CORBA.Principal extract_Principal()
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * @ deprecated
     */
    public void insert_Principal(org.omg.CORBA.Principal p)
    {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public synchronized org.omg.CORBA.portable.Streamable extract_Streamable()
    {
    	//if(!m_type.equivalent(m_value._type()))
        //    System.out.println("AnyImpl.extract_Streamable no equivalente");
        if (m_user_value) {
            if (m_value != null) {
                return m_value;
            } else {
                throw new org.omg.CORBA.INTERNAL("No Streamable in Any.");
            }
        } else {
            throw new BAD_INV_ORDER("No Streamable in Any.");
        }
    }

    public synchronized void 
    	insert_Streamable(org.omg.CORBA.portable.Streamable s)
    {
        if (s == null) {
            throw new BAD_PARAM("Null Streamable reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }
        //if(!m_type.equivalent(s._type()))
        //    System.out.println("AnyImpl.insert_Streamable no equivalente");
        reset_value();
        m_user_value = true;
        m_value = s;
        m_type = m_value._type();
    }

    public synchronized org.omg.CORBA.Object extract_Object()
    {
        if (m_type.kind().value() != TCKind._tk_objref) {
            throw new BAD_OPERATION("No Object in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }

        // the value is marshaled in the inputstream

        if (m_value == null)
            initValue(new org.omg.CORBA.ObjectHolder());

        try {
            return ((org.omg.CORBA.ObjectHolder) m_value).value;
        }
        catch (ClassCastException cce) { // there is another holder
            createMarshaledValue();
            m_marshaled_value.rewind();
            return m_marshaled_value.read_Object();
        }
    }

    public synchronized void insert_Object(org.omg.CORBA.Object obj)
    {
        if (obj instanceof org.omg.CORBA.LocalObject) {
            throw new MARSHAL("Impossible to marshall a local object.", 4,
                              CompletionStatus.COMPLETED_NO);
        }

        reset_value();
        m_value = new org.omg.CORBA.ObjectHolder(obj);
        m_type = m_value._type();
    }

    public synchronized void insert_Object(org.omg.CORBA.Object obj,
                                           org.omg.CORBA.TypeCode type)
    {
        if (type == null) {
            throw new BAD_PARAM("Null reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }
        reset_value();
        m_value = new org.omg.CORBA.ObjectHolder(obj);
        m_type = type;
    }

    public synchronized java.io.Serializable extract_Value()
    {
        int kind = m_type.kind().value();

        if ((kind != TCKind._tk_value) && (kind != TCKind._tk_value_box)) {
            throw new BAD_OPERATION("No Value in Any.", 0,
                                    CompletionStatus.COMPLETED_NO);
        }
        // the value is marshaled in the inputstream

        if (m_value != null) {
            if (m_value instanceof ValueHolder) {

                java.io.Serializable js = ((ValueHolder) m_value).value;

                return js;
            } else { // there is another holder
                createMarshaledValue();
            }
        }

        if (m_marshaled_value != null) {
            m_marshaled_value.rewind();
            return m_marshaled_value.read_value();
        }

        throw new BAD_OPERATION("Any without value");
    }

    public synchronized void insert_Value(java.io.Serializable v)
    {
        reset_value();

        if (v instanceof org.omg.CORBA.portable.StreamableValue) {
            org.omg.CORBA.portable.StreamableValue val = 
                (org.omg.CORBA.portable.StreamableValue) v;

            insert_Streamable(new ValueHolder(val._type(), val));
            m_type = val._type();
            return;
        } else {
            throw new BAD_PARAM("It is not StreamableValue");
        }
    }

    public synchronized void insert_Value(java.io.Serializable v,
                                          org.omg.CORBA.TypeCode t)
    {
        if (t == null) {
            throw new BAD_PARAM("Null TypeCode reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        int kind = t.kind().value();

        if ((kind == TCKind._tk_value) || (kind == TCKind._tk_value_box)) {
            insert_Streamable(new ValueHolder(t, v));
            m_type = t;
        } else {
            throw new BAD_OPERATION();
        }
    }

    public static boolean dump(org.omg.CORBA.Any any,
                               java.io.PrintWriter output)
        throws java.io.IOException
    {
        if ((any == null) || (output == null)) {
            throw new BAD_PARAM("Null reference.", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        output.print("[ANY]");
        TypeCodeDumper.dump(any.type(), output);

        org.omg.CORBA.portable.InputStream input = any.create_input_stream();

        return TypeCodeDumper.dumpValue(any.type(), input, output);
    }

}
