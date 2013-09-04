//
// Repository.java (interfaceOperations)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public interface RepositoryOperations
   extends org.omg.CORBA.ContainerOperations {

  org.omg.CORBA.Contained lookup_id(java.lang.String search_id);

  org.omg.CORBA.TypeCode get_canonical_typecode(org.omg.CORBA.TypeCode tc);

  org.omg.CORBA.PrimitiveDef get_primitive(org.omg.CORBA.PrimitiveKind kind);

  org.omg.CORBA.StringDef create_string(int bound);

  org.omg.CORBA.WstringDef create_wstring(int bound);

  org.omg.CORBA.SequenceDef create_sequence(int bound, org.omg.CORBA.IDLType element_type);

  org.omg.CORBA.ArrayDef create_array(int length, org.omg.CORBA.IDLType element_type);

  org.omg.CORBA.FixedDef create_fixed(short digits, short scale);


}
